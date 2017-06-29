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

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.carp.cfg.CarpSetting;
import org.carp.exception.CarpException;

public class CarpDataSource implements DataSource{
	private CarpSetting carp;
	private PrintWriter out;
	private Properties p;
	public CarpDataSource(CarpSetting carp) throws CarpException{
		this.carp = carp;
		try {
			Class.forName(carp.getDriverClass());
			p = new Properties();
			p.setProperty("user", carp.getUserName());
			p.setProperty("password", carp.getPassword());
			p.setProperty("shutdown", "true");
		}catch (Exception ex) {
			throw new CarpException(ex);
		}
	}

	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(carp.getUrl(), p);
	}

	public Connection getConnection(String username, String password)
			throws SQLException {
		return DriverManager.getConnection(carp.getUrl(), username,password);
	}

	public PrintWriter getLogWriter() throws SQLException {
		return out;
	}

	public int getLoginTimeout() throws SQLException {
		return DriverManager.getLoginTimeout();
	}

	public void setLogWriter(PrintWriter out) throws SQLException {
		this.out = out;
	}

	public void setLoginTimeout(int seconds) throws SQLException {
		DriverManager.setLoginTimeout(seconds);
	}

	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public <T> T unwrap(Class<T> iface) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}

}
