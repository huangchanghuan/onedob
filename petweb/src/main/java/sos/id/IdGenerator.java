package sos.id;

import org.carp.CarpSession;
import org.carp.beans.PrimarysMetadata;
import org.carp.exception.CarpException;
import org.carp.factory.BeansFactory;
import org.carp.id.Generator;
import org.carp.impl.AbstractCarpSession;
import org.carp.transaction.Transaction;
import sos.conn.SessionUtil;
import sos.constants.Constants;
import sos.pojo.SysSequences;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


/**
 * 主键生成类
 * @author Administrator
 *
 */
public class IdGenerator implements Generator {
	private static final Map<String,Long[]> map = new java.util.concurrent.ConcurrentHashMap<String, Long[]>(new HashMap<String,Long[]>());
	
	/**
	 * 获取table的下一个序列值
	 * @param tableName
	 * @return
	 */
	public Long getNextPk(String tableName,AbstractCarpSession arg0, Object arg1,PrimarysMetadata arg2){
		Long currValue = null;
		if(tableName==null || tableName.trim().equals(""))
			return currValue;
		try{
			Long[] value = (Long[])map.get(tableName);
			//存在可用值
			if(value!=null && value[2].longValue()< value[1].longValue()){
				currValue = new Long(value[0].longValue());
				value[0] = new Long(value[0].longValue()+1);
				value[2] = new Long(value[2].longValue()+1);
			}else{//不存在可用值，重新从序列表中取得新的序列值
				SysSequences obj = this.getSequences(arg0, arg1, arg2);
				currValue = new Long(obj.getCurrValue().longValue());
				value = new Long[3];
				value[0] = obj.getCurrValue().longValue()+1;//currValue;//obj.getCurrValue();
				value[1] = obj.getIncrementvalue().longValue();
				value[2] = new Long(1);
			}
			map.put(tableName, value);
		}catch(Exception e){
			e.printStackTrace();
		}
		return currValue;
	}
	
	
	@Override
	public Serializable generate(AbstractCarpSession arg0, Object arg1,PrimarysMetadata arg2) throws Exception {
		String table = BeansFactory.getBean(arg1.getClass()).getTable().toUpperCase();
		Long id = getNextPk(table, arg0,  arg1, arg2);
		arg2.setValue(arg1, id);
		return id;
	}
	
	
	public SysSequences getSequences(AbstractCarpSession arg0, Object arg1,PrimarysMetadata arg2) throws Exception {
		CarpSession session = null;
		Transaction tx = null;
		SysSequences obj = null;
		try {
			session = SessionUtil.getSessionBuilder().getSession();
			tx = session.beginTransaction();
			String table = BeansFactory.getBean(arg1.getClass()).getTable().toUpperCase();
			obj = (SysSequences)session.get(SysSequences.class, table);
			if(obj==null){
				obj = new SysSequences();
				obj.setTableName(table);
				obj.setCurrValue(new Long(Constants.DEFAULT_CURR_VALUE+Constants.DEFAULT_INCREMENT_VALUE).intValue());
				obj.setIncrementvalue(new Long(Constants.DEFAULT_INCREMENT_VALUE).intValue());
				session.save(obj);
				obj.setCurrValue(new Long(Constants.DEFAULT_CURR_VALUE).intValue());
			}else{
				long currValue = obj.getCurrValue().longValue();
				obj.setCurrValue(new Long(obj.getCurrValue().longValue()+obj.getIncrementvalue().longValue()).intValue());
				session.update(obj);
				obj.setCurrValue(new Long(currValue).intValue());
			}
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
			try { tx.rollback(); } catch (CarpException e1) { }
		}finally{
			try { session.close(); } catch (CarpException e) { }
		}
		return obj;
	}

}
