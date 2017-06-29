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

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.carp.CarpDataSetQuery;
import org.carp.CarpQuery;
import org.carp.DataSet;
import org.carp.engine.exec.BatchExecutor;
import org.carp.engine.exec.CarpQueryExecutor;
import org.carp.engine.exec.DatasetQueryExecutor;
import org.carp.engine.exec.QueryExecutor;
import org.carp.engine.exec.UpdateExecutor;
import org.carp.exception.CarpException;
import org.carp.parameter.Parameter;
import org.carp.sql.CarpSql;

/**
 * 锟斤拷询锟洁，执锟叫诧拷询锟斤拷锟斤拷
 * @author Administrator
 *
 */
public abstract class AbstractCarpQuery implements CarpQuery,CarpDataSetQuery{
	private AbstractCarpSession session;//l锟斤拷session
	private CarpSql carpSql = null;
	private String	          sql;		//锟斤拷执锟叫碉拷sql锟斤拷锟�
	private PreparedStatement	ps;		//执锟斤拷sql锟斤拷java.sql.PreparedStatement锟斤拷实锟斤拷
	private int	              timeout = 0; //l锟接筹拷时
	private String[]	      returnNames; //执锟斤拷select锟斤拷洌拷锟斤拷氐锟絪elect 锟街讹拷锟叫憋拷
	private Class<?>[]	      returnTypes; //执锟斤拷select锟斤拷洌�
	private Class<?>	      cls;
	private Parameter	      param	     = new Parameter();
	private int	              fetchSize	 = 20;
	private int	              firstIndex	= -1;
	private int	              maxCount	 = -1;
	
	
	public AbstractCarpQuery(AbstractCarpSession session, String sql) throws CarpException {
		this(session, null, sql);
	}

	public AbstractCarpQuery(AbstractCarpSession session, Class<?> cls) throws CarpException {
		
		this(session, cls, null);
	}

	public AbstractCarpQuery(AbstractCarpSession session, Class<?> cls, String sql) throws CarpException {
		try {
			this.session = session;
			this.cls = cls;
			this.sql = sql;
			int fs = this.session.getJdbcContext().getContext().getCarpSetting().getFetchSize();
			if(fs > 0)
				this.fetchSize = fs;
			this.carpSql = this.session.getJdbcContext().getContext().getCarpSql(cls);
		} catch (Exception ex) {
			throw new CarpException("锟斤拷菘锟絣锟斤拷为锟秸伙拷锟斤拷Sql锟斤拷锟斤拷锟斤拷锟斤拷锟介！", ex);
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public CarpSql getCarpSql() {
		return carpSql;
	}
	
	/**
	 * 执锟叫诧拷询
	 * @see CarpQuery
	 */
	public List<?> list() throws CarpException {
		List<Object> list = null;
		try {
			CarpQueryExecutor query = new CarpQueryExecutor(this);
			list = query.list();
			this.returnNames = query.getReturnNames();
			this.returnTypes = query.getReturnTypes();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new CarpException("Query error,may sql or parameters incorrect.", ex);
		}
		return list;
	}

	public DataSet dataSet() throws CarpException {
		try {
			DatasetQueryExecutor executor = new DatasetQueryExecutor(this);
			return executor.dataSet();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new CarpException("Query error,may sql or parameters incorrect.", ex);
		}
	}
	
	public ResultSet resultSet()throws CarpException{
		try {
			QueryExecutor query = new QueryExecutor(this);
			return query.getResultSet();
		} catch (Exception ex) {
			throw new CarpException("Query error,may sql or parameters incorrect.", ex);
		}
	}
	
	public CarpQuery addBatch() throws CarpException {
		try{
			
//			SQLParameterFactory.createUpdateSQLParameter(this).processSQLParameters();
//			this.ps.addBatch();
//			this.clearParameters();
			new BatchExecutor(this).addBatch();
		}catch(Exception ex){
			throw new CarpException(ex); 
		}
		return this;
	}

	public void executeBatch()throws CarpException{
		try {
			this.ps.executeBatch();
		} catch (SQLException ex) {
			if(ex.getNextException()!=null)
				ex.getNextException().printStackTrace();
			throw new CarpException(ex); 
		}
	}
	
	public CarpQuery clearParameters(){
		this.param.clear();
		return this;
	}

	public int executeUpdate() throws CarpException {
		int code = -1;
		if(!this.session.isOpen())
			throw new CarpException("connection was closed,could not execute!");
		try{
			this.session.getJdbcContext().isCommit();
			code = new UpdateExecutor(this).getRowCount();
//			SQLParameterFactory.createUpdateSQLParameter(this).processSQLParameters();
//			code = ps.executeUpdate();
		}catch(Exception ex){
			throw new CarpException("执锟叫达拷锟斤拷锟斤拷锟斤拷Sql锟斤拷锟斤拷约锟斤拷锟斤拷锟斤拷欠锟斤拷锟饺凤拷锟�",ex);
		}
		return code;
	}

	public String getQueryString() {
		return this.sql;
	}

	public String[] getReturnNames() {
		return this.returnNames;
	}

	public Class<?>[] getReturnTypes() {
		return this.returnTypes;
	}

	public CarpQuery setAsciiStream(int index, InputStream x) throws SQLException {
		param.setParameter(index, x, InputStream.class);
		return this;
	}
	
	public CarpQuery setAsciiStream(int index, Reader x) throws SQLException {
		param.setParameter(index, x, Reader.class);
		return this;
	}

	public CarpQuery setFirstIndex(int beginIndex) {
		this.firstIndex = beginIndex;
		return this;
	}

	public CarpQuery setBigDecimal(int index, BigDecimal x) throws SQLException {
		param.setParameter(index, x, BigDecimal.class);
		return this;
	}


	public CarpQuery setBinaryStream(int index, InputStream x) throws SQLException {
		param.setParameter(index, x, InputStream.class);
		return this;
	}

	public CarpQuery setBoolean(int index, boolean x) throws SQLException {
		param.setParameter(index, x, boolean.class);
		return this;
	}

	public CarpQuery setByte(int index, byte x) throws SQLException {
		param.setParameter(index, x, byte.class);
		return this;
	}

	public CarpQuery setBytes(int index, byte[] x) throws SQLException {
		param.setParameter(index, x, byte[].class);
		return this;
	}

	public CarpQuery setCharacterStream(int index, Reader reader) throws SQLException {
		param.setParameter(index, reader, Reader.class);
		return this;
	}

	public CarpQuery setDate(int index, java.util.Date value) throws SQLException {
		param.setParameter(index, value, java.util.Date.class);
		return this;
	}
	public CarpQuery setDate(int index, java.sql.Date value) throws SQLException {
		param.setParameter(index, value, java.sql.Date.class);
		return this;
	}

	public CarpQuery setDouble(int index, double x) throws SQLException {
		param.setParameter(index, x, double.class);
		return this;
	}

	public CarpQuery setMaxCount(int endIndex) {
		this.maxCount = endIndex;
		return this;
	}

	public CarpQuery setFetchSize(int fetchSize) {
		this.fetchSize = fetchSize;
		return this;
	}

	public CarpQuery setFloat(int index, float x) throws SQLException {
		param.setParameter(index, x, float.class);
		return this;
	}

	public CarpQuery setInt(int index, int x) throws SQLException {
		param.setParameter(index, x, int.class);
		return this;
	}

	public CarpQuery setLong(int index, long x) throws SQLException {
		param.setParameter(index, x, long.class);
		return this;
	}

	public CarpQuery setNull(int index, int sqlType) throws SQLException {
		param.setParameter(index, sqlType, null);
		return this;
	}

	public CarpQuery setObject(int index, Object x) throws SQLException {
		param.setParameter(index, x, Object.class);
		return this;
	}

	public CarpQuery setRef(int index, Ref x) throws SQLException {
		param.setParameter(index, x, Ref.class);
		return this;
	}

	public CarpQuery setShort(int index, short x) throws SQLException {
		param.setParameter(index, x, short.class);
		return this;
	}

	public CarpQuery setString(int index, String x) throws SQLException {
		param.setParameter(index, x, String.class);
		return this;
	}

	public CarpQuery setTime(int index, java.util.Date value) throws SQLException {
		java.sql.Time ts = null;
		if (value != null)
			ts = new java.sql.Time(value.getTime());
		param.setParameter(index, ts, java.sql.Time.class);
		return this;
	}
	public CarpQuery setTime(int index, java.sql.Time value) throws SQLException {
		param.setParameter(index, value, java.sql.Time.class);
		return this;
	}

	public CarpQuery setTimeout(int timeout) {
		this.timeout = timeout;
		return this;
	}

	public CarpQuery setTimestamp(int index, java.util.Date value) throws SQLException {
		java.sql.Timestamp ts = null;
		if (value != null)
			ts = new java.sql.Timestamp(value.getTime());
		param.setParameter(index, ts, java.sql.Timestamp.class);
		return this;
	}

	public CarpQuery setURL(int index, URL x) throws SQLException {
		param.setParameter(index, x, URL.class);
		return this;
	}

	public String getSql() {
    	return sql;
    }

	public void setSql(String sql) {
    	this.sql = sql;
    }

	public PreparedStatement getPreparedStatement() {
    	return ps;
    }

	public int getTimeout() {
    	return timeout;
    }

	public Parameter getParameters() {
    	return param;
    }

	public int getFetchSize() {
    	return fetchSize;
    }

	public int getFirstIndex() {
    	return firstIndex;
    }

	public int getMaxCount() {
    	return maxCount;
    }

	public void setPreparedStatement(PreparedStatement ps) {
    	this.ps = ps;
    }

	public AbstractCarpSession getSession() {
		return session;
	}

	public Class<?> getCls() {
		return cls;
	}

	public void setReturnNames(String[] returnNames) {
		this.returnNames = returnNames;
	}

	public void setReturnTypes(Class<?>[] returnTypes) {
		this.returnTypes = returnTypes;
	}
}
