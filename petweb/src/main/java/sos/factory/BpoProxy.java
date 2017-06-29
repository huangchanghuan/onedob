package sos.factory;

import com.sunstar.sos.bpo.BaseBpo;
import com.sunstar.sos.cfg.SystemConfig;
import com.sunstar.sos.conn.SessionUtil;
import com.sunstar.sos.log.LoggerProxy;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodProxy;
import org.apache.log4j.Logger;
import org.carp.CarpSession;
import org.carp.exception.CarpException;
import org.carp.transaction.Transaction;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Method;


/**
 * 工厂模式，业务对象构建工厂类
 * 
 * @author Administrator
 * 
 */
public class BpoProxy implements net.sf.cglib.proxy.MethodInterceptor{
	private static final Logger logger = Logger.getLogger(BpoProxy.class);
	//连接打开，累计方法次数
	private static ThreadLocal<Integer> sessionOpenCount = new ThreadLocal<Integer>();
	private BaseBpo _bpo;
//	private int count = 0;
	private Transaction tx;
	private boolean isSessionOpen = false;
	private boolean isTxOpen = false;
	private CarpSession session;
	private boolean isSuccess = true;
	private StringBuilder str = new StringBuilder();

	protected void initSessionOpenCount(){
		if(sessionOpenCount.get() == null)
			sessionOpenCount.set(0);
	}
	/**
	 * 执行前方法
	 * @param m
	 * @throws CarpException
	 */
	private void before(Method m) throws CarpException{
		///被忽略的方法，如getter、setter等方法，不需要打开session连接
		if(BpoFactory.isIgnoreMethod(m)) 
			return;;//setter方法，不需要打开session
		if(!this.isSessionOpen){
			session = SessionUtil.getSession();
			initSessionOpenCount();
			_bpo.getDao().setSession(session);
			this.isSessionOpen = true;
		}
		if(!this.isTxOpen && BpoFactory.isTransactionMethod(m)){ //事务方法，开启事务
			if(SystemConfig.isBpoproxy())
				logger.info("事务方法："+m.getName());
			tx = session.beginTransaction();
			_bpo.setTransaction(tx);
			this.isTxOpen = !this.isTxOpen;
		}
	}
	
	/**
	 * 创建代理对象
	 * @param cls bpo类
	 * @return bpo代理对象
	 */
	public Object createProxy(Class<?> cls) {  
        Enhancer enhancer = new Enhancer();  
        enhancer.setSuperclass(cls);  
        enhancer.setCallback(this);  
        enhancer.setClassLoader(cls.getClassLoader());
        _bpo = (BaseBpo)enhancer.create();
        return _bpo;
    }  
	/**
	 * 执行方法
	 */
	public Object intercept(Object obj, Method m, Object[] args, MethodProxy proxy) {
		try {before(m);
		} catch (CarpException e1) {}
		sessionOpenCount.set(sessionOpenCount.get()+1);
		if(SystemConfig.isBpoproxy() && !BpoFactory.isIgnoreMethod(m))
			logger.info("方法执行前==》 方法名称："+m.getName()+"; count："+sessionOpenCount.get());
		Object value = null;
		try {
			if(this.isSuccess || BpoFactory.isIgnoreMethod(m))
				value = proxy.invokeSuper(obj, args);
		} catch (Throwable e) {
			exception(e);
			this.isSuccess = false;
		}
		sessionOpenCount.set(sessionOpenCount.get()-1);
		String type = m.getGenericReturnType().toString();
		if(SystemConfig.isBpoproxy() && !BpoFactory.isIgnoreMethod(m))
			logger.debug("方法执行后==》 方法名称："+m.getName()+"； 返回值："+value+"； count: "+sessionOpenCount.get()+"； 是否执行成功："+this.isSuccess);
		if(!this.isSuccess && !BpoFactory.isIgnoreMethod(m)){
			if(type.equals("boolean")) value = false;
			else value = null;
		}
		after(m);
		return value;
	}
	/**
	 * 执行后方法
	 * @param m
	 * @throws CarpException
	 */
	private void after(Method m){
		String name = m.getName();
		//setter方法，不需要关闭session,因为没有打开session
		if(BpoFactory.isIgnoreMethod(m) || sessionOpenCount.get() != 0)
			return;
		if(SystemConfig.isBpoproxy())
			logger.info("方法执行结束==》 方法名称："+name+"；事务方法："+BpoFactory.isTransactionMethod(m));
		boolean isTxSuccess = false;
		try{
			if(this.isTxOpen && this.isSuccess){
				tx.commit();
				isTxSuccess = true;
			}else{
				try { if(tx != null) tx.rollback(); } catch (Exception e) { }
			}
		}catch(Exception ex1){
			try { tx.rollback(); } catch (Exception e) { }
		}finally{
			SessionUtil.closeSession(session);
			if(this.isTxOpen){
				logger.debug("数据表："+_bpo.getTable());
				LoggerProxy.getProxy().log(_bpo.getTable(), _bpo.getAction(), isTxSuccess?"SUCC":"FAIL", str.toString()+_bpo.getResult());
			}
			else if(!this.isSuccess)
				LoggerProxy.getProxy().log(str.toString());
			resetInitValue();
		}
	}
	
	/**
	 * 方法执行结束后，重置为初始值.
	 */
	private void resetInitValue(){
		this.isSuccess = true;
		this.isTxOpen = false;
		this.isSessionOpen = false;
		this.tx = null;
		this.session = null;
	}
	
	private void exception(Throwable e){
		e.printStackTrace();
		ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
		PrintStream ps = new PrintStream(baos);
		e.printStackTrace(ps);
		str.append(baos.toString());
		ps.close();
		try { baos.close(); } catch (IOException e1) { }
	}
}
