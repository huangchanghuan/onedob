package com.onedob.util;

import java.util.HashMap;
import java.util.Map;

/**
 * �����࣬һ�μ���ȫ��������������ʵ��
 * @author Administrator
 *
 */
public class SysParametersUtil {
	private final static Map<String,String> map = new HashMap<String,String>();
	static{
		load();
	}
	/**
	 * ȫ������
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
	 * ��ȡ����ֵ
	 * @param parameter ����
	 * @return
	 */
	public static String getValue(String parameter){
		return map.get(parameter);
	}
	
	/**
	 * ��ȡ����ֵ
	 * @param parameter ����
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
	 * ��ȡ����ֵ
	 * @param parameter ����
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
