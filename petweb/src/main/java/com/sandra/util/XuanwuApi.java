package com.sandra.util;


import com.sandra.util.pojo.Param;
import com.xuanwu.gateway.entity.Req;
import com.xuanwu.gateway.entity.Resp;
import com.xuanwu.gateway.send.DefaultPostMsg;
import com.xuanwu.gateway.send.IPostMsg;

import java.lang.reflect.Field;
import java.util.*;

public class XuanwuApi {
	private static String signKey = SysParametersUtil.getValue("SIGN_KEY");
	public static void main(String[] args) throws Exception {
		
		//设置网关ip与端口，用于发送信息，建议保持该实例为单例
		IPostMsg post = new DefaultPostMsg("42.159.29.20",18090);
		
		//查询流量包
//		Req req = queryTrafficPackage();
		
		Req req = queryTrafficPackageAll();
		//订购
//		Req req = orderForSingle();
//		Req req = orderForBatch();
		
		Resp resp = post.post(req);
		System.out.println(resp);
	}
	
	/**
	 * 查询流量包
	 * 查询企业下的单个手机号适用的流量包
	 * @return
	 */
	public static Req queryTrafficPackage() {
		Req req = new Req();
		req.setActionType(Param.ACTION_QUERY_PHONE_PKG);
		req.setCompanyCode("admin@test");
		req.setRequestID(UUID.randomUUID().toString());
		req.setTimeStamp(System.currentTimeMillis());
		String data = "{phoneno:\"13500000001\"}";//查询企业下的单个手机号适用的流量包
		req.setData(data);
		req.setSign(getSign(req, signKey));
		return req;
	}
	/**
	 * 查询流量包
	 * 查询企业适用的流量包
	 * @return
	 */
	public static Req queryTrafficPackageAll() {
		Req req = new Req();
		req.setActionType(Param.ACTION_QUERY_ENT_PKG);
		req.setCompanyCode("admin@test");
		req.setRequestID(UUID.randomUUID().toString());
		req.setTimeStamp(System.currentTimeMillis());
		req.setSign(getSign(req, signKey));
		return req;
	}
	/**
	 * 单个号码订购流量包订购
	 * @return
	 */
	public static Req orderForSingle() {
		Req req = new Req();
		req.setActionType(Param.ACTION_ORDER_SINGLE);
		req.setCompanyCode("admin@test");
		req.setRequestID(UUID.randomUUID().toString());
		req.setTimeStamp(System.currentTimeMillis());
		req.setSign(getSign(req, signKey));
		String data = "{"
				+ "batchname:\"测试单发2\","
				+ "phoneno:\"13500000005\","
				+ "bizcode:\"CM0100010M\""
				+ "}";
		req.setData(data);
		return req;
	}
	
	/**
	 * 单个号码订购流量包订购
	 * @param batchName 名称
	 * @param phoneNo 手机号
	 * @param bizCode 流量接口
	 * @return
	 */
	public static Req orderForSingle(String batchName, String phoneNo, String bizCode) {
		Req req = new Req();
		req.setActionType(Param.ACTION_ORDER_SINGLE);
		req.setCompanyCode(SysParametersUtil.getValue("COMPANY_CODE"));
		req.setRequestID(UUID.randomUUID().toString());
		req.setTimeStamp(System.currentTimeMillis());
		req.setSign(getSign(req, signKey));
		String data = "{"
				+ "batchname:\""+batchName+"\","
				+ "phoneno:\""+phoneNo+"\","
				+ "bizcode:\""+bizCode+"\""
				+ "}";
		req.setData(data);
		return req;
	}
	
	/**
	 * 批量订购
	 * @return
	 */
	public static Req orderForBatch() {
		Req req = new Req();
		req.setActionType(Param.ACTION_ORDER_BATCH);
		req.setCompanyCode("admin@test");
		req.setRequestID(UUID.randomUUID().toString());
		req.setTimeStamp(System.currentTimeMillis());
		String data = "{"
				+ "batchname:\"测试批次名称batch错误编码\","
				+ "memberlist:[{\"phoneno\":\"13500000001\"},{\"phoneno\":\"18600000001\"},{\"phoneno\":\"13300000001\"}],"
				+ "orderpkgrule:[{\"bizcode\":\"CM0100010M\"},{\"bizcode\":\"CU0100020M\"},{\"bizcode\":\"CT0100005M\"}]"
				+ "}";
		req.setData(data);
		req.setSign(getSign(req,signKey));
		return req;
	}
	
	/**
	 * 签名算法
	 * @param req 请求消息对象
	 * @param signKey 企业密钥
	 * @return
	 */
	public static String getSign(Req req, String signKey) {
		String requestID = req.getRequestID();
		String companyCode = req.getCompanyCode();
		long timeStamp = req.getTimeStamp();
		String actionType = req.getActionType();
		return getSign(signKey, requestID, companyCode, timeStamp, actionType);
	}
	/**
	 * 签名算法
	 * @param signKey 企业密钥
	 * @param requestID 请求流水号
	 * @param companyCode 企业账户
	 * @param timeStamp 时间戳
	 * @param actionType 请求类型
	 * @return
	 */
	public static String getSign(String signKey, String requestID, String companyCode, long timeStamp, String actionType) {
		Map<String, Object> sPara = new HashMap<String, Object>();
		sPara.put("requestid", requestID);
		sPara.put("companycode", companyCode);
		sPara.put("timestamp", timeStamp);
		sPara.put("actiontype", actionType);

		List<String> keys = new ArrayList<String>(sPara.keySet());
		Collections.sort(keys);

		String prestr = "";
		for (int i = 0; i < keys.size(); i++) {
			String key = (String) keys.get(i);
			String value = sPara.get(key).toString();
			if (value==null || value.equals("")) {
				continue;
			}
			if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
				prestr = prestr + key + "=" + value;
			} else {
				prestr = prestr + key + "=" + value + "&";
			}
		}
		prestr += "&appsecret=" + signKey;
		String mysign = Md5Encrypt.md5(prestr);
		return mysign;
	}

	public static String toHttpParam(Req req) throws IllegalArgumentException, IllegalAccessException {
		StringBuffer b = new StringBuffer();
		Field[] fields = req.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			String name = field.getName();
//			name = name.substring(0, 1).toUpperCase()+name.substring(1);
			field.setAccessible(true);
			if (i>0) {
				b.append("&");
			}
			b.append(name).append("=").append(field.get(req)==null?"":field.get(req));
		}
		return b.toString();
	}
	
}
