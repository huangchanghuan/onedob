package sos.validators;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.sunstar.sos.validators.validator.BaseValidator;

/**
 * 拦截输入校验类
 * @author Administrator
 *
 */
public class ValidatorInterceptor extends AbstractInterceptor{
	private static final long serialVersionUID = 1L;
	//方法校验集合
	private static Map<String,ValidatorMethod> map = new HashMap<String,ValidatorMethod>();
	//已经处理的Action校验文件集合
	private static Map<String,Boolean> fMap = new HashMap<String,Boolean>();
	
	/**
	 * 拦截方法
	 */
	@Override
	public String intercept(ActionInvocation actioninvocation) throws Exception {
		ValidatorMethod vm = getValidator(actioninvocation);
		if(vm != null && !vm.validator(actioninvocation.getStack())){
			return vm.getResult();
		}
		return actioninvocation.invoke();
	}
	
	/**
	 * 取得方法校验器对象
	 * @param inv
	 * @return
	 */
	private ValidatorMethod getValidator(ActionInvocation inv){
		String cls = inv.getAction().getClass().getName();
		String method = inv.getProxy().getMethod();
		ValidatorMethod vm = map.get(cls+"-"+method);
		if(vm == null && fMap.get(cls) == null){
			vm = parser(inv, cls+"-"+method);//解析校验文件，取得方法的校验器列表，
		}
		return vm;
	}
	
	/**
	 * 解析校验文件
	 * @param inv
	 */
	synchronized private ValidatorMethod parser(ActionInvocation inv, String key){
		String cls = inv.getAction().getClass().getName();
		fMap.put(cls, true);
		ValidatorMethod vm = map.get(key);
		if(vm != null)
			return vm;
		InputStream ins = ValidatorInterceptor.class.getClassLoader().getResourceAsStream(cls.replaceAll("\\.", "/")+".Validator.xml");
		if(ins == null)
			return null;
		SAXReader reader = new SAXReader();
		try {
			Document doc = reader.read(ins);
			List<Element> list = doc.getRootElement().elements();
			for(Element e : list){
				String methodName = e.attributeValue("name");
				Element ret = e.element("return");
				ValidatorMethod method = new ValidatorMethod(methodName,ret.attributeValue("type"),ret.getTextTrim());
				parserFieldValidator(e,method);
				map.put(cls+"-"+methodName, method);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				ins.close();
			} catch (IOException e) {}
		}
		return null;
	}
	
	/**
	 * 解析字段校验器
	 * @param ele
	 * @param method
	 * @throws Exception 
	 */
	private void parserFieldValidator(Element ele,ValidatorMethod method) throws Exception{
		List<Element> list = ele.elements("field");
		for(Element e : list){
			String type = e.attributeValue("type");
			BaseValidator b = BaseValidator.getValidator(type);
			b.setFieldname(e.attributeValue("name"));
			b.setType(type);
			b.setRetType(method.getRetType());
			b.setMessage(e.element("message").getTextTrim());
			parserParameter(e,b);
			method.addFieldValidator(b);
		}
	}
	
	/**
	 * 解析校验器的字段校验参数定义
	 * @param ele
	 * @param b
	 * @throws Exception 
	 */
	private void parserParameter(Element ele, BaseValidator b) throws Exception{
		@SuppressWarnings("unchecked")
		List<Element> list = ele.elements("param");
		for(Element e : list){
			String name =  e.attributeValue("name");
			String message = e.attributeValue("message");
			String value = e.getTextTrim();
			b.addParamter(name, message, value);
		}
	}
}
