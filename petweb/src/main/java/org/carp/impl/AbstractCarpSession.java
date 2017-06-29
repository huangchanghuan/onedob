/**
 * Copyright 2009-2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.carp.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;

import org.carp.CarpDataSetQuery;
import org.carp.CarpQuery;
import org.carp.CarpSession;
import org.carp.engine.event.Event;
import org.carp.engine.event.FindEvent;
import org.carp.engine.event.MapEvent;
import org.carp.engine.event.SaveEvent;
import org.carp.engine.event.UpdateEvent;
import org.carp.exception.CarpException;
import org.carp.factory.CarpEventFactory;
import org.carp.intercept.Interceptor;
import org.carp.jdbc.ConnectionProvider;
import org.carp.jdbc.JDBCContext;
import org.carp.transaction.Transaction;

/**
 * 数据库连接会话类
 * @author Administrator
 *
 */
public abstract class AbstractCarpSession implements CarpSession{
	private JDBCContext jdbcContext;
	private PreparedStatement ps = null;
	private String sql;
	private Transaction tx ;
	private List<Object> updateList ;
	private Interceptor interceptor;
	
	/**
	 * 
	 * @param provider
	 * @throws CarpException
	 */
	public AbstractCarpSession(ConnectionProvider provider) throws CarpException{
		this(provider,null);
	}
	
	/**
	 * 
	 * @param provider
	 * @param interceptor
	 * @throws CarpException
	 */
	public AbstractCarpSession(ConnectionProvider provider,Interceptor interceptor) throws CarpException{
		this.jdbcContext = new JDBCContext(provider);
		this.interceptor = interceptor;
	}
	
	/**
	 * 获取连接
	 */
	public Connection getConnection() throws CarpException{
		try {
			return this.jdbcContext.getConnection();
		} catch (Exception e) {
			throw new CarpException("不能取得数据库连接!",e);
		}
	}

	/**
	 * 根据一个对象删除一条记录
	 * @param obj
	 * @return
	 * @throws CarpException
	 */
	public void delete(Object obj) throws CarpException {
		this.delete(null, obj);
	}
	
	/**
	 * @param cls
	 * @param id
	 * @return
	 * @throws CarpException
	 */
	public void delete(Class<?> cls, Serializable id) throws CarpException{
		Object obj = id;
		this.delete(cls, obj);
	}
	
	/**
	 * 
	 * @param cls
	 * @param key
	 * @return
	 * @throws CarpException
	 */
	public void delete(Class<?> cls, Map<String, Object> key) throws CarpException{
		Object obj = key;
		this.delete(cls, obj);
	}
	
	private void delete(Class<?> cls, Object obj) throws CarpException{
		if(!isOpen())
			throw new CarpException("Connection could not used！could not execute delete");
		try{
			this.jdbcContext.isCommit();
			CarpEventFactory.deleteEvent(this, cls, obj).execute();
//			CarpEvent event = CarpEventFactory.createDeleteEvent(this,cls, obj);
//			event.execute();
		}catch(Exception ex){
			throw new CarpException("delete failed."+obj,ex);
		}
	}

	public Object get(Class<?> cls, Serializable id)throws CarpException {
		Object key = id;
		return this.get(cls, key);
	}
	
	public Object get(Class<?> cls, Map<String, Object> key)throws CarpException {
		Object values = key;
		return this.get(cls, values);
	}
	
	private Object get(Class<?> cls, Object key)throws CarpException {
		try{
			Event event = new FindEvent(this,cls,key);
			event.execute();
			return event.getEntity();//new CarpLoadProcessor(this,cls,key).get();
		}catch(Exception ex){
			throw new CarpException(ex);
		}
	}

	public Transaction beginTransaction() throws CarpException {
		try {
			tx = this.jdbcContext.getTransaction();
			tx.begin();
			return tx;
		} catch (Exception e) {
			throw new CarpException(e);
		}
	}

	public boolean isOpen() {
		try{
			return !this.jdbcContext.isClose();
		}catch(Exception ex){
			return false;
		}
	}
	
	public Serializable save(Object obj)throws CarpException {
		if(!isOpen())
			throw new CarpException("connection was closed,could not execute save!");
		try{
			this.jdbcContext.isCommit();
			Event event = new SaveEvent(this,obj);
			event.execute();
			return event.getPrimaryValue();
		}catch(Exception ex){
			ex.printStackTrace();
			throw new CarpException("保存对象到数据库失败！",ex);
		}
	}
	
	public void save(String table, Map<String,Object> map)throws CarpException {
		if(!isOpen())
			throw new CarpException("connection was closed,could not execute save!");
		try{
			this.jdbcContext.isCommit();
			new MapEvent(this,table,map).execute();
		}catch(Exception ex){
			throw new CarpException("failed!",ex);
		}
	}

	public void update(Object obj) throws CarpException {
		if(!isOpen())
			throw new CarpException("Connection could not used！could not execute udate");
		try{
			this.jdbcContext.isCommit();
			new UpdateEvent(this,obj).execute();
//			CarpEventFactory.createUpdateEvent(this, obj).execute();
		}catch(Exception ex){
			throw new CarpException("update failed."+obj,ex);
		}
	}
	
	public void close() throws CarpException {
		try{
			if(this.jdbcContext.isClose())
				throw new CarpException("Session closed!");
			if(ps!=null)
				ps.close();
			ps = null;
			this.jdbcContext.close();
		}catch(Exception ex){
			throw new CarpException("close sesssion failed！",ex);
		}
	}

	public CarpDataSetQuery creatDataSetQuery(String sql) throws CarpException{
		if(!isOpen())
			throw new CarpException("Connection could not used！could not create Query");
		return new CarpQueryImpl(this,sql);//CarpQueryFactory.createCarpQueryWithSql(this, sql);
	}
	
	public CarpQuery creatUpdateQuery(String sql) throws CarpException{
		return this.creatQuery(null, sql);
	}
	
	public CarpQuery creatQuery(Class<?> cls) throws CarpException{
		return this.creatQuery(cls, null);
	}
	
	public CarpQuery creatQuery(Class<?> cls,String sql) throws CarpException{
		if(!isOpen())
			throw new CarpException("Connection can't used！could not create Query");
		try{
			return new CarpQueryImpl(this,cls,sql);//CarpQueryFactory.createCarpQuery(this, cls, sql);
		}catch(Exception ex){
			throw new CarpException("query failed！",ex);
		}
	}
	
	public List<Object> getUpdateObjects(){
		return updateList;
	}
	public void clearUpdateObjects(){
		if(updateList != null)
			updateList.clear();
	}

	

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public PreparedStatement getPs() {
		return ps;
	}

	public void setPs(PreparedStatement ps) {
		this.ps = ps;
	}



	public JDBCContext getJdbcContext() {
		return jdbcContext;
	}



	public Interceptor getInterceptor() {
		return interceptor;
	}
}
