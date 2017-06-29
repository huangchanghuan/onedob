/**
 * Copyright 2009-2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.carp.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ObjectUtil {
	
	public static Field getField(Class<?> cls,String name){
		Field f = null;
		try{
			f = cls.getDeclaredField(name);
		}catch(Exception ex){ }
		return f;
	}
	
	public static Class<?> getFieldType(Class<?> cls,String name){
		Class<?> fc = null;
		try{
			Field f = cls.getDeclaredField(name);
			fc = f.getType();
		}catch(Exception ex){ }
		return fc;
	}
	
	/**
	 * 该方法执行一个对象的克隆操作，需要对象类具有无参构造函数。
	 * @param obj
	 * @return
	 */
	public static Object cloneObject(Object obj){
		try{
			Class<?> cls = obj.getClass();
			Object vo = cls.newInstance();
			Field[] fs = cls.getDeclaredFields();
			for(int i=0; i<fs.length; ++i){
				Object value = getValue(obj,getGetter(fs[i]),new Class[]{},new Object[]{});
				setValue(vo,getSetter(fs[i]),new Class[]{fs[i].getType()},new Object[]{value});
			}
			return vo;
		}catch(Exception ex){ }
		return null;
	}
	
	public static Method getMethod(Class<?> cls,String name,Class<?>[] parameterTypes){
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
	
	public static Object getValue(Object obj,Field f){
		Object o = null;
		try {
			boolean b = f.isAccessible();
			f.setAccessible(true);
			o = f.get(obj);
			f.setAccessible(b);
		} catch (Exception e) {} 
		return o;
	}
	
	public static Object getValue(Object obj,String name,Class<?>[] parameterTypes,Object[] values){
		try {
			return getMethod(obj.getClass(),name,parameterTypes).invoke(obj, values);
		} catch (Exception e) {}
		return null; 
	}
	
	public static void setValue(Object obj,String name,Class<?>[] parameterTypes,Object[] values){
		try {
			getMethod(obj.getClass(),name,parameterTypes).invoke(obj, values);
		} catch (Exception e) {} 
	}
	
	public static void setValue(Object obj,Object value,String name,Class<?> parameterType){
		try {
			Method m = getMethod(obj.getClass(),getSetter(name),new Class<?>[]{parameterType});
			if(value == null)
				m.invoke(obj, new Object[]{value});
			else if(parameterType.equals(int.class))
				m.invoke(obj, new Object[]{new Integer(value+"").intValue()});
			else if(parameterType.equals(Integer.class))
				m.invoke(obj, new Object[]{new Integer(value+"")});
			else if(parameterType.equals(long.class))
				m.invoke(obj, new Object[]{new Long(value+"").longValue()});
			else if(parameterType.equals(Long.class))
				m.invoke(obj, new Object[]{new Long(value+"")});
			else if(parameterType.equals(short.class))
				m.invoke(obj, new Object[]{new Short(value+"").shortValue()});
			else if(parameterType.equals(Short.class))
				m.invoke(obj, new Object[]{new Short(value+"")});
			else if(parameterType.equals(byte.class))
				m.invoke(obj, new Object[]{new Byte(value+"").byteValue()});
			else if(parameterType.equals(Byte.class))
				m.invoke(obj, new Object[]{new Byte(value+"")});
			else if(parameterType.equals(String.class))
				m.invoke(obj, new Object[]{value+""});
			else if(parameterType.equals(float.class))
				m.invoke(obj, new Object[]{new Float(value+"").floatValue()});
			else if(parameterType.equals(Float.class))
				m.invoke(obj, new Object[]{new Float(value+"")});
			else if(parameterType.equals(Double.class))
				m.invoke(obj, new Object[]{new Double(value+"")});
			else if(parameterType.equals(double.class))
				m.invoke(obj, new Object[]{new Double(value+"").doubleValue()});
			else
				m.invoke(obj, new Object[]{value});
		} catch (Exception e) {} 
	}
	
	public static Class<?> getClass(Object obj){
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
	
	public static String getFieldName(String name){
		name = name.toLowerCase();
		String[] str = name.split("_");
		StringBuilder buf = new StringBuilder();
		for(String s : str){
			if(!s.trim().equals("")){
				if(buf.length() == 0){
					buf.append(s);
				}else{
					if(s.length() == 1)
						buf.append(s.toUpperCase());
					else
						buf.append(s.substring(0, 1).toUpperCase()+s.substring(1));
				}
			}
		}
		return buf.toString();
	}
}
