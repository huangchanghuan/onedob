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
package org.carp;

import java.math.BigDecimal;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.carp.exception.CarpException;

/**
 * 查询接口定义类
 * @author zhou
 * @since 0.1
 */
public interface Query {
	/**
	 * 查询字符串
	 * @return
	 */
	String getQueryString();
	/**
	 * 查询sql的select列表字段的类型数组
	 * @return
	 */
	Class<?>[] getReturnTypes();
	
	/**
	 * 查询sql的select列表字段的名称数组
	 * @return
	 */
	String[] getReturnNames();
	/**
	 * 直接返回查询sql语句的结果集，返回的结果集数据取决于数据库是否支持分页功能，如果不支持则返回全部数据
	 * @return
	 * @throws CarpException
	 */
	ResultSet resultSet()throws CarpException;
	/**
	 * 设置查询sql的起始索引
	 * @param first
	 * @return
	 */
	Query setFirstIndex(int first);
	/**
	 * 设置查询的结果集最多可以返回多少条记录。
	 * @param last
	 * @return
	 */
	Query setMaxCount(int last);
	/**
	 * 设置查询超时
	 * @param timeout
	 * @return
	 */
	Query setTimeout(int timeout);
	/**
	 * 设置Statement对象的fetch大小
	 * @param fetchSize
	 * @return
	 */
	Query setFetchSize(int fetchSize);
	/**
	 * 执行sql(update,delete,insert)语句
	 * @return
	 * @throws CarpException
	 */
    int executeUpdate() throws CarpException;
    /**
     * 以批处理方式执行Sql(update,delete,insert)语句
     * @throws CarpException
     */
    void	executeBatch()throws CarpException;
    /**
     * 把sql(update,delete,insert)语句，添加到批处理中
     * @return
     * @throws CarpException
     */
    Query addBatch() throws CarpException;
    /**
     * 清理session中的查询参数
     * @return
     * @throws SQLException
     */
    Query clearParameters() throws SQLException;
    /**
     * 
     * @param index
     * @param sqlType
     * @return
     * @throws SQLException
     */
    Query setNull(int index, int sqlType) throws SQLException;
    /**
     * 
     * @param index
     * @param x
     * @return
     * @throws SQLException
     */
    Query setBoolean(int index, boolean x) throws SQLException;
    /**
     * 
     * @param index
     * @param x
     * @return
     * @throws SQLException
     */
    Query setByte(int index, byte x) throws SQLException;
    /**
     * 
     * @param index
     * @param x
     * @return
     * @throws SQLException
     */
    Query setShort(int index, short x) throws SQLException;
    /**
     * 
     * @param index
     * @param x
     * @return
     * @throws SQLException
     */
    Query setInt(int index, int x) throws SQLException;
    /**
     * 
     * @param index
     * @param x
     * @return
     * @throws SQLException
     */
    Query setLong(int index, long x) throws SQLException;
    /**
     * 
     * @param index
     * @param x
     * @return
     * @throws SQLException
     */
    Query setFloat(int index, float x) throws SQLException;
    Query setDouble(int index, double x) throws SQLException;
    Query setBigDecimal(int index, BigDecimal x) throws SQLException;
    Query setString(int index, String x) throws SQLException;
    Query setBytes(int index, byte x[]) throws SQLException;
    Query setDate(int index, Date value) throws SQLException;
    Query setDate(int index, java.sql.Date value) throws SQLException;
    Query setTime(int index, Date value)  throws SQLException;
    Query setTime(int index, java.sql.Time value)  throws SQLException;
    Query setTimestamp(int index, Date value)  throws SQLException;
    Query setAsciiStream(int index, java.io.InputStream x)  throws SQLException;
    Query setAsciiStream(int index, java.io.Reader reader)  throws SQLException;
    Query setBinaryStream(int index, java.io.InputStream x) throws SQLException;
    Query setObject(int index, Object x) throws SQLException;
    Query setCharacterStream(int index, java.io.Reader reader) throws SQLException;
    Query setRef(int index, Ref x) throws SQLException;
    Query setURL(int index, java.net.URL x) throws SQLException;
}
