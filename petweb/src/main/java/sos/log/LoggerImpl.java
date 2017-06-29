package sos.log;

import com.sunstar.sos.cfg.SystemConfig;
import com.sunstar.sos.dao.SysLoggingDao;
import com.sunstar.sos.pojo.SysLogs;
import com.sunstar.sos.util.FileUtil;

/**
 * ADMS日志接口实现类型
 * 记录Web系统的操作及数据日志，根据参数设置，将日志记录到DB或控制台中。
 * @author zhou
 *
 */
public class LoggerImpl implements ADMSLogger{
	
	public void log(String table, String action, String result, String content) {
		if(SystemConfig.logLocation().equals("db")){
			SysLogs logs = SysLoggingDao.processUserLogs(table, action, result);
			logs.setLogContext(FileUtil.compressString(content));
			SysLoggingDao.log(logs);
		}else{
			logger.info("表："+table);
			logger.info("操作："+action);
			logger.info("结果："+result);
			logger.info("内容："+content);
		}
	}

	public void log(String result) {
		logger.info(result);
	}
}
