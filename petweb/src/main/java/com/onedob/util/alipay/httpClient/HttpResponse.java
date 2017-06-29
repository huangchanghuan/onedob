package com.onedob.util.alipay.httpClient;

import com.sunstar.adms.util.alipay.AlipayConfig;
import org.apache.commons.httpclient.Header;

import java.io.UnsupportedEncodingException;

/* *
 *������HttpResponse
 *���ܣ�Http���ض���ķ�װ
 *��ϸ����װHttp������Ϣ
 *�汾��3.3
 *���ڣ�2011-08-17
 *˵����
 *���´���ֻ��Ϊ�˷����̻����Զ��ṩ���������룬�̻����Ը����Լ���վ����Ҫ�����ռ����ĵ���д,����һ��Ҫʹ�øô��롣
 *�ô������ѧϰ���о�֧�����ӿ�ʹ�ã�ֻ���ṩһ���ο���
 */

public class HttpResponse {

    /**
     * �����е�Header��Ϣ
     */
    private Header[] responseHeaders;

    /**
     * String���͵�result
     */
    private String stringResult;

    /**
     * btye���͵�result
     */
    private byte[]   byteResult;

    public Header[] getResponseHeaders() {
        return responseHeaders;
    }

    public void setResponseHeaders(Header[] responseHeaders) {
        this.responseHeaders = responseHeaders;
    }

    public byte[] getByteResult() {
        if (byteResult != null) {
            return byteResult;
        }
        if (stringResult != null) {
            return stringResult.getBytes();
        }
        return null;
    }

    public void setByteResult(byte[] byteResult) {
        this.byteResult = byteResult;
    }

    public String getStringResult() throws UnsupportedEncodingException {
        if (stringResult != null) {
            return stringResult;
        }
        if (byteResult != null) {
            return new String(byteResult, AlipayConfig.input_charset);
        }
        return null;
    }

    public void setStringResult(String stringResult) {
        this.stringResult = stringResult;
    }

}
