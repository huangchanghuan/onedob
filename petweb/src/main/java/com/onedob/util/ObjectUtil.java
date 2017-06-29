package com.onedob.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ObjectUtil {
	
	public static Field getField(Class cls,String name){
		Field f = null;
		try{
			f = cls.getDeclaredField(name);
		}catch(Exception ex){ }
		return f;
	}
	
	public static Object cloneObject(Object obj){
		try{
			Class cls = obj.getClass();
			Object vo = cls.newInstance();
			Field[] fs = cls.getDeclaredFields();
			for(int i=0; i<fs.length; ++i){
				Object value = getValue(obj,getGetter(fs[i]),new Class[]{},new Object[]{});
				setValue(vo,getSetter(fs[i]),new Class[]{fs[i].getType()},new Object[]{value});
			}
			return vo;
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	
	public static Object getValue(Class<?> cls, Object value){
		Object v = value;
		if(cls.equals(int.class))
			v = new Integer(value.toString()).intValue();
		else if(cls.equals(Integer.class))
			v = new Integer(value.toString());
		else if(cls.equals(long.class))
			v = new Long(value.toString()).longValue();
		else if(cls.equals(Long.class))
			v = new Long(value.toString());
		else if(cls.equals(String.class))
			v = value.toString();
		else if(cls.equals(short.class))
			v = new Short(value.toString()).shortValue();
		else if(cls.equals(Short.class))
			v = new Short(value.toString());
		return v;
	}
	
	public static Method getMethod(Class cls,String name,Class[] parameterTypes){
		try {
			return cls.getMethod(name, parameterTypes);
		} catch (Exception e) {}
		return null;
	}
	
	public static void setValue(Object obj,Method m,Object[] values){
		try {
			m.invoke(obj, values);
		} catch (Exception e) {}
	}

	public static Object getValue(Object obj,String name,Class[] parameterTypes,Object[] values){
		try {
			return getMethod(obj.getClass(),name,parameterTypes).invoke(obj, values);
		} catch (Exception e) { }
		return null; 
	}
	public static Object getFieldValue(Object obj,String name)throws Exception{
		Field f = null;
		try {
			f = obj.getClass().getDeclaredField(name);
		} catch (Exception e) {
			throw new Exception("�ࣺ"+obj.getClass()+" ��û�����ԣ�"+name+", �쳣��Ϣ��"+e.getMessage());
		}
		try {
			boolean b = f.isAccessible();
			f.setAccessible(true);
			Object v = f.get(obj);
			f.setAccessible(b);
			return v;
		} catch (Exception e) {
			throw new Exception("��"+obj.getClass()+" ������ȡ����Ϊ��"+name+" ��ֵʧ��, �쳣��Ϣ��"+e.getMessage());
		} 
	}
	
	public static void setValue(Object obj,String name,Class[] parameterTypes,Object[] values){
		try {
			Method m = getMethod(obj.getClass(),name,parameterTypes);
			m.invoke(obj, values);
		} catch (Exception e) {}
	}
	
	public static Class getClass(Object obj){
		return obj.getClass();
	}
	
	public static String getClassName(Object obj){
		return obj.getClass().getName();
	}
	
	public static String getUpName(Field field){
		String name = field.getName();
		name = name.substring(0,1).toUpperCase()+name.substring(1);
		return name;
	}
	
	public static String getGetter(Field field){
		String name = field.getName();
		name = name.substring(0,1).toUpperCase()+name.substring(1);
		return "get"+name;
	}
	
	public static String getSetter(Field field){
		String name = field.getName();
		name = name.substring(0,1).toUpperCase()+name.substring(1);
		return "set"+name;
	}
	
	public static String getGetter(String name){
		name = name.substring(0,1).toUpperCase()+name.substring(1);
		return "get"+name;
	}
	
	public static String getSetter(String name){
		name = name.substring(0,1).toUpperCase()+name.substring(1);
		return "set"+name;
	}
}
