package com.sandra.constants;


import com.sandra.util.SysParametersUtil;

public class Constants {
	
	public static final long DEFAULT_CURR_VALUE = 101;
	public static final long DEFAULT_INCREMENT_VALUE = 30;
	/**
	 * 关爱汇接口安全码
	 */
	public static final String GUANAI_SIGNKEY = SysParametersUtil.getValue("GUANAI_SIGNKEY");
	/**
	 * 关爱汇接口请求页大小
	 */
	public static final Integer GUANAI_PAGESIZE = 30;
	/**
	 * 关爱汇接口公司代码
	 */
	public static final String GUANAI_COMPANYCODE = SysParametersUtil.getValue("GUANAI_COMPANYCODE");
	/**
	 * 关爱汇接口请求url
	 */
	public static final String GUANAI_URL = SysParametersUtil.getValue("GUANAI_URL");

	
	
	/**
	 * 力行接口公司代码
	 */
	public static final String LIXING_COMPANYCODE = SysParametersUtil.getValue("LIXING_COMPANYCODE");
	/**
	 * 力行接口请求url
	 */
	public static final String LIXING_URL = SysParametersUtil.getValue("LIXING_URL");
	/**
	 * 力行用户账号
	 */
	public static final String LIXING_USER_CD = SysParametersUtil.getValue("LIXING_USER_CD");
	/**
	 * 力行接口安全码
	 */
	public static final String LIXING_SIGNKEY = SysParametersUtil.getValue("LIXING_SIGNKEY");
	
	/**
	 * 邮箱
	 */
	public static final String MAIL_LIXING_CHECK_TITLE ="惠纷享预订确认函";
	public static final String MAIL_HOST=SysParametersUtil.getValue("EMAIL_HOST");//邮箱host
	public static final String MAIL_USER=SysParametersUtil.getValue("EMAIL_USERNAME");//邮箱用户名
	public static final String MAIL_PWD=SysParametersUtil.getValue("EMAIL_PASSWORD");//邮箱密码
	
	
	/**
	 * 捷旅接口请求url
	 */
	public static final String JIELV_COMMON_URL = SysParametersUtil.getValue("JIELV_COMMON_URL");
	public static final String JIELV_NEWORDER_URL = SysParametersUtil.getValue("JIELV_NEWORDER_URL");
//	public static final String  JIELV_COMMON_URL = "http://interface.jladmin.cn:90/common/service.do";
//	public static final String  JIELV_NEWORDER_URL = "http://api.jladmin.cn/common/orderService.do";
	
	/**
	 * 授权码
	 */
	public static final String JIELV_AUTH_NO = SysParametersUtil.getValue("JIELV_AUTH_NO");
//	public static final String  JIELV_AUTH_NO = "gzzxdjltour";
	
	/**
	 * 用户编号
	 */
	public static final String JIELV_USER_CD = SysParametersUtil.getValue("JIELV_USER_CD");
//	public static final String  JIELV_USER_CD = "GZ36654";
	
	
	/**
	 *  宽途接口请求url
	 */
	public static final String KUANTU_URL = SysParametersUtil.getValue("KUANTU_URL");
	
	/**
	 *  宽途接口应用ID
	 */
	public static final String KUANTU_APPID = SysParametersUtil.getValue("KUANTU_APPID");
	
	/**
	 *  宽途接口安全码
	 */
	public static final String KUANTU_APPKEY = SysParametersUtil.getValue("KUANTU_APPKEY");

	/**
	 * 京东大客户静态常量
	 */
	public final static String JD_URL = "https://router.jd.com/api";
	public final static String JD_APP_KEY = "71719fd6933a45f48b8b782d60b177cd";
	public final static String JD_TOKEN = SysParametersUtil.getValue("JD_TOKEN");
	public final static String ENCODING_UTF8 = "UTF-8";
	public final static boolean SHOW_URL_LOG=true;//是否输出访问的URL
	/**
	 * 上海友票
	 */
	public final static String YP_URL = SysParametersUtil.getValue("YP_URL");
	public final static String YP_SECKey= SysParametersUtil.getValue("YP_SECKey");//通讯密钥
	public final static String YP_CHANNELCOL= SysParametersUtil.getValue("YP_CHANNELCOL");//渠道编号
	/**
	 * 小米推送账户信息
	 */
	public final static String XIAOMI_PAK_NAME = "com.sunstar.business_huifenxiang";
	public final static String XIAOMI_USER_PAK_NAME = "com.sunstar.huifenxiang";
	public final static String XIAOMI_APP_SEC = "RTDTH2BYdmoe2dvaD1309Q==";
	public final static String XIAOMI_USERAPP_SEC = "QhOi/DUsDtFy0S2jOFqAVw==";

	public final static String XIAOMI_APP_IOS_SEC = "bcENOH4dVjw4gXrci93FVA==";
	
	/**
	 * 门店令牌
	 */
	public final static String SHOPAPP_TOKEN = "SHOPAPP_TOKEN_";

	/**
	 * 银旅通
	 */
	public final static String MemberId = "etws";
	public final static String Password = "admin123";
	public final static String RSAPulbicKey = "";
	// 取自WSDL文件顶部的<definitions targetNamespace="">的值
	public final static String NameSpace = "http://www.cnto.cn/ws/scene/v1.0/";
	public final static String Host = "http://etws.yocity.cn/";

	public final class Charset {
		public static final String GB2312 = "GB2312";
		public static final String UTF8 = "UTF-8";
		public static final String GBK = "GBK";
	}
}
