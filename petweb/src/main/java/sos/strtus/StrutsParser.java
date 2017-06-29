package sos.strtus;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Struts.xml配置文件解析类
 * @author z
 */
public class StrutsParser {
	private static Map<String,Map<String,String>> nsMap = new HashMap<String,Map<String,String>>();
	static{
		parser();
	}
	
	public static List<String> getNamespaces(){
		List<String> list = new ArrayList<String>();
		for(Iterator<String> it = nsMap.keySet().iterator(); it.hasNext();){
			list.add(it.next());
		}
		return list;
	}
	
	public static Map<String,String> getActionname(String namespace){
		return nsMap.get(namespace);
	}
	
	/**
	 * 解析struts.xml及子模块的struts配置文件
	 */
	public static void parser(){
		nsMap.clear();
		try{
			Document doc = getDocument(StrutsParser.class.getClassLoader().getResourceAsStream("struts.xml"));
			Element root = doc.getRootElement();
			@SuppressWarnings("unchecked")
			List<Element> list = root.elements("include");
			System.out.println(list.size());
			for(Element elem : list){
				String f = elem.attributeValue("file");
				parserNamespace(getDocument(StrutsParser.class.getClassLoader().getResourceAsStream(f)));
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private static void parserNamespace(Document doc){
		@SuppressWarnings("unchecked")
		List<Element> list = doc.getRootElement().elements("package");
		for(Element elem : list){
			String ns = elem.attributeValue("namespace");
			if(nsMap.get(ns)==null){
				nsMap.put(ns, new HashMap<String,String>());
			}
			@SuppressWarnings("unchecked")
			List<Element> actions = elem.elements("action");
			for(Element act : actions){
				nsMap.get(ns).put(act.attributeValue("name"), act.attributeValue("class"));
			}
		}
	}
	
	private static Document getDocument(InputStream stream){
		SAXReader reader = new SAXReader(false);
		try{
			reader.setValidation(false);
			reader.setEntityResolver(new EntityResolver() {
                public InputSource resolveEntity(String publicId,
                        String systemId) throws SAXException, IOException {
                    return new InputSource(new StringReader(""));
                }
            });
			return reader.read(stream);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	public static void main(String[] s)throws Exception{
		
	}
}
