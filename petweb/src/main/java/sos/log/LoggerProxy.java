package sos.log;

import com.sunstar.sos.cfg.SystemConfig;

/**
 * 数据日志代理类
 * @author zhou
 */
public class LoggerProxy implements ADMSLogger {

	private ADMSLogger log;
	
	public LoggerProxy(){
		this(new LoggerImpl());
	}
	
	public LoggerProxy(ADMSLogger log){
		this.log = log;
	}
	
	public static ADMSLogger getProxy(){
		return new LoggerProxy();
	}
	
	public static ADMSLogger getProxy(ADMSLogger log){
		return new LoggerProxy(log);
	}
	
	private void before(){
	}
	
	private void after(){
		
	}

	/**
	 * 日志记录
	 */
	public void log(String table, String actionType, String result, String content) {
		if(!SystemConfig.isDatalogs())
			return;
		before();
		log.log(table, actionType, result, content);
		after();
	}

	public void log(String result) {
		if(!SystemConfig.isDatalogs())
			return;
		log.log(result);
	}
}
