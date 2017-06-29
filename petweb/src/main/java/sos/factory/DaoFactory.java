package sos.factory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.sunstar.sos.dao.BaseDao;



/**
 * 工厂模式，基本数据访问对象构建类
 * @author Administrator
 *
 */
public class DaoFactory{
	private static final Logger logger = Logger.getLogger(DaoFactory.class);
	private static final Map<Class<?>,Class<?>>  daoMap = new ConcurrentHashMap<Class<?>,Class<?>>();
	
	public static BaseDao getDao(){
		return getDao(null);
	}
	
	public static BaseDao getDao(Class<?> cls){
		BaseDao dao = null;
		try {
			dao = (BaseDao) getDaoClass(cls).newInstance();
		} catch (Exception e1) {}
		return dao;
	}
	
	private static Class<?> getDaoClass(Class<?> cls){
		if(cls == null)
			return BaseDao.class;
		Class<?> daoCls = daoMap.get(cls);
		if(daoCls != null)
			return daoCls;
		String clsName = cls.getName();
		int index = clsName.indexOf(".pojo.");
		String daoStr = clsName.substring(0,index)+".dao."+clsName.substring(clsName.lastIndexOf(".")+1)+"Dao";
		try {
			daoCls = Class.forName(daoStr);
		}catch(Exception e){
			daoCls = BaseDao.class;
		}
		daoMap.put(cls, daoCls);
		logger.debug("Pojo : "+cls +" ,   Dao : "+daoCls.getName());
		return daoCls;
	}
}
