package sos.cfg;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.sunstar.adms.sysmanage.pojo.SysParameters;
import com.sunstar.sos.factory.BpoFactory;

/**
 * 是否启用权限、数据、操作、sql显示、BPOProxy等日志记录
 * @author zhou
 */
public class SystemConfig {
	private static final Logger logger = Logger.getLogger(SystemConfig.class);
	private static boolean permissionlog = true;
	private static boolean datalogs = true;
	private static boolean sqlconfig = true;
	private static boolean bpoproxy = true;
	private static String  logLocation = "db";
	
	static{
		refresh();
	}
	
	public static void refresh(){
		try {
			String sql = "select * from ss_sys_parameters where ";
			sql += "parameter in ('DATA_LOG','SQL_CONFIG_LOG','PERMISSION_LOG','BPO_PROXY_LOG','LOG_OUTPUT_LOCATION')";
			List<?> list = BpoFactory.getBpo().search(SysParameters.class, sql);
			Map<String,String> map = new HashMap<String,String>();
			for(Object o : list){
				SysParameters p = (SysParameters)o;
				map.put(p.getParameter(),p.getValue());
			}
			permissionlog = new Boolean(map.get("PERMISSION_LOG")!=null?map.get("PERMISSION_LOG"):"true");
			datalogs = new Boolean(map.get("DATA_LOG")!=null?map.get("DATA_LOG"):"true");
			sqlconfig = new Boolean(map.get("SQL_CONFIG_LOG")!=null?map.get("SQL_CONFIG_LOG"):"true");
			bpoproxy = new Boolean(map.get("BPO_PROXY_LOG")!=null?map.get("BPO_PROXY_LOG"):"true");
			logLocation = (map.get("LOG_OUTPUT_LOCATION")!=null?map.get("LOG_OUTPUT_LOCATION"):"DB").toLowerCase();
			logger.info("Permission log : " + permissionlog);
			logger.info("data logs recode : " + datalogs);
			logger.info("sql config : " + sqlconfig);
			logger.info("bpo proxy : " + bpoproxy);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 是否需要数据日志，指把有变动的数据存储起来
	 * @return
	 */
	public static boolean isDatalogs() {
		return datalogs;
	}
	
	/**
	 * 当启用权限控制时，是否显示权限log
	 * @return
	 */
	public static boolean isPermissionlog() {
		return permissionlog;
	}

	/**
	 * 是否需要显示sql
	 * @return
	 */
	public static boolean isSqlconfig() {
		return sqlconfig;
	}
	
	/**
	 * 是否需要显示bpo proxy 对象的调用日志记录
	 * @return
	 */
	public static boolean isBpoproxy() {
		return bpoproxy;
	}
	
	public static String logLocation(){
		return logLocation;
	}
}
