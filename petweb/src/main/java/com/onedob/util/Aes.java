package com.onedob.util;

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

	
	private KeyGenerator keygen;  //KeyGenerator �ṩ�Գ���Կ�������Ĺ��ܣ�֧�ָ����㷨
	
	private SecretKey deskey;  //SecretKey ���𱣴�Գ���Կ
	
	private Cipher c;  //Cipher������ɼ��ܻ���ܹ���
	 
	private byte[] cipherByte;  //���ֽ����鸺�𱣴���ܵĽ�� 

	private static final String password = "sunstarchina_shengshang_test"; // �����ļ�
	private static final String password2= SysParametersUtil.getValue("PASS_WORD");

	public Aes() throws NoSuchAlgorithmException, NoSuchPaddingException {
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(password.getBytes());
		keygen = KeyGenerator.getInstance("AES");  //ʵ����֧��DES�㷨����Կ������(�㷨���������谴�涨�������׳��쳣)
		keygen.init(128, random); //AES����ʹ��128��192����256λ��Կ��������128λ������ܺͽ�������  		  		
		deskey = keygen.generateKey();  //������Կ  		  
		c = Cipher.getInstance("AES"); //����Cipher����,ָ����֧�ֵ�DES�㷨
	}  

	/**  
	 * ���ַ�������  
	 *      
	 */  
	public byte[] Encrytor(String str) throws InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
		// ������Կ����Cipher������г�ʼ����ENCRYPT_MODE��ʾ����ģʽ  
		c.init(Cipher.ENCRYPT_MODE, deskey);  //deskey�ǳ�ʼ��������
		byte[] src = str.getBytes();  
		// ���ܣ���������cipherByte  //���ܲ���,���ؼ��ܺ���ֽ����飬Ȼ����Ҫ���롣��Ҫ����뷽ʽ��Base64, HEX, UUE,7bit�ȵȡ�
		cipherByte = c.doFinal(src);   
		return cipherByte;  
	}  

	public String encrytor(String str) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
		return parseByte2HexStr(Encrytor(str));  
	}  


	/**  
	 * ���ַ�������  
	 *    
	 */  
	public byte[] Decryptor(byte[] buff) throws InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException {
		// ������Կ����Cipher������г�ʼ����DECRYPT_MODE��ʾ����ģʽ  
		c.init(Cipher.DECRYPT_MODE, deskey);
		cipherByte = c.doFinal(buff);  
		return cipherByte;  
	}  

	public String decryptor(String str) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		return new String(Decryptor(parseHexStr2Byte(str)));

	}  



	/**��������ת����16����  
	 * ����Ҫ����Ϊ���ܺ��byte�����ǲ���ǿ��ת�����ַ����ģ�����֮���ַ�����byte��������������²��ǻ���ģ�
	 * Ҫ�������������������Ҫ��һЩ�޶������Կ��ǽ�����������ת����ʮ�����Ʊ�ʾ
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

	/**��16����ת��Ϊ������  
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
		System.out.println("������:" + msg);
		System.out.println("���ܺ�:" + new String(parseByte2HexStr(encontent)));
		System.out.println("���ܺ�:" + new String(decontent));

		System.out.println("------------");

		System.out.println("������:" + msg);
		String e = de1.encrytor(msg);
		System.out.println("���ܺ�:" + e);
		String d = de1.decryptor(e);
		System.out.println("���ܺ�:" + d);
		System.out.println("ok==");
		
	}  
	
	

}  

