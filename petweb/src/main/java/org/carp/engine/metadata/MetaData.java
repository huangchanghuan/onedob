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
package org.carp.engine.metadata;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.carp.assemble.Assemble;
import org.carp.type.TypesMapping;

/**
 * 元数据类
 * @author zhou
 * @since 0.1
 */
public class MetaData {
	protected static final Logger logger = Logger.getLogger(MetaData.class);
	private List<String> columns = new ArrayList<String>(8);
	private List<Class<?>> columnJavaType = new ArrayList<Class<?>>(8);
	private Map<String,Class<?>> columnJavaTypeMap = new HashMap<String,Class<?>>(8);
	private Map<String,Assemble> assemble = new HashMap<String,Assemble>();
	private int columnCount;
	
	public MetaData(ResultSet rs)throws Exception{
		parserResultSetMeta(rs.getMetaData());
	}
	
	public String getColumnName(int index){
		return this.columns.get(index);
	}
	
	public Class<?> getColumnJavaType(int index){
		return this.columnJavaType.get(index);
	}
	
	public Assemble getAssemble(String col){
		return this.assemble.get(col);
	}
	
	
	public int getColumnCount() {
    	return columnCount;
    }
	
	/**
	 * 解析ResultSet元数据
	 * @param rsmd
	 * @throws Exception
	 */
	private void parserResultSetMeta(ResultSetMetaData rsmd)throws Exception{
		columnCount = rsmd.getColumnCount();
		for(int i = 1; i <= columnCount; ++i){
			String col = rsmd.getColumnLabel(i).toUpperCase();
			columns.add(col);
			Class<?> typeCls = TypesMapping.getJavaType(rsmd.getColumnType(i));
			columnJavaType.add(typeCls);
			columnJavaTypeMap.put(col, typeCls);
			//column处理类
			this.assemble.put(col,(Assemble)(TypesMapping.getAssembleClass(typeCls).newInstance()));
			if(logger.isDebugEnabled())
				logger.debug("列名 : "+col +", 列Java类型 : "+ typeCls);
		}
	}


	public List<String> getColumns() {
		return columns;
	}

	public List<Class<?>> getColumnJavaType() {
		return columnJavaType;
	}
	
	public Class<?> getColumnJavaType(String colname) {
		return this.columnJavaTypeMap.get(colname);
	}
	
	
	protected Map<String,Class<?>> getColumnsJavaType(){
		return this.columnJavaTypeMap;
	}
	
	protected Map<String,Assemble> getAssembles(){
		return this.assemble;
	}

	
	public Field getField(int index){
		return null;
	}

	public Field getField(String colname){
		return null;
	}
}
