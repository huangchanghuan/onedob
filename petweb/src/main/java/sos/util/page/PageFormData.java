package sos.util.page;

import java.util.ArrayList;
import java.util.List;

import org.carp.DataSet;

public class PageFormData
{
	private PageForm pageForm;
	private List data;
	private DataSet ds;
	private List rowNo;
	
	public PageFormData(List _date, PageForm _pageForm,long totalSize)
	{
		this.pageForm = _pageForm;
		this.data = _date;
		this.pageForm.setTotalSize(totalSize);
		this.pageForm.initPageForm();
		this.pageForm.setCurrSize(data.size());
	}
	
	public PageFormData(List _data,long totalSize,int pageNo,int pageSize)
	{
		this.data = _data;
		rowNo = new ArrayList();
		this.pageForm = new PageForm(totalSize,pageNo,pageSize);
		for(int i = (pageNo-1)*pageSize + 1; i<(pageNo-1)*pageSize + _data.size(); ++i)
			rowNo.add(""+i);
		pageForm.setCurrSize(data.size());
	}
	
	public PageFormData(DataSet _ds,long totalSize,int pageNo,int pageSize)
	{
		this.ds = _ds;
		rowNo = new ArrayList();
		this.pageForm = new PageForm(totalSize,pageNo,pageSize);
		for(int i = (pageNo-1)*pageSize + 1; i<(pageNo-1)*pageSize + ds.count(); ++i)
			rowNo.add(""+i);
		pageForm.setCurrSize(data.size());
	}

	public PageForm getPageForm()
	{
		return pageForm;
	}

	public void setPageForm(PageForm pageForm)
	{
		this.pageForm = pageForm;
	}

	public List getData()
	{
		return data;
	}

	public void setData(List data)
	{
		this.data = data;
	}

	public List getRowNo()
	{
		return rowNo;
	}

	public void setRowNo(List rowNo)
	{
		this.rowNo = rowNo;
	}

	public DataSet getDataSet() {
		return ds;
	}
}
