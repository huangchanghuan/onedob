package com.sandra.util;

/** 
 * double的计算不精确，会有类似0.0000000000000002的误差，正确的方法是使用BigDecimal或者用整型 
 */

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;

public class DoubleUtil implements Serializable {
    
  
    /** 
     * 提供精确的加法运算。 
     * @param value1 被加数 
     * @param value2 加数 
     * @return 两个参数的和 
     */  
    public static Double add(Number value1, Number value2) {
        BigDecimal b1 = new BigDecimal(Double.toString(value1.doubleValue()));
        BigDecimal b2 = new BigDecimal(Double.toString(value2.doubleValue()));
        return b1.add(b2).doubleValue();  
    }  
  
    /** 
     * 提供精确的减法运算。 
     *  
     * @param value1 
     *            被减数 
     * @param value2 
     *            减数 
     * @return 两个参数的差 
     */  
    public static double sub(Number value1, Number value2) {
        BigDecimal b1 = new BigDecimal(Double.toString(value1.doubleValue()));
        BigDecimal b2 = new BigDecimal(Double.toString(value2.doubleValue()));
        return b1.subtract(b2).doubleValue();  
    }  
  
    /** 
     * 提供精确的乘法运算。 
     *  
     * @param value1 
     *            被乘数 
     * @param value2 
     *            乘数 
     * @return 两个参数的积 
     */  
        public static Double mul(Number value1, Number value2) {
        BigDecimal b1 = new BigDecimal(Double.toString(value1.doubleValue()));
        BigDecimal b2 = new BigDecimal(Double.toString(value2.doubleValue()));
        return b1.multiply(b2).doubleValue();  
    }  
   
  
    /** 
     * 提供（相对）精确的除法运算。 当发生除不尽的情况时，由scale参数指定精度，以后的数字四舍五入。 
     *  
     * @param dividend 
     *            除数 
     * @param divisor 
     *            被除数 
     * @param scale 
     *            表示表示需要精确到小数点以后几位。 
     * @return 两个参数的商 
     */  
    public static Double div(Number dividend, Number divisor, Integer scale) {
        if (scale < 0) {  
            throw new IllegalArgumentException(
                    "精确值不能低于0");  
        }  
        BigDecimal b1 = new BigDecimal(Double.toString(dividend.doubleValue()));
        BigDecimal b2 = new BigDecimal(Double.toString(divisor.doubleValue()));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    
    /** 
     * 提供（相对）精确的乘法运算。以后的数字四舍五入。 
     *  
     * @param dividend 
     *            乘数
     * @param divisor 
     *            乘数
     * @param scale 
     *            表示表示需要精确到小数点以后几位。 
     * @return 两个参数的积 
     */ 
    public static Double mul(Double dividend, Double divisor, Integer scale) {
        if (scale < 0) {  
            throw new IllegalArgumentException(
                    "精确值不能低于0");  
        }
        return round(mul(dividend, divisor),scale);
    }
  
    /** 
     * 提供精确的小数位四舍五入处理。 
     *  
     * @param value 
     *            需要四舍五入的数字 
     * @param scale 
     *            小数点后保留几位 
     * @return 四舍五入后的结果 
     */  
    public static Double round(Double value, Integer scale) {
        if (scale < 0) {  
            throw new IllegalArgumentException(
                    "精确值不能低于0");  
        }  
        return new BigDecimal(value).setScale(scale,java.math.BigDecimal.ROUND_HALF_UP).doubleValue();
    }  
    /** 
     * 提供精确的小数位舍去处理。 
     * @param dividend 
     *            除数 
     * @param divisor 
     *            被除数 
     * @param scale 
     *            小数点后保留几位 
     * @return 舍去后的结果 
     */  
    public static Double divDown(Number dividend, Number divisor, Integer scale) {
        if (scale < 0) {  
            throw new IllegalArgumentException(
                    "精确值不能低于0");  
        }  
        BigDecimal b1 = new BigDecimal(Double.toString(dividend.doubleValue()));
        BigDecimal b2 = new BigDecimal(Double.toString(divisor.doubleValue()));
        return b1.divide(b2, scale, BigDecimal.ROUND_DOWN).doubleValue();
    }
    /**
     * 两个数相除不舍去。 
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
     * 比较两个double数大小
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
     * 格式化显示double,假如小数点后是0，则去除，比如1.23显示1.23，1.00显示1
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