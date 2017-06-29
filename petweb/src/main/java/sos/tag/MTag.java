package sos.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ComponentTagSupport;

import com.opensymphony.xwork2.util.ValueStack;

/**
 * 判断方法名称标签类
 * @author Administrator
 *
 */
public class MTag extends ComponentTagSupport{
	private static final long serialVersionUID = 561998753448794337L;
	private String value;
	
	public Component getBean(ValueStack arg0, HttpServletRequest arg1, HttpServletResponse arg2) {
		return new M(arg0,arg1);
	}
	//获得参数   
    protected void populateParams() {   
        super.populateParams();   
        M m = (M)component;   
        m.setValue(value);
    }
    
	public void setValue(String value) {
		this.value = value;
	}
}
