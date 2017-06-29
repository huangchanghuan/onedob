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
package org.carp.engine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.carp.CarpQuery;
import org.carp.beans.CarpBean;
import org.carp.beans.DICMetadata;
import org.carp.beans.OTMMetadata;
import org.carp.beans.OTOMetadata;
import org.carp.beans.PrimarysMetadata;
import org.carp.engine.metadata.QueryMetaData;
import org.carp.engine.result.ResultSetProcessor;
import org.carp.exception.CarpException;
import org.carp.factory.BeansFactory;
import org.carp.impl.AbstractCarpSession;
/**
 * 根据主键查询某条记录。
 * @author zhou
 * @since 0.1
 */
public class CarpLoadProcessor {
	private Connection conn = null;
	private AbstractCarpSession session;
	private PreparedStatement ps;
	private Class<?> cls;
	private Object key;
	private Object data;
	private CarpBean bean;
	/**
	 * 构造函数
	 * @param session
	 * @param cls
	 * @param key
	 * @throws Exception
	 */
	public CarpLoadProcessor(AbstractCarpSession session,Class<?> cls, Object key)throws Exception{
		init(session,cls, key);
		createPreparedStatment();
		processParameters();
		executeQuery();
		cascadeOperator();
	}
	
	private void init(AbstractCarpSession session,Class<?> cls, Object key) throws CarpException{
		this.session = session;
		this.conn = this.session.getConnection();
		this.cls = cls;
		this.key = key;
	}
	/**
	 * 创建PreparedStatment对象
	 * @throws Exception
	 */
	private void createPreparedStatment()throws Exception{
		String sql = this.session.getJdbcContext().getContext().getCarpSql(this.cls).getLoadSql();
		if(this.session.getJdbcContext().getContext().getCarpSetting().isShowSql())
			System.out.println("Carp SQL : "+sql);
		ps = this.conn.prepareStatement(sql);
	}
	/**
	 * 将查询参数与主键值对应绑定
	 * @throws Exception
	 */
	private void processParameters()throws Exception{
		if(key == null)
			throw new CarpException("Primary Key value is null！");
		ParametersProcessor psp = new ParametersProcessor(ps);
		bean = BeansFactory.getBean(cls);
		List<PrimarysMetadata> pms = bean.getPrimarys();
		if(this.key instanceof HashMap){
			@SuppressWarnings("unchecked")
			Map<String, ?> keys = (Map<String, ?>)key;
			if(pms.size() != keys.size())
				throw new CarpException("primarys count is ！"+keys.size()+", but provide key's is "+pms.size());
			for(int i=0, count = pms.size(); i < count; ++i){
				PrimarysMetadata pm = pms.get(i);
				String col = null;
				for(Iterator<String> it = keys.keySet().iterator();it.hasNext();){
					col = it.next();
					if(pm.getColName().equals(col.toUpperCase()))break;
				}
				Object value = keys.get(col);
				if(value == null)
					throw new CarpException(pm.getColName()+" : value is null！");
				psp.setStatementParameters(value, pm.getFieldType(), i+1);
			}
		}else{
			if(pms.size() != 1)
				throw new CarpException("primarys count != 1！");
			psp.setStatementParameters(key, pms.get(0).getFieldType(), 1);
		}
	}
	/**
	 * 返回记录对象
	 * @return
	 * @throws Exception
	 */
	public Object get()throws Exception{
		return data;
	}
	
	/**
	 * 执行查询
	 * @throws Exception
	 */
	private void executeQuery() throws Exception{
		ResultSet rs =  null;
		try{
			rs = ps.executeQuery();
			ResultSetProcessor rsp = new ResultSetProcessor(cls,new QueryMetaData(cls,rs),rs);
			data = rsp.get();
			if(data == null)
				throw new CarpException("not found object！");
		}finally{
			rs.close();
			ps.close();
		}
	}
	
	/**
	 * 级联加载
	 * @throws Exception
	 */
	private void cascadeOperator()throws Exception{
		cascadeDICOperator();
		cascadeOTMOperator();
		cascadeOTOOperator();
		//cascadeMTOOperator();
	}
	
	/**
	 * 加载代码表
	 * @throws CarpException
	 */
	private void cascadeDICOperator() throws CarpException{
		List<DICMetadata> dics = bean.getDics();
		if(dics != null)
			for(DICMetadata dic : dics){
				dic.setValue(data, this.session.creatQuery(dic.getDicClass(), dic.getSql()).list());
			}
	}
	/**
	 * 加载一对多
	 * @throws Exception
	 */
	private void cascadeOTMOperator() throws Exception{
		List<OTMMetadata> otms = bean.getOtms();
		if(otms != null)
			for(OTMMetadata otm : otms){
				String sql = this.session.getJdbcContext().getContext().getCarpSql(otm.getChildClass()).getQuerySql();
				if(sql.toLowerCase().indexOf(" where ")>0)
					sql += " and "+otm.getFkey()+" = ?";
				else
					sql += " where "+otm.getFkey()+" = ?";
				CarpQuery query = this.session.creatQuery(otm.getChildClass(), sql);
				if(bean.getPrimarys().get(0).getFieldType().equals(String.class))
					query.setString(1, key.toString());
				else
					query.setLong(1, new Long(key.toString()));
				otm.setValue(data, query.list());
			}
	}
	/**
	 * 加载一对一
	 * @throws Exception
	 */
	private void cascadeOTOOperator() throws Exception{
		List<OTOMetadata> otos = bean.getOtos();
		if(otos != null)
			for(OTOMetadata oto : otos){
				oto.setValue(data, this.session.get(oto.getFieldType(), key.toString()));
			}
	}
}
