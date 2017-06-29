package sos.tag;

import java.io.Writer;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.components.Component;

import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.util.ValueStack;
import com.sunstar.sos.cfg.ModulesConfig;
import com.sunstar.sos.constants.Constants;
import com.sunstar.sos.permission.LoginUser;

public class MenuRight extends Component {
	private String value;// 功能权限
	private String moduleid;
	private String name;
	
	private boolean hasPermissin = false; //是否具有操作权限
	private boolean escape = false;
	HttpServletRequest _req;

	public MenuRight(ValueStack arg0, HttpServletRequest arg1) {
		// TODO Auto-generated constructor stub
		super(arg0);
		this._req = arg1;
	}
	
	private void initPermissions(){
		LoginUser user = (LoginUser)this._req.getSession().getAttribute(Constants.LOGIN_USER);
//		ActionProxy proxy = ServletActionContext.getContext().getActionInvocation().getProxy();
		hasPermissin = user.hasMenuPermission(moduleid);
	}
	
	
	public boolean start(Writer writer) {
		initPermissions();
		if(hasPermissin){
			try{
				if(value != null && !"".equals(value.trim())){
					writer.write("<li><a target='mainFrame' href='"+value+"' class='re-nav'>"+name);
				}
			}catch(Exception e){e.printStackTrace();}
			return super.start(writer);
		}
		else{
			if(value != null && !"".equals(value.trim()))
				return super.start(writer);
			return false;
		}
	}
	
	
	
	@Override
	public boolean end(Writer writer, String body) {
		// TODO Auto-generated method stub
		try{
			if(hasPermissin)
			if(value != null && !"".equals(value.trim())){
				writer.write("</a></li>");
			}
		}catch(Exception e){e.printStackTrace();}
		return super.end(writer, body);
	}

	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	public void setEscape(boolean escape) {
		this.escape = escape;
	}
	public boolean isEscape() {
		return escape;
	}

	public String getModuleid() {
		return moduleid;
	}

	public void setModuleid(String moduleid) {
		this.moduleid = moduleid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
