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
package org.carp.jdbc;

import org.apache.commons.dbcp.BasicDataSource;
import org.carp.cfg.CarpSetting;
import org.carp.exception.CarpException;

/**
 * 数据库连接实现类
 * @author zhou
 * @since 0.1
 */
public class DBCPConnectionProvider extends AbstractConnectionProvider {
	private CarpSetting carp;
	public DBCPConnectionProvider(CarpSetting carp) throws CarpException{
		this.carp = carp;
		build();
		this.databaseProducename();
		this.dialect();
	}

	private void build(){
		BasicDataSource bds = new BasicDataSource();
		bds.setDriverClassName(carp.getDriverClass());
		bds.setUrl(carp.getUrl());
		bds.setUsername(carp.getUserName());
		bds.setPassword(carp.getPassword());
		if(carp.getMaxActive()!=0)
			bds.setMaxActive(carp.getMaxActive());
		if(carp.getMaxIdle()!=0)
			bds.setMaxIdle(carp.getMaxIdle());
		if(carp.getMaxWait()!=0)
			bds.setMaxWait(carp.getMaxWait());
		if(carp.getMinIdle()!=0)
			bds.setMinIdle(carp.getMinIdle());
		if(carp.getCatalog()!=null && !carp.getCatalog().equals(""))
			bds.setDefaultCatalog(carp.getCatalog());
		this.setDataSource(bds);
	}

	public CarpSetting getCarpSetting() {
		return this.carp;
	}
}
