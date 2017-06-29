package sos.pojo;

import org.carp.annotation.Column;
import org.carp.annotation.Id;
import org.carp.annotation.Table;

/**
 * SysLogs pojo类。
 * @author 周彬  代码生成工具v1.0
 * @version 1.0
 */
@Table(name = "ss_sys_logs", remark = "操作日志表，用于记录系统登录，添加、修改、删除操作。")
public class SysLogs implements java.io.Serializable{
	private static final long serialVersionUID = -2226198764006724287L;

	@Id(name = "log_id", remark = "主键")
	private Integer logId;
	
	@Column(name = "user_no", remark = "操作用户名")
	private String userNo;
	
	@Column(name = "table_name", remark = "操作表")
	private String tableName;
	
	@Column(name = "action_type", remark = "操作类型 登录：LOGIN 添加：WRITE 修改：UPDATE 删除：DELETE")
	private String actionType;
	
	@Column(name = "action_time", remark = "操作时间")
	private String actionTime;
	
	private String endTime;
	
	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}


	@Column(name = "log_context", remark = "操作内容")
	private byte[] logContext;
	
	@Column(name = "action_result", remark = "操作结果  成功：SUCC 失败：FAIL")
	
	private String actionResult;
	
	private String context;
	

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public Integer getLogId()
	{
		return logId;
	}
	
	public void setLogId(Integer logId)
	{
		this.logId = logId;
	}

	public String getUserNo()
	{
		return userNo;
	}
	
	public void setUserNo(String userNo)
	{
		this.userNo = userNo;
	}

	public String getTableName()
	{
		return tableName;
	}
	
	public void setTableName(String tableName)
	{
		this.tableName = tableName;
	}

	public String getActionType()
	{
		return actionType;
	}
	
	public void setActionType(String actionType)
	{
		this.actionType = actionType;
	}

	public String getActionTime()
	{
		return actionTime;
	}
	
	public void setActionTime(String actionTime)
	{
		this.actionTime = actionTime;
	}

	public byte[] getLogContext()
	{
		return logContext;
	}
	
	public void setLogContext(byte[] logContext)
	{
		this.logContext = logContext;
	}

	public String getActionResult()
	{
		return actionResult;
	}
	
	public void setActionResult(String actionResult)
	{
		this.actionResult = actionResult;
	}


	public String toString(){
		StringBuffer buf = new StringBuffer();
				buf.append("\r\nlogId ="+ this.logId );
						buf.append("\r\nuserNo ="+ this.userNo );
				buf.append("\r\ntableName ="+ this.tableName );
				buf.append("\r\nactionType ="+ this.actionType );
				buf.append("\r\nactionTime ="+ this.actionTime );
				buf.append("\r\nlogContext ="+ this.logContext );
				buf.append("\r\nactionResult ="+ this.actionResult );
				return buf.toString();
	}
}
