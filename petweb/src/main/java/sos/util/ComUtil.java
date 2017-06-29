package sos.util;

import java.util.List;

public class ComUtil {
	
	public static  boolean isBank(String str){
		if(str==null||"".equals(str.trim())){
			return true;
		}else {
			return false;
		}
	}
	
	public static  boolean isBank(Integer i){
		if(i==null){
			return true;
		}else {
			return false;
		}
	}
	
	public static  boolean isBank(Long l){
		if(l==null){
			return true;
		}else {
			return false;
		}
	}
	
	public static  boolean isBank(List list){
		if(list==null||list.size()<=0){
			return true;
		}else {
			return false;
		}
	}

}
