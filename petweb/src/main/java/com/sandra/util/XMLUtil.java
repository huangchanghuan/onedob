package com.sandra.util;


import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class XMLUtil {

	/**
     * 解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据。
    * @param strxml
     * @return
     * @throws IOException
     */
    public static Map doXMLParse(String strxml) throws  IOException, DocumentException {
        strxml = strxml.replaceFirst("encoding=\".*\"", "encoding=\"UTF-8\"");

        if(null == strxml || "".equals(strxml)) {
            return null;
        }
        
        Map m = new HashMap();
        
        InputStream in = new ByteArrayInputStream(strxml.getBytes("UTF-8"));
        SAXReader builder = new SAXReader ();
        Document doc = builder.read(in);
        Element root = doc.getRootElement();
        List list = root.elements();
        Iterator it = list.iterator();
        while(it.hasNext()) {
            Element e = (Element) it.next();
            String k = e.getName();
            String v = "";
            List children = e.elements();
            if(children.isEmpty()) {
                v = e.getTextTrim();
            } else {
                v = XMLUtil.getChildrenText(children);
            }
            
            m.put(k, v);
        }
        
        //关闭流
       in.close();
        
        return m;
    }
    
    /**
     * 获取子结点的xml
     * @param children
     * @return String
     */
    public static String getChildrenText(List children) {
        StringBuffer sb = new StringBuffer();
        if(!children.isEmpty()) {
            Iterator it = children.iterator();
            while(it.hasNext()) {
                Element e = (Element) it.next();
                String name = e.getName();
                String value = e.getTextTrim();
                List list = e.elements();
                sb.append("<" + name + ">");
                if(!list.isEmpty()) {
                    sb.append(XMLUtil.getChildrenText(list));
                }
                sb.append(value);
                sb.append("</" + name + ">");
            }
        }
        
        return sb.toString();
    }
}
