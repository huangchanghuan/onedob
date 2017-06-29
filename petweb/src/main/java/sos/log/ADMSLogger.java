package sos.log;

import org.apache.log4j.Logger;

public interface ADMSLogger {
	static Logger logger = Logger.getLogger(Logger.class);
	/**
	 * 记录操作日志
	 * @param tableName 操作的数据表
	 * @param actionType 操作类型：WRITE、UPDATE、 DELETE、 LOGIN、 LOGON
	 * @param result 操作结果：成功/失败
	 * @param content 操作内容：pojo对象
	 */
	public void log(String table, String actionType, String result, String content);
	
	/**
	 * 记录操作日志
	 * 仅仅记录查询操作，不入库
	 * @param result 操作结果,如果发生错误，则为异常信息
	 */
	public void log(String result);

}
