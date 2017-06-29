package sos.validators.validator;

import com.opensymphony.xwork2.util.ValueStack;

/**
 * ×Ö·û´®ÑéÖ¤Àà
 * @author Administrator
 *
 */
public class StringValidator extends BaseValidator {
	private boolean trim = false;
	private boolean required = false;
	private Integer minLength = null;
	private Integer maxLength = null;
	private String expression = null;
	private String regex = null;
	private String contains = null;
	
	public boolean validator(ValueStack stack){
		Object o = stack.findValue(this.getFieldname());
		String value = (String)o;
		if(required && (o == null || ((String)o).trim().equals(""))){
			this.setErrorMessage(this.getMSGMap().get("required"));
			return false;
		}
		if(trim)
			value = value!=null?value.trim():null;
		if(minLength != null){
			if(value!=null && value.length()<minLength){
				this.setErrorMessage(this.getMSGMap().get("minLength"));
				return false;
			}
		}
		if(maxLength != null){
			if(value!=null && value.length()>maxLength){
				this.setErrorMessage(this.getMSGMap().get("maxLength"));
				return false;
			}
		}
		if(expression != null){
			if(value!=null){
				
				this.setErrorMessage(this.getMSGMap().get("maxLength"));
				return false;
			}
		}
		return true;
	}
}
