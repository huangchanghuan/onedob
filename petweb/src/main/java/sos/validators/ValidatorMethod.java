package sos.validators;

import java.util.ArrayList;
import java.util.List;

import com.opensymphony.xwork2.util.ValueStack;
import com.sunstar.sos.validators.validator.BaseValidator;

/**
 * 校验器方法执行校验对象，用于执行某个方法的校验
 * @author Administrator
 *
 */
public class ValidatorMethod {
	private String methodName;
	private String retType;
	private String retString;
	
	/**
	 * 校验器集合
	 */
	private List<BaseValidator> fvList = new ArrayList<BaseValidator>();
	
	/**
	 * 构造函数
	 * @param method action方法名
	 * @param retType  如果校验失败后，返回数据的格式: html json
	 * @param retString 如果校验失败后， 返回result定义字符串。
	 */
	public ValidatorMethod(String method,String retType,String retString){
		this.methodName = method;
		this.retString = retString;
		this.retType = retType;
	}
	
	/**
	 * 迭代校验器，执行校验
	 * @param stack
	 * @return
	 */
	public boolean validator(ValueStack stack){
		for(BaseValidator valid : fvList){
			valid.setValueStack(stack);
			if(!valid.validator(stack))
				return false;
		}
		return true;
	}
	
	/**
	 * 添加校验器对象
	 * @param bv
	 */
	public void addFieldValidator(BaseValidator bv){
		fvList.add(bv);
	}
	
	public String getResult(){
		return retString;
	}

	public String getMethodName() {
		return methodName;
	}

	public String getRetType() {
		return retType;
	}
}
