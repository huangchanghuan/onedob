package sos.tag;

import java.io.Writer;

import org.apache.struts2.components.Component;

import com.opensymphony.xwork2.util.ValueStack;
import com.sunstar.adms.util.SysParametersUtil;
import com.sunstar.sos.util.page.PageForm;

public class Page extends Component {
	private String value;
	private String position;
	private PageForm pageForm;
	private boolean escape = false;
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Page(ValueStack stack) {
		super(stack);
	}
	public boolean start(Writer writer) {
		boolean bool = super.start(writer);
		pageForm = (PageForm)stack.findValue("pageForm");
		String pos = SysParametersUtil.getValue("PAGE_TOOLBAR_POSITION");
		pos = (pos==null || pos.trim().equals(""))?"bottom":pos.toLowerCase();
		if("top".equals(pos) && !"top".equalsIgnoreCase(position))
			return false;
		if("bottom".equals(pos) && !"bottom".equalsIgnoreCase(position))
			return false;
		try{
			StringBuffer buf = new StringBuffer(200);
			if(pageForm.getTotalPage()==0)
				pageForm.setTotalPage(1);
			buf.append("<table border=0 class=\"pagetoolbar\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\"><tr>");
			buf.append("<td align=\"left\"><font color=\"#000000\" size=\"2\">")
			.append("当前第").append(pageForm.getPageNo()).append("页,")
			.append(pageForm.getCurrSize()).append("条记录,");
			
			if("all".equals(pos) && "bottom".equalsIgnoreCase(position))
				buf.append("每页<input name=\"pageForm.pageSize\" onkeypress=\"changePageSize(this.value,event)\" ");
			else if("bottom".equals(pos) && "bottom".equalsIgnoreCase(position))
				buf.append("每页<input name=\"pageForm.pageSize\" onkeypress=\"changePageSize(this.value,event)\" ");
			else if("top".equals(pos) && "top".equalsIgnoreCase(position))
				buf.append("每页<input name=\"pageForm.pageSize\" onkeypress=\"changePageSize(this.value,event)\" ");
			else
				buf.append("每页<input onkeypress=\"changePageSize(this.value,event)\" ");
				
			
			buf.append(" style=\"border-style:solid;border-width:1px;width: ")
			.append(String.valueOf(pageForm.getPageSize()).length()*12)
			.append("px\" value=\"").append(pageForm.getPageSize()).append("\">条记录/")
			.append("共").append(pageForm.getTotalPage()).append("页，")
			.append("有").append(pageForm.getTotalSize()).append("条记录")
			.append("</font></td>");
			
			buf.append("<td>");
			buf.append("<div class=\"pagination fr\"><ul>");
			if(pageForm.getClickPages().size()==0)
				buf.append("<li class=\"active\"><a>1</a></li>");
			else{
				int prev_page=pageForm.getPrevPage();
				if(pageForm.isHasPrev()){
					buf.append("<li class=\"first\"><a href=\"#\" onclick=\"goPage(1)\"><img src='../img/page/first.png' width='16px'/></a></li>");
					buf.append("<li class=\"prev\"><a href=\"#\" onclick=\"goPage(").append(prev_page).append(")\">").append("<img src='../img/page/pre.png' width='16px'/></a></li>");
				}
				if(pageForm.getClickPages().get(0)!=1)
					buf.append("<li><a href=\"#\" onclick=\"goPage(1)\">1</a></li><li class=\"spaces\">...</li>");
					for(int i=0; i<pageForm.getClickPages().size();++i){
						int page = pageForm.getClickPages().get(i);
						if(page==pageForm.getPageNo())
							buf.append("<li class=\"active\"><a>").append(page).append("</a></li>");
						else
							buf.append("<li><a href=\"#\" onclick=\"goPage(").append(page).append(")\">").append(page).append("</a></li>");
					}
				int page = pageForm.getClickPages().get(pageForm.getClickPages().size()-1);
				if(page != pageForm.getTotalPage()){
					buf.append("<li class=\"spaces\">...</li>");
					buf.append("<li><a href=\"#\" onclick=\"goPage(").append(pageForm.getTotalPage());
					buf.append(")\">").append(pageForm.getTotalPage()).append("</a></li>");
				}
				int next_page=pageForm.getNextPage();
				int total_page=pageForm.getTotalPage();
				if(pageForm.isHasNext()){
					buf.append("<li class=\"next\"><a href=\"#\" onclick=\"goPage(").append(next_page).append(")\">").append("<img src='../img/page/next.png' width='16px'/></a></li>");
					buf.append("<li class=\"last\"><a href=\"#\" onclick=\"goPage(").append(total_page).append(")\">").append("<img src='../img/page/last.png' width='16px'/></a></li>");
				}
			}
			buf.append("</ul></div></td><td align=\"right\"><font color=\"#000000\" size=\"2\">");
			if("all".equals(pos) && "bottom".equalsIgnoreCase(position))
				buf.append("转至<input type=\"text\" name=\"pageForm.pageNo\" value=\"");
			else if("bottom".equals(pos) && "bottom".equalsIgnoreCase(position))
				buf.append("转至<input type=\"text\" name=\"pageForm.pageNo\" value=\"");
			else if("top".equals(pos) && "top".equalsIgnoreCase(position))
				buf.append("转至<input type=\"text\" name=\"pageForm.pageNo\" value=\"");
			else
				buf.append("转至<input type=\"text\" value=\"");
			buf.append(pageForm.getPageNo()).append("\" ")
			.append("style=\"border-style:solid;border-width:1px;width:")
			.append(String.valueOf(pageForm.getTotalPage()).length()*18).append("px\"")
			.append("onkeypress=\"changePageNo(this.value,event)\">")
			.append("页")
			.append("</font></td>")
			.append("</tr></table>");
			writer.write(buf.toString());
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return bool;
	}
	public void setEscape(boolean escape) {
		this.escape = escape;
	}
	public boolean isEscape() {
		return escape;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
}
