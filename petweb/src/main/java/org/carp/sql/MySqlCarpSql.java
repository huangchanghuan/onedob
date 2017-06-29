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
import org.carp.exception.CarpException;

public class MySqlCarpSql extends AbstractSql {
	private static final Logger logger = Logger.getLogger(MySqlCarpSql.class);
	/**
	 * 取得执行分页查询时，select查询的分页sql语句
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public String getPageSql(String sql) throws Exception{
		StringBuffer carpSQL = new StringBuffer(sql);
		carpSQL.append("  limit ?, ? ");
		sql = carpSQL.toString();
		if(logger.isDebugEnabled())
			logger.debug(sql);
		return sql;
	}

	public int position() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getSequenceSql(String seq) throws CarpException {
		throw new CarpException("My Sql 暂不支持的数据库类型!");
	}

	public void setQueryParameters(PreparedStatement ps, int firstIndex,
			int maxIndex, int paramsCount) throws Exception {
		ps.setInt(paramsCount+1, firstIndex);
		ps.setInt(paramsCount+2, maxIndex);
	}
}
