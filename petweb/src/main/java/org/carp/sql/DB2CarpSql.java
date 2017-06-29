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
package org.carp.sql;

import java.sql.PreparedStatement;

import org.apache.log4j.Logger;

public class DB2CarpSql extends AbstractSql {
	private static final Logger logger = Logger.getLogger(DB2CarpSql.class);
	/**
	 * 取得执行分页查询时，select查询的分页sql语句兴汉龙腾三国兵锋 
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	@Override
	public String getPageSql(String sql) throws Exception{
		StringBuffer str = new StringBuffer("select * from ( select rownumber() over() as row_num,t.* from (");
		str.append(sql);
		str.append(") t) t where t.row_num > ? and t.row_num <= ?");
		sql = str.toString();
		if(logger.isDebugEnabled())
			logger.debug(sql);
		return sql;
	}

	public int position() {
		return 0;
	}

	public String getSequenceSql(String seq) {
		return "select nextval for "+seq+"  from sysibm.sysdummy1";
	}

	/**
	 * 因为使用的是Oracle经典的三层查询，其索引占位符位于sql语句的最后（查询参数占位符在前面，如果存在参数的话）。
	 * 所以需要知道参数个数，来确定其占位符的索引值。
	 */
	public void setQueryParameters(PreparedStatement ps, int firstIndex,
			int maxIndex, int paramsCount) throws Exception {
		ps.setInt(paramsCount+1, firstIndex);
		ps.setInt(paramsCount+2, firstIndex+maxIndex);
	}
}
