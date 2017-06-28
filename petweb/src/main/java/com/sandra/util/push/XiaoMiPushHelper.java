package com.sandra.util.push;


import com.sandra.constants.Constants;
import com.xiaomi.xmpush.server.Message;
import com.xiaomi.xmpush.server.Message.IOSBuilder;
import com.xiaomi.xmpush.server.Result;
import com.xiaomi.xmpush.server.Sender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//import org.json.simple.parser.ParseException;

public class XiaoMiPushHelper {

	private static final Logger logger = LoggerFactory
			.getLogger(XiaoMiPushHelper.class.getName());
	private static XiaoMiPushHelper instance = new XiaoMiPushHelper();
	
	
	private final int PUSH_RETRIES=3;//重复发送次数
	
	
	
	private XiaoMiPushHelper(){
		
	}
	
	public static XiaoMiPushHelper getInstance(){
		return instance;
	}

	/**
	 * 安卓根据别名推送

	 * @param messagePayload
	 *            设置要发送的消息内容
	 * @param title
	 *            设置在通知栏展示的通知的标题
	 * @param description
	 *            设置在通知栏展示的通知的描述
	 */
	public void sendPushMsg(String regId, String messagePayload, String title,
                            String description, String jsonStr
//			,String actName
			) {
		//生产环境
		com.xiaomi.xmpush.server.Constants.useOfficial();
		//测试环境
//		com.xiaomi.xmpush.server.Constants.useSandbox();
		Sender sender = new Sender(Constants.XIAOMI_USERAPP_SEC);
		System.out.println("1--------->"+Constants.XIAOMI_USERAPP_SEC+":"+Constants.XIAOMI_USER_PAK_NAME);
		Message message = new Message.Builder().title(title)
				.description(description).payload(messagePayload)
				.restrictedPackageName(Constants.XIAOMI_USER_PAK_NAME)
				.notifyType(1) // 使用默认提示音提示
				.passThrough(0)//	设置消息是否通过透传的方式送给app，1表示透传消息，0表示通知栏消息。
//				.extra(com.xiaomi.xmpush.server.Constants.EXTRA_PARAM_NOTIFY_EFFECT, 
//						com.xiaomi.xmpush.server.Constants.NOTIFY_ACTIVITY)
//				.extra(com.xiaomi.xmpush.server.Constants.EXTRA_PARAM_INTENT_URI, 
//                    "intent:#Intent;component="+Constants.XIAOMI_PAK_NAME+"/."+actName+";end")
				.extra(com.xiaomi.xmpush.server.Constants.EXTRA_PARAM_NOTIFY_FOREGROUND, "1")
				.extra("content", jsonStr)  // 自定义键值对
				.build();
		try {
//			Result result = sender.sendToAlias(message, alias, PUSH_RETRIES);
			Result result =sender.send(message, regId, PUSH_RETRIES);
			logger.info("push regId:"+regId+",Server response: " + "MessageId: "
					+ result.getMessageId() + " ErrorCode: "
					+ result.getErrorCode().getValue()+","+result.getErrorCode().getName()+","+result.getErrorCode().getFullDescription() + " Reason: "
					+ result.getReason());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ios根据设备号单个推送对象
	 * @param regId：发送的设备id
	 * @param description：设置在通知栏展示的通知的描述
	 * @param jsonStr：要传参的json字符串
	 */
	public void sendIosPushMsgByReg(String regId, String description, String jsonStr) throws Exception {
		List<String> regIds=new ArrayList<String>();
		regIds.add(regId);
		sendIosPushMsgByReg(regIds, description, jsonStr);
	}
	
	/**
	 * ios根据设备号多个推送对象
	 * @param regIds：发送的设备id
	 * @param description：设置在通知栏展示的通知的描述
	 * @param jsonStr：要传参的json字符串
	 */
	public void sendIosPushMsgByReg(List<String> regIds, String description, String jsonStr) throws Exception {
		sendIosPushMsgByReg(regIds, description, jsonStr, null, null, null, null, null);
	}
	
	/**
	 * ios根据设备号多个推送对象
	 * @param regIds：发送的设备id数组
	 * @param description：设置在通知栏展示的通知的描述
	 * @param jsonStr：透传给app的内容数组
	 * @param badge：可选项，传null代表默认。自定义通知数字角标。
	 * @param soundUrl：可选项，传null代表默认。自定义消息铃声。
	 * @param timeToLive：可选项，传null代表默认。如果用户离线，设置消息在服务器保存的时间，单位：ms。代表服务器默认最长保留两周。
	 * @param timeToSend：可选项，传null代表默认。定时发送消息。timeToSend是用自1970年1月1日以来00:00:00.0 UTC时间表示（以毫秒为单位的时间）。注：仅支持七天内的定时消息。
	 * @param flowControl：可选项，传null代表不平滑推送。平滑推送，服务端支持最低1000/s的qps，最高100000/s
	 */
	public void sendIosPushMsgByReg(List<String> regIds, String description, String jsonStr, Integer badge, String soundUrl
			, Integer timeToLive, Long timeToSend, Integer flowControl) throws Exception {
		if(regIds==null||regIds.size()<0||description==null||jsonStr==null){
			throw new RuntimeException("regIds|description|jsonStr格式不合法，不允许空值");
		}
		//生产环境
		com.xiaomi.xmpush.server.Constants.useOfficial();
		//测试环境
		//com.xiaomi.xmpush.server.Constants.useSandbox();
		Sender sender = new Sender(Constants.XIAOMI_APP_IOS_SEC);
		logger.info("1--------->"+Constants.XIAOMI_APP_SEC+":"+Constants.XIAOMI_PAK_NAME);
		IOSBuilder builder=new Message.IOSBuilder()
				.description(description)
	            //.soundURL("default")    // 消息铃声
				.category("action")     // 快速回复类别
				.extra("content", jsonStr);  // 自定义键值对
		if(badge!=null)builder.badge(badge);
		if(soundUrl!=null)builder.soundURL(soundUrl);
		if(timeToLive!=null)builder.timeToLive(timeToLive);
		if(timeToSend!=null)builder.timeToSend(timeToSend);
		if(flowControl!=null)builder.extra("flow_control", flowControl+""); 
		Message message = builder.build();
		Result result =sender.send(message, regIds, PUSH_RETRIES);
		logger.info("push alias:"+regIds+",Server response: " + "MessageId: "
				+ result.getMessageId() + " ErrorCode: "
				+ result.getErrorCode().getValue()+","+result.getErrorCode().getName()+","+result.getErrorCode().getFullDescription() + " Reason: "
				+ result.getReason());
	}
	
	
	public void sendMessage() throws Exception {
		com.xiaomi.xmpush.server.Constants.useOfficial();
		Sender sender = new Sender(Constants.XIAOMI_USERAPP_SEC);
		System.out.println("1--------->"+Constants.XIAOMI_USERAPP_SEC+":"+Constants.XIAOMI_USER_PAK_NAME);
	     Message message = new Message.Builder()
	                .title("测试")
	                .description("测试description").payload("测试payload")
	                .restrictedPackageName(Constants.XIAOMI_USER_PAK_NAME)
	                .notifyType(1)     // 使用默认提示音提示
	                .build();
	     Result result =sender.send(message, "VCDio2xIlyLS5loeyYzqicpBm5rUGfRAenzkD2C/SJk=", 0); //根据regID，发送消息到指定设备上，不重试。
	     System.out.println(result.toString());
	     logger.info("push regId:"+",Server response: " + "MessageId: "
					+ result.getMessageId() + " ErrorCode: "
					+ result.getErrorCode().getValue()+","+result.getErrorCode().getName()+","+result.getErrorCode().getFullDescription() + " Reason: "
					+ result.getReason());
	}
	
	
	public static void main(String[] args) {
		try {
			new XiaoMiPushHelper().sendIosPushMsgByReg("TwXwnf73+L7tELHcByQDiJSLQ+1ZUPBDAuUEWT1K240=", "测试4",
					"{\"msgtype\":\"0\",\"actiontype\":\"0\",\"businesstype\":\"3\",\"info\":{\"cpid\":\"1094\"}}");
//			new XiaoMiPushHelper().sendPushMsg("VCDio2xIlyLS5loeyYzqicpBm5rUGfRAenzkD2C/SJk=", "", "测试标题", "测试描述","{\"msgtype\":\"0\",\"actiontype\":\"0\",\"businesstype\":\"1\",\"tel\":\"15915922588\",\"info\":{\"cpid\":\"1094\"}}");
//			new XiaoMiPushHelper().sendMessage();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
