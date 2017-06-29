package sos.cache;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;

import java.io.IOException;
import java.util.*;

/**
 * memcached client实例化类
 * 创建 memcached client 实例对象
 * @author Administrator
 *
 */
public class CachedClient {
	// MemCachedClient 实例对象
	protected static MemCachedClient mcc = new MemCachedClient();
	
	// 类加载时初始化memcached的参数设置
	static{
		Properties p = new Properties();
		try {
			SockIOPool pool = SockIOPool.getInstance();//连接池实例
			p.load(CachedClient.class.getResourceAsStream("/memcached.properties"));
			int count = Integer.parseInt(p.getProperty("memcached.count"),10);
			List<String> servers = new ArrayList<String>();
			for(int i = 1; i<=count; ++i){
				String ip = p.getProperty("memcached"+i+".ip","192.168.0.254");
				String port = p.getProperty("memcached"+i+".port","11211");
				servers.add(ip+":"+port);
			}
			pool.setServers(servers.toArray(new String[0]));
//			pool.setServers(new String[]{p.getProperty("memcached.ip", "192.168.0.254")+":"+p.getProperty("memcached.port", "11211")});
			pool.setNagle(false);
			pool.setSocketTO(3000);
			pool.setSocketConnectTO(0);
			pool.initialize();
			
//			failover表示对于服务器出现问题时的自动修复。
//			initConn初始的时候连接数，
//			minConn表示最小闲置连接数，
//			maxConn最大连接数，
//			maintSleep表示是否需要延时结束
//			nagle是TCP对于socket创建的算法，
//			socketTO是socket连接超时时间，
//			aliveCheck表示心跳检查，确定服务器的状态。
//			Servers是memcached服务端开的地址和ip列表字符串，
//			weights是上面服务器的权重，必须数量一致，否则权重无效
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 取得memcached实例对象
	 * @return
	 */
	public static MemCachedClient getClient(){
		return mcc;
	}
	
	public static void main(String[] str)throws Exception{
		//testSet();
		testGet();
	}
	
	public static void testSet(){
		Map m= new HashMap();
		m.put("date", new Date());
		m.put("name", "随时看房");
		mcc.set("key1", m);
	}
	
	public static void testGet(){

		System.out.println(CachedClient.getClient().delete("CITYLIST"));
	}
}
