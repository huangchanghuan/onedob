package sos.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ComponentTagSupport;

import com.opensymphony.xwork2.util.ValueStack;

public class PageTag extends ComponentTagSupport{
	private static final long serialVersionUID = 561995753448794337L;
	private String value;
	private String position;
	
	public Component getBean(ValueStack arg0, HttpServletRequest arg1,
			HttpServletResponse arg2) {
		return new Page(arg0);
	}
	
	//获得参数   
    protected void populateParams() {   
        super.populateParams();   
        Page pages = (Page)component;   
        pages.setValue(value);
        pages.setPosition(position);
    }
    
	public void setValue(String value) {
		this.value = value;
	}

	public void setPosition(String position) {
		this.position = position;
	}
}
