package sos.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 字段注解类
 * 标记该字段是否启用唯一性约束
 * @author zhou
 */
@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.FIELD})
public @interface Validation {
	/**
	 * 启用唯一性约束
	 * @return true/false  启用/不启用
	 */
	boolean unique() default true;
}
