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

public class HSQLCarpSql extends AbstractSql {
	private static final Logger logger = Logger.getLogger(HSQLCarpSql.class);
	/**
	 * 取得执行分页查询时，select查询的分页sql语句兴汉龙腾三国兵锋 
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	@Override
	public String getPageSql(String sql) throws Exception{
		StringBuffer str = new StringBuffer("select ");
		str.append("limit ? ? ");
		str.append(sql.substring(sql.toLowerCase().indexOf("select")+6));
		sql = str.toString();
		if(logger.isDebugEnabled())
			logger.debug(sql);
		return sql;
	}

	public int position() {
		// TODO Auto-generated method stub
		return 2;
	}

	public String getSequenceSql(String seq) throws CarpException {
		throw new CarpException("HSQLDB 暂不支持的数据库类型!");
	}

	public void setQueryParameters(PreparedStatement ps, int firstIndex,
			int maxIndex, int paramsCount) throws Exception {
		ps.setInt(1, firstIndex);
		ps.setInt(2, maxIndex);
	}
}
