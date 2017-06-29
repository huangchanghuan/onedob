package sos.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ComponentTagSupport;

import com.opensymphony.xwork2.util.ValueStack;

public class RightTag extends ComponentTagSupport{
	private static final long serialVersionUID = 561995753448794337L;
	private String value;
	private String onclick;
	
	public Component getBean(ValueStack arg0, HttpServletRequest arg1, HttpServletResponse arg2) {
		return new Right(arg0,arg1);
	}
	//获得参数   
    protected void populateParams() {   
        super.populateParams();   
        Right right = (Right)component;   
        right.setValue(value);
        right.setOnclick(onclick);
    }
    
	public void setValue(String value) {
		this.value = value;
	}
	
	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}
	
}
