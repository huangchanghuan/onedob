package sos.dao;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.carp.CarpSession;
import org.carp.exception.CarpException;
import org.carp.transaction.Transaction;

import com.sunstar.sos.cfg.SystemConfig;
import com.sunstar.sos.conn.SessionUtil;
import com.sunstar.sos.constants.Constants;
import com.sunstar.sos.pojo.SysSequences;

/**
 * 序列值生成DAO类
 * @author Administrator
 *
 */
public class SequencesDao {
	private static final Logger logger = Logger.getLogger(SequencesDao.class);
	
	/**
	 * 获取table对应的序列值对象.
	 * @param table
	 * @return
	 */
	public SysSequences findByPk(String table) {
		BaseDao dao = new BaseDao();
		CarpSession session = null;
		try {
			session = SessionUtil.getSessionBuilder().getSession();
			dao.setSession(session);
		} catch (CarpException e2) {}
		Transaction tx = null;
		SysSequences obj = null;
		try{
			tx = session.beginTransaction();
			try{
			obj = (SysSequences)dao.find(SysSequences.class, table);
			}catch(Exception ex){}
			if(obj==null){
				obj = new SysSequences();
				obj.setTableName(table);
				obj.setCurrValue(new Long(Constants.DEFAULT_CURR_VALUE+Constants.DEFAULT_INCREMENT_VALUE).intValue());
				obj.setIncrementvalue(new Long(Constants.DEFAULT_INCREMENT_VALUE).intValue());
				dao.add(obj);
				obj.setCurrValue(new Long(Constants.DEFAULT_CURR_VALUE).intValue());
			}else{
				long currValue = obj.getCurrValue().longValue();
				obj.setCurrValue(new Long(obj.getCurrValue().longValue()+obj.getIncrementvalue().longValue()).intValue());
				dao.update(obj);
				obj.setCurrValue(new Long(currValue).intValue());
			}
			tx.commit();
			if(SystemConfig.isBpoproxy())
				logger.info("Table = "+table+" , id value = "+obj.getCurrValue());
		}catch(Exception e){
			e.printStackTrace();
			try { tx.rollback(); } catch (CarpException e1) { }
		}finally{
			try { session.close(); } catch (CarpException e) { }
		}
		return obj;
	}
	
	
	/**
	 * 获取table对应的序列值对象.券号生成专属，格式为yyMMdd0001
	 * @param table
	 * @return
	 */
	public SysSequences findByVoucherPk(String table) {
		BaseDao dao = new BaseDao();
		CarpSession session = null;
		String nowDate = new SimpleDateFormat("yyMMdd").format(new Date());
		try {
			session = SessionUtil.getSessionBuilder().getSession();
			dao.setSession(session);
		} catch (CarpException e2) {}
		Transaction tx = null;
		SysSequences obj = null;
		try{
			tx = session.beginTransaction();
			try{
			obj = (SysSequences)dao.find(SysSequences.class, table);
			}catch(Exception ex){}
			if(obj==null){
				obj = new SysSequences();
				obj.setTableName(table);
				obj.setCurrValue(new Long(nowDate+"001").intValue()+new Long(1).intValue());
				obj.setIncrementvalue(1);
				dao.add(obj);
				obj.setCurrValue(new Long(nowDate+"001").intValue());
			}else{
				//同一天直接相加
				long currValue = 0;
				if(obj.getCurrValue().toString().substring(0,6).equals(nowDate.substring(0,6))){
					currValue = obj.getCurrValue().longValue();
				}else{
					currValue = new Long(nowDate+"001").intValue();
				}
				obj.setCurrValue(new Long(currValue+obj.getIncrementvalue().longValue()).intValue());
				dao.update(obj);
				obj.setCurrValue(new Long(currValue).intValue());
			}
			tx.commit();
			if(SystemConfig.isBpoproxy())
				logger.info("Table = "+table+" , id value = "+obj.getCurrValue());
		}catch(Exception e){
			e.printStackTrace();
			try { tx.rollback(); } catch (CarpException e1) { }
		}finally{
			try { session.close(); } catch (CarpException e) { }
		}
		return obj;
	}
}
