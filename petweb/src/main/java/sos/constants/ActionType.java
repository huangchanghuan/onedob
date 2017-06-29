package sos.constants;


/**
 * 操作类型定义类,定义操作命令字
 * @author Administrator
 *
 */
public class ActionType
{
	private ActionType(){}
	/********************* 请求的命令字  *******************************/
	/****** 登录、注销 *********/
	public static final String ACTION_LOGIN = "login";
	public static final String ACTION_LOGOUT = "logout";
	/******  读、查询 操作 **********/
	public static final String ACTION_MAIN = "main";
	public static final String ACTION_LIST = "list";
	public static final String ACTION_DATA = "data";
	public static final String ACTION_SUB_DATA = "subData";
	public static final String ACTION_SUB_LIST= "subList";
	public static final String ACTION_SEARCH = "search";
	public static final String ACTION_PREVIEW = "preview";
	public static final String ACTION_GO_DOWNLOAD = "goDownload";
	public static final String ACTION_DOWNLOAD = "download";
	public static final String ACTION_TREE = "tree";
	public static final String ACTION_NOTIFY = "notify";
	public static final String ACTION_MENU = "menu";
	public static final String ACTION_CONDITION = "condition";
	
	/******  写、添加 操作 **********/
	public static final String ACTION_GO_ADD = "goAdd";
	public static final String ACTION_ADD = "add";
	public static final String ACTION_GO_UPLOAD = "goUpload";
	public static final String ACTION_UPLOAD = "upload";
	
	/******  修、更新 操作 **********/
	public static final String ACTION_GO_UPDATE = "goUpdate";
	public static final String ACTION_UPDATE = "update";
	
	/******  删、删除 操作 **********/
	public static final String ACTION_DELETE = "delete";
	
	/******  看、查看 操作 **********/
	public static final String ACTION_GO_VIEW = "goView";
	public static final String ACTION_VIEW = "view";
	
	/******  审核 操作 **********/
	public static final String ACTION_GO_CHECK = "goCheck";
	public static final String ACTION_CHECK = "check";
	
	
	/*********************   可用的result结果字，但不限于下面这些   ************************/
	public static final String RESULT_ADD_UPDATE = "addOrUpdate";//转到添加/修改界面
	public static final String RESULT_NO_USER = "noUser";//没有用户，session超时或没有登录
	public static final String RESULT_NO_CMD = "cmdError";//命令字错误，不允许的命令字
	public static final String RESULT_NO_PATH = "pathError";//不存在的访问路径或者没有登记注册的访问路径
	public static final String RESULT_NO_RIGHT = "noRight";//没有权限
	public static final String RESULT_NO_FUNC_METHOD = "noFuncMethod";//没有定义功能和方法映射
	public static final String RESULT_SUCCESS = "success"; //操作成功
	public static final String RESULT_FAILSE = "failse"; //操作失败
	public static final String RESULT_ERROR = "error";   //命令字错误，或者其它错误，转到错误页面
	public static final String RESULT_MSG = "msg";  //返回消息,或利用msg属性字段返回小的片段数据
	public static final String RESULT_MAIN_PAGE = "mainPage";  //返回主界面
	public static final String BRAND_RESULT_SUCCESS = "brandMainPage";
}