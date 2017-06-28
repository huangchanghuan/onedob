package com.sandra.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 参数类，一次加载全部参数，供后续实用
 * @author Administrator
 *
 */
public class SysParametersUtil {
	private final static Map<String,String> map = new HashMap<String,String>();
	static{
		load();
	}
	/**
	 * 全部加载
	 */
	public static void load(){
		map.clear();
		try{
			//ResultSet ds = SessionUtil.getSession().creatDataSetQuery("select * from ss_sys_parameters where sts='ACTV'").resultSet();
			//ds.beforeFirst();
			//while(ds.next()){
			//	map.put(ds.getString("PARAMETER"), ds.getString("VALUE"));
			//}
		}catch(Exception ex){}
	}
	
	/**
	 * 获取参数值
	 * @param parameter 参数
	 * @return
	 */
	public static String getValue(String parameter){
		return map.get(parameter);
	}
	
	/**
	 * 获取参数值
	 * @param parameter 参数
	 * @return
	 */
	public static Integer getValueInt(String parameter){
		try{
			return Integer.parseInt(map.get(parameter));
		}catch(Exception e){
			
		}
		return null;
	}
	
	/**
	 * 获取参数值
	 * @param parameter 参数
	 * @return
	 */
	public static Boolean getValueBool(String parameter){
		try{
			return Boolean.parseBoolean(map.get(parameter));
		}catch(Exception e){
			
		}
		return null;
	}
}
