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
package org.carp.engine.event;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.carp.beans.PrimarysMetadata;
import org.carp.engine.ParametersProcessor;
import org.carp.engine.cascade.FindCascade;
import org.carp.engine.metadata.QueryMetaData;
import org.carp.engine.result.ResultSetProcessor;
import org.carp.exception.CarpException;
import org.carp.impl.AbstractCarpSession;

public class FindEvent extends Event{
	private Class<?> cls;
	private Object key;
	
	public FindEvent(AbstractCarpSession session,Class<?> cls,Object key) throws CarpException{
		super(session,cls,key,"find");
		this.cls = cls;
		this.key = key;
	}

	/**
	 * ����Statement�����(���ֵ
	 */
	@Override
	public void processStatmentParameters(ParametersProcessor psProcess)throws Exception {
		this.processPrimaryValues(psProcess);
	}

	@Override
	public void cascadeBeforeOperator() throws Exception {
	}
	
	/**
	 * ������¼����?��
	 * @throws Exception
	 */
	public void execute()throws Exception{
		cascadeBeforeOperator(); //statement����ǰ�ļ�j����
		buildSql(); //���sql���
		executeBefore();
		buildStatement(); //����statement����
		processStatmentParameters(new ParametersProcessor(this.getSession().getPs())); //����statement����
		executeStatement(); //ִ��statement
		executeAfter();
		cascadeAfterOperator(); //statement�����ļ�j����
	}
	
	/**
	 * ����ѯ���������ֵ��Ӧ��
	 * @throws Exception
	 */
	protected void processPrimaryValues(ParametersProcessor psp)throws Exception{
		if(key == null)
			throw new CarpException("Primary Key value is null��");
		List<PrimarysMetadata> pms = getBean().getPrimarys();
		if(this.key instanceof HashMap){
			@SuppressWarnings("unchecked")
			Map<String, ?> keys = (Map<String, ?>)key;
			if(pms.size() != keys.size())
				throw new CarpException("primarys count is ��"+keys.size()+", but provide key's is "+pms.size());
			for(int i=0, count = pms.size(); i < count; ++i){
				PrimarysMetadata pm = pms.get(i);
				String col = null;
				for(Iterator<String> it = keys.keySet().iterator();it.hasNext();){
					col = it.next();
					if(pm.getColName().equals(col.toUpperCase()))break;
				}
				Object value = keys.get(col);
				if(value == null)
					throw new CarpException(pm.getColName()+" : value is null��");
				psp.setStatementParameters(value, pm.getFieldType(), i+1);
			}
		}else{
			if(pms.size() != 1)
				throw new CarpException("primarys count != 1��");
			psp.setStatementParameters(key, pms.get(0).getFieldType(), 1);
		}
	}
	
	/**
	 * ִ�в�ѯ
	 * @throws Exception
	 */
	private void executeStatement() throws Exception{
		ResultSet rs =  null;
		try{
			rs = getSession().getPs().executeQuery();
			ResultSetProcessor rsp = new ResultSetProcessor(cls,new QueryMetaData(cls,rs),rs);
			this.setEntity(rsp.get());
			if(this.getEntity() == null)
				throw new CarpException("not found object��");
		}catch(Exception ex){
			ex.printStackTrace();
			throw new Exception(ex);
		}
		finally{
			rs.close();
			getSession().getPs().close();
		}
	}
	
	@Override
	public void cascadeAfterOperator() throws Exception {
		org.carp.engine.cascade.Cascade cascade = new FindCascade(getSession(), getBean(), getEntity(), key);
		cascade.cascadeDICOperator().cascadeOTMOperator().cascadeOTOOperator();
	}

	@Override
	protected void executeBefore() throws Exception {
		if(this.getSession().getJdbcContext().getContext().getCarpSetting().getInterceptor() != null)
			this.getSession().getJdbcContext().getContext().getCarpSetting().getInterceptor().onBeforeLoad(getEntity(), getSession());
		if(this.getSession().getInterceptor() != null)
			this.getSession().getInterceptor().onBeforeLoad(getEntity(), getSession());
	}

	@Override
	protected void executeAfter() throws Exception {
		if(this.getSession().getJdbcContext().getContext().getCarpSetting().getInterceptor() != null)
			this.getSession().getJdbcContext().getContext().getCarpSetting().getInterceptor().onAfterLoad(getEntity(), getSession());
		if(this.getSession().getInterceptor() != null)
			this.getSession().getInterceptor().onAfterLoad(getEntity(), getSession());
	}
}
