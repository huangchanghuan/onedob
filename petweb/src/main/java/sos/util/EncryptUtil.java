package sos.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


public class EncryptUtil 
{
	private final static String seckey = "!@#_user_$%^^&)(_password__~=?><,./";
	public static void main(String[] sss) throws Exception
	{
		System.out.println(encrypt("test","test"));
	}
	
	/**
	 * 密码加密
	 * @param user  用户名
	 * @param password 原始密码
	 * @return 返回加密后字符串，不可逆向解密。
	 */
	public static String encrypt(String user,String password){
		try {
			java.security.MessageDigest md = java.security.MessageDigest.getInstance( "MD5" );
			md.update((user+seckey+password).getBytes()); 
			return bytes2Hex(md.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public static String bytes2Hex(byte[] b) {
		StringBuffer buf = new StringBuffer(b.length * 2);
		for (int i = 0, count = b.length; i < count; ++i) {
			String s = Integer.toHexString(b[i] & 0xFF);
			if (s.length() == 1)
				buf.append("0");
			buf.append(s.toUpperCase());
		}
		return buf.toString();
	}

	public static byte[] hex2Bytes(String hex) {
		byte[] b = new byte[hex.length() / 2];
		byte[] bytesHex = hex.getBytes();
		for (int i = 0, count = bytesHex.length; i < count; i = i + 2) {
			b[i / 2] = (byte) Integer.parseInt(new String(bytesHex, i, 2), 16);
		}
		return b;
	}
	
	/**
	 * 密码加密
	 * @param user  用户名
	 * @param password 原始密码
	 * @return 返回加密后字符串，不可逆向解密。
	 */
	public static String encrypt(String name){
		try {
			java.security.MessageDigest md = java.security.MessageDigest.getInstance( "MD5" );
			md.update((name+seckey).getBytes()); 
			return bytes2Hex(md.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/** 
	 * 加密 
	 *  
	 * @param content 需要加密的内容 
	 * @param password  加密密码 
	 * @return 
	 */  
	public static byte[] encryptAES(String content ) {  
	        try {             
	                KeyGenerator kgen = KeyGenerator.getInstance("AES");  
	                kgen.init(128, new SecureRandom(seckey.getBytes()));  
	                SecretKey secretKey = kgen.generateKey();  
	                byte[] enCodeFormat = secretKey.getEncoded();  
	                SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");  
	                Cipher cipher = Cipher.getInstance("AES");// 创建密码器   
	                byte[] byteContent = content.getBytes("utf-8");  
	                cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化   
	                byte[] result = cipher.doFinal(byteContent);  
	                return result; // 加密   
	        } catch (NoSuchAlgorithmException e) {  
	                e.printStackTrace();  
	        } catch (NoSuchPaddingException e) {  
	                e.printStackTrace();  
	        } catch (InvalidKeyException e) {  
	                e.printStackTrace();  
	        } catch (UnsupportedEncodingException e) {  
	                e.printStackTrace();  
	        } catch (IllegalBlockSizeException e) {  
	                e.printStackTrace();  
	        } catch (BadPaddingException e) {  
	                e.printStackTrace();  
	        }  
	        return null;  
	}  
	
	/**解密 
	 * @param content  待解密内容 
	 * @param password 解密密钥 
	 * @return 
	 */  
	public static byte[] decryptAES(byte[] content ) {  
	        try {  
	                 KeyGenerator kgen = KeyGenerator.getInstance("AES");  
	                 kgen.init(128, new SecureRandom(seckey.getBytes()));  
	                 SecretKey secretKey = kgen.generateKey();  
	                 byte[] enCodeFormat = secretKey.getEncoded();  
	                 SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");              
	                 Cipher cipher = Cipher.getInstance("AES");// 创建密码器   
	                cipher.init(Cipher.DECRYPT_MODE, key);// 初始化   
	                byte[] result = cipher.doFinal(content);  
	                return result; // 加密   
	        } catch (NoSuchAlgorithmException e) {  
	                e.printStackTrace();  
	        } catch (NoSuchPaddingException e) {  
	                e.printStackTrace();  
	        } catch (InvalidKeyException e) {  
	                e.printStackTrace();  
	        } catch (IllegalBlockSizeException e) {  
	                e.printStackTrace();  
	        } catch (BadPaddingException e) {  
	                e.printStackTrace();  
	        }  
	        return null;  
	}  
}
