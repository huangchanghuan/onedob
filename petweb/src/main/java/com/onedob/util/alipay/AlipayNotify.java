package com.onedob.util.alipay;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/* *
 *������AlipayNotify
 *���ܣ�֧����֪ͨ������
 *��ϸ������֧�������ӿ�֪ͨ����
 *�汾��3.3
 *���ڣ�2012-08-17
 *˵����
 *���´���ֻ��Ϊ�˷����̻����Զ��ṩ���������룬�̻����Ը����Լ���վ����Ҫ�����ռ����ĵ���д,����һ��Ҫʹ�øô��롣
 *�ô������ѧϰ���о�֧�����ӿ�ʹ�ã�ֻ���ṩһ���ο�

 *************************ע��*************************
 *����֪ͨ����ʱ���ɲ鿴���дlog��־��д��TXT������ݣ������֪ͨ�����Ƿ�����
 */
public class AlipayNotify {

    /**
     * ֧������Ϣ��֤��ַ
     */
    private static final String HTTPS_VERIFY_URL = "https://mapi.alipay.com/gateway.do?service=notify_verify&";

    /**
     * ��֤��Ϣ�Ƿ���֧���������ĺϷ���Ϣ
     * @param params ֪ͨ�������Ĳ�������
     * @return ��֤���
     */
    public static boolean verify(Map<String, String> params) {

        //�ж�responsetTxt�Ƿ�Ϊtrue��isSign�Ƿ�Ϊtrue
        //responsetTxt�Ľ������true����������������⡢���������ID��notify_idһ����ʧЧ�й�
        //isSign����true���밲ȫУ���롢����ʱ�Ĳ�����ʽ���磺���Զ�������ȣ��������ʽ�й�
    	String responseTxt = "false";
		if(params.get("notify_id") != null) {
			String notify_id = params.get("notify_id");
			responseTxt = verifyResponse(notify_id);
		}
	    String sign = "";
	    if(params.get("sign") != null) {sign = params.get("sign");}
	    boolean isSign = getSignVeryfy(params, sign);

        //д��־��¼����Ҫ���ԣ���ȡ����������ע�ͣ�
        //String sWord = "responseTxt=" + responseTxt + "\n isSign=" + isSign + "\n ���ػ����Ĳ�����" + AlipayCore.createLinkString(params);
	    //AlipayCore.logResult(sWord);

        if (isSign && responseTxt.equals("true")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * ���ݷ�����������Ϣ������ǩ�����
     * @param Params ֪ͨ�������Ĳ�������
     * @param sign �ȶԵ�ǩ�����
     * @return ���ɵ�ǩ�����
     */
	private static boolean getSignVeryfy(Map<String, String> Params, String sign) {
    	//���˿�ֵ��sign��sign_type����
    	Map<String, String> sParaNew = AlipayCore.paraFilter(Params);
        //��ȡ��ǩ���ַ���
        String preSignStr = AlipayCore.createLinkString(sParaNew);
        //���ǩ����֤���
        boolean isSign = false;
        if(AlipayConfig.sign_type.equals("MD5") ) {
        	isSign = MD5.verify(preSignStr, sign, AlipayConfig.key, AlipayConfig.input_charset);
        }
        return isSign;
    }

    /**
    * ��ȡԶ�̷�����ATN���,��֤����URL
    * @param notify_id ֪ͨУ��ID
    * @return ������ATN���
    * ��֤�������
    * invalid����������� ��������������ⷵ�ش�����partner��key�Ƿ�Ϊ�� 
    * true ������ȷ��Ϣ
    * false �������ǽ�����Ƿ�������ֹ�˿������Լ���֤ʱ���Ƿ񳬹�һ����
    */
    private static String verifyResponse(String notify_id) {
        //��ȡԶ�̷�����ATN�������֤�Ƿ���֧��������������������

        String partner = AlipayConfig.partner;
        String veryfy_url = HTTPS_VERIFY_URL + "partner=" + partner + "&notify_id=" + notify_id;

        return checkUrl(veryfy_url);
    }

    /**
    * ��ȡԶ�̷�����ATN���
    * @param urlvalue ָ��URL·����ַ
    * @return ������ATN���
    * ��֤�������
    * invalid����������� ��������������ⷵ�ش�����partner��key�Ƿ�Ϊ�� 
    * true ������ȷ��Ϣ
    * false �������ǽ�����Ƿ�������ֹ�˿������Լ���֤ʱ���Ƿ񳬹�һ����
    */
    private static String checkUrl(String urlvalue) {
        String inputLine = "";

        try {
            URL url = new URL(urlvalue);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection
                .getInputStream()));
            inputLine = in.readLine().toString();
        } catch (Exception e) {
            e.printStackTrace();
            inputLine = "";
        }

        return inputLine;
    }
}
