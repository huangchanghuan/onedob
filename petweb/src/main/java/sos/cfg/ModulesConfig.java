package sos.cfg;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.carp.DataSet;

import com.sunstar.sos.factory.BpoFactory;

/**
 * 模块-功能-方法加载类
 * @author zhou
 *
 */
public class ModulesConfig {
	private static final Logger logger = Logger.getLogger(ModulesConfig.class);
	//访问路径与模块ID
	private static final Map<String,String> map = new java.util.concurrent.ConcurrentHashMap<String, String>();
	//模块ID与模块名称
	private static final Map<String,String> moduleMap = new java.util.concurrent.ConcurrentHashMap<String, String>();
	//访问路径与action方法
	private static final Map<String,String> urlMap = new java.util.concurrent.ConcurrentHashMap<String, String>();
	//action、action方法与功能
	private static final Map<String,String> funcMap = new java.util.concurrent.ConcurrentHashMap<String, String>();

	/**
	 * 初始化加载
	 */
	static{
		loading();
	}
	
	/**
	 * 加载模块-功能-方法
	 */
	public static void loading(){
		try{
			map.clear();
			urlMap.clear();
			moduleMap.clear();
			funcMap.clear();
			DataSet	ds = BpoFactory.getBpo().searchDataSet(SqlConfig.getSQL("SYSTEM/moduleFuncsConfig").getSql());
			while(ds.next()){
				String id = ""+ds.getData("module_id");
				//访问路径，不含action方法
				String url = ""+ds.getData("name_space")+"/"+ds.getData("action_name");
				String func = ""+ds.getData("func_name");
				String name = ds.getData("module_name")+"["+ds.getData("module_cname")+"] -- "+func;
				//访问路径 带有actin方法，包含路径信息
				String method = ds.getData("name_space")+"/"+ds.getData("action_name")+"/"+ds.getData("method_name");
				urlMap.put(method, func);//将访问路径放入urlMap中，用于验证url的正确性
				moduleMap.put(method, name);//将访问路径放入moduleMap中，用于获取url对应的模块名称
				if(funcMap.get(func) == null){
					funcMap.put(func, url);//业务功能名称，用于获取访问的url
				}
				if(map.get(url) == null)
					map.put(url, id);//将访问路径(不带action方法)放入map中，用于验证url的正确性
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	/**
	 * 判断模块是否已经为业务功能定义方法映射
	 * @param url 访问连接
	 * @param method 方法名称
	 * @return
	 */
	public static boolean isDefineFuncMap(String namespace,String action,String method){
		if("login".equals(action))
			return true;
		if("modules".equals(action) && "menu".equals(method))
			return true;
		if(SystemConfig.isPermissionlog())
			logger.info("url = "+namespace+"/"+action+"/"+method);
		return urlMap.containsKey(namespace+"/"+action+"/"+method);
	}
	
	/**
	 * 路径是否存在
	 * @param url
	 * @return
	 */
	public static boolean existUrl(String namespace,String action){
		if(action.equals("login") || action.equals("modules"))
			return true;
		if(SystemConfig.isPermissionlog()){
			logger.info("本次访问路径 URL = "+namespace+"/"+action+"  \r\n当前用户的可访问路径MAP："+map);
		}
		return map.containsKey(namespace+"/"+action);
	}
	
//	/**
//	 * 根据url取模块ID
//	 * @param url
//	 * @return
//	 */
//	public static String getModuleId(String namespace,String action){
//		return map.get(namespace+"/"+action);
//	}
//	/**
//	 * 根据url取模块ID
//	 * @param url
//	 * @return
//	 */
//	public static String getModuleFunc(String namespace,String action,String method){
//		return url.get(namespace+"/"+action+"/"+method);
//	}
}
