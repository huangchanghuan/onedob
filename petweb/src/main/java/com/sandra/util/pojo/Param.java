package com.sandra.util.pojo;

public interface Param {
	/**
	 * 可用流量包列表查询
	 */
	public final String ACTION_QUERY_ENT_PKG = "A0001";
	/**
	 * 单号码可用流量包列表查询
	 */
	public final String ACTION_QUERY_PHONE_PKG = "A0002";
	/**
	 * 批量流量包订购
	 */
	public final String ACTION_ORDER_BATCH = "A0101";
	/**
	 * 单号码流量包订购
	 */
	public final String ACTION_ORDER_SINGLE = "A0102";
}
