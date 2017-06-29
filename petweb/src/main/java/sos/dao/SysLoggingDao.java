package sos.dao;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.carp.CarpSession;
import org.carp.exception.CarpException;
import org.carp.transaction.Transaction;

import com.sunstar.sos.conn.SessionUtil;
import com.sunstar.sos.permission.LoginUser;
import com.sunstar.sos.pojo.SysLogs;
import com.sunstar.sos.util.SequencesUtil;

public class SysLoggingDao {
	/**
	 * 记录日志对象
	 * @param log
	 */
	public static void log(SysLogs log) {
		BaseDao dao = new BaseDao();
		CarpSession session = null;
		Transaction tx = null;
		try{
			session = SessionUtil.getSessionBuilder().getSession();
			dao.setSession(session);
			tx = session.beginTransaction();
			log.setLogId(SequencesUtil.getInstance().getNextPk(SysLogs.class).intValue());
			dao.add(log);
			tx.commit();
		}catch(Exception e){e.printStackTrace();
			try { tx.rollback(); } catch (CarpException e1) { e1.printStackTrace();}
		}finally{
			try { session.close(); } catch (CarpException e) { e.printStackTrace();}
		}
	}
	
	public static SysLogs processUserLogs(String tableName,String actionType,String result){
		LoginUser user = (LoginUser)LoginUser.userThread.get();
		SysLogs log = new SysLogs();
		if(user == null){
			user = new LoginUser();
			user.setUserName("SystemUser");
		}
		log.setUserNo(user.getUserName());
		log.setTableName(tableName.toUpperCase());
		log.setActionType(actionType);
		log.setActionTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		log.setActionResult(result.toUpperCase());
		return log;
	}
}
