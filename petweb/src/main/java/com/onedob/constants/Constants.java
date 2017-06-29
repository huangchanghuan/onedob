package com.onedob.constants;


import com.onedob.util.SysParametersUtil;

public class Constants {
	
	public static final long DEFAULT_CURR_VALUE = 101;
	public static final long DEFAULT_INCREMENT_VALUE = 30;
	/**
	 * �ذ���ӿڰ�ȫ��
	 */
	public static final String GUANAI_SIGNKEY = SysParametersUtil.getValue("GUANAI_SIGNKEY");
	/**
	 * �ذ���ӿ�����ҳ��С
	 */
	public static final Integer GUANAI_PAGESIZE = 30;
	/**
	 * �ذ���ӿڹ�˾����
	 */
	public static final String GUANAI_COMPANYCODE = SysParametersUtil.getValue("GUANAI_COMPANYCODE");
	/**
	 * �ذ���ӿ�����url
	 */
	public static final String GUANAI_URL = SysParametersUtil.getValue("GUANAI_URL");

	
	
	/**
	 * ���нӿڹ�˾����
	 */
	public static final String LIXING_COMPANYCODE = SysParametersUtil.getValue("LIXING_COMPANYCODE");
	/**
	 * ���нӿ�����url
	 */
	public static final String LIXING_URL = SysParametersUtil.getValue("LIXING_URL");
	/**
	 * �����û��˺�
	 */
	public static final String LIXING_USER_CD = SysParametersUtil.getValue("LIXING_USER_CD");
	/**
	 * ���нӿڰ�ȫ��
	 */
	public static final String LIXING_SIGNKEY = SysParametersUtil.getValue("LIXING_SIGNKEY");
	
	/**
	 * ����
	 */
	public static final String MAIL_LIXING_CHECK_TITLE ="�ݷ���Ԥ��ȷ�Ϻ�";
	public static final String MAIL_HOST=SysParametersUtil.getValue("EMAIL_HOST");//����host
	public static final String MAIL_USER=SysParametersUtil.getValue("EMAIL_USERNAME");//�����û���
	public static final String MAIL_PWD=SysParametersUtil.getValue("EMAIL_PASSWORD");//��������
	
	
	/**
	 * ���ýӿ�����url
	 */
	public static final String JIELV_COMMON_URL = SysParametersUtil.getValue("JIELV_COMMON_URL");
	public static final String JIELV_NEWORDER_URL = SysParametersUtil.getValue("JIELV_NEWORDER_URL");
//	public static final String  JIELV_COMMON_URL = "http://interface.jladmin.cn:90/common/service.do";
//	public static final String  JIELV_NEWORDER_URL = "http://api.jladmin.cn/common/orderService.do";
	
	/**
	 * ��Ȩ��
	 */
	public static final String JIELV_AUTH_NO = SysParametersUtil.getValue("JIELV_AUTH_NO");
//	public static final String  JIELV_AUTH_NO = "gzzxdjltour";
	
	/**
	 * �û����
	 */
	public static final String JIELV_USER_CD = SysParametersUtil.getValue("JIELV_USER_CD");
//	public static final String  JIELV_USER_CD = "GZ36654";
	
	
	/**
	 *  ��;�ӿ�����url
	 */
	public static final String KUANTU_URL = SysParametersUtil.getValue("KUANTU_URL");
	
	/**
	 *  ��;�ӿ�Ӧ��ID
	 */
	public static final String KUANTU_APPID = SysParametersUtil.getValue("KUANTU_APPID");
	
	/**
	 *  ��;�ӿڰ�ȫ��
	 */
	public static final String KUANTU_APPKEY = SysParametersUtil.getValue("KUANTU_APPKEY");

	/**
	 * ������ͻ���̬����
	 */
	public final static String JD_URL = "https://router.jd.com/api";
	public final static String JD_APP_KEY = "71719fd6933a45f48b8b782d60b177cd";
	public final static String JD_TOKEN = SysParametersUtil.getValue("JD_TOKEN");
	public final static String ENCODING_UTF8 = "UTF-8";
	public final static boolean SHOW_URL_LOG=true;//�Ƿ�������ʵ�URL
	/**
	 * �Ϻ���Ʊ
	 */
	public final static String YP_URL = SysParametersUtil.getValue("YP_URL");
	public final static String YP_SECKey= SysParametersUtil.getValue("YP_SECKey");//ͨѶ��Կ
	public final static String YP_CHANNELCOL= SysParametersUtil.getValue("YP_CHANNELCOL");//�������
	/**
	 * С�������˻���Ϣ
	 */
	public final static String XIAOMI_PAK_NAME = "com.sunstar.business_huifenxiang";
	public final static String XIAOMI_USER_PAK_NAME = "com.sunstar.huifenxiang";
	public final static String XIAOMI_APP_SEC = "RTDTH2BYdmoe2dvaD1309Q==";
	public final static String XIAOMI_USERAPP_SEC = "QhOi/DUsDtFy0S2jOFqAVw==";

	public final static String XIAOMI_APP_IOS_SEC = "bcENOH4dVjw4gXrci93FVA==";
	
	/**
	 * �ŵ�����
	 */
	public final static String SHOPAPP_TOKEN = "SHOPAPP_TOKEN_";

	/**
	 * ����ͨ
	 */
	public final static String MemberId = "etws";
	public final static String Password = "admin123";
	public final static String RSAPulbicKey = "";
	// ȡ��WSDL�ļ�������<definitions targetNamespace="">��ֵ
	public final static String NameSpace = "http://www.cnto.cn/ws/scene/v1.0/";
	public final static String Host = "http://etws.yocity.cn/";

	public final class Charset {
		public static final String GB2312 = "GB2312";
		public static final String UTF8 = "UTF-8";
		public static final String GBK = "GBK";
	}
}
