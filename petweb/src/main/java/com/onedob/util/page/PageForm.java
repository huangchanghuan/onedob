package com.onedob.util.page;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PageForm
{
	private int totalPage;		//��ҳ��
	private long totalSize;		//�ܼ�¼��
	private int pageSize = 10;	//ÿҳ��¼��
	private int currSize; 		//��ǰҳ���¼��
	private int pageNo = 1;		//��ǰҳ��
	private int prevPage;		//��ǰҳ��ǰһҳҳ��
	private int nextPage;		//��ǰҳ����һҳҳ��
	private boolean hasPrev;	//�Ƿ���ǰһҳ
	private boolean hasNext;	//�Ƿ�����һҳ
	private Map pages;			//��ҳ���ļ��ϣ�����һ�������б��У���ѡ��
	private int firstPage;		//��һҳҳ�룬��1��ʼ
	private int lastPage;		//���һҳҳ��
	private boolean hasFirst;	//�Ƿ��е�һҳ
	private boolean hasLast;	//�Ƿ������һҳ
	private Class cls;			//��ǰ������ģ����
	private String cmd;			//����������
	private List<Integer> clickPages = new ArrayList<Integer>(5);	//��ҳ�п��Գ��ֵ�������5��ҳ�룬����ҳʹ�á�
	
	

	public PageForm(){
		
	}
	
	/**
	 * ���캯��
	 * @param totalSize �ܼ�¼��
	 * @param pageSize  ÿҳ��¼��
	 * @param pageNo    ��ǰҳ��
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
	 * ��ʼ��ҳ������
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
		if(totalPage <= 5){ //���ڻ����5ҳ��ȫ����ʾ.
			for(int i=1;i<=totalPage;++i)
				clickPages.add(i);
		}else{//����5ҳ
			if((totalPage-pageNo)<=2){ //������ҳ����ҳβ
				for(int i=totalPage-4;i<=totalPage;++i)
					clickPages.add(i);
			}
			if(pageNo <=3){ //������ҳ����ҳͷ
				for(int i=1;i<=5;++i)
					clickPages.add(i);
			}
			if(pageNo>3 && (totalPage-pageNo)>2){//�����м�λ��
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
