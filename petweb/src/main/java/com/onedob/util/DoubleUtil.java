package com.onedob.util;

/** 
 * double�ļ��㲻��ȷ����������0.0000000000000002������ȷ�ķ�����ʹ��BigDecimal���������� 
 */

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;

public class DoubleUtil implements Serializable {
    
  
    /** 
     * �ṩ��ȷ�ļӷ����㡣 
     * @param value1 ������ 
     * @param value2 ���� 
     * @return ���������ĺ� 
     */  
    public static Double add(Number value1, Number value2) {
        BigDecimal b1 = new BigDecimal(Double.toString(value1.doubleValue()));
        BigDecimal b2 = new BigDecimal(Double.toString(value2.doubleValue()));
        return b1.add(b2).doubleValue();  
    }  
  
    /** 
     * �ṩ��ȷ�ļ������㡣 
     *  
     * @param value1 
     *            ������ 
     * @param value2 
     *            ���� 
     * @return ���������Ĳ� 
     */  
    public static double sub(Number value1, Number value2) {
        BigDecimal b1 = new BigDecimal(Double.toString(value1.doubleValue()));
        BigDecimal b2 = new BigDecimal(Double.toString(value2.doubleValue()));
        return b1.subtract(b2).doubleValue();  
    }  
  
    /** 
     * �ṩ��ȷ�ĳ˷����㡣 
     *  
     * @param value1 
     *            ������ 
     * @param value2 
     *            ���� 
     * @return ���������Ļ� 
     */  
        public static Double mul(Number value1, Number value2) {
        BigDecimal b1 = new BigDecimal(Double.toString(value1.doubleValue()));
        BigDecimal b2 = new BigDecimal(Double.toString(value2.doubleValue()));
        return b1.multiply(b2).doubleValue();  
    }  
   
  
    /** 
     * �ṩ����ԣ���ȷ�ĳ������㡣 �����������������ʱ����scale����ָ�����ȣ��Ժ�������������롣 
     *  
     * @param dividend 
     *            ���� 
     * @param divisor 
     *            ������ 
     * @param scale 
     *            ��ʾ��ʾ��Ҫ��ȷ��С�����Ժ�λ�� 
     * @return ������������ 
     */  
    public static Double div(Number dividend, Number divisor, Integer scale) {
        if (scale < 0) {  
            throw new IllegalArgumentException(
                    "��ȷֵ���ܵ���0");  
        }  
        BigDecimal b1 = new BigDecimal(Double.toString(dividend.doubleValue()));
        BigDecimal b2 = new BigDecimal(Double.toString(divisor.doubleValue()));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    
    /** 
     * �ṩ����ԣ���ȷ�ĳ˷����㡣�Ժ�������������롣 
     *  
     * @param dividend 
     *            ����
     * @param divisor 
     *            ����
     * @param scale 
     *            ��ʾ��ʾ��Ҫ��ȷ��С�����Ժ�λ�� 
     * @return ���������Ļ� 
     */ 
    public static Double mul(Double dividend, Double divisor, Integer scale) {
        if (scale < 0) {  
            throw new IllegalArgumentException(
                    "��ȷֵ���ܵ���0");  
        }
        return round(mul(dividend, divisor),scale);
    }
  
    /** 
     * �ṩ��ȷ��С��λ�������봦�� 
     *  
     * @param value 
     *            ��Ҫ������������� 
     * @param scale 
     *            С���������λ 
     * @return ���������Ľ�� 
     */  
    public static Double round(Double value, Integer scale) {
        if (scale < 0) {  
            throw new IllegalArgumentException(
                    "��ȷֵ���ܵ���0");  
        }  
        return new BigDecimal(value).setScale(scale,java.math.BigDecimal.ROUND_HALF_UP).doubleValue();
    }  
    /** 
     * �ṩ��ȷ��С��λ��ȥ���� 
     * @param dividend 
     *            ���� 
     * @param divisor 
     *            ������ 
     * @param scale 
     *            С���������λ 
     * @return ��ȥ��Ľ�� 
     */  
    public static Double divDown(Number dividend, Number divisor, Integer scale) {
        if (scale < 0) {  
            throw new IllegalArgumentException(
                    "��ȷֵ���ܵ���0");  
        }  
        BigDecimal b1 = new BigDecimal(Double.toString(dividend.doubleValue()));
        BigDecimal b2 = new BigDecimal(Double.toString(divisor.doubleValue()));
        return b1.divide(b2, scale, BigDecimal.ROUND_DOWN).doubleValue();
    }
    /**
     * �������������ȥ�� 
     */
    public static Double divNoScale(Number dividend, Number divisor) {
        BigDecimal b1 = new BigDecimal(Double.toString(dividend.doubleValue()));
        BigDecimal b2 = new BigDecimal(Double.toString(divisor.doubleValue()));
        return b1.divide(b2,  BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static void main(String[] args) {
        System.out.println(doubleTrans(33.00));
    }
    /**
     * �Ƚ�����double����С
     * @param price1
     * @param price2
     * @return
     * @throws Exception
     */
    public static Integer comparePrice(double price1, double price2) throws Exception {
		BigDecimal a1= new BigDecimal(price1);
		BigDecimal a2 = new BigDecimal(price2); //0.00
		int result = a1.compareTo(a2) ;				
		return result;
	}
    
    private static DecimalFormat df = new DecimalFormat("######.##");
    /**
     * ��ʽ����ʾdouble,����С�������0����ȥ��������1.23��ʾ1.23��1.00��ʾ1
     * @param num
     * @return
     */
    public static String doubleTrans(double num){
//	    if(Math.round(num)-num==0){
//		        return String.valueOf((long)num);
//	    }
//	    return String.valueOf(num);
	    return String.valueOf(df.format(num));
	}
    
    
}  