package sos.cfg;

import com.sunstar.sos.parameter.Parameter;
import com.sunstar.sos.permission.LoginUser;
import com.sunstar.sos.util.ObjectUtil;
import org.apache.log4j.Logger;
import org.carp.Query;

import java.sql.SQLException;
import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * sql语句预处理类
 * @author Administrator
 *
 */
public class Sql implements Cloneable{
	private static final Logger logger = Logger.getLogger(Sql.class);
	private List<Parameter> params; //查询参数
	private String[] tables; //可变表名定义数组
	private String tableNames; //可变表名,变量定义标签值
	private String sql;	//xml中预定义的sql语句
	private String orderbySql; //xml中预定义的排序sql，将被附加到sql的后面。
	private Object po;  //参数值所在的po对象
	private Map<String,Object> map;//参数值所在的Map对象
	private boolean condition; //是否根据参数列表生成sql条件，附加到sql语句后面。
	private boolean where;    // 是否需要在sql语句后面添加where字符串。
	
	public Sql(String sql, String orderby, List<Parameter> params, boolean condition,boolean where){
		this.params = params;
		this.sql = sql;
		this.orderbySql = orderby!=null?" "+orderby:" ";
		this.condition = condition;
		this.where = where;
	}
	public List<Parameter> getParams(){
		return this.params;
	}
	
	public Sql setPoAndMap(Object _po, Map<String,Object> _map){
		this.po = _po;
		this.map = _map;
		return this;
	}
	
	public void setPo(Object _po){
		this.po = _po;
	}

	public void setMap(Map<String,Object> _map){
		this.map = _map;
	}
	
	public Sql setSql(String _sql){
		this.sql = _sql;
		return this;
	}
	
	public void setTables(String[] _table){
		this.tables = _table;
	}
	
	public void setTableNames(String tableNames) {
		this.tableNames = tableNames;
	}
	/**
	 * 取得查询Sql语句
	 * @return
	 * @throws Exception 
	 */
	public String getSql() throws Exception{
		return getSelectSql()+this.orderbySql;
	}
	
	public String getSqlBlock(){
		return this.sql;
	}
	/*
	 * 取得查询的sql语句
	 */
	public String getSelectSql() throws Exception{
		processVarTables();//处理可变数据表名称
		if(params == null || params.isEmpty())
			return sql;
		if(!this.condition)
			return sql;
		StringBuilder s = new StringBuilder();
		boolean flag = true; //
		for(int i=0; i<params.size(); ++i){
			Parameter p = params.get(i);
			if(!isAppandSql(p))
				continue;
			if(this.where && flag){
				s.append(" where ");
				flag = false;
			}else
				s.append(p.getRelation());
			if(p.isLeftBracket())s.append("(");
			
			//是否等于switchvalue值。如果相等则使用switchcol的字段名作为参数附加到sql语句中，
			//否则，还是使用col的字段名作为参数 附加到sql语句后面。
			boolean b = p.getSwitchValue()!=null && p.getSwitchValue().equals(""+this.getValue(p));
			s.append(" ").append(b?p.getSwitchCol():p.getCol());
			if(p.getOp().equals("between"))
				s.append(" between ? and ? ");
			else if(p.getOp().equals("in") || p.getOp().equals("not in")){
				s.append(" ").append(p.getOp()).append(" (");
				Object o = this.getFieldValue(p, p.getField1());
				if(!(o instanceof List)){
					throw new Exception("参数："+p.getField1()+" 不是一个集合!");
				}
				List<?> list = (List<?>)o;
				for(int m = 0, count = list.size(); m < count; ++m){
					if(m != 0)
						s.append(",");
					s.append("?");
				}
				s.append(") ");
			}else
				s.append(" ").append(p.getOp()).append(" ? ");
			if(p.isRightBracket())s.append(")");
		}
		return sql+s.toString();
	}
	
	/**
	 * 处理可变数据表名称
	 */
	private void processVarTables(){
		if(this.tableNames != null && this.tables != null && this.tables.length > 0){
			String[] ts = this.tableNames.split(",");
			for(int i=0, count=ts.length; i<count; ++i){
				this.sql = this.sql.replaceAll(ts[i].trim(), this.tables[i]);
			}
		}
	}
	
	/**
	 * 是否需要将参数生成sql条件片段附加到sql语句的后面。
	 * @param p
	 * @return
	 * @throws Exception
	 */
	private boolean isAppandSql(Parameter p)throws Exception{
		if(!p.isCondition())return false;
		return isNeeded(p);
	}
	
	
	
	/**
	 * 判断是否需要处理该参数，如果参数对应的参数值为空则不添加到sql语句的后面。
	 * @param p
	 * @return
	 * @throws Exception 
	 */
	private boolean isNeeded(Parameter p) throws Exception{
		if(p.getObj().equals("po")){
			Object v = ObjectUtil.getFieldValue(po, p.getField1());
			if(SystemConfig.isBpoproxy())
				logger.info(p);
			if(v == null)return false;
			if((v+"").trim().equals(""))return false;
			if((v+"").equals(""+p.getExcValue()))return false;
		}else if(p.getObj().equals("map")){
			Object[] v = (Object[])map.get(p.getField1());
			if(v == null)return false;//如果是空,不加条件字段
			//if((v[0]+"").trim().equals(""))return false;//如果是 空格 或者 '' 不加字段
			//if((v[0]+"").equals(""+p.getExcValue()))return false;//如果是设置值不加字段


			if(p.getExcValue()==null){
				if((v[0]+"").trim().equals(""))return false;//如果是 空格 或者 '' 不加字段
			}else {
				if(!p.getExcValue().contains("|")){//如果不包含"|",字段为"" 或者 空格,不加到sql
					if((v[0]+"").trim().equals(""))return false;//如果是 空格 或者 '' 不加字段
					if((v[0]+"").equals(""+p.getExcValue()))return false;//如果是设置值不加字段
				}else{//如果包含"|",字段为"" 或者 空格 也要加上sql语句
					if(!p.getExcValue().substring(0,p.getExcValue().indexOf("|")).equals("")){
					if((v[0]+"").equals(p.getExcValue().substring(0,p.getExcValue().indexOf("|"))))return false;//如果是设置值不加字段
					}
				}
			}


		}else{ //obj = user
			LoginUser user = LoginUser.userThread.get();
			if(user == null)
				return false;
			Object v = ObjectUtil.getFieldValue(user, p.getField1());
			if(v == null)return false;
			if((v+"").trim().equals(""))return false;
			if((v+"").equals(""+p.getExcValue()))return false;
		}
		return true;
	}
	
	/**
	 * 取得字段参数的字段值
	 * @param p
	 * @return
	 * @throws Exception
	 */
	private Object getValue(Parameter p)throws Exception{
		if(p.getObj().equals("po")){
			return ObjectUtil.getFieldValue(po, p.getField1());
		}else if(p.getObj().equals("map")){
			Object[] v = (Object[])map.get(p.getField1());
			return v[0];
		}else{ //obj = user
			LoginUser user = LoginUser.userThread.get();
			if(user == null)
				return false;
			return ObjectUtil.getFieldValue(user, p.getField1());
		}
	}
	
	/**
	 * 注入sql语句的查询参数
	 * @param query  查询对象。
	 * @throws SQLException
	 */
	public void processParameters(Query query) throws Exception{
		int idx = 1;
		for(int i=0; i<params.size(); ++i){
			Parameter p = params.get(i);
			if(!isNeeded(p))
				continue;
			Object value = getFieldValue(p,p.getField1());
			if(p.getOp() != null && (p.getOp().equals("in") || p.getOp().equals("not in"))){
				List<?> list = (List<?>)value;
				for(int m=0,count=list.size(); m<count; ++m){
					setParameterValue(query,idx,p.getType(),list.get(m));
					++idx;
				}
				--idx;
			}else{
				setParameterValue(query,idx,p.getType(),value);
				if(SystemConfig.isBpoproxy())
					logger.info("value = "+value+" , index = "+idx+", type = "+p.getType());
				//如果需要拼接条件语句，则判断between操作符
				if(this.condition && p.isCondition() && p.getOp().equals("between")){
					++idx;
					Object v = getFieldValue(p,p.getField2());
					setParameterValue(query,idx,p.getType(),v);
				}
			}
			++idx;
		}
	}
	
	/**
	 * 获取指定field的值。
	 * @param p
	 * @param field
	 * @return
	 * @throws Exception 
	 */
	private Object getFieldValue(Parameter p, String field) throws Exception{
		if(p.getObj().equals("po")){
			return ObjectUtil.getFieldValue(po, field);
		}else if(p.getObj().equals("map")){
			Object[] v = (Object[])map.get(field);
			return v[0];
		}else{ //obj = user
			LoginUser user = LoginUser.userThread.get();
			if(user == null)
				return false;
			Object v = ObjectUtil.getFieldValue(user, field);
			return v;
		}
	}
	
	/**
	 * 设置查询sql的参数，将参数值注入。
	 * @param query 查询对象
	 * @param index 参数索引
	 * @param type  参数类型
	 * @param value 参数值
	 * @throws NumberFormatException
	 * @throws SQLException
	 */
	private void setParameterValue(Query query, int index, String type, Object value) throws NumberFormatException, SQLException{
//		if(value== null)
//			query.setNull(index, Types.NULL);
		if("int".equals(type)){
			query.setInt(index, Integer.parseInt(""+value));
		}else if("long".equals(type)){
			query.setLong(index, Long.parseLong(value+""));
		}else if("short".equals(type)){
			query.setShort(index, Short.parseShort(value+""));
		}else if("double".equals(type)){
			query.setDouble(index, Double.parseDouble(value+""));
		}else if("float".equals(type)){
			query.setFloat(index, Float.parseFloat(value+""));
		}else if("string".equals(type)){
			query.setString(index,value+"");
		}else if("date".equals(type)){
			query.setDate(index, (Date)value);
		}else if("time".equals(type)){
			query.setTime(index, (Time)value);
		}else if("timestamp".equals(type)){
			query.setTimestamp(index, (Date)value);
		}else if("bytes".equals(type)){
			query.setBytes(index, (byte[]) value);
		}
	}
	
	/**
	 * clone一个新的Sql对象。
	 */
	@Override
	public Sql clone() throws CloneNotSupportedException {
		Sql _sql = (Sql)super.clone();
		return _sql;
	}
}
