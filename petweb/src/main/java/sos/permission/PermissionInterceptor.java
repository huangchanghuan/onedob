package sos.permission;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.StrutsStatics;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.sunstar.sos.cfg.ModulesConfig;
import com.sunstar.sos.cfg.SystemConfig;
import com.sunstar.sos.constants.ActionType;
import com.sunstar.sos.constants.Constants;
/**
 * 权限拦截器类
 * @author zhou
 *
 */
public class PermissionInterceptor extends AbstractInterceptor {
	private static final Logger logger = Logger.getLogger(PermissionInterceptor.class);
	private static final long serialVersionUID = -7565791452709783328L;
	public void init() {
		super.init();
	}

	/**
	 * 执行拦截
	 */
	public String intercept(ActionInvocation invocation) throws Exception {
		ActionContext actionContext = invocation.getInvocationContext();
		ActionProxy proxy = invocation.getProxy();
		String actionName = proxy.getActionName();
		HttpServletRequest req = (HttpServletRequest) actionContext.get(StrutsStatics.HTTP_REQUEST);
		HttpServletResponse resp = (HttpServletResponse) actionContext.get(StrutsStatics.HTTP_RESPONSE);
		
		HttpSession session = req.getSession();
		
		LoginUser user = (LoginUser) req.getSession().getAttribute(Constants.LOGIN_USER);
		String method = proxy.getMethod();
		req.setAttribute("_method", method);
		if(SystemConfig.isPermissionlog()){
			logger.info("访问路径:"+proxy.getNamespace()+"/"+actionName+", method = "+method);
		}
		if(method == null)
			return ActionType.RESULT_NO_CMD;
		if(!ModulesConfig.existUrl(proxy.getNamespace(), actionName)){
			req.setAttribute("NO_DEFINE_URL", req.getRequestURI());
			logger.error("未配置访问路径URL："+req.getRequestURI()+" 所在模块的业务功能， 以及业务功能所对应的Action方法关系配置。" +
					     "请在模块管理中配置该模块的功能以及与Action方法的映射关系" );
			return "noConfUrl";
		}
		if(!ModulesConfig.isDefineFuncMap(proxy.getNamespace(), proxy.getActionName(), method)){
			logger.error("未配置Action方法:"+method+" 与业务功能的映射关系,请在模块管理中配置该模块的功能与Action方法的映射关系");
			return ActionType.RESULT_NO_FUNC_METHOD;
		}
		if(user!=null) 
			LoginUser.userThread.set(user);
		//需要执行权限过滤  
		if("true".equalsIgnoreCase(session.getServletContext().getInitParameter("permission"))){
			if(actionName.equals("login") && Pattern.matches("login|logout|isLogoned", method) ){
				if(method.equals("login") && user != null)
					return ActionType.RESULT_MAIN_PAGE;
				if(method.equals("logout") && user == null)
					return ActionType.ACTION_LOGIN;
				return invocation.invoke();
			}
			if(user == null)
				return ActionType.RESULT_NO_USER;
			if(!actionName.equals("login")){
				if(!user.hasPermissions(proxy))
					return ActionType.RESULT_NO_RIGHT;
				else
					return invocation.invoke();
			}
		}
		return invocation.invoke();
	}
}
