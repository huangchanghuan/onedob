package com.sandra.util;

import javax.crypto.*;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;

/**
 * @author mmy
 *
 */
public class Aes {  

	
	private KeyGenerator keygen;  //KeyGenerator 提供对称密钥生成器的功能，支持各种算法
	
	private SecretKey deskey;  //SecretKey 负责保存对称密钥
	
	private Cipher c;  //Cipher负责完成加密或解密工作
	 
	private byte[] cipherByte;  //该字节数组负责保存加密的结果 

	private static final String password = "sunstarchina_shengshang_test"; // 配置文件
	private static final String password2= SysParametersUtil.getValue("PASS_WORD");

	public Aes() throws NoSuchAlgorithmException, NoSuchPaddingException {
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(password.getBytes());
		keygen = KeyGenerator.getInstance("AES");  //实例化支持DES算法的密钥生成器(算法名称命名需按规定，否则抛出异常)
		keygen.init(128, random); //AES可以使用128、192、和256位密钥，并且用128位分组加密和解密数据  		  		
		deskey = keygen.generateKey();  //生成密钥  		  
		c = Cipher.getInstance("AES"); //生成Cipher对象,指定其支持的DES算法
	}  

	/**  
	 * 对字符串加密  
	 *      
	 */  
	public byte[] Encrytor(String str) throws InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
		// 根据密钥，对Cipher对象进行初始化，ENCRYPT_MODE表示加密模式  
		c.init(Cipher.ENCRYPT_MODE, deskey);  //deskey是初始化向量。
		byte[] src = str.getBytes();  
		// 加密，结果保存进cipherByte  //加密操作,返回加密后的字节数组，然后需要编码。主要编解码方式有Base64, HEX, UUE,7bit等等。
		cipherByte = c.doFinal(src);   
		return cipherByte;  
	}  

	public String encrytor(String str) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
		return parseByte2HexStr(Encrytor(str));  
	}  


	/**  
	 * 对字符串解密  
	 *    
	 */  
	public byte[] Decryptor(byte[] buff) throws InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException {
		// 根据密钥，对Cipher对象进行初始化，DECRYPT_MODE表示加密模式  
		c.init(Cipher.DECRYPT_MODE, deskey);
		cipherByte = c.doFinal(buff);  
		return cipherByte;  
	}  

	public String decryptor(String str) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		return new String(Decryptor(parseHexStr2Byte(str)));

	}  



	/**将二进制转换成16进制  
	 * 这主要是因为加密后的byte数组是不能强制转换成字符串的，换言之：字符串和byte数组在这种情况下不是互逆的；
	 * 要避免这种情况，我们需要做一些修订，可以考虑将二进制数据转换成十六进制表示
	 * @param buf  
	 * @return  
	 */   
	public static String parseByte2HexStr(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {   
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {   
				hex = '0' + hex;   
			}   
			sb.append(hex.toUpperCase());   
		}   
		return sb.toString();   
	}   

	/**将16进制转换为二进制  
	 * @param hexStr  
	 * @return  
	 */   
	public static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1)   
			return null;   
		byte[] result = new byte[hexStr.length()/2];   
		for (int i = 0;i< hexStr.length()/2; i++) {   
			int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);
			int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);
			result[i] = (byte) (high * 16 + low);   
		}   
		return result;   
	}   


	/**  
	 * @param args     
	 */  
	public static void main(String[] args) throws Exception {
		Aes de1 = new Aes();  
		String msg ="18666187868";
		byte[] encontent = de1.Encrytor(msg);  
		byte[] decontent = de1.Decryptor(encontent);  
		System.out.println("明文是:" + msg);
		System.out.println("加密后:" + new String(parseByte2HexStr(encontent)));
		System.out.println("解密后:" + new String(decontent));

		System.out.println("------------");

		System.out.println("明文是:" + msg);
		String e = de1.encrytor(msg);
		System.out.println("加密后:" + e);
		String d = de1.decryptor(e);
		System.out.println("解密后:" + d);
		System.out.println("ok==");
		
	}  
	
	

}  

