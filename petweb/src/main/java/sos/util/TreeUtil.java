package sos.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dom4j.Element;
import org.dom4j.dom.DOMElement;

import com.sunstar.sos.bpo.BaseBpo;
import com.sunstar.sos.factory.BpoFactory;

public class TreeUtil
{
	private BaseBpo bpo = BpoFactory.getBpo();
	private Map map;
	private Class cls;
	private String tableName;
	private String id;
	private String parentId;
	private String name;
	private String cname;
	private String condition;
	
	public TreeUtil(Class cls, String tableName, String id, String parentId, String name, String cname) throws Exception
	{
		bpo = BpoFactory.getBpo(cls,null);
		init(cls, tableName, id, parentId, name, cname, null);
	}
	
	public TreeUtil(Class cls, String tableName, String id, String parentId, String name, String cname, String condition) throws Exception
	{
		bpo = BpoFactory.getBpo(cls,null);
		init(cls, tableName, id, parentId, name, cname, condition);
	}
	
	public void init(Class cls, String tableName, String id, String parentId, String name, String cname, String condition) throws Exception
	{
		map = new HashMap();
		this.cls = cls;
		this.tableName = tableName;
		this.id = "get"+id.substring(0, 1).toUpperCase()+id.substring(1);
		this.parentId = "get"+parentId.substring(0, 1).toUpperCase()+parentId.substring(1);
		this.name = "get"+name.substring(0, 1).toUpperCase()+name.substring(1);
		this.cname = "get"+cname.substring(0, 1).toUpperCase()+cname.substring(1);
		this.condition = condition;
		initMap();
	}

	public void initTree(Element tree) throws Exception
	{
		createTree(tree, new Long(0));
	}
	
	public void initTree(Element tree, Object parentIdValue) throws Exception
	{
		createTree(tree, parentIdValue);
	}
	
	private void initMap() throws Exception
	{
		List list = null;
		if(condition!=null)
		{
			System.out.println("==========================       from "+cls.getName()+" "+condition);
			list = bpo.search("select * from "+tableName+" "+condition);
			System.out.println("==========================       "+list.size());
		}
		else
		{
			list = bpo.search("select * from "+tableName);
		}
		
		
		for(int i=0;i<list.size();i++)
		{
			Object o = list.get(i);
			Method m_id = cls.getMethod(id, null);
			Object o_id = m_id.invoke(o, null);
			map.put(o_id, o);
		}
	}
	private void createTree(Element tree, Object parentIdValue) throws Exception
	{
		List list = subChildren(parentIdValue); 
		//System.out.println("list.size()="+list.size());
		if(!list.isEmpty())
		{
			for (int i = 0; i < list.size(); i++)
			{
				Object o = list.get(i);
				Element node = new DOMElement("item");
				Method m_id = cls.getMethod(id, null);
				Object o_id = m_id.invoke(o, null);
				Method m_name = cls.getMethod(name, null);
				Object o_name = m_name.invoke(o, null);
				Method m_cname = cls.getMethod(cname, null);
				Object o_cname = m_cname.invoke(o, null);
				node.addAttribute("text", (String) o_cname);
				node.addAttribute("id", String.valueOf(o_id));
				node.addAttribute("tooltip", (String) o_name);
				node.addAttribute("open", "1");
				node.addAttribute("im0", "books_close.gif");
				node.addAttribute("im1", "books_close.gif");
				node.addAttribute("im2", "tombs.gif");
				tree.add(node);
				this.createTree(node, o_id); 
			}
		}
	}
	private List subChildren(Object parentIdValue) throws Exception
	{
		List list = new ArrayList();
		Set idSet = map.keySet();
		Iterator iterator = idSet.iterator();
		while(iterator.hasNext())
		{
			Object o = map.get(iterator.next());
			Method m_parentId = cls.getMethod(parentId, null);
			String o_parentId = String.valueOf(m_parentId.invoke(o, null));
			if(o_parentId.equals(String.valueOf(parentIdValue)))
			{
				list.add(o);
			}
		}
		return list;
	}
	
	public List getChildren(Object o) throws Exception
	{
		Method m_id = cls.getMethod(id, null);
		Object o_id = m_id.invoke(o, null);
		return subChildren(o_id);
	}
	
	public List getAllChildren(Object o) throws Exception
	{
		List allChild = new ArrayList();
		Method m_id = cls.getMethod(id, null);
		Object o_id = m_id.invoke(o, null);
		List list = subChildren(o_id);
		for(int i=0;i<list.size();i++)
		{
			Object o_tmp = list.get(i);
			allChild.addAll(getAllChildren(o_tmp));
		}
		allChild.addAll(list);
		return allChild;
	}
	
	public Object getParent(Object o) throws Exception
	{
		Object parent = null;
		Method m_parentId = cls.getMethod(parentId, null);
		Object o_parentId = m_parentId.invoke(o, null);
		parent = map.get(o_parentId);
		return parent;
	}
	
	public List getAllParent(Object o) throws Exception
	{
		List allParent = new ArrayList();
		Object parent = getParent(o);
		if(parent!=null)
		{
			allParent.addAll(getAllParent(parent));
		}
		allParent.add(parent);
		return allParent;
	}
}
