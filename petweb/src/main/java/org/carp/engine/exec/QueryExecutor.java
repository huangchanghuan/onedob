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
package org.carp.engine.exec;

import java.sql.ResultSet;

import org.carp.engine.metadata.MetaData;
import org.carp.impl.AbstractCarpQuery;

/**
 * 查询执行器类
 * @author zhou
 * @since 0.2
 */
public class QueryExecutor extends Executor{
	private ResultSet rs;
	private MetaData metadata; //结果集元数据

	public QueryExecutor(AbstractCarpQuery query) throws Exception{
		super(query);
	}

	@Override
	protected void executeStatement() throws Exception {
		rs = this.getQuery().getPreparedStatement().executeQuery();
	}
	
	/**
	 * 返回结果集
	 * @return
	 */
	public ResultSet getResultSet(){
		return rs;
	}
	
	
	protected MetaData getMetadata() {
		return this.metadata;
	}
	protected void setMetadata(MetaData metadata) {
		this.metadata = metadata;
	}
	
	/**
	 * 取得查询字段数组
	 * @return
	 */
	public String[] getReturnNames(){
		return metadata.getColumns().toArray(new String[0]);
	}
	/**
	 * 取得查询字段的类型数组
	 * @return
	 */
	public Class<?>[] getReturnTypes(){
		return (Class<?>[])metadata.getColumnJavaType().toArray(new Class<?>[0]);
	}
}
