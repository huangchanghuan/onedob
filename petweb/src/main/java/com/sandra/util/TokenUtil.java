package com.sandra.util;


public class TokenUtil {

	public static String getToken(String phone){
		String token = null;
		try{
			Aes de1 = new Aes();  
			token = de1.encrytor(phone);  
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return token;
	}

	public static String getCustPhone(String token){
		String phone = null;
		try{
			Aes de1 = new Aes();  
			phone = de1.decryptor(token); 
		}catch(Exception e){
			e.printStackTrace();
		}
		return phone;
	}

	public static String getMd5Token(String phone){
		String str = MD5Util.MD5Encode(phone, "UTF-8");
		String token = str+MD5Util.MD5Encode(str, "UTF-8");
		return token;
	}
	
	public static void main(String[] args) {
		String str = MD5Util.MD5Encode("13531448181", "UTF-8");
		System.out.println(str+MD5Util.MD5Encode(str, "UTF-8"));
		System.out.println(getToken("13531448181"));
	}
}
