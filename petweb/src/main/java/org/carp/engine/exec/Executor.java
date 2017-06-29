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

import org.carp.engine.SQLParameter;
import org.carp.engine.sql.Sql;
import org.carp.engine.statement.CarpStatement;
import org.carp.impl.AbstractCarpQuery;
/**
 * 执行器基本抽象类
 * @author zhou
 * @since 0.2
 */
public abstract class Executor {
	private AbstractCarpQuery _query = null;
	public Executor(AbstractCarpQuery query) throws Exception{
		this._query = query;
		process();
	}
	
	protected AbstractCarpQuery getQuery(){
		return this._query;
	}
	
	/**
	 * 处理过程
	 * @throws Exception
	 */
	protected void process() throws Exception{
		new Sql(this._query).showSql();//构建Sql语句
		new CarpStatement(this._query).createQueryStatement(); //创建Statement对象
		//设置Statement参数
		new SQLParameter(this._query).processSQLParameters();
		executeStatement();//执行Statement
	}
	/**
	 * 执行Statement，子类需要实现该方法
	 * @throws Exception
	 */
	abstract protected void executeStatement()throws Exception;
}
