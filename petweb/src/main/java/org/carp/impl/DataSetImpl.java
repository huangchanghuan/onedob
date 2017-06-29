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

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.carp.DataSet;
import org.carp.engine.metadata.MetaData;
import org.carp.engine.result.ResultSetProcessor;

/**
 * DataSet接口实现类 
 * @author Administrator
 *
 */
public class DataSetImpl  implements DataSet{
	private List<List<Object>> row = new ArrayList<List<Object>>();
	private int dataCount;
	private MetaData cqmd;
	private int index = -1;
	
	public DataSetImpl(AbstractCarpQuery query, MetaData cqmd,ResultSet rs)throws Exception{
		try{
			ResultSetProcessor rsp = new ResultSetProcessor(query,cqmd,rs);
			rsp.createDataSet(row);
			this.cqmd = cqmd;
			dataCount = row.size()-1;
		}finally{
			rs.close();
		}
	}
	
	
	public boolean next(){
		if(index < dataCount){
			++index;
			return true;
		}
		return false;
	}
	
	public Object getData(String name){
		int col_index = cqmd.getColumns().indexOf(name.toUpperCase());
		if(col_index == -1){
			System.out.println("字段不存在："+name);
		}
		return row.get(index).get(col_index);
	}
	public Object getData(int index){
		int col_index = cqmd.getColumns().size();
		if(index < 0 || index >=col_index){
			System.out.println("索引超出范围："+index);
		}
		return row.get(index).get(index);
	}
	
	public List<List<Object>> getData() {
		return row;
	}
	public List<String> getTitle() {
		return 	cqmd.getColumns();
	}
	public List<Object> getRowData(int index) {
		return row.get(index);
	}

	public List<Class<?>> getColumnType() {
		return this.cqmd.getColumnJavaType();
	}
	
	public int count(){
		return dataCount + 1;
	}
}
