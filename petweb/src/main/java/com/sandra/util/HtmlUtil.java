package com.sandra.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlUtil {  
    private static final String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
    private static final String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
    private static final String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
    private static final String regEx_space = "\\s*|\t|\r|\n";//定义空格回车换行符
      
    /** 
     * @param htmlStr 
     * @return 
     *  删除Html标签 
     */  
    public static String delHTMLTag(String htmlStr) {
        Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(htmlStr);
        htmlStr = m_script.replaceAll(""); // 过滤script标签  
  
        Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(htmlStr);
        htmlStr = m_style.replaceAll(""); // 过滤style标签  
  
        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll(""); // 过滤html标签  
  
        Pattern p_space = Pattern.compile(regEx_space, Pattern.CASE_INSENSITIVE);
        Matcher m_space = p_space.matcher(htmlStr);
        htmlStr = m_space.replaceAll(""); // 过滤空格回车标签  
        return htmlStr.trim(); // 返回文本字符串  
    }  
      
    public static String getTextFromHtml(String htmlStr){
        htmlStr = delHTMLTag(htmlStr);  
        htmlStr = htmlStr.replaceAll("&nbsp;", "");  
//        htmlStr = htmlStr.substring(0, htmlStr.indexOf("。")+1);  
        return htmlStr;  
    } 
    /**
     * 只去掉回车换行符
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
        String str = "<n>啦啦啦啦啦啦啦 <p class=\"text\" style=\"font-size:14px;color:#777777;font-family:'Microsoft YaHei', 微软雅黑, SimHei, 黑体, STHeiti, 'MingLiu Verdana', Arial, Helvetica;\">"
	+"爱康集团是中国位居前列的检款管理（预防医疗）集团，依托期下健康医疗服务中心、IT技术平台和强大的客户服务体系，每年为数百万客户提供健康体检、疾病检测、齿科服务、移动医疗、私人医生、职场医疗、疫苗接种、中医理疗、抗衰老等健康管理服务。"
+"</p>"
+"<p class=\"text\" style=\"font-size:14px;color:#777777;font-family:'Microsoft YaHei', 微软雅黑, SimHei, 黑体, STHeiti, 'MingLiu Verdana', Arial, Helvetica;\">"
	+"截止2015年9月，爱康已在香港，北京，上海，广州，深圳，重庆，天津，南京，苏州，杭州，成都，福州，长春，江阴，常州，武汉，长沙，烟台，银川，威海，潍坊和沈阳22大城市设有80家体检与医疗中心，近30家齿科服务中心，从亚洲知名的防癌筛查和心血管疾病检测中心，到服务企业员工的中高端体检中心；从涉外高端门诊，到连锁齿科服务中心；从私人医生服务，懂啊疫苗接种和抗衰老服务，爱康实现了全方位的覆盖。"
+"</p>"
+"<p class=\"text\" style=\"font-size:14px;color:#777777;font-family:'Microsoft YaHei', 微软雅黑, SimHei, 黑体, STHeiti, 'MingLiu Verdana', Arial, Helvetica;\">"
	+"<img src=\"http://121.201.16.213/couponsweb/image/brand/160223.581.3455.jpg\" alt=\"\" width=\"100%\" /> "
+"</p><n>啦啦啦啦啦啦啦<n>";  
        System.out.println(getTextFromHtml(str));
    }  
}  
