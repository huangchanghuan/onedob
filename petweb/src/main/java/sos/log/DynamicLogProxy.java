package sos.log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Date;

public class DynamicLogProxy implements InvocationHandler {

	private ADMSLogger log;
	
	public DynamicLogProxy(){
		this(new LoggerImpl());
	}
	
	public DynamicLogProxy(ADMSLogger log){
		this.log = log;
	}
	
	public ADMSLogger bind(){
		return (ADMSLogger)Proxy.newProxyInstance(log.getClass().getClassLoader(), log.getClass().getInterfaces(), this);
	}
	
	public static ADMSLogger getProxy(){
		return new DynamicLogProxy().bind();
	}
	
	public static ADMSLogger getProxy(ADMSLogger log){
		return new DynamicLogProxy(log).bind();
	}
	
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		before();
		Object result = method.invoke(log, args);
		after();
		return result;
	}

	public void before(){
		//System.out.println("ddddddddddddd");
	}
	public void after(){
		
	}
	
	public static void main(String[] ss){
		System.out.println("proxy begin ...... ");
		long begin = new Date().getTime();
		for(long i=0; i<400000L;++i){ // 10£¬000£¬000£¬000
			getProxy().log("", "", "", "");
		}
		System.out.println("proxy cost time : "+ (new Date().getTime()-begin));
		
		
		System.out.println("normal begin ...... ");
		begin = new Date().getTime();
		for(long i=0; i<400000L;++i){ // 10£¬000£¬000£¬000
			new LoggerImpl().log("", "", "", "");
		}
		System.out.println("normal cost time : "+ (new Date().getTime()-begin));
		
		
		
//		new DynamicLogProxy(new LoggerImpl()).bind().log("", "", "ffffffffff", "fffffffffff");
	}
}
