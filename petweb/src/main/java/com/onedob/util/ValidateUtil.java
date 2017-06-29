package com.onedob.util;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateUtil {

	/** 
     * �ֻ�����֤ 
     *  
     * @param  str 
     * @return ��֤ͨ������true 
     */  
    public static boolean isMobile(String str) {
        Pattern p = null;
        Pattern p1 = null;
        Matcher m = null;
        Matcher m1 = null;
        boolean b = false;   
        p = Pattern.compile("^[1][2,3,4,5,6,7,8,9]{9}$"); // ��֤�ֻ���
        p1 = Pattern.compile("^[1][2,3,4,5,6,7,8,9]{8}$"); // ��֤�ֻ���
        m = p.matcher(str);  
        m1 = p1.matcher(str);  
        b = m.matches() || m1.matches();   
        return b;  
    }  
    /** 
     * �绰������֤ 
     *  
     * @param  str 
     * @return ��֤ͨ������true 
     */  
    public static boolean isPhone(String str) {
        Pattern p1 = null,p2 = null;
        Matcher m = null;
        boolean b = false;    
        p1 = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$");  // ��֤�����ŵ�
        p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$");         // ��֤û�����ŵ�
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
     * У������Ƿ�Ϊ��
     * @param str
     * @return �������Ϊ�շ���true
     */
    public static boolean isBlank(String str){
        boolean b = false;  
    	if(str ==null || "".equals(str)|| "null".equals(str)){
    		b = true;
    	}        
        return b;
    }
    /**
     * ��������Ƿ�Ϊ����
     * @param str
     *  @return ���������Ϊ���־ͷ���true
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
     * �жϴ�·���ļ��Ƿ����
     * @param url:url��ַ
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
