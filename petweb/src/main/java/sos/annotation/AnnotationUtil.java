package sos.annotation;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import org.carp.beans.CarpBean;
import org.carp.beans.ColumnsMetadata;
import org.carp.beans.PrimarysMetadata;
import org.carp.factory.BeansFactory;

public class AnnotationUtil {
	/**
	 * 取得po对象对应的唯一约束定义所需要的验证sql
	 * @param po
	 * @return
	 */
	public static String getUniqueSql(Object po){
		try{
			Class<?> cls = po.getClass();
			Field[] farr = cls.getDeclaredFields();
			CarpBean bean = BeansFactory.getBean(cls);
			StringBuilder wSql = new StringBuilder("");
			for(Field f:farr)
				processParameters(po,bean,f,wSql);
			return wSql.toString();
		}catch(Exception e){e.printStackTrace();};
		return null;
	}
	
	/**
	 * 处理唯一约束的验证sql参数
	 * @param po
	 * @param bean
	 * @param f
	 * @param wSql
	 */
	private static void processParameters(Object po,CarpBean bean,Field f,StringBuilder wSql){
		try{
			List<PrimarysMetadata> pms = bean.getPrimarys();
			List<ColumnsMetadata> cols = bean.getColumns();
			Validation anno = f.getAnnotation(Validation.class);
			if(anno != null && anno.unique()){
				for(PrimarysMetadata pm : pms){
					if(pm.getFieldName().equals(f.getName())){
						if(wSql.length() != 0)
							wSql.append(" and ");
						wSql.append(pm.getColName()).append("=").append(isNumberType(pm.getFieldType())?"":"'")
						.append(pm.getValue(po)).append(isNumberType(pm.getFieldType())?"":"'");
						return;
					}
				}
				for(ColumnsMetadata col : cols){
					if(col.getFieldName().equals(f.getName())){
						if(wSql.length() != 0)
							wSql.append(" and ");
						wSql.append(col.getColName()).append("=").append(isNumberType(col.getFieldType())?"":"'")
						.append(col.getValue(po)).append(isNumberType(col.getFieldType())?"":"'");
						return;
					}
				}
			}
		}catch(Exception ex){ex.printStackTrace();}
	}
	
	public static boolean isNumberType(Class<?> type){
		if(type.equals(Long.class) ||type.equals(long.class))
			return true;
		if(type.equals(Integer.class) ||type.equals(int.class))
			return true;
		if(type.equals(Short.class) ||type.equals(short.class))
			return true;
		if(type.equals(Double.class) ||type.equals(double.class))
			return true;
		if(type.equals(Float.class) ||type.equals(float.class))
			return true;
		if(type.equals(Byte.class) ||type.equals(byte.class))
			return true;
		if(type.equals(BigDecimal.class) ||type.equals(BigInteger.class))
			return true;
		return false;
	}
}
