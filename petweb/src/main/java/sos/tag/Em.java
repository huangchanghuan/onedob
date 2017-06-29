package sos.tag;

import java.io.Writer;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.components.Component;

import com.opensymphony.xwork2.util.ValueStack;

/**
 * 判断方法名称实现类
 * @author zhou
 */
public class Em extends Component {
	
	private boolean escape = false;
	HttpServletRequest _req;
	
	public Em(ValueStack stack,HttpServletRequest req) {
		super(stack);
		this._req = req;
	}
	public boolean start(Writer writer) {
		String _m = (String)this._req.getAttribute("_m_flag_");
		this._req.removeAttribute("_m_flag_");
		if(_m != null && _m.equals("false")){
			return super.start(writer);
		}
		return false;
	}
	
	@Override
	public boolean end(Writer writer, String body) {
		return super.end(writer, body);
	}
	
	public void setEscape(boolean escape) {
		this.escape = escape;
	}
	public boolean isEscape() {
		return escape;
	}
}
