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

import java.sql.PreparedStatement;

import org.apache.log4j.Logger;
import org.carp.engine.parameter.Parameter;
import org.carp.exception.CarpException;
import org.carp.type.TypesMapping;

/**
 * 参数处理类
 * 设置Statement对象的查询参数
 * @author zhou	
 * @since 0.1
 */
public class ParametersProcessor {
	private static final Logger logger = Logger.getLogger(ParametersProcessor.class);
	private PreparedStatement ps;
	
	public ParametersProcessor(PreparedStatement ps){
		this.ps = ps;
	}
	
	/**
	 * 设置PreparedStatement参数
	 * @param value 参数值
	 * @param typeCls 参数类型
	 * @param index 参数索引
	 * @throws Exception
	 */
	public void setStatementParameters(Object value,Class<?> typeCls,int index) throws Exception{
		if(TypesMapping.getParamsClass(typeCls)== null){
			throw new CarpException("不支持的参数类型：" + typeCls);
		}
		Parameter param = (Parameter)TypesMapping.getParamsClass(typeCls).newInstance();
		if(logger.isDebugEnabled()){
			logger.debug("ParameterType : "+typeCls+" ,  ParameterIndex ： "+index+" ,  Value  : "+value);
		}
		param.setValue(ps, value, index);
	}
	
	/**
	 * 设置PreparedStatement参数
	 * @param value 参数值
	 * @param javaType 参数类型
	 * @param index 参数索引
	 * @throws Exception
	 */
	public void setStatementParameters(Object value,int javaType,int index)throws Exception{
		if(TypesMapping.getParamsClass(javaType)== null){
			throw new CarpException("不支持的参数类型：" + javaType);
		}
		Parameter param = (Parameter)TypesMapping.getParamsClass(javaType).newInstance();
		if(logger.isDebugEnabled()){
			logger.debug("ParameterIndex ： "+index+" ,  Value  : "+value);
		}
		param.setValue(ps, value, index);
	}
}
