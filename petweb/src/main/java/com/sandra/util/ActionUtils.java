package com.sandra.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Action实用类
 * @author zhou
 */
public class ActionUtils {
	private static final List<String> excludeMethods = new ArrayList<String>();
	static{
		excludeMethods.add("setPo");
		excludeMethods.add("getPo");
		excludeMethods.add("execute");
		excludeMethods.add("setBpo");
		excludeMethods.add("getBpo");
		excludeMethods.add("setObj");
		excludeMethods.add("getPageForm");
		excludeMethods.add("setFormData");
		excludeMethods.add("getSession");
		excludeMethods.add("setMessage");
		excludeMethods.add("getFormValue");
		excludeMethods.add("setMsg");
		excludeMethods.add("beginRequest");
		excludeMethods.add("endRequest");
		excludeMethods.add("getRequest");
		excludeMethods.add("getResponse");
		excludeMethods.add("getContextPath");
		excludeMethods.add("getRealPath");
		excludeMethods.add("getServletContext");
		excludeMethods.add("getMsg");
		excludeMethods.add("getCmd");
		excludeMethods.add("setCmd");
		excludeMethods.add("getFormMap");
		excludeMethods.add("setFormMap");
		excludeMethods.add("setFormValue");
		excludeMethods.add("getFormStringValue");
		excludeMethods.add("getFormData");
		excludeMethods.add("setPageForm");
		excludeMethods.add("getPath");
		excludeMethods.add("error");
		excludeMethods.add("getData");
		excludeMethods.add("getObj");
		excludeMethods.add("setData");
		excludeMethods.add("setActionErrors");
		excludeMethods.add("getActionErrors");
		excludeMethods.add("setActionMessages");
		excludeMethods.add("getActionMessages");
		excludeMethods.add("getErrorMessages");
		excludeMethods.add("getErrors");
		excludeMethods.add("setFieldErrors");
		excludeMethods.add("getFieldErrors");
		excludeMethods.add("hasKey");
		excludeMethods.add("getTexts");
		excludeMethods.add("getTexts");
		excludeMethods.add("addActionError");
		excludeMethods.add("addActionMessage");
		excludeMethods.add("addFieldError");
		excludeMethods.add("doDefault");
		excludeMethods.add("hasActionErrors");
		excludeMethods.add("hasActionMessages");
		excludeMethods.add("hasErrors");
		excludeMethods.add("hasFieldErrors");
		excludeMethods.add("clearFieldErrors");
		excludeMethods.add("clearActionErrors");
		excludeMethods.add("clearMessages");
		excludeMethods.add("clearErrors");
		excludeMethods.add("clearErrorsAndMessages");
		excludeMethods.add("pause");
		excludeMethods.add("setContainer");
		excludeMethods.add("clone");
		excludeMethods.add("getLocale");
		excludeMethods.add("validate");
		excludeMethods.add("getText");
		excludeMethods.add("input");
		excludeMethods.add("wait");
		excludeMethods.add("equals");
		excludeMethods.add("toString");
		excludeMethods.add("hashCode");
		excludeMethods.add("getClass");
		excludeMethods.add("notify");
		excludeMethods.add("notifyAll");
		excludeMethods.add("login");
		excludeMethods.add("logout");
		excludeMethods.add("cleanRequest");
		excludeMethods.add("setJSONErrorMessage");
		excludeMethods.add("setHtmlErrorMessage");
		excludeMethods.add("getSessionFromCookie");
		excludeMethods.add("getSessionFromCached");
		excludeMethods.add("getFormatted");
	}
	/**
	 * 获取action的业务方法
	 * @param cls action类
	 * @return
	 */
	public static List<String> getMethods(Class<?> cls){
		List<String> list = new ArrayList<String>();
		Method[] ms = cls.getMethods();
		for(Method m:ms){
			String name = m.getName();
			if(excludeMethods.contains(name))
				continue;
			list.add(name);
		}
		return list;
	}
	
}
