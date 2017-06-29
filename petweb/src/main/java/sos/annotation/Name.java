package sos.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 方法注解类
 * 标记该方法为session方法，需要开启数据库操作
 * @author zhou
 */
@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.FIELD})
public @interface Name {
	/**
	 * pojo字段的中文名称
	 * @return
	 */
	String name();
}
