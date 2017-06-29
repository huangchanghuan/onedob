package sos.util;

import com.sunstar.sos.bpo.BaseBpo;
import com.sunstar.sos.permission.LoginUser;

/**
 * 长连接线程响应类
 * @author zhou
 */
public class LongLinkThread extends Thread {
	private int process = 0;
	private BaseBpo bpo;
	private Object obj;
	private String type;
	private LoginUser usr;
	public LongLinkThread(BaseBpo _bpo,LoginUser user , Object o, String type){
		this.bpo = _bpo;
		this.obj = o;
		this.type = type;
		this.usr = user;
	}
	public void run(){
		System.out.println("当前登录用户  === "+LoginUser.userThread.get());
		try {
			Object id = bpo.save(obj);
			if(id != null)
				process = 1;
			else
				process = -1;
		} catch (Exception e) {
			e.printStackTrace();
			process = -1;
		}
	}
	/**
	 * 取得处理进度
	 * @return 0 正在处理中， 1 处理成功， -1  处理失败.
	 */
	public int getProcess(){
		return process;
	}
}
