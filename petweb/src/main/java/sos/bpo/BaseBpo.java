package sos.bpo;

import com.sunstar.sos.action.BaseAction;
import com.sunstar.sos.annotation.AConnection;
import com.sunstar.sos.annotation.ATransaction;
import com.sunstar.sos.cache.CachedClient;
import com.sunstar.sos.cfg.Sql;
import com.sunstar.sos.constants.Constants;
import com.sunstar.sos.dao.BaseDao;
import com.sunstar.sos.permission.LoginUser;
import com.sunstar.sos.util.LogsUtil;
import com.sunstar.sos.util.ObjectUtil;
import com.sunstar.sos.util.page.PageFormData;
import org.apache.log4j.Logger;
import org.carp.DataSet;
import org.carp.beans.CarpBean;
import org.carp.beans.DICMetadata;
import org.carp.exception.CarpException;
import org.carp.factory.BeansFactory;
import org.carp.transaction.Transaction;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 业务处理对象类，本类封装了大部分(简单)的业务操作逻辑，一般的业务不需要实现自己的业务处理类，
 * 如果存在复杂的特有的不通用的业务操作，则需要继承本类，覆盖父类的方法或者定义新的业务方法。
 * @author Administrator
 *
 */
public class BaseBpo{
	protected static final Logger logger = Logger.getLogger(BaseBpo.class);
	private String result="";
	private Class<?> pojoClass;
	private BaseDao dao;
	private String table;
	private String action;
	private BaseAction baseAction;
	private Transaction _tx;//事务对象
	
	
	public void setTransaction(Transaction tx){
		this._tx = tx;
	}
	public Transaction getTransaction(){
		return this._tx;
	}
	/**操作类型*/
	public String getAction() {
		return action;
	}

	public void setBaseAction(BaseAction _action){
		this.baseAction = _action;
	}
	protected BaseAction getBaseAction(){
		return this.baseAction;
	}
	/**
	 * 根据pojo类取得其映射的数据表名
	 * @param cls pojo类
	 * @return
	 * @throws CarpException 
	 */
	public String getTable(){
		if(table == null)
			try {
				table = BeansFactory.getBean(this.pojoClass).getTable();
			} catch (CarpException e) {
				table = "";
			}
		return table;
	}
	
	/**
	 * 设置持久化对象pojo类
	 * @param cls
	 */
	public void setPojoClass(Class<?> cls){
		this.pojoClass = cls;
	}
	
	/**
	 * 设置dao对象
	 * @param dao
	 * @throws Exception 
	 */
	public void setDao(BaseDao dao){
		this.dao = dao;
	}
	
	/**
	 * 获取dao对象
	 * @param dao
	 * @throws Exception 
	 */
	public BaseDao getDao(){
		return this.dao;
	}
	
	/**
	 * 基于持久化类对应的数据表，查询数据的总记录数
	 * @return
	 * @throws Exception
	 */
	@AConnection
	protected long count()throws Exception{
		CarpBean bean = BeansFactory.getBean(this.pojoClass);
		return count("select count(*) from "+bean.getTable());
	}
	
	/**
	 * 查询某表中制定字段值的一条记录
	 * @param tableName
	 * @param fieldName
	 * @param value
	 * @return
	 * @throws Exception
	 */
	@AConnection
	public Object findUqByField(String tableName,String fieldName,Object value)throws Exception{
		String sql = "select * from " + tableName + " where "+fieldName+" = ";
		if(value instanceof Integer || value instanceof Long){
			sql += value.toString();
		}else if(value instanceof String){
			sql += "'"+value.toString()+"'";
		}
		List list = search(sql);
		Object obj = null;
		if(list != null && list.size() > 0) obj = list.get(0);
		return obj;
	}
	
	/**
	 * 根据sql查询数据的记录数
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	@AConnection
	public long count(String sql) throws Exception{
		long count = 0;
		DataSet ds = dao.dataSet(sql);
		count = new Long(ds.getRowData(0).get(0)+"");
		return count;
	}
	/**
	 * 根据sql查询数据的记录数
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	@AConnection
	public long count(Sql sql) throws Exception{
		return dao.count(sql);
	}
	
	/**
	 * 基于持久化类对应的数据表，查询数据
	 * @return
	 * @throws Exception
	 */
	@AConnection
	public List<?> search() throws Exception{
		return dao.search(this.pojoClass);
	}
	
	/**
	 * 基于持久化类对应的数据表，根据sql，查询数据
	 * @param sql
	 * @return
	 * @throws Exception 
	 */
	@AConnection
	public List<Map<String,Object>> searchMap(String sql) throws Exception {
		DataSet ds = dao.dataSet(sql);
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		List<String> title = ds.getTitle();
		while(ds.next()){
			Map<String,Object> map = new HashMap<String,Object>();
			for(String t:title){
				map.put(t, ds.getData(t));
			}
			list.add(map);
		}
		return list;
	}
	
	/**
	 * 基于持久化类对应的数据表，根据sql，查询数据
	 * @param sql
	 * @return
	 * @throws Exception 
	 */
	@AConnection
	public List<Map<String,Object>> searchMap(Sql sql) throws Exception {
		DataSet ds = dao.dataSet(sql);
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		List<String> title = ds.getTitle();
		while(ds.next()){
			Map<String,Object> map = new HashMap<String,Object>();
			for(String t:title){
				map.put(t, ds.getData(t));
			}
			list.add(map);
		}
		return list;
	}
	
	/**
	 * 基于持久化类对应的数据表，根据sql，查询数据
	 * @param sql
	 * @return
	 * @throws Exception 
	 */
	@AConnection
	public List<?> search(String sql) throws Exception {
		return dao.search(this.pojoClass,sql);
	}
	
	/**
	 * 基于持久化类对应的数据表，根据sql，查询数据
	 * @param sql
	 * @return
	 * @throws Exception 
	 */
	@AConnection
	public List<?> search(Sql sql) throws Exception {
		return dao.search(this.pojoClass,sql);
	}
	
	/**
	 * 根据sql，查询数据,返回cls类的对象集合
	 * @param sql
	 * @return
	 * @throws Exception 
	 */
	@AConnection
	public List<?> search(Class<?> cls, String sql) throws Exception {
		return dao.search(cls,sql);
	}
	
	/**
	 * 根据sql，查询数据,返回cls类的对象集合
	 * @param sql
	 * @return
	 * @throws Exception 
	 */
	@AConnection
	public List<?> search(Class<?> cls, Sql sql) throws Exception {
		return dao.search(cls,sql);
	}
	
	/**
	 * 根据sql查询数据
	 * @param sql
	 * @return
	 * @throws Exception 
	 */
	@AConnection
	public DataSet searchDataSet(String sql) throws Exception{
		return dao.dataSet(sql);
	}
	
	/**
	 * 根据sql查询数据
	 * @param sql
	 * @return
	 * @throws Exception 
	 */
	@AConnection
	public DataSet searchDataSet(Sql sql) throws Exception{
		return dao.dataSet(sql);
	}
	
	/**
	 * 基于持久化类对应的数据表，查询数据,带有翻页功能
	 * @param page
	 * @param size
	 * @return
	 * @throws Exception 
	 */
	@AConnection
	public PageFormData searchPageMap(String sql,int page,int size) throws Exception{
		String low = sql.toLowerCase();
		String sqlCount = "select count(*) ";
		if(low.indexOf("order by")!= -1)
			sqlCount += sql.substring(low.indexOf("from"), low.indexOf("order by")); 
		else
			sqlCount += sql.substring(low.indexOf("from"));
		long count = this.count(sqlCount);
		if((page-1)*size >=count)
			page = 1;
		DataSet ds = dao.dataSetPage(sql, page, size);
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		List<String> title = ds.getTitle();
		while(ds.next()){
			Map<String,Object> map = new HashMap<String,Object>();
			for(String t:title){
				map.put(t, ds.getData(t));
			}
			list.add(map);
		}
		return new PageFormData(list,count,page,size);
	}
	
	/**
	 * 基于持久化类对应的数据表，查询数据,带有翻页功能
	 * @param page
	 * @param size
	 * @return
	 * @throws Exception 
	 */
	@AConnection
	public PageFormData searchPageMap(Sql _sql,int page,int size) throws Exception{
		Sql cSql = _sql.clone();
		String select = "select count(*) "+cSql.getSqlBlock().substring(cSql.getSqlBlock().indexOf(" from "));
		cSql.setSql(select);
		long count = this.count(cSql);
		if((page-1)*size >=count)
			page = 1;
		DataSet ds = dao.dataSetPage(_sql, page, size);
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		List<String> title = ds.getTitle();
		while(ds.next()){
			Map<String,Object> map = new HashMap<String,Object>();
			for(String t:title){
				map.put(t, ds.getData(t));
			}
			list.add(map);
		}
		return new PageFormData(list,count,page,size);
	}
	
	/**
	 * 基于持久化类对应的数据表，查询数据,带有翻页功能
	 * @param page
	 * @param size
	 * @return
	 * @throws Exception 
	 */
	@AConnection
	public PageFormData searchPage(int page,int size) throws Exception{
		long count = this.count();
		if((page-1)*size >=count)
			page = 1;
		List<?> list = dao.searchPage(this.pojoClass, page, size);
		return new PageFormData(list,count,page,size);
	}
	
	/**
	 * 基于持久化类对应的数据表，根据sql查询数据，带有翻页功能
	 * @param sql
	 * @param page
	 * @param size
	 * @return
	 * @throws Exception 
	 */
	@AConnection
	public PageFormData searchPage(String sql,int page,int size) throws Exception{
		String low = sql.toLowerCase();
		String sqlCount = "select count(*) ";
		if(low.indexOf("order by")!= -1)
			sqlCount += sql.substring(low.indexOf("from"), low.indexOf("order by")); 
		else
			sqlCount += sql.substring(low.indexOf("from"));
		long count = this.count(sqlCount);
		if((page-1)*size >=count)
			page = 1;
		List<?> list = dao.searchPage(this.pojoClass,sql, page, size);
		return new PageFormData(list,count,page,size);
	}
	
	/**
	 * 基于持久化类对应的数据表，根据sql查询数据，带有翻页功能
	 * @param sql
	 * @param page
	 * @param size
	 * @return
	 * @throws Exception 
	 */
	@AConnection
	public PageFormData searchPage(Sql _sql,int page,int size) throws Exception{
		Sql cSql = _sql.clone();
		String select = "select count(*) "+cSql.getSqlBlock().substring(cSql.getSqlBlock().indexOf(" from "));
		cSql.setSql(select);
		long count = this.count(cSql);
		if((page-1)*size >=count)
			page = 1;
		List<?> list = dao.searchPage(this.pojoClass,_sql, page, size);
		return new PageFormData(list,count,page,size);
	}
	
	/**
	 * 基于持久化类对应的数据表，根据sql查询数据，带有翻页功能
	 * @param cls 持久化类
	 * @param sql 查询语句
	 * @param page 页码
	 * @param size 每页记录数
	 * @return
	 * @throws Exception 
	 */
	@AConnection
	public PageFormData searchPage(Class<?> cls, String sql,int page,int size) throws Exception{
		String low = sql.toLowerCase();
		String sqlCount = "select count(*) ";
		if(low.indexOf("order by")!= -1)
			sqlCount += sql.substring(low.indexOf("from"), low.indexOf("order by")); 
		else
			sqlCount += sql.substring(low.indexOf("from"));
		long count = this.count(sqlCount);
		if((page-1)*size >=count)
			page = 1;
		List<?> list = dao.searchPage(cls,sql, page, size);
		return new PageFormData(list,count,page,size);
	}
	
	/**
	 * 基于持久化类对应的数据表，根据sql查询数据，带有翻页功能
	 * @param cls 持久化类
	 * @param sql 查询语句
	 * @param page 页码
	 * @param size 每页记录数
	 * @return
	 * @throws Exception 
	 */
	@AConnection
	public PageFormData searchPage(Class<?> cls, Sql _sql,int page,int size) throws Exception{
		Sql cSql = _sql.clone();
		String select = "select count(*) "+cSql.getSqlBlock().substring(cSql.getSqlBlock().indexOf(" from "));
		cSql.setSql(select);
		long count = this.count(cSql);
		if((page-1)*size >=count)
			page = 1;
		List<?> list = dao.searchPage(cls,_sql, page, size);
		return new PageFormData(list,count,page,size);
	}
	
	@AConnection
	public PageFormData searchPageUnion(String _sql,int page,int size) throws Exception{
		String cSql = _sql.toLowerCase();
		String select = "select count(*) from ( "+cSql+" ) a";
		long count = this.count(select);
		if((page-1)*size >=count)
			page = 1;
		List<?> list = dao.searchPage(this.pojoClass,_sql, page, size);
		return new PageFormData(list,count,page,size);
	}
	
	/**
	 * 根据sql查询数据，带有翻页功能
	 * @param sql
	 * @param page
	 * @param size
	 * @return
	 * @throws Exception 
	 */
	@AConnection
	public PageFormData searchDataSetPage(String sql,int page,int size) throws Exception{
		String low = sql.toLowerCase();
		String sqlCount = "select count(*) " + sql.substring(low.indexOf("from"),low.indexOf("order by")); 
		long count = this.count(sqlCount);
		if((page-1)*size >=count)
			page = 1;
		DataSet ds = dao.dataSetPage(sql, page, size);
		return new PageFormData(ds,count,page,size);
	}
	
	/**
	 * 根据sql查询数据，带有翻页功能
	 * @param sql
	 * @param page
	 * @param size
	 * @return
	 * @throws Exception 
	 */
	@AConnection
	public PageFormData searchDataSetPage(Sql _sql,int page,int size) throws Exception{
		Sql cSql = _sql.clone();
		String select = "select count(*) "+cSql.getSqlBlock().substring(cSql.getSqlBlock().indexOf(" from "));
		cSql.setSql(select);
		long count = this.count(cSql);
		if((page-1)*size >=count)
			page = 1;
		DataSet ds = dao.dataSetPage(_sql, page, size);
		return new PageFormData(ds,count,page,size);
	}
	
	/**
	 * 保存持久化对象到数据库
	 * @param obj
	 * @return
	 */
	@ATransaction
	public Serializable save(Object obj)throws Exception{
		if(obj == null)
			return null;
		Serializable[] ids = this.save(new Object[]{obj});
		if(ids != null)
			return ids[0];
		return null;
	}
	
	/**
	 * 保存持久化对象到数据库及缓存中
	 * @param obj
	 * @return
	 */
	@ATransaction
	public Serializable saveCache(Object obj)throws Exception{
		if(obj == null)
			return null;
		Serializable[] ids = this.saveCache(new Object[]{obj});
		if(ids != null)
			return ids[0];
		return null;
	}

	/**
	 * 保存持久化对象到数据库
	 * @param objs
	 * @return
	 */
	@ATransaction
	public Serializable[] save(Object[] objs)throws Exception{
		if(objs == null || objs.length == 0)
			return null;
		Serializable[] ids = null;
		if(this.action == null) action = Constants.LOG_WRITE;
		table = BeansFactory.getBean(objs[0].getClass()).getTable();
		for(int i=0,count =objs.length; i < count; ++i)
			processObject(objs[i]);
		ids = dao.add(objs);
		result += LogsUtil.getBatchSaveString(objs);
		return ids;
	}
	
	/**
	 * 保存持久化对象到数据库及缓存中
	 * @param objs
	 * @return
	 */
	@ATransaction
	public Serializable[] saveCache(Object[] objs)throws Exception{
		if(objs == null || objs.length == 0)
			return null;
		Serializable[] ids = null;
		if(this.action == null) action = Constants.LOG_WRITE;
		table = BeansFactory.getBean(objs[0].getClass()).getTable();
		for(int i=0,count =objs.length; i < count; ++i)
			processObject(objs[i]);
		ids = dao.addCache(objs);
		result += LogsUtil.getBatchSaveString(objs);
		return ids;
	}
	
	/**
	 * 删除记录，根据主键值
	 * @param id 主键值
	 * @return
	 */
	@ATransaction
	public boolean deleteById(Serializable id)throws Exception{
		return this.deleteById(new Serializable[]{id});
	}
	/**
	 * 根据主键值从数据库及缓存中删除一条记录
	 * @param id 主键值
	 * @return
	 */
	@ATransaction
	public boolean deleteCacheById(Serializable id)throws Exception{
		return this.deleteCacheById(new Serializable[]{id});
	}
	
	/**
	 * 删除对象，根据对象的主键值
	 * @param obj 已经被持久化的对象
	 * @return
	 */
	@ATransaction
	public boolean delete(Object obj)throws Exception{
		if(obj == null)
			return false;
		if(this.action == null) action = Constants.LOG_DELETE;
		CarpBean bean = BeansFactory.getBean(obj.getClass());
		table = bean.getTable();
		Object o = dao.find(obj.getClass(), bean.getPrimaryValue(obj));
		dao.delete(obj);
		result += LogsUtil.getDeleteString(o);
		return true;
	}
	/**
	 * 从数据库及缓存中删除一个对象
	 * @param obj 已经被持久化的对象
	 * @return
	 */
	@ATransaction
	public boolean deleteCache(Object obj)throws Exception{
		if(obj == null)
			return false;
		if(this.action == null) action = Constants.LOG_DELETE;
		CarpBean bean = BeansFactory.getBean(obj.getClass());
		table = bean.getTable();
		Object o = dao.find(obj.getClass(), bean.getPrimaryValue(obj));
		dao.deleteCache(obj);
		result += LogsUtil.getDeleteString(o);
		return true;
	}
	
	/**
	 * 删除对象，根据对象的主键值
	 * @param obj 已经被持久化的对象
	 * @return
	 */
	@ATransaction
	public boolean delete(Object[] objs)throws Exception{
		if(objs == null || objs.length==0)
			return false;
		Class<?> cls = this.pojoClass;
		try{
			if(this.action == null) action = Constants.LOG_DELETE;
			CarpBean bean = BeansFactory.getBean(objs[0].getClass());
			table = bean.getTable();
			Serializable[] ids = new java.io.SerializablePermission[objs.length];
			for(int i = 0, count = objs.length; i < count; ++i)
				ids[i] = bean.getPrimaryValue(objs[i]);
			this.pojoClass =  objs[0].getClass();
			return this.deleteById(ids);
		}finally{
			this.pojoClass = cls;
		}
	}
	/**
	 * 从数据库及缓存中删除一组对象
	 * @param obj 已经被持久化的对象
	 * @return
	 */
	@ATransaction
	public boolean deleteCache(Object[] objs)throws Exception{
		if(objs == null || objs.length==0)
			return false;
		Class<?> cls = this.pojoClass;
		try{
			if(this.action == null) action = Constants.LOG_DELETE;
			CarpBean bean = BeansFactory.getBean(objs[0].getClass());
			table = bean.getTable();
			Serializable[] ids = new java.io.SerializablePermission[objs.length];
			for(int i = 0, count = objs.length; i < count; ++i)
				ids[i] = bean.getPrimaryValue(objs[i]);
			this.pojoClass =  objs[0].getClass();
			return this.deleteCacheById(ids);
		}finally{
			this.pojoClass = cls;
		}
	}
	
	/**
	 * 删除记录，根据主键值
	 * @param ids 主键值(数组)
	 * @return
	 */
	@ATransaction
	public boolean deleteById(Serializable[] ids)throws Exception{
		if(ids == null || ids.length == 0)
			return true;
		if(this.action == null) action = Constants.LOG_DELETE;
		CarpBean bean = BeansFactory.getBean(this.pojoClass);
		table = bean.getTable();
		StringBuilder sql = new StringBuilder("select * from "+table+" where ");
		String pk = bean.getPrimarys().get(0).getColName();
		for(int i=0; i<ids.length; ++i){
			if(i!=0)
				sql.append(" or ");
			sql.append(pk);
			sql.append(" = ");
			if (bean.getPrimarys().get(0).getFieldType().equals(String.class))
				sql.append("'"+ids[i]+"'");
			else
				sql.append(ids[i]);
		}
		Object[] obj = dao.search(this.pojoClass, sql.toString()).toArray();
		dao.delete(this.pojoClass, ids);
		result += LogsUtil.getBatchDeleteString(obj);
		return true;
	}
	/**
	 * 根据主键值集合从数据库及缓存中删除一组记录
	 * @param ids 主键值集合(数组)
	 * @return
	 */
	@ATransaction
	public boolean deleteCacheById(Serializable[] ids)throws Exception{
		if(ids == null || ids.length == 0)
			return true;
		if(this.action == null) action = Constants.LOG_DELETE;
		CarpBean bean = BeansFactory.getBean(this.pojoClass);
		table = bean.getTable();
		StringBuilder sql = new StringBuilder("select * from "+table+" where ");
		String pk = bean.getPrimarys().get(0).getColName();
		for(int i=0; i<ids.length; ++i){
			if(i!=0)
				sql.append(" or ");
			sql.append(pk);
			sql.append(" = ");
			if (bean.getPrimarys().get(0).getFieldType().equals(String.class))
				sql.append("'"+ids[i]+"'");
			else
				sql.append(ids[i]);
		}
		Object[] obj = dao.search(this.pojoClass, sql.toString()).toArray();
		dao.deleteCache(this.pojoClass, ids);
		result += LogsUtil.getBatchDeleteString(obj);
		return true;
	}
	
	/**
	 * 根据主键，查找pojoClass类对应的对象
	 * @param id
	 * @return 对象
	 */
	@AConnection
	public Object findByIdFromCache(Serializable id)throws Exception{
		if(id == null){
			logger.info(this.pojoClass + " id's value was null,but id's value could not null!");
			return null;
		}
		return dao.findCache(this.pojoClass, id);
	}
	
	/**
	 * 根据主键，查找pojoClass类对应的对象
	 * @param id
	 * @return 对象
	 */
	@AConnection
	public Object findById(Serializable id)throws Exception{
		if(id == null){
			logger.info(this.pojoClass + " id's value was null,but id's value could not null!");
			return null;
		}
		return dao.find(this.pojoClass, id);
	}
	
	/**
	 * 根据对象（对象信息不完整，一般主要该对象需要具有主键值），查找该对象对应的数据库记录对象
	 * @param id
	 * @return
	 */
	@AConnection
	public Object findByObj(Object obj)throws Exception{
		if(obj == null){
			logger.info(" Object was null,but object could not null!");
			return null;
		}
		CarpBean bean = BeansFactory.getBean(obj.getClass());
		return dao.find(obj.getClass(), bean.getPrimaryValue(obj));
	}
	/**
	 * 根据对象（对象信息不完整，一般主要该对象需要具有主键值），查找该对象对应的数据库记录对象
	 * @param id
	 * @return
	 */
	@AConnection
	public Object findByObjFromCache(Object obj)throws Exception{
		if(obj == null){
			logger.info(" Object was null,but object could not null!");
			return null;
		}
		CarpBean bean = BeansFactory.getBean(obj.getClass());
		return dao.findCache(obj.getClass(), bean.getPrimaryValue(obj));
	}
	
	/**
	 * 加载类cls的字典类的相关数据
	 * @param cls 类
	 * @return cls对象
	 * @throws Exception
	 */
	@AConnection
	public Object initDicData(Object obj)throws Exception{
		if(obj == null)
			return null;
		CarpBean bean = BeansFactory.getBean(obj.getClass());
		List<DICMetadata> dics = bean.getDics();
		if(dics != null && dics.size()>0)
			for(DICMetadata dic : dics){
				dic.setValue(obj, this.search(dic.getDicClass(), dic.getSql()));
			}
		return obj;
	}
	
	/**
	 * 更新对象记录
	 * @param objs
	 * @return
	 * @throws Exception 
	 */
	@ATransaction
	public boolean update(Object[] objs)throws Exception{
		if(objs==null || objs.length==0)
			return true;
		if(this.action == null) action = Constants.LOG_UPDATE;
		CarpBean bean = BeansFactory.getBean(objs[0].getClass());
		String sql = getSelectSqlByUpdateObject(bean, objs);
		Object[] obj = dao.search(objs[0].getClass(), sql).toArray();
		Map<String,Object> map = new HashMap<String,Object>();
		updateMap(bean,map,obj);
		this.processUpdateObject(objs);
		dao.update(objs);
		recordLog(bean,map,objs);//result += LogsUtil.getUpdateString(obj, objs);
		return true;
	}
	
	/**
	 * 更新数据库及缓存中的一组对象记录
	 * @param objs
	 * @return
	 * @throws Exception 
	 */
	@ATransaction
	public boolean updateCache(Object[] objs)throws Exception{
		if(objs==null || objs.length==0)
			return true;
		if(this.action == null) action = Constants.LOG_UPDATE;
		CarpBean bean = BeansFactory.getBean(objs[0].getClass());
		String sql = getSelectSqlByUpdateObject(bean, objs);
		Object[] obj = dao.search(objs[0].getClass(), sql).toArray();
		Map<String,Object> map = new HashMap<String,Object>();
		updateMap(bean,map,obj);
		this.processUpdateObject(objs);
		dao.updateCache(objs);
		recordLog(bean,map,objs);//result += LogsUtil.getUpdateString(obj, objs);
		return true;
	}
	
	/**
	 * 更新前、更新后数据，暂存在result中，供保存日志使用。
	 * @param bean
	 * @param map
	 * @param objs
	 */
	private void recordLog(CarpBean bean,Map<String,Object> map,Object[] objs){
		for(int i=0; i<objs.length; ++i){
			Object beforeObj = map.get(bean.getPrimaryValue(objs[i])+"");
			result += LogsUtil.getUpdateString(beforeObj, objs[i]);
		}
	}
	
	/**
	 * 将原始记录数据放入Map中
	 * @param bean
	 * @param map
	 * @param obj
	 */
	private void updateMap(CarpBean bean,Map<String,Object> map,Object[] obj){
		for(int i=0; i<obj.length; ++i){
			map.put(bean.getPrimaryValue(obj[i])+"", obj[i]);
		}
	}
	
	//根据更新对象，生成这些对象的select sql语句，用于查询原始数据。
	private String getSelectSqlByUpdateObject(CarpBean bean, Object[] objs) throws CarpException{
		table = bean.getTable();
		StringBuilder sql = new StringBuilder("select * from  "+table+"  where  ");
		String pk = bean.getPrimarys().get(0).getColName();
		for(int i=0; i<objs.length; ++i){
			if(i!=0)
				sql.append(" or ");
			sql.append(pk);
			sql.append(" = ");
			if (bean.getPrimarys().get(0).getFieldType().equals(String.class))
				sql.append("'"+bean.getPrimaryValue(objs[i])+"'");
			else
				sql.append(bean.getPrimaryValue(objs[i]));
		}
		return sql.toString();
	}
	/**
	 * 更新对象记录
	 * @param obj
	 * @return
	 * @throws Exception 
	 */
	@ATransaction
	public boolean update(Object obj) throws Exception{
		return this.update(new Object[]{obj});
	}
	/**
	 * 更新数据库及缓存中的一条对象记录
	 * @param obj
	 * @return
	 * @throws Exception 
	 */
	@ATransaction
	public boolean updateCache(Object obj) throws Exception{
		return this.updateCache(new Object[]{obj});
	}
	
	/**
	 * 根据sql（查询sql），确定查询的记录是否存在。
	 * @param sql 一般sql语句写法为，select * from table.注意:不用使用count函数。否则返回值一定为true.
	 * @return
	 * @throws Exception 
	 */
	@AConnection
	public boolean exist(String sql) throws Exception{
		boolean bool = false;
		DataSet ds = dao.dataSet(sql);
		if(ds.count()!=0)
			bool = true;
		return bool;
	}
	
	/**
	 * 根据sql（查询sql），确定查询的记录是否存在。
	 * @param sql 一般sql语句写法为，select * from table.注意:不用使用count函数。否则返回值一定为true.
	 * @return
	 * @throws Exception 
	 */
	@AConnection
	public boolean exist(Sql sql) throws Exception{
		boolean bool = false;
		DataSet ds = dao.dataSet(sql);
		if(ds.count()!=0)
			bool = true;
		return bool;
	}
	
	/**
	 * 根据sql（查询sql），返回第一条记录的第一个字段值。
	 * @param sql
	 * @return
	 * @throws Exception 
	 */
	@AConnection
	public Object getColumnData(String sql)throws Exception{
		Object o = null;
		DataSet ds = dao.dataSet(sql);
		if(ds.count()!=0)
			o = ds.getRowData(0).get(0);
		return o;
	}
	/**
	 * 根据sql（查询sql），返回第一条记录的第一个字段值。
	 * @param sql
	 * @return
	 * @throws Exception 
	 */
	@AConnection
	public Object getColumnData(Sql sql)throws Exception{
		Object o = null;
		DataSet ds = dao.dataSet(sql);
		if(ds.count()!=0)
			o = ds.getRowData(0).get(0);
		return o;
	}
	
	/**
	 * 执行insert、update、delete操作的Sql语句
	 * @param sql
	 * @return
	 * @throws Exception 
	 */
	@ATransaction
	public boolean executeSql(String sql)throws Exception{
		String _sql = sql.toLowerCase().trim();
		if(_sql.startsWith("delete"))
			action = Constants.LOG_DELETE;
		else if(_sql.startsWith("update"))
			action = Constants.LOG_UPDATE;
		else
			action = Constants.LOG_WRITE;
		sql = sql.trim()
//				.toLowerCase()
				;
		if(sql.startsWith("delete")){
			table = sql.substring(sql.indexOf("from")+5).trim().split(" ")[0];
		}else{
			table = sql.substring(7).trim().split(" ")[0];
		}
		dao.executeUpdate(sql);
		return true;
	}
	
	/**
	 * 执行insert、update、delete操作的Sql语句
	 * @param sql
	 * @return
	 * @throws Exception 
	 */
	@ATransaction
	public boolean executeSql(Sql sql)throws Exception{
		String _sql = sql.getSql();
		if(_sql.startsWith("delete"))
			action = Constants.LOG_DELETE;
		else if(_sql.startsWith("update"))
			action = Constants.LOG_UPDATE;
		else
			action = Constants.LOG_WRITE;
		_sql = _sql.trim().toLowerCase();
		if(_sql.startsWith("delete")){
			table = _sql.substring(_sql.indexOf("from")+5).trim().split(" ")[0];
		}else{
			table = _sql.substring(7).trim().split(" ")[0];
		}
		dao.executeUpdate(sql);
		return true;
	}
	
	/**
	 * 处理需要保存的对象，完成默认属性值
	 * @param obj
	 */
	private void processObject(Object obj){
		ObjectUtil.setValue(obj, "setStsTime", new Class[]{Date.class}, new Object[]{new Date()});
		try {
			Object o = ObjectUtil.getFieldValue(obj, "sts");
			if(o==null||"".equals(o)){
				ObjectUtil.setValue(obj, "setSts", new Class[]{String.class}, new Object[]{"ACTV"});
			}
		} catch (Exception e) {
			//e.printStackTrace();
		}
		LoginUser user = (LoginUser)LoginUser.userThread.get();
		if(user != null ){
			ObjectUtil.setValue(obj, "setOperator", new Class[]{String.class}, new Object[]{user.getUserId()});
		}
	}
	
	/**
	 * 处理需要更新的对象，完成修改时间、操作人等数据的赋值。
	 * @param objs
	 */
	private void processUpdateObject(Object[] objs){
		LoginUser user = (LoginUser)LoginUser.userThread.get();
		for(int i=0; i<objs.length; ++i){
			ObjectUtil.setValue(objs[i], "setStsTime", new Class[]{Date.class}, new Object[]{new Date()});
			if(user != null ){
				ObjectUtil.setValue(objs[i], "setOperator", new Class[]{String.class}, new Object[]{user.getUserId()});
			}
		}
	}
	
	/**
	 * 保存对象到缓存中
	 * @param key 键值
	 * @param o   待保存对象
	 */
	public void saveToCache(String key,Object o){
		CachedClient.getClient().set(key, o);
	}
	/**
	 * 保存对象到缓存中
	 * @param key 键值
	 * @param o   待保存对象
	 */
	public void saveToCache(String key,Map<?,?> m){
		CachedClient.getClient().set(key, m);
	}
	
	/**
	 * 从缓存中取得保存的对象
	 * @param key 键值
	 * @return
	 */
	public Object getObjectFromCache(String key){
		return CachedClient.getClient().get(key);
	}
	
	/**
	 * 更新缓存中的对象
	 * @param key 键值
	 * @param o  待更新对象 
	 */
	public void updateToCache(String key,Object o){
		CachedClient.getClient().replace(key, o);
	}
	
	/**
	 * 更新缓存中的对象
	 * @param key 键值
	 * @param o  待更新对象
	 */
	public void updateToCache(String key,Map<?,?> m){
		CachedClient.getClient().replace(key, m);
	}
	
	/**
	 * 删除缓存中的对象
	 * @param key
	 */
	public void deleteFromCache(String key){
		CachedClient.getClient().delete(key);
	}
	
	public String getResult() {
		return result;
	}
	
	public String getFileName(String path,Integer id){
		String fileName = "";
		String dateStr = new SimpleDateFormat("yyMMdd").format(new Date());
		int result = 1000 + new Double(Math.random() * 8999).intValue();
		fileName = dateStr+"."+id+"."+result;
		if(new File(path+fileName).exists()){
			fileName = getFileName(path,id);
		}
		return fileName;
	}
}
