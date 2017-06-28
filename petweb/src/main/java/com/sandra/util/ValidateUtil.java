package com.sandra.util;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateUtil {

	/** 
     * 手机号验证 
     *  
     * @param  str 
     * @return 验证通过返回true 
     */  
    public static boolean isMobile(String str) {
        Pattern p = null;
        Pattern p1 = null;
        Matcher m = null;
        Matcher m1 = null;
        boolean b = false;   
        p = Pattern.compile("^[1][2,3,4,5,6,7,8,9]{9}$"); // 验证手机号
        p1 = Pattern.compile("^[1][2,3,4,5,6,7,8,9]{8}$"); // 验证手机号
        m = p.matcher(str);  
        m1 = p1.matcher(str);  
        b = m.matches() || m1.matches();   
        return b;  
    }  
    /** 
     * 电话号码验证 
     *  
     * @param  str 
     * @return 验证通过返回true 
     */  
    public static boolean isPhone(String str) {
        Pattern p1 = null,p2 = null;
        Matcher m = null;
        boolean b = false;    
        p1 = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$");  // 验证带区号的
        p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$");         // 验证没有区号的
        if(str.length() >9)  
        {   m = p1.matcher(str);  
            b = m.matches();    
        }else{  
            m = p2.matcher(str);  
            b = m.matches();   
        }    
        return b;  
    }  
    
    /**
     * 校验参数是否为空
     * @param str
     * @return 如果参数为空返回true
     */
    public static boolean isBlank(String str){
        boolean b = false;  
    	if(str ==null || "".equals(str)|| "null".equals(str)){
    		b = true;
    	}        
        return b;
    }
    /**
     * 检验参数是否为数字
     * @param str
     *  @return 如果参数不为数字就返回true
     */
    public static boolean isNumber(String str){
    	boolean b = false;
    	Pattern pattern = Pattern.compile("[0-9]*");
    	Matcher isNum = pattern.matcher(str);
    	   if( !isNum.matches() ){
    	       return true; 
    	   } 
    	
    	return b;
    }
    
    /**
     * 判断此路径文件是否存在
     * @param url:url地址
     * @return
     */
    public static boolean isUrlExist(String url){
		try { 
			HttpURLConnection.setFollowRedirects(false);
			HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
			con.setRequestMethod("HEAD");  
//			System.out.println("=======url:"+url+",flag:"+(con.getResponseCode() == HttpURLConnection.HTTP_OK));
			return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} 
         
    }
}
