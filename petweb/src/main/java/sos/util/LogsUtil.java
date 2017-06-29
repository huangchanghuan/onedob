package sos.util;

import com.sunstar.sos.pojo.SysLogs;

public class LogsUtil {
	public static String getSaveString(Object obj){
		StringBuffer context = new StringBuffer("已保存数据:\r\n");
		return context.append(obj.toString()).toString();
	}
	
	public static String getBatchSaveString(Object[] obj){
		StringBuffer context = new StringBuffer("已保存数据:\r\n");
		for(int i=0; i<obj.length; ++i){
			context.append(obj[i].toString());
			context.append("\r\n");
		}
		return context.toString();
	}
	
	public static SysLogs getLogginObject(String table,String action,Object[] obj){
		SysLogs logs = new SysLogs();
		StringBuffer context = new StringBuffer("已保存数据:\r\n");
		for(int i=0; i<obj.length; ++i){
			context.append(obj[i].toString());
			context.append("\r\n");
		}
		logs.setContext(context.toString());
		logs.setTableName(table);
		logs.setActionType(action);
		return logs;
	}
	
	public static String getBatchDeleteString(Object[] obj){
		StringBuffer context = new StringBuffer("已删除数据:\r\n");
		for(int i=0; i<obj.length; ++i){
			context.append(obj[i].toString());
			context.append("\r\n");
		}
		return context.toString();
	}
	
	public static String getUpdateString(Object beforeObj,Object obj){
		StringBuffer context = new StringBuffer("修改前数据:");
		context.append(beforeObj.toString());
		context.append("\r\n修改后数据:");
		context.append(obj.toString());
		return context.toString();
	}
	
	public static String getDeleteString(Object obj){
		StringBuffer context = new StringBuffer("已删除数据:\r\n");
		return context.append(obj.toString()).toString();
	}
}
