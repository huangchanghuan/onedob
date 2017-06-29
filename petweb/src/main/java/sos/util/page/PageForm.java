package sos.util.page;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PageForm
{
	private int totalPage;		//总页数
	private long totalSize;		//总记录数
	private int pageSize = 10;	//每页记录数
	private int currSize; 		//当前页面记录数
	private int pageNo = 1;		//当前页码
	private int prevPage;		//当前页的前一页页码
	private int nextPage;		//当前页的下一页页码
	private boolean hasPrev;	//是否有前一页
	private boolean hasNext;	//是否有下一页
	private Map pages;			//总页数的集合，放入一个下拉列表中，共选择
	private int firstPage;		//第一页页码，从1开始
	private int lastPage;		//最后一页页码
	private boolean hasFirst;	//是否有第一页
	private boolean hasLast;	//是否有最后一页
	private Class cls;			//当前操作的模块类
	private String cmd;			//操作命令字
	private List<Integer> clickPages = new ArrayList<Integer>(5);	//翻页行可以出现的连续的5个页码，供翻页使用。
	
	

	public PageForm(){
		
	}
	
	/**
	 * 构造函数
	 * @param totalSize 总记录数
	 * @param pageSize  每页记录数
	 * @param pageNo    当前页码
	 */
	public PageForm(long totalSize,int pageNo,int pageSize)
	{
		this.setTotalSize(totalSize);
		this.setPageSize(pageSize);
		this.setPageNo(pageNo);
		this.initPageForm();
	}
	
	public String getCmd()
	{
		return cmd;
	}

	public void setCmd(String cmd)
	{
		this.cmd = cmd;
	}

	/**
	 * 初始化页面数据
	 */
	public void initPageForm(){
		this.totalPage = (int)this.getTotalSize()/this.getPageSize();
		if(this.getTotalSize()%this.getPageSize()!=0 || this.getTotalSize()==0)
			this.totalPage += 1;
		this.prevPage = this.pageNo==1 ? 1 : this.pageNo-1;
		this.nextPage = this.totalPage==this.pageNo ? this.pageNo : this.pageNo+1;
		this.initFormList();
		this.hasPrev = this.pageNo==1 ? false:true;
		this.hasNext = this.pageNo==this.totalPage ? false:true;
		this.firstPage = 1;
		this.lastPage = this.totalPage;
		this.hasFirst = this.pageNo==1 ? false:true;
		this.hasLast = this.pageNo == this.totalPage ? false:true;
		if(totalPage <= 5){ //少于或等于5页，全部显示.
			for(int i=1;i<=totalPage;++i)
				clickPages.add(i);
		}else{//多于5页
			if((totalPage-pageNo)<=2){ //靠近总页数的页尾
				for(int i=totalPage-4;i<=totalPage;++i)
					clickPages.add(i);
			}
			if(pageNo <=3){ //靠近总页数的页头
				for(int i=1;i<=5;++i)
					clickPages.add(i);
			}
			if(pageNo>3 && (totalPage-pageNo)>2){//处于中间位置
				for(int i=pageNo-2;i<=pageNo+2;++i)
					clickPages.add(i);
			}
		}
	}
	
	private void initFormList()
	{
		pages = new HashMap();
		for(int i=0; i<this.totalPage; ++i)
			pages.put(String.valueOf(i+1), String.valueOf(i+1));
	}
	
	public int getTotalPage()
	{
		return totalPage;
	}

	public void setTotalPage(int totalPage)
	{
		this.totalPage = totalPage;
	}

	public long getTotalSize()
	{
		return totalSize;
	}

	public void setTotalSize(long totalSize)
	{
		this.totalSize = totalSize;
	}

	public int getPageSize()
	{
		return pageSize;
	}

	public void setPageSize(int pageSize)
	{
		this.pageSize = pageSize;
	}

	public int getPageNo()
	{
		return this.pageNo;
	}

	public void setPageNo(int pageNo)
	{
		this.pageNo = pageNo;
	}

	public int getPrevPage()
	{
		return prevPage;
	}

	public void setPrevPage(int prevPage)
	{
		this.prevPage = prevPage;
	}

	public int getNextPage()
	{
		return nextPage;
	}

	public void setNextPage(int nextPage)
	{
		this.nextPage = nextPage;
	}

	public boolean isHasPrev()
	{
		return hasPrev;
	}

	public void setHasPrev(boolean hasPrev)
	{
		this.hasPrev = hasPrev;
	}

	public boolean isHasNext()
	{
		return hasNext;
	}

	public void setHasNext(boolean hasNext)
	{
		this.hasNext = hasNext;
	}

	public Map getPages()
	{
		return pages;
	}

	public void setPages(Map pages)
	{
		this.pages = pages;
	}

	public int getFirstPage()
	{
		return firstPage;
	}

	public void setFirstPage(int firstPage)
	{
		this.firstPage = firstPage;
	}

	public int getLastPage()
	{
		return lastPage;
	}

	public void setLastPage(int lastPage)
	{
		this.lastPage = lastPage;
	}

	public boolean isHasFirst()
	{
		return hasFirst;
	}

	public void setHasFirst(boolean hasFirst)
	{
		this.hasFirst = hasFirst;
	}

	public boolean isHasLast()
	{
		return hasLast;
	}

	public void setHasLast(boolean hasLast)
	{
		this.hasLast = hasLast;
	}
	
	public Class getCls()
	{
		return cls;
	}

	public void setCls(Class cls)
	{
		this.cls = cls;
	}

	public int getCurrSize()
	{
		return currSize;
	}

	public void setCurrSize(int currSize)
	{
		this.currSize = currSize;
	}
	public List<Integer> getClickPages() {
		return clickPages;
	}

	public void setClickPages(List<Integer> clickPages) {
		this.clickPages = clickPages;
	}
}
