package sos.cfg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import com.sunstar.sos.parameter.Parameter;

/**
 * sql语句配置文件解析类
 * @author Administrator
 *
 */
public class SqlConfig {
	private static final Logger logger = Logger.getLogger(SqlConfig.class);
	private static final Map<String,Sql> moduleMap = new HashMap<String,Sql>();
	static{
		readAdmsConfig();
	}
	
	public static void main(String[] ss)throws Exception{
//		Events e = new Events();
//		e.setStatusId((short)1);
//		e.setAssignedor("333");
//		
//		System.out.println(getSQL("prject_manage/gantt_events",e,null,new String[]{"ttttttt","bbbbbbbbbb"}).getSql());
	}
	/**
	 * 取得查询sql对象
	 * @param name  sql对象对应的key名称
	 * @return
	 * @throws Exception
	 */
	public static Sql getSQL(String name)throws Exception{
		return getSQL(name,null,null);
	}
	
	/**
	 * 取得查询sql对象
	 * @param name  sql对象对应的key名称
	 * @return
	 * @throws Exception
	 */
	public static Sql getSQL(String name,String[] tables)throws Exception{
		return getSQL(name,null,null,tables);
	}
	
	/**
	 * 取得查询sql对象
	 * @param name  sql对象对应的key名称
	 * @param po  po对象
	 * @param map map对象
	 * @return
	 * @throws Exception
	 */
	public static Sql getSQL(String name,Object po,Map<String,Object> map)throws Exception{
		Sql sql = moduleMap.get(name);
		if(sql == null)
			throw new Exception("找不到sql名称为："+name+" 的Sql对象，请检查参数或sql配置文件是否正确。");
		Sql _sql = sql.clone();
		_sql.setMap(map);
		_sql.setPo(po);
		return _sql;
	}
	/**
	 * 取得查询sql对象
	 * @param name  sql对象对应的key名称
	 * @param po  po对象
	 * @param map map对象
	 * @return
	 * @throws Exception
	 */
	public static Sql getSQL(String name,Object po,Map<String,Object> map,String[] tables)throws Exception{
		Sql sql = moduleMap.get(name);
		if(sql == null)
			throw new Exception("找不到sql名称为："+name+" 的Sql对象，请检查参数或sql配置文件是否正确。");
		Sql _sql = sql.clone();
		_sql.setMap(map);
		_sql.setPo(po);
		_sql.setTables(tables);
		return _sql;
	}
	
	/**
	 * 读取sql文件列表
	 */
	public static void readAdmsConfig(){
		moduleMap.clear();
		org.dom4j.io.SAXReader reader = new org.dom4j.io.SAXReader();
		try {
			Document doc = reader.read(SqlConfig.class.getClassLoader().getResourceAsStream("sql.conf.xml"));
			Element root = doc.getRootElement();
			List<?> elemes = root.element("sql-files").elements();
			for(int i=0,count = elemes.size(); i < count; ++i){
				Element elem = (Element)elemes.get(i);
				String file = elem.attributeValue("file").trim();
				if(file.trim().equals(""))
					throw new Exception("sql file is null ");
				if(SystemConfig.isSqlconfig())
					logger.info("正在解析sql配置文件："+file);
				parserSqlFile(file);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 解析sql文件
	 * @param file sql文件名
	 * @throws Exception
	 */
	private static void parserSqlFile(String file) throws Exception{
		org.dom4j.io.SAXReader  reader = new org.dom4j.io.SAXReader();
		Document doc = reader.read(SqlConfig.class.getClassLoader().getResourceAsStream(file));
		Element root = doc.getRootElement();
		List<?> modules = root.elements();
		for(int i = 0, count = modules.size(); i < count; ++i){
			Element module = (Element)modules.get(i);
			if(!module.getName().toLowerCase().equals("module"))
				throw new Exception("sql conf file error, tag of modules was error. file : "+file+" , modles: "+module.getName());
			parserSql(module);
		}
	}
	
	/**
	 * 解析Sql模块
	 * @param module sql的DOM对象
	 * @throws Exception
	 */
	private static void parserSql(Element module) throws Exception{
		List<?> list = module.elements();
		for(int i = 0, count = list.size(); i < count; ++i){
			Element mod = (Element)list.get(i);
			String sqlname = mod.attributeValue("name");
			String key = module.attributeValue("name")+"/"+sqlname;
			if(moduleMap.get(key)!=null)//sql语句已经存在！
				throw new Exception("exist sql name. module: "+module.getName()+" ,  sql's name : "+sqlname);
			String sql = mod.element("sql").getTextTrim(); //读取sql语句
			if(sql.trim().equals(""))
				throw new Exception("sql is null. sql's name : "+sqlname+" , modles: "+module.getName());
			if(SystemConfig.isSqlconfig())
				logger.info("module: "+module.attributeValue("name")+", name: "+sqlname+" , sql: "+sql);
			String cond = mod.attributeValue("condition");
			String where = mod.attributeValue("where");
			Element orderElem = mod.element("orderby");
			String orderby = orderElem != null?orderElem.getText():"";
			if(SystemConfig.isSqlconfig())
				logger.info("condition: "+cond+" ,  order by : "+orderby);
			Sql _sql = new Sql(sql,orderby,new ArrayList<Parameter>(), (cond!=null && cond.trim().equals("true"))?true:false
							,(where!=null && where.trim().equals("true"))?true:false);
			moduleMap.put(key, _sql);
			processSqlTables(key,_sql,mod);
			processSqlParams(key, _sql, mod);
		}
	}
	
	/**
	 * 处理可变table列表
	 * @param key
	 * @param _sql
	 * @param sqlelem
	 * @throws Exception
	 */
	private static void processSqlTables(String key, Sql _sql, Element sqlelem) throws Exception{
		Element tables = (Element)sqlelem.selectSingleNode("table");
		if(tables != null){
			_sql.setTableNames(tables.getTextTrim());
		}
	}
	
	/**
	 * 处理sql的参数
	 * @param key sql语句索引key，用于检索对应的sql对象
	 * @param _sql sql对象
	 * @param sqlelem sql的DOM对象
	 * @throws Exception
	 */
	private static void processSqlParams(String key, Sql _sql, Element sqlelem) throws Exception{
		List<?> list = sqlelem.elements("param");
		List<Parameter> params = _sql.getParams();
		for(int m = 0, len = list.size(); m < len; ++m){
			Element pe = (Element)list.get(m);
			Parameter p = new Parameter();
			p.setObj(pe.attributeValue("obj"));
			p.setCol(pe.attributeValue("col"));
			p.setType(pe.attributeValue("type"));
			p.setField1(pe.attributeValue("f1"));
			p.setField2(pe.attributeValue("f2"));
			String op = pe.attributeValue("op");
			op = (op!=null && !op.trim().equals(""))?op.trim():"";
			if(op.equalsIgnoreCase("eq"))
				p.setOp("=");
			else if(op.equalsIgnoreCase("neq"))
				p.setOp("<>");
			else if(op.equalsIgnoreCase("gt"))
				p.setOp(">");
			else if(op.equalsIgnoreCase("egt"))
				p.setOp(">=");
			else if(op.equalsIgnoreCase("lt"))
				p.setOp("<");
			else if(op.equalsIgnoreCase("elt"))
				p.setOp("<=");
			else if(op.equalsIgnoreCase("like"))
				p.setOp("like");
			else if(op.equalsIgnoreCase("between"))
				p.setOp("between");
			else if(op.equalsIgnoreCase("in"))
				p.setOp("in");
			else if(op.equalsIgnoreCase("notin"))
				p.setOp("not in");
			p.setRelation(" "+pe.attributeValue("rel")+" ");
			p.setLeftBracket((pe.attributeValue("lb")!=null && !pe.attributeValue("lb").equals(""))?true:false );
			p.setRightBracket((pe.attributeValue("rb")!=null && !pe.attributeValue("rb").equals(""))?true:false );
			p.setExcValue(pe.attributeValue("excValue"));
			p.setCondition((pe.attributeValue("condition")!=null && !pe.attributeValue("condition").equals(""))?true:false);
			p.setSwitchValue(pe.attributeValue("switchvalue"));
			p.setSwitchCol(pe.attributeValue("switchcol"));
			if(SystemConfig.isSqlconfig())
				logger.info("SQL参数：obj="+p.getObj()+", col="+p.getCol()+", type="+p.getType()
						+", f1="+p.getField1()+", f2="+p.getField2()+", op="+op+", excValue="+p.getExcValue()
						+", rel="+p.getRelation()+", lb="+p.isLeftBracket()+", rb="+p.isRightBracket());
			params.add(p);
		}
	}
}
