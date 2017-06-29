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

import java.sql.Connection;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.carp.exception.CarpException;
import org.carp.sql.CarpSql;
import org.carp.sql.DB2CarpSql;
import org.carp.sql.DefaultSql;
import org.carp.sql.HSQLCarpSql;
import org.carp.sql.MsSqlServerCarpSql;
import org.carp.sql.MySqlCarpSql;
import org.carp.sql.OracleCarpSql;
import org.carp.sql.PostgreSqlCarpSql;
import org.carp.sql.SqlServer2005CarpSql;
/**
 * ConnectionProvider接口实现类
 * @author Administrator
 * @see ConnectionProvider
 * @version 0.2
 */
public abstract class AbstractConnectionProvider implements ConnectionProvider {
	private static final Logger logger = Logger.getLogger(AbstractConnectionProvider.class);
	private Class<?> 	carpSqlClass;
	private String databaseName;
	private int databaseVersion;
	private DataSource dataSource;
	
	/**
	 * 提取数据库信息：数据库产品名，数据库版本号.
	 * 如：ORACLE,DB2，等
	 * @throws CarpException
	 */
	protected void databaseProducename() throws CarpException{
		Connection conn = null;
		try{
			conn = this.getDataSource().getConnection();
			this.databaseName = conn.getMetaData().getDatabaseProductName().toUpperCase();
			this.databaseVersion = conn.getMetaData().getDatabaseMajorVersion();
			if(logger.isDebugEnabled()){
				logger.debug("database : "+this.getDatabaseProductName()+" , MajorVersion : "+this.databaseVersion);
			}
		}catch(Exception ex){
			throw new CarpException("获取数据库产品名称失败！不知道所使用的数据库类型。",ex);
		}finally{
			try{conn.close(); conn = null;}catch(Exception ex){};
		}
	}
	
	/**
	 * 配置数据库所使用的CarpSql，如果不存在所对应的CarpSql实现类，则使用默认的DefaultSql类
	 */
	protected void dialect(){
		if(this.getCarpSetting().getDatabaseDialect()!=null)
			carpSqlClass = this.getCarpSetting().getDatabaseDialect();
		else{
			if(this.databaseName.indexOf("DB2") != -1){//db2
				carpSqlClass = DB2CarpSql.class;
			}else if(this.databaseName.indexOf("ORACLE") != -1){//oracle
				carpSqlClass = OracleCarpSql.class;
			}else if(this.databaseName.indexOf("MYSQL") != -1){//mysql
				carpSqlClass = MySqlCarpSql.class;
			}else if(this.databaseName.indexOf("HSQL") != -1){//hsqldb
				carpSqlClass = HSQLCarpSql.class;
			}else if(this.databaseName.indexOf("POSTGRE") != -1){//PostgreSql
				carpSqlClass = PostgreSqlCarpSql.class;
			}else if(this.databaseName.indexOf("SQL SERVER") != -1){
				if(this.databaseVersion >= 9)//sql server 2005
					carpSqlClass = SqlServer2005CarpSql.class;
				else//sql server 2000
					carpSqlClass = MsSqlServerCarpSql.class;
			}else{//默认sql产生类
				carpSqlClass = DefaultSql.class;
			}
		}
		if(logger.isDebugEnabled())
			logger.debug("database dialect : "+this.getCarpSqlClass());
	}

	/**
	 * 根据pojo类加载对应的sql处理对象
	 */
	public CarpSql getCarpSql(Class<?> cls) {
		CarpSql carp = null;
		try {
			carp = (CarpSql)carpSqlClass.newInstance();
			carp.setClass(cls);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(getCarpSetting().getSchema()!=null && !getCarpSetting().getSchema().equals(""))
			carp.setSchema(this.getCarpSetting().getSchema());
		return carp;
	}

	public DataSource getDataSource() {
		return this.dataSource;
	}

	protected void setDataSource(DataSource dataSource){
		this.dataSource = dataSource;
	}
	
	public String getDatabaseProductName() {
		return databaseName;
	}
	
	protected void setDatabaseProductName(String database){
		this.databaseName = database;
	}


	public Class<?> getCarpSqlClass() {
		return carpSqlClass;
	}
}
