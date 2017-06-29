package com.onedob.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RandomUtil {
	public static String getInitPassword() {
		// int
		// no=Integer.parseInt(SysParametersUtil.getInstance().getValue("PASSWORD_MIN_DIGITS"));����ϵͳû���������������������д��������λ
		int no = 6;
		String initPassword = RandomUtil.getRandomNormalString(no);
		return initPassword;
	}

	public static String getRandomNormalString(int length) {
		// include 0-9,a-z,A-Z
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < length; i++) {
			result.append(getRandomNormalChar());
		}
		return result.toString();
	}

	public static char getRandomNormalChar() {
		// include 0-9,a-z,A-Z
		int number = getRandomInt(0, 62);
		int zeroChar = 48;
		int nineChar = 57;
		int aChar = 97;
		int zChar = 122;
		int AChar = 65;
		int ZChar = 90;

		char result;

		if (number < 10) {
			result = (char) (getRandomInt(zeroChar, nineChar + 1));
			return result;

		} else if (number >= 10 && number < 36) {
			result = (char) (getRandomInt(AChar, ZChar + 1));
			return result;
		} else if (number >= 36 && number < 62) {
			result = (char) (getRandomInt(aChar, zChar + 1));
			return result;
		} else {
			return 0;
		}
	}

	public static int getRandomInt(int min, int max) {
		// include min, exclude max
		int result = min + new Double(Math.random() * (max - min)).intValue();

		return result;
	}
	
	public static String getConsumeCode(){
		String str = "";
		String nowDate = new SimpleDateFormat("MMdd").format(new Date());
		String key = "";
		if(Integer.parseInt(nowDate.substring(0,2))%3 == 0){
			key = (Integer.parseInt(nowDate.substring(2,4))+62)+"";
		}else if(Integer.parseInt(nowDate.substring(0,2))%3 == 1){
			key = nowDate.substring(2,4);
		}else{
			key = (Integer.parseInt(nowDate.substring(2,4))+31)+"";
		}
		String radom = getRandomInt(10000000,99999999)+"";
		str = radom.substring(0,1)+key.substring(0,1)
				+radom.substring(1,7)+key.substring(1,2)
				+radom.substring(7,8);
		return str;
	}
	
	public static void main(String[] args) {
		for(int i=0;i<3000;i++){
			String str = getConsumeCode();
			if(str.indexOf("4")>-1){
				i --;
			}else{
				System.out.println(str);
			}
		}
//		System.out.println(getRandomInt(1000,9999));
	}
}
