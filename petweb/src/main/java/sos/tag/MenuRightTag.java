package sos.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ComponentTagSupport;

import com.opensymphony.xwork2.util.ValueStack;

public class MenuRightTag extends ComponentTagSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7673536381885511789L;
	private String moduleid;
	private String value;
	private String name;

	@Override
	public Component getBean(ValueStack arg0, HttpServletRequest arg1,
			HttpServletResponse arg2) {
		// TODO Auto-generated method stub
		return new MenuRight(arg0,arg1);
	}
	//获得参数   
    protected void populateParams() {   
        super.populateParams();   
        MenuRight mr = (MenuRight)component;   
        mr.setValue(value);
        mr.setModuleid(moduleid);
        mr.setName(name);
    }
    
	public void setValue(String value) {
		this.value = value;
	}
	public void setModuleid(String moduleid) {
		this.moduleid = moduleid;
	}
	public void setName(String name){
		this.name = name;
	}
	
}
