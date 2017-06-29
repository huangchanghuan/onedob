package sos.tag;

import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;

import com.opensymphony.xwork2.util.ValueStack;
import com.sunstar.sos.constants.Constants;
import com.sunstar.sos.permission.LoginUser;

/**
 * 权限标签，判断当前用户对请求的链接是否具有操作权限
 * @author zhou
 */
public class Right extends Component {
	private String value;// 功能权限
	private String onclick; //点击事件
	
	private boolean hasPermissin = false; //是否具有操作权限
	private boolean escape = false;
	HttpServletRequest _req;
	HttpServletResponse _res;
	
	public Right(ValueStack stack,HttpServletRequest req,HttpServletResponse res) {
		super(stack);
		this._req = req;
		this._res = res;
	}
	
	public Right(ValueStack stack,HttpServletRequest req) {
		super(stack);
		this._req = req;
	}
	public boolean start(Writer writer) {
		try {
			initPermissions();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(hasPermissin){
			try{
				if(onclick != null && !"".equals(onclick.trim())){
					while(true){
						if(onclick.indexOf("#{") > 0){ //存在参数
							String param = onclick.substring(onclick.indexOf("#{")+2, onclick.indexOf("}"));
							if(param.trim().length()==0)
								onclick = onclick.replaceAll("#{"+param+"}", "");
							Object value = this.stack.findValue(param.trim());
							if(value instanceof String[])
								onclick = onclick.replaceAll("#[{]"+param+"[}]", ((String[])value)[0]);
							else
								onclick = onclick.replaceAll("#[{]"+param+"[}]", value.toString());
						}else
							break;
					}
					writer.write("<a href='javascript:void(0);' onclick=\""+onclick+"\">");
				}
			}catch(Exception e){e.printStackTrace();}
			return super.start(writer);
		}
		else{
			if(onclick != null && !"".equals(onclick.trim()))
				return super.start(writer);
			return false;
		}
		
		
	}
	
	@Override
	public boolean end(Writer writer, String body) {
		
		try{
			if(hasPermissin)
			if(onclick != null && !"".equals(onclick.trim())){
				writer.write("</a>");
			}
		}catch(Exception e){e.printStackTrace();}
		return super.end(writer, body);
	}
	private void initPermissions() throws Exception{
		LoginUser user = (LoginUser)this._req.getSession().getAttribute(Constants.LOGIN_USER);
//		LoginUser user = UserHelper.getUserFromCache(UserHelper.readKeyFromCookie(_req,_res));
//		ActionProxy proxy = ServletActionContext.getContext().getActionInvocation().getProxy();
		hasPermissin = user.hasPermission(value);
//		hasPermissin = user.hasPermission(ModulesConfig.getModuleId(proxy.getNamespace(), proxy.getActionName()), value);
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getOnclick() {
		return onclick;
	}
	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}
	public void setEscape(boolean escape) {
		this.escape = escape;
	}
	public boolean isEscape() {
		return escape;
	}
}
