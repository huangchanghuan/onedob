package sos.tag;

import java.io.Writer;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.components.Component;

import com.opensymphony.xwork2.util.ValueStack;

/**
 * 判断方法名称实现类
 * @author zhou
 */
public class M extends Component {
	private String value;// action方法名称
	
	private boolean escape = false;
	HttpServletRequest _req;
	
	public M(ValueStack stack,HttpServletRequest req) {
		super(stack);
		this._req = req;
	}
	public boolean start(Writer writer) {
		String _m = (String)this._req.getAttribute("_method");
		if(_m == null || !_m.equals(value)){
			this._req.setAttribute("_m_flag_", "false");
			return false;
		}else
			this._req.setAttribute("_m_flag_", "true");
		return super.start(writer);
	}
	
	@Override
	public boolean end(Writer writer, String body) {
		return super.end(writer, body);
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	public void setEscape(boolean escape) {
		this.escape = escape;
	}
	public boolean isEscape() {
		return escape;
	}
}
