package org.carp.engine.sql;

import org.apache.log4j.Logger;
import org.carp.impl.AbstractCarpQuery;
import org.carp.impl.AbstractCarpSession;
import org.carp.sql.CarpSql;

/**
 * 查询Sql
 * @author zhou
 * @since 0.1
 */
public final class Sql {
	private final static Logger logger = Logger.getLogger(Sql.class);
	AbstractCarpQuery query;
	AbstractCarpSession _session;
	
	public Sql(AbstractCarpQuery query) throws Exception{
		this.query = query;
		buildeSql();
	}
	public Sql(AbstractCarpSession session) throws Exception{
		this._session = session;
	}
	/**
	 * 生成查询sql
	 * @throws Exception
	 */
	public void buildeSql() throws Exception{
		CarpSql carpSql = query.getSession().getJdbcContext().getContext().getCarpSql(query.getCls());//CarpSqlFactory.getCarpSql(db, this.getQuery().getCls());
		if(logger.isDebugEnabled()){
			logger.debug("Query Class："+query.getCls());
			logger.debug("CarpSql对象:"+carpSql);
		}
		if(query.getFirstIndex()!=-1 && query.getMaxCount()!=-1 ){
			if(query.getSql() == null)
				query.setSql(carpSql.getPageSql());
			else
				query.setSql(carpSql.getPageSql(query.getSql()));
		}else{
			if(query.getSql() == null)
				query.setSql(carpSql.getQuerySql());
		}
		if(logger.isDebugEnabled()){
			logger.debug("Query SQL: "+query.getSql());
		}
	}
	
	/**
	 * 生成插入、更新、删除、查找sql
	 * @param cls 实体类
	 * @param sqlType sql语句类型：insert，update，delete，find
	 * @return
	 * @throws Exception
	 */
	public String buildeSql(Class<?> cls,String sqlType) throws Exception{
		CarpSql carpSql = _session.getJdbcContext().getContext().getCarpSql(cls);
		if(sqlType.equals("insert")){
			return carpSql.getInsertSql();
		}else if(sqlType.equals("update")){
			return carpSql.getUpdateSql();
		}else if(sqlType.equals("delete")){
			return carpSql.getDeleteSql();
		}else{ // sqlType == find
			return carpSql.getLoadSql();
		}
	}
	
	/**
	 * 显示sql
	 */
	public void showSql(){
		if(query.getSession().getJdbcContext().getContext().getCarpSetting().isShowSql()){
			System.out.println("Carp SQL : "+query.getSql());
		}
	}
	/**
	 * 显示sql
	 */
	public void showSql(String sql){
		if(_session.getJdbcContext().getContext().getCarpSetting().isShowSql())
				System.out.println("Carp SQL : "+sql);
	}
}
