package sos.constants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sunstar.sos.bpo.BaseBpo;
import com.sunstar.sos.factory.BpoFactory;

/**
 * 获取酒店映射数据
 * @author linminghang
 *
 */
public class HotelPara {
	
	private static Map<String, String> bedTypeNameMap=new HashMap<String, String>();//床型名称
	
	private static Map<String, String> netTypeNameMap=new HashMap<String, String>();//上网类型名称
	
	private static Map<String, String> breakNameMap=new HashMap<String, String>();//早餐名称
	
	private static Map<String, String> starNameMap=new HashMap<String, String>();//星级名称
	
	
	
	static{
		initBedType();
		initNetType();
		initBreak();
		initStarType();
	}
	
	
	/**
	 * 根据床位英文获取中文名称
	 * @param bedTypeName
	 * @return
	 */
	public static String getBedTypeNameCN(String bedTypeName){
		return bedTypeNameMap.get(bedTypeName);
	}
	
	/**
	 * 根据上网方式英文获取中文名称
	 * @param bedTypeName
	 * @return
	 */
	public static String getNettypeCN(String netType){
		return netTypeNameMap.get(netType);
	}
	
	/**
	 * 根据上网方式英文获取中文名称
	 * @param bedTypeName
	 * @return
	 */
	public static String getBreakCN(String breakType){
		return breakNameMap.get(breakType);
	}
	
	/**
	 * 根据星级id获取中文名称
	 * @param bedTypeName
	 * @return
	 */
	public static String getStarCN(String starId){
		return starNameMap.get(starId);
	}
	
	
	private static void initStarType(){
		starNameMap.put("0", "三星以下");
		starNameMap.put("1", "三星");
		starNameMap.put("2", "四星");
		starNameMap.put("3", "五星");
	}
	
	private static void initBedType(){
		bedTypeNameMap.put("single", "单床");
		bedTypeNameMap.put("double", "双床");
		bedTypeNameMap.put("big", "大床");
		bedTypeNameMap.put("cir", "圆床");
		bedTypeNameMap.put("sindou", "单床/双床");
		bedTypeNameMap.put("bigdou", "大床/双床");
		bedTypeNameMap.put("bigsing", "大床/单床");
	}
	
	private static void initNetType(){
		netTypeNameMap.put("1", "宽带");
		netTypeNameMap.put("2", "拔号");
		netTypeNameMap.put("3", "wi-fi");
		netTypeNameMap.put("4", "无网络");
	}
	
	private static void initBreak(){
		breakNameMap.put("7", "无早");
		breakNameMap.put("8", "一份早餐");
		breakNameMap.put("9", "两份早餐");
		breakNameMap.put("10", "床位早餐");
		breakNameMap.put("11", "床位中餐");
		breakNameMap.put("15785", "三份早餐");
		breakNameMap.put("15786", "四份早餐");
		breakNameMap.put("15789", "七份早餐");
		breakNameMap.put("15787", "五份早餐");
		breakNameMap.put("15788", "六份早餐");
		breakNameMap.put("21923", "2大1小早餐");
		breakNameMap.put("21925", "双早双晚");
		breakNameMap.put("15790", "八份早餐");
		breakNameMap.put("15791", "九份早餐");
		breakNameMap.put("15792", "十份早餐");
		breakNameMap.put("12", "床位晚餐");
	}
	
	

}
