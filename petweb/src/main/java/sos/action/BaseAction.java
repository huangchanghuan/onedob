package sos.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.carp.beans.CarpBean;
import org.carp.factory.BeansFactory;

import redis.clients.jedis.Jedis;

import com.opensymphony.xwork2.ActionSupport;
import com.sunstar.sos.annotation.AnnotationUtil;
import com.sunstar.sos.bpo.BaseBpo;
import com.sunstar.sos.cache.RedisSessionClient;
import com.sunstar.sos.constants.ActionType;
import com.sunstar.sos.constants.Constants;
import com.sunstar.sos.util.page.PageForm;
import com.sunstar.sos.util.page.PageFormData;

/**
 * Action基类，已实现大部分的跳转方法，业务action继承该基类即可。
 * @author zhou
 */
public abstract class BaseAction extends ActionSupport
{
	private static final long serialVersionUID = 1291522531722539973L;
	protected static Logger logger = Logger.getLogger(BaseAction.class);
	private String msg;  //一般用于json数据传递，返回给页面
	private PageFormData formData;  //包含页面数据和翻页信息
	private List<?> data;          //页面需要显示的数据
	private PageForm pageForm = new PageForm();  // 页面翻页，页码等信息
	private Map<String,Object> formMap = new HashMap<String,Object>();
	private String cmd; //命令字

	public abstract BaseBpo getBpo();
	public abstract Object getPo();
	public abstract void setPo(Object po);

	/****** 登录、注销 *********/
	public String login()throws Exception{
		return ActionType.RESULT_SUCCESS;
	}
	public String logout()throws Exception{
		this.getSession().removeAttribute(Constants.LOGIN_USER);
		return ActionType.RESULT_SUCCESS;
	}
	/******  读、查询 操作 **********/
	public String main()throws Exception{
		return ActionType.ACTION_MAIN;
	}
	public String list()throws Exception{
		return ActionType.ACTION_LIST;
	}
	public String data()throws Exception{
		this.setFormData(getBpo().searchPage(this.getPageForm().getPageNo(), this.getPageForm().getPageSize()));
		return ActionType.ACTION_DATA;
	}

	public String condition()throws Exception{
		return ActionType.ACTION_CONDITION;
	}
	
	/******  写、添加 操作 **********/
	public String goAdd()throws Exception{
		getBpo().initDicData(getPo());
		return ActionType.RESULT_ADD_UPDATE;
	}
	public String add()throws Exception{
		if(this.validationUnique())
			this.setMessage(-1, "已经存在，不能完成添加！");
		else{
			if(getPo()!=null && getBpo().save(getPo())!=null)
				this.setMessage(1, "保存成功");
			else
				this.setMessage(-1, "添加失败");
		}
		return ActionType.RESULT_MSG;
	}
	public String goUpload()throws Exception{
		return ActionType.ACTION_GO_UPLOAD;
	}
	
	/******  修、更新 操作 **********/
	public String goUpdate()throws Exception{
		java.io.Serializable value = (java.io.Serializable)BeansFactory.getBean(getPo().getClass()).getPrimarys().get(0).getValue(getPo());
		this.setPo(getBpo().findById(value));
		return ActionType.RESULT_ADD_UPDATE;
	}
	public String update()throws Exception{
//		if(this.validationUnique())
//			this.setMessage(-1, "已经存在，不能完成更新！");
//		else{
			if(getPo()!=null && getBpo().update(getPo()))
				this.setMessage(1, "更新成功!");
			else
				this.setMessage(-1, "更新失败!");
//		}
		return ActionType.RESULT_MSG;
	}
	
	/******  删、删除 操作 **********/
	public String delete()throws Exception{
		Object ids = this.getFormValue("ids");
		if(ids!=null && ((String[])ids).length>0){
			if(getBpo().deleteById((java.io.Serializable[])ids)){
				this.setMessage(1, "删除成功");
			}else{
				this.setMessage(-1, "删除失败");
			}
		}else{
			this.setMessage(-1, "没有可以删除的数据!");
		}
		return ActionType.RESULT_MSG;
	}
	
	/******  看、查看 操作 **********/
	public String goView()throws Exception{
		this.goUpdate();
		return ActionType.ACTION_GO_VIEW;
	}
	public String view()throws Exception{
		this.goUpdate();
		return ActionType.ACTION_VIEW;
	}
	
	protected boolean validationUnique(){
		try{
			CarpBean bean = BeansFactory.getBean(getPo().getClass());
			String sql = "select count(*) from "+ bean.getTable()+" where ";
			String whereSql = AnnotationUtil.getUniqueSql(getPo());
			if(!whereSql.equals("")){//存在验证
				if(getBpo().count(sql+whereSql)>0)
					return true;
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return false;
	}
	
	public String error(Exception e)
	{
		this.setMsg("后台出错拉,可能是操作失误,具体错误原因:"+e.toString());
		return ActionType.RESULT_ERROR;
	}
	
	public void beginRequest(String message){
		this.getSession().setAttribute(ActionType.ACTION_NOTIFY, "false");
		this.getSession().setAttribute("msg", message);
	}
	public void endRequest(String msg, boolean isSucc){
		this.getSession().setAttribute(ActionType.ACTION_NOTIFY, "true");
		this.getSession().setAttribute("msg", msg);
		this.getSession().setAttribute("IS_SUCCESS", isSucc);
	}
	
	public void cleanRequest(){
		this.getSession().removeAttribute(ActionType.ACTION_NOTIFY);
		this.getSession().removeAttribute("msg");
		this.getSession().removeAttribute("IS_SUCCESS");
	}
	
	/**
	 * 获取request;
	 * 
	 */
	public HttpServletRequest getRequest() {
		HttpServletRequest request = ServletActionContext.getRequest();
		return request;
	}
	
	/**
	 * 获取response;
	 * 
	 */
	public HttpServletResponse getResponse() {
		HttpServletResponse response = ServletActionContext.getResponse();
		return response;
	}
	
	/**
	 * 获取session;
	 */
	public HttpSession getSession() {
		HttpSession session = getRequest().getSession();
		return session;
	}
	
	/**
	 * 取得上下文路径
	 * @return
	 */
	public String getContextPath(){	
		return 	getRequest().getContextPath();
	}
	
	/**
	 * 取得上下文路径
	 * @return
	 */
	public String getRealPath(){	
		return 	getServletContext().getRealPath("");
	}
	
	/**
	 * 取得servlet上下文
	 * @return
	 */
	public ServletContext getServletContext(){
		return ServletActionContext.getServletContext();
	}
	
	public String getMsg(){
		return msg;
	}

	public void setMsg(String msg){
		this.msg = msg;
	}
	
	public void setJSONErrorMessage(int pid,String e){
		this.msg = "{\"processId\":\""+pid+"\",\"msg\":\""+e+"\"}";
	}
	
	public void setHtmlErrorMessage(String e){
		this.msg = e;
	}
	
	public void setMessage(int processId, String msg){
		this.msg = "{\"processId\":\""+processId+"\",\"msg\":\""+msg+"\"}";
	}
	public void setMessage(int processId, String msg,String idvalue){
		this.msg = "{\"processId\":\""+processId+"\",\"msg\":\""+msg+"\",\"id\":\""+idvalue+"\"}";
	}
	public void setMessage(int processId, String msg,String name,String value){
		this.msg = "{\"processId\":\""+processId+"\",\"msg\":\""+msg+"\",\""+name+"\":\""+value+"\"}";
	}

	public String getCmd(){
		return cmd;
	}

	public void setCmd(String cmd){
		this.cmd = cmd;
	}

	public Map<String,Object> getFormMap(){
		return formMap;
	}

	public void setFormMap(Map<String,Object> formMap){
		this.formMap = formMap;
	}
	
	public void setFormValue(String key, Object value){
    	formMap.put(key, value);
    }

    public Object getFormValue(String key){
        return formMap.get(key);
    }
    
    public String getFormStringValue(String key){
    	Object o = formMap.get(key);
    	if(o != null && ((String[])o).length>0)
    		return ((String[])o)[0];
    	return null;
    }


	public PageFormData getFormData(){
		return formData;
	}

	public void setFormData(PageFormData formData){
		this.formData = formData;
		this.setPageForm(this.formData.getPageForm());
		this.setData(this.formData.getData());
	}

	public List<?> getData(){
		return data;
	}

	public void setData(List<?> data){
		this.data = data;
	}

	public PageForm getPageForm(){
		return pageForm;
	}

	public void setPageForm(PageForm pageForm){
		this.pageForm = pageForm;
	}
	 
	public String getPath(){
		return this.getRequest().getContextPath();
	}
}
