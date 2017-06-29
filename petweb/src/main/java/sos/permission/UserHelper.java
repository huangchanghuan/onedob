package sos.permission;


import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sunstar.sos.cache.CachedClient;
import com.sunstar.sos.util.EncryptUtil;

/**
 * 用户信息辅助
 * 读写用户缓存
 * 读写Cookie,将用户key写入Cookie，下次访问时，从Cookie中读取key，根据key从缓存中取出用户信息。，以便于达到负载均衡的目录（sesssion问题）.
 * @author Administrator
 *
 */
public class UserHelper {
	/**
	 * 将登陆用户信息写入缓存中，以便于下次访问的时候，不需要重新登陆。
	 * @param key
	 * @throws Exception
	 */
	public static void saveToCache(String key,LoginUser user) throws Exception{
		CachedClient.getClient().set(key,user,new Date(60*60*1000));
	}
	
	/**
	 * 将登陆用户信息写入缓存中，以便于下次访问的时候，不需要重新登陆。
	 * @param key
	 * @throws Exception
	 */
	public static void saveToCache(LoginUser user,HttpServletRequest req, HttpServletResponse res) throws Exception{
		String key = createKey(user,req,res);
		saveToCache(key,user);
	}
	
	/**
	 * 根据键值 key， 从缓存中获取登陆用户信息
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static LoginUser getUserFromCache(String key)throws Exception{
		if(key==null)return null;
		LoginUser user = (LoginUser)CachedClient.getClient().get(key);
		return user;
	}
	
	/**
	 * 根据键值 key， 从缓存中删除用户信息
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static void deleteUserFromCache(String key){
		CachedClient.getClient().delete(key);
	}
	
	/**
	 * 写Cookie
	 * @param key
	 * @param req
	 * @param res
	 */
	public static void writeKeyToCookie(String key, HttpServletRequest req, HttpServletResponse res){
		Cookie c = new Cookie(getCookieName(req,res),key);
		c.setMaxAge(50*365*24*60*60);//Cookie 50年失效
//		c.setDomain(req.getServerName());
		c.setPath(req.getContextPath()); //存放目录在当前上下文根
		res.addCookie(c);
	}
	
	/**
	 * 写Cookie
	 * @param key
	 * @param value
	 * @param req
	 * @param res
	 */
	public static void writeKeyValueToCookie(String key, String value, HttpServletRequest req, HttpServletResponse res){
		Cookie c = new Cookie(key,value);
		c.setMaxAge(50*365*24*60*60);//Cookie 50年失效
//		c.setDomain(req.getServerName());
		c.setPath(req.getContextPath()); //存放目录在当前上下文根
		res.addCookie(c);
	}
	
	/**
	 * 删除cookie
	 * @param key
	 * @param req
	 * @param res
	 */
	public static void deleteKeyFromCookie(HttpServletRequest req, HttpServletResponse res){
		Cookie c = new Cookie(getCookieName(req,res),null);
		c.setPath(req.getContextPath()); //存放目录在当前上下文根
		c.setMaxAge(0);// 当为 -1 时， 将在浏览器关闭时删除cookie.
		res.addCookie(c);
	}
	
	/**
	 * 删除cookie
	 * @param key
	 * @param req
	 * @param res
	 */
	public static void deleteKeyValueFromCookie(String key,HttpServletRequest req, HttpServletResponse res){
		Cookie c = new Cookie(key,null);
		c.setPath(req.getContextPath()); //存放目录在当前上下文根
		c.setMaxAge(0);// 当为 -1 时， 将在浏览器关闭时删除cookie.
		res.addCookie(c);
	}
	
	/**
	 * 创建写入缓存时的键值 key
	 * @param user
	 * @param req
	 * @param res
	 * @return
	 */
	public static String createKey(LoginUser user, HttpServletRequest req, HttpServletResponse res){
		String orgKey = user.getUserName()+req.getSession().getId()+System.currentTimeMillis()+req.getContextPath();
		return EncryptUtil.bytes2Hex(EncryptUtil.encryptAES(orgKey));
	}

	/**
	 * 从cookie中读取用户的键值key
	 * @param req
	 * @param res
	 * @return
	 */
	public static String readKeyFromCookie(HttpServletRequest req, HttpServletResponse res){
		Cookie[] cookies = req.getCookies();
		if(cookies != null && cookies.length > 0){
			int count = cookies.length;
			String cookieName = getCookieName(req,res);
			for(int i=0; i<count; i++){
				Cookie c = cookies[i];
				if(c.getName().equals(cookieName)){
					return c.getValue();
				}
			}
		}
		return null;
	}
	
	/**
	 * 根据Key从cookie中获取对应的Value
	 * @param req
	 * @param res
	 * @return
	 */
	public static String readValueFromCookie(String key , HttpServletRequest req){
		Cookie[] cookies = req.getCookies();
		if(cookies != null && cookies.length > 0){
			int count = cookies.length;
			for(int i=0; i<count; i++){
				Cookie c = cookies[i];
				if(c.getName().equals(key)){
					return c.getValue();
				}
			}
		}
		return null;
	}
	
	/**
	 * 生成cookie的name，并返还给使用者
	 * @param req
	 * @param res
	 * @return
	 */
	public static String getCookieName(HttpServletRequest req, HttpServletResponse res){
		return "user-"+req.getContextPath().substring(1)+"-key";
	}
}
