package sos.validators.validator;

import java.util.regex.Pattern;

import com.opensymphony.xwork2.util.ValueStack;

/**
 * 字符校验器类
 * @author Administrator
 *
 */
public class RequiredStringValidator extends BaseValidator {
	private boolean trim = false;
	private Integer minLength;
	private Integer maxLength;
	private String expression;
	private String regex;
	
	
	/**
	 * 执行校验
	 */
	@Override
	public boolean validator(ValueStack stack) {
		Object o = stack.findValue(this.getFieldname());
		//必填判断
		if(o == null || ((String)o).trim().length() == 0){
			this.setErrorMessage(this.getMessage());
			return false;
		}
		//去除前后不可见字符。
		String value = trim?((String)o).trim():(String)o;
		//判断最小长度
		if(minLength != null && value.length() < minLength){
			this.setErrorMessage(this.getMSGMap().get("minLength"));
			return false;
		}
		//判断最大长度
		if(maxLength != null && value.length() > maxLength){
			this.setErrorMessage(this.getMSGMap().get("maxLength"));
			return false;
		}
		//计算OGNL表达式
		if(expression != null &&! (Boolean)stack.findValue(expression, true)){
			this.setErrorMessage(this.getMSGMap().get("expression"));
			return false;
		}
		//正则表达式判断
		if(regex != null && !Pattern.matches(regex, value)){
			this.setErrorMessage(this.getMSGMap().get("regex"));
			return false;
		}
		return true;
	}
}
