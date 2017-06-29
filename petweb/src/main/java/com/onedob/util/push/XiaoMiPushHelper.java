package com.onedob.util.push;


import com.onedob.constants.Constants;
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
	
	
	private final int PUSH_RETRIES=3;//�ظ����ʹ���
	
	
	
	private XiaoMiPushHelper(){
		
	}
	
	public static XiaoMiPushHelper getInstance(){
		return instance;
	}

	/**
	 * ��׿���ݱ�������

	 * @param messagePayload
	 *            ����Ҫ���͵���Ϣ����
	 * @param title
	 *            ������֪ͨ��չʾ��֪ͨ�ı���
	 * @param description
	 *            ������֪ͨ��չʾ��֪ͨ������
	 */
	public void sendPushMsg(String regId, String messagePayload, String title,
                            String description, String jsonStr
//			,String actName
			) {
		//��������
		com.xiaomi.xmpush.server.Constants.useOfficial();
		//���Ի���
//		com.xiaomi.xmpush.server.Constants.useSandbox();
		Sender sender = new Sender(Constants.XIAOMI_USERAPP_SEC);
		System.out.println("1--------->"+Constants.XIAOMI_USERAPP_SEC+":"+Constants.XIAOMI_USER_PAK_NAME);
		Message message = new Message.Builder().title(title)
				.description(description).payload(messagePayload)
				.restrictedPackageName(Constants.XIAOMI_USER_PAK_NAME)
				.notifyType(1) // ʹ��Ĭ����ʾ����ʾ
				.passThrough(0)//	������Ϣ�Ƿ�ͨ��͸���ķ�ʽ�͸�app��1��ʾ͸����Ϣ��0��ʾ֪ͨ����Ϣ��
//				.extra(com.xiaomi.xmpush.server.Constants.EXTRA_PARAM_NOTIFY_EFFECT, 
//						com.xiaomi.xmpush.server.Constants.NOTIFY_ACTIVITY)
//				.extra(com.xiaomi.xmpush.server.Constants.EXTRA_PARAM_INTENT_URI, 
//                    "intent:#Intent;component="+Constants.XIAOMI_PAK_NAME+"/."+actName+";end")
				.extra(com.xiaomi.xmpush.server.Constants.EXTRA_PARAM_NOTIFY_FOREGROUND, "1")
				.extra("content", jsonStr)  // �Զ����ֵ��
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
	 * ios�����豸�ŵ������Ͷ���
	 * @param regId�����͵��豸id
	 * @param description��������֪ͨ��չʾ��֪ͨ������
	 * @param jsonStr��Ҫ���ε�json�ַ���
	 */
	public void sendIosPushMsgByReg(String regId, String description, String jsonStr) throws Exception {
		List<String> regIds=new ArrayList<String>();
		regIds.add(regId);
		sendIosPushMsgByReg(regIds, description, jsonStr);
	}
	
	/**
	 * ios�����豸�Ŷ�����Ͷ���
	 * @param regIds�����͵��豸id
	 * @param description��������֪ͨ��չʾ��֪ͨ������
	 * @param jsonStr��Ҫ���ε�json�ַ���
	 */
	public void sendIosPushMsgByReg(List<String> regIds, String description, String jsonStr) throws Exception {
		sendIosPushMsgByReg(regIds, description, jsonStr, null, null, null, null, null);
	}
	
	/**
	 * ios�����豸�Ŷ�����Ͷ���
	 * @param regIds�����͵��豸id����
	 * @param description��������֪ͨ��չʾ��֪ͨ������
	 * @param jsonStr��͸����app����������
	 * @param badge����ѡ���null����Ĭ�ϡ��Զ���֪ͨ���ֽǱꡣ
	 * @param soundUrl����ѡ���null����Ĭ�ϡ��Զ�����Ϣ������
	 * @param timeToLive����ѡ���null����Ĭ�ϡ�����û����ߣ�������Ϣ�ڷ����������ʱ�䣬��λ��ms�����������Ĭ����������ܡ�
	 * @param timeToSend����ѡ���null����Ĭ�ϡ���ʱ������Ϣ��timeToSend������1970��1��1������00:00:00.0 UTCʱ���ʾ���Ժ���Ϊ��λ��ʱ�䣩��ע����֧�������ڵĶ�ʱ��Ϣ��
	 * @param flowControl����ѡ���null����ƽ�����͡�ƽ�����ͣ������֧�����1000/s��qps�����100000/s
	 */
	public void sendIosPushMsgByReg(List<String> regIds, String description, String jsonStr, Integer badge, String soundUrl
			, Integer timeToLive, Long timeToSend, Integer flowControl) throws Exception {
		if(regIds==null||regIds.size()<0||description==null||jsonStr==null){
			throw new RuntimeException("regIds|description|jsonStr��ʽ���Ϸ����������ֵ");
		}
		//��������
		com.xiaomi.xmpush.server.Constants.useOfficial();
		//���Ի���
		//com.xiaomi.xmpush.server.Constants.useSandbox();
		Sender sender = new Sender(Constants.XIAOMI_APP_IOS_SEC);
		logger.info("1--------->"+Constants.XIAOMI_APP_SEC+":"+Constants.XIAOMI_PAK_NAME);
		IOSBuilder builder=new Message.IOSBuilder()
				.description(description)
	            //.soundURL("default")    // ��Ϣ����
				.category("action")     // ���ٻظ����
				.extra("content", jsonStr);  // �Զ����ֵ��
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
	                .title("����")
	                .description("����description").payload("����payload")
	                .restrictedPackageName(Constants.XIAOMI_USER_PAK_NAME)
	                .notifyType(1)     // ʹ��Ĭ����ʾ����ʾ
	                .build();
	     Result result =sender.send(message, "VCDio2xIlyLS5loeyYzqicpBm5rUGfRAenzkD2C/SJk=", 0); //����regID��������Ϣ��ָ���豸�ϣ������ԡ�
	     System.out.println(result.toString());
	     logger.info("push regId:"+",Server response: " + "MessageId: "
					+ result.getMessageId() + " ErrorCode: "
					+ result.getErrorCode().getValue()+","+result.getErrorCode().getName()+","+result.getErrorCode().getFullDescription() + " Reason: "
					+ result.getReason());
	}
	
	
	public static void main(String[] args) {
		try {
			new XiaoMiPushHelper().sendIosPushMsgByReg("TwXwnf73+L7tELHcByQDiJSLQ+1ZUPBDAuUEWT1K240=", "����4",
					"{\"msgtype\":\"0\",\"actiontype\":\"0\",\"businesstype\":\"3\",\"info\":{\"cpid\":\"1094\"}}");
//			new XiaoMiPushHelper().sendPushMsg("VCDio2xIlyLS5loeyYzqicpBm5rUGfRAenzkD2C/SJk=", "", "���Ա���", "��������","{\"msgtype\":\"0\",\"actiontype\":\"0\",\"businesstype\":\"1\",\"tel\":\"15915922588\",\"info\":{\"cpid\":\"1094\"}}");
//			new XiaoMiPushHelper().sendMessage();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
