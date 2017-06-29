package sos.sms;

import java.util.UUID;

import com.sunstar.sms.GsmsResponse;
import com.sunstar.sms.MTPacks;
import com.sunstar.sms.MessageData;
import com.sunstar.sms.WebServiceLocator;

public class SunstarSmsSender {

	public static final String SMS_USER = "hfx@sunstar";
	public static final String SMS_PASS = "Oe3pgUYi";

	public static String send(String phone,String content){
		String ret = "";
		try{
			WebServiceLocator webServiceLocator = new WebServiceLocator();
			String strArgs[] = new String[2];
			strArgs[0] = SMS_USER;		//账号
			strArgs[1] = SMS_PASS;		//密码
			
			MessageData[] messagedatas = new MessageData[1];   //号码数量
			for(int i = 0; i < messagedatas.length; i++){
				messagedatas[i] = new MessageData();
				messagedatas[i].setContent(content);//信息内容
				messagedatas[i].setPhone(phone);
				messagedatas[i].setVipFlag(true);
			}
			
			MTPacks pack = new MTPacks();
			pack.setUuid(UUID.randomUUID().toString());
			pack.setBatchID(UUID.randomUUID().toString());
			pack.setBatchName("ss_"+System.currentTimeMillis());
			pack.setMsgs(messagedatas);
			pack.setMsgType(1);//消息类型，短信1、彩信2
			pack.setCustomNum("13801");//扩展号
			pack.setSendType(0);//发送类型，群发0、组发1
			pack.setDistinctFlag(true);//是否过滤重复号码
			//生产环境不发短信，注释此代码
			GsmsResponse resp = webServiceLocator.getWebServiceSoap().post(strArgs[0], strArgs[1], pack);
			ret = resp.getMessage();
		}catch(Exception e){
			ret = "失败";
			e.printStackTrace();
		}
		return ret;
	}
	
	public static String sendCode(String phone,String code){
		String ret = "";
		try{
			String content = "验证码："+code+"。请勿将验证码告知他人并确认该申请是您本人操作！  ";
			WebServiceLocator webServiceLocator = new WebServiceLocator();
			String strArgs[] = new String[2];
			strArgs[0] = SMS_USER;		//账号
			strArgs[1] = SMS_PASS;		//密码
			
			MessageData[] messagedatas = new MessageData[1];   //号码数量
			for(int i = 0; i < messagedatas.length; i++){
				messagedatas[i] = new MessageData();
				messagedatas[i].setContent(content);//信息内容
				messagedatas[i].setPhone(phone);
				messagedatas[i].setVipFlag(true);
			}
			
			MTPacks pack = new MTPacks();
			pack.setUuid(UUID.randomUUID().toString());
			pack.setBatchID(UUID.randomUUID().toString());
			pack.setBatchName("ss_"+System.currentTimeMillis());
			pack.setMsgs(messagedatas);
			pack.setMsgType(1);//消息类型，短信1、彩信2
			pack.setCustomNum("13801");//扩展号
			pack.setSendType(0);//发送类型，群发0、组发1
			pack.setDistinctFlag(true);//是否过滤重复号码
			GsmsResponse resp = webServiceLocator.getWebServiceSoap().post(strArgs[0], strArgs[1], pack);
			ret = resp.getMessage();
		}catch(Exception e){
			ret = "失败";
			e.printStackTrace();
		}
		return ret;
	}
}
