
package com.onedob.util.alipay;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/* *
 *������UtilDate
 *���ܣ��Զ��嶩����
 *��ϸ�������࣬����������ȡϵͳ���ڡ�������ŵ�
 *�汾��3.3
 *���ڣ�2012-08-17
 *˵����
 *���´���ֻ��Ϊ�˷����̻����Զ��ṩ���������룬�̻����Ը����Լ���վ����Ҫ�����ռ����ĵ���д,����һ��Ҫʹ�øô��롣
 *�ô������ѧϰ���о�֧�����ӿ�ʹ�ã�ֻ���ṩһ���ο���
 */
public class UtilDate {
	
    /** ������ʱ����(���»���) yyyyMMddHHmmss */
    public static final String dtLong                  = "yyyyMMddHHmmss";
    
    /** ����ʱ�� yyyy-MM-dd HH:mm:ss */
    public static final String simple                  = "yyyy-MM-dd HH:mm:ss";
    
    /** ������(���»���) yyyyMMdd */
    public static final String dtShort                 = "yyyyMMdd";
	
    
    /**
     * ����ϵͳ��ǰʱ��(��ȷ������),��Ϊһ��Ψһ�Ķ������
     * @return
     *      ��yyyyMMddHHmmssΪ��ʽ�ĵ�ǰϵͳʱ��
     */
	public  static String getOrderNum(){
		Date date=new Date();
		DateFormat df=new SimpleDateFormat(dtLong);
		return df.format(date);
	}
	
	/**
	 * ��ȡϵͳ��ǰ����(��ȷ������)����ʽ��yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public  static String getDateFormatter(){
		Date date=new Date();
		DateFormat df=new SimpleDateFormat(simple);
		return df.format(date);
	}
	
	/**
	 * ��ȡϵͳ����������(��ȷ����)����ʽ��yyyyMMdd
	 * @return
	 */
	public static String getDate(){
		Date date=new Date();
		DateFormat df=new SimpleDateFormat(dtShort);
		return df.format(date);
	}
	
	/**
	 * �����������λ��
	 * @return
	 */
	public static String getThree(){
		Random rad=new Random();
		return rad.nextInt(1000)+"";
	}
	
}
