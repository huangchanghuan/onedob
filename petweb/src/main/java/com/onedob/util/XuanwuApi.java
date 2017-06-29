package com.onedob.util;


import com.onedob.util.pojo.Param;
import com.xuanwu.gateway.entity.Req;
import com.xuanwu.gateway.entity.Resp;
import com.xuanwu.gateway.send.DefaultPostMsg;
import com.xuanwu.gateway.send.IPostMsg;

import java.lang.reflect.Field;
import java.util.*;

public class XuanwuApi {
	private static String signKey = SysParametersUtil.getValue("SIGN_KEY");
	public static void main(String[] args) throws Exception {
		
		//��������ip��˿ڣ����ڷ�����Ϣ�����鱣�ָ�ʵ��Ϊ����
		IPostMsg post = new DefaultPostMsg("42.159.29.20",18090);
		
		//��ѯ������
//		Req req = queryTrafficPackage();
		
		Req req = queryTrafficPackageAll();
		//����
//		Req req = orderForSingle();
//		Req req = orderForBatch();
		
		Resp resp = post.post(req);
		System.out.println(resp);
	}
	
	/**
	 * ��ѯ������
	 * ��ѯ��ҵ�µĵ����ֻ������õ�������
	 * @return
	 */
	public static Req queryTrafficPackage() {
		Req req = new Req();
		req.setActionType(Param.ACTION_QUERY_PHONE_PKG);
		req.setCompanyCode("admin@test");
		req.setRequestID(UUID.randomUUID().toString());
		req.setTimeStamp(System.currentTimeMillis());
		String data = "{phoneno:\"13500000001\"}";//��ѯ��ҵ�µĵ����ֻ������õ�������
		req.setData(data);
		req.setSign(getSign(req, signKey));
		return req;
	}
	/**
	 * ��ѯ������
	 * ��ѯ��ҵ���õ�������
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
	 * �������붩������������
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
				+ "batchname:\"���Ե���2\","
				+ "phoneno:\"13500000005\","
				+ "bizcode:\"CM0100010M\""
				+ "}";
		req.setData(data);
		return req;
	}
	
	/**
	 * �������붩������������
	 * @param batchName ����
	 * @param phoneNo �ֻ���
	 * @param bizCode �����ӿ�
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
	 * ��������
	 * @return
	 */
	public static Req orderForBatch() {
		Req req = new Req();
		req.setActionType(Param.ACTION_ORDER_BATCH);
		req.setCompanyCode("admin@test");
		req.setRequestID(UUID.randomUUID().toString());
		req.setTimeStamp(System.currentTimeMillis());
		String data = "{"
				+ "batchname:\"������������batch�������\","
				+ "memberlist:[{\"phoneno\":\"13500000001\"},{\"phoneno\":\"18600000001\"},{\"phoneno\":\"13300000001\"}],"
				+ "orderpkgrule:[{\"bizcode\":\"CM0100010M\"},{\"bizcode\":\"CU0100020M\"},{\"bizcode\":\"CT0100005M\"}]"
				+ "}";
		req.setData(data);
		req.setSign(getSign(req,signKey));
		return req;
	}
	
	/**
	 * ǩ���㷨
	 * @param req ������Ϣ����
	 * @param signKey ��ҵ��Կ
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
	 * ǩ���㷨
	 * @param signKey ��ҵ��Կ
	 * @param requestID ������ˮ��
	 * @param companyCode ��ҵ�˻�
	 * @param timeStamp ʱ���
	 * @param actionType ��������
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
			if (i == keys.size() - 1) {// ƴ��ʱ�����������һ��&�ַ�
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
