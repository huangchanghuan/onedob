package sos.permission;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.carp.DataSet;

import com.sunstar.adms.usermanage.pojo.SysUsers;
import com.sunstar.sos.cfg.SqlConfig;
import com.sunstar.sos.cfg.SystemConfig;
import com.sunstar.sos.factory.BpoFactory;

/**
 * 用户权限
 * @author zhou
 */
public class Permissions implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(Permissions.class);

	//模块权限
	private Set<String> moduleSet = new HashSet<String>();
	//模块-功能-方法的映射map
	private Map<String,Set<String>> permsMap = new HashMap<String,Set<String>>();
	//功能-方法的映射map
	private Set<String> permSet = new HashSet<String>();
	
	/**
	 * 取得用户所拥有的模块权限
	 * @param user
	 * @throws Exception 
	 */
	public Permissions(SysUsers vo) throws Exception {
		DataSet ds = BpoFactory.getBpo().searchDataSet(SqlConfig.getSQL("UsersManage/queryPermission", vo, null));
		while(ds.next()){
			String moduleId = ds.getData("module_id").toString();
			String funcName = ds.getData("func_name").toString();
			String ns = ds.getData("name_space").toString();
			String action = ds.getData("action_name").toString();
			String method = ds.getData("method_name").toString();
			String url = ns+"/"+action+"/"+method;
			
			if(permsMap.get(funcName) == null)//模块功能未加载
				permsMap.put(funcName, new HashSet<String>());
			if(!permsMap.get(funcName).contains(url))//模块URL未加载
				permsMap.get(funcName).add(url);
			if(SystemConfig.isPermissionlog())
				logger.info("模块功能名称: "+funcName+"; 访问URL: "+url);
			permSet.add(url);
			if(!moduleSet.contains(moduleId)){
				moduleSet.add(moduleId);
			}
		}
	}
	
	/**
	 * 判断用户是否具有操作权限，对某个路径的访问。
	 * @param namespace 命名空间
	 * @param action    action
	 * @param cmd       action方法
	 * @return true/false  有/无
	 */
	public boolean hasRight(String funcName){
		if("all".equals(LoginUser.userThread.get().getUserName()))
				return true;
		if(SystemConfig.isPermissionlog())
			logger.info("业务功能名称: "+funcName);
		return permsMap.containsKey(funcName);
	}
	
	/**
	 * 判断用户是否具有操作权限，对某个路径的访问。
	 * @param namespace 命名空间
	 * @param action    action
	 * @param cmd       action方法
	 * @return true/false  有/无
	 */
	public boolean hasRight(String namespace,String action,String cmd){
		if("modules".equals(action) && "menu".equals(cmd))
			return true;
		boolean b = permSet.contains(namespace+"/"+action+"/"+cmd);
		if(!b){
			logger.info("请配置Action:"+namespace+"/"+action+"的方法："+cmd+" 对应的模块业务功能。");
			return false;
		}
		return b;
	}
	
	/**
	 * 判断用户是否具有操作权限，对某个路径的访问。
	 * @param namespace 命名空间
	 * @param action    action
	 * @param cmd       action方法
	 * @return true/false  有/无
	 */
	public boolean hasRight(String funcName, String namespace,String action,String cmd){
		if("modules".equals(action) && "menu".equals(cmd))
			return true;
		Set<String> set = permsMap.get(funcName);
		if(set == null){
			logger.info("请配置Action:"+namespace+"/"+action+"的方法："+cmd+" 对应的模块业务功能。");
			return false;
		}
		if(SystemConfig.isPermissionlog())
			logger.info("业务功能名称: "+funcName+", 路径："+namespace+"/"+action+"  方法 : "+cmd+" ,  可访问路径集合:"+set);
		return set.contains(namespace+"/"+action+"/"+cmd);
	}
	
	
	public boolean hasModuleRight(String moduleId){
		return moduleSet.contains(moduleId);
	}
//	/**
//	 * 是否有该模块( ModuleId) 的权限 (right)
//	 * @param moduleId 模块ID
//	 * @param right  功能名
//	 * @return
//	 */
//	public boolean hasOptRight(String moduleId, String funcName){
//		Set<String> map = permsMap.get(moduleId);
//		if(SystemConfig.isPermissionlog())
//			logger.info("模块Id: "+moduleId+", 功能 : "+funcName+" ,  权限集合: "+map);
//		if(map==null)
//			return false;
//		return map.contains(funcName);
//	}
}
