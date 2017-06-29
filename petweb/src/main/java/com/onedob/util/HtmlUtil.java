package com.onedob.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlUtil {  
    private static final String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // ����script��������ʽ
    private static final String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // ����style��������ʽ
    private static final String regEx_html = "<[^>]+>"; // ����HTML��ǩ��������ʽ
    private static final String regEx_space = "\\s*|\t|\r|\n";//����ո�س����з�
      
    /** 
     * @param htmlStr 
     * @return 
     *  ɾ��Html��ǩ 
     */  
    public static String delHTMLTag(String htmlStr) {
        Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(htmlStr);
        htmlStr = m_script.replaceAll(""); // ����script��ǩ  
  
        Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(htmlStr);
        htmlStr = m_style.replaceAll(""); // ����style��ǩ  
  
        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll(""); // ����html��ǩ  
  
        Pattern p_space = Pattern.compile(regEx_space, Pattern.CASE_INSENSITIVE);
        Matcher m_space = p_space.matcher(htmlStr);
        htmlStr = m_space.replaceAll(""); // ���˿ո�س���ǩ  
        return htmlStr.trim(); // �����ı��ַ���  
    }  
      
    public static String getTextFromHtml(String htmlStr){
        htmlStr = delHTMLTag(htmlStr);  
        htmlStr = htmlStr.replaceAll("&nbsp;", "");  
//        htmlStr = htmlStr.substring(0, htmlStr.indexOf("��")+1);  
        return htmlStr;  
    } 
    /**
     * ֻȥ���س����з�
     * @param htmlStr
     * @return
     */
    public static String getTextFromSpace(String htmlStr){
    	Pattern p_space = Pattern.compile(regEx_space, Pattern.CASE_INSENSITIVE);
        Matcher m_space = p_space.matcher(htmlStr);
        htmlStr = m_space.replaceAll(""); // 
        htmlStr = htmlStr.replaceAll("&nbsp;", "");  
        return htmlStr;  
    } 
    public static void main(String[] args) {
        String str = "<n>�������������� <p class=\"text\" style=\"font-size:14px;color:#777777;font-family:'Microsoft YaHei', ΢���ź�, SimHei, ����, STHeiti, 'MingLiu Verdana', Arial, Helvetica;\">"
	+"�����������й�λ��ǰ�еļ�����Ԥ��ҽ�ƣ����ţ��������½���ҽ�Ʒ������ġ�IT����ƽ̨��ǿ��Ŀͻ�������ϵ��ÿ��Ϊ������ͻ��ṩ������졢������⡢�ݿƷ����ƶ�ҽ�ơ�˽��ҽ����ְ��ҽ�ơ�������֡���ҽ���ơ���˥�ϵȽ����������"
+"</p>"
+"<p class=\"text\" style=\"font-size:14px;color:#777777;font-family:'Microsoft YaHei', ΢���ź�, SimHei, ����, STHeiti, 'MingLiu Verdana', Arial, Helvetica;\">"
	+"��ֹ2015��9�£�����������ۣ��������Ϻ������ݣ����ڣ����죬����Ͼ������ݣ����ݣ��ɶ������ݣ����������������ݣ��人����ɳ����̨��������������Ϋ��������22���������80�������ҽ�����ģ���30�ҳݿƷ������ģ�������֪���ķ���ɸ�����Ѫ�ܼ���������ģ���������ҵԱ�����и߶�������ģ�������߶�����������ݿƷ������ģ���˽��ҽ�����񣬶���������ֺͿ�˥�Ϸ��񣬰���ʵ����ȫ��λ�ĸ��ǡ�"
+"</p>"
+"<p class=\"text\" style=\"font-size:14px;color:#777777;font-family:'Microsoft YaHei', ΢���ź�, SimHei, ����, STHeiti, 'MingLiu Verdana', Arial, Helvetica;\">"
	+"<img src=\"http://121.201.16.213/couponsweb/image/brand/160223.581.3455.jpg\" alt=\"\" width=\"100%\" /> "
+"</p><n>��������������<n>";  
        System.out.println(getTextFromHtml(str));
    }  
}  
