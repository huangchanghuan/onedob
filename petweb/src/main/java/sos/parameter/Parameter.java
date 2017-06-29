package sos.parameter;

/**
 * 查询参数类
 * @author zhou
 *
 */
public class Parameter {
	private String obj;  //对象类型： po,map,user
	private String col;  //字段名称
	private String type;  //类型
	private String field1; //对象属性1
	private String field2; //对象属性22，当操作符为between and时，colvalue2需要被设置值
	private String op; //操作符，> < = >= <= <> between and like 
	private String relation; //字段之间的关系，and / or ,关系为前匹配，如：and field=‘aaa’
	private String excValue; //被排除的值，当为这个值的时候，不生成where过滤条件
	private boolean leftBracket; //有没有左括号
	private boolean rightBracket;//有没有右括号
	private boolean condition = false; // 该参数是否自动生成where条件附加到sql语句的后面，如果需要则在配置文件中添加属性condition=true即可。
	private String switchValue; //对象的field1字段的值，是否等于switchvalue值。如果相等则使用switchcol的字段名作为参数附加到sql语句中，仅仅用于between and条件中
	private String switchCol;  //可替换col作为查询参数生成where条件，仅仅用于between and条件中
	
	public String getObj() {
		return obj;
	}
	public void setObj(String obj) {
		this.obj = obj;
	}
	public String getCol() {
		return col;
	}
	public void setCol(String col) {
		this.col = col;
	}
	public String getField1() {
		return field1;
	}
	public void setField1(String field1) {
		this.field1 = field1;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getField2() {
		return field2;
	}
	public void setField2(String field2) {
		this.field2 = field2;
	}
	public String getOp() {
		return op;
	}
	public void setOp(String op) {
		this.op = op;
	}
	
	public String getExcValue() {
		return excValue;
	}
	public void setExcValue(String excValue) {
		this.excValue = excValue;
	}
	public String getRelation() {
		return relation;
	}
	public void setRelation(String relation) {
		this.relation = relation;
	}
	public boolean isLeftBracket() {
		return leftBracket;
	}
	public void setLeftBracket(boolean leftBracket) {
		this.leftBracket = leftBracket;
	}
	public boolean isRightBracket() {
		return rightBracket;
	}
	public void setRightBracket(boolean rightBracket) {
		this.rightBracket = rightBracket;
	}
	public boolean isCondition() {
		return condition;
	}
	public void setCondition(boolean condition) {
		this.condition = condition;
	}
	public String getSwitchValue() {
		return switchValue;
	}
	public void setSwitchValue(String switchValue) {
		this.switchValue = switchValue;
	}
	public String getSwitchCol() {
		return switchCol;
	}
	public void setSwitchCol(String switchCol) {
		this.switchCol = switchCol;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Parameter [obj=");
		builder.append(obj);
		builder.append("\r\n, col=");
		builder.append(col);
		builder.append("\r\n, type=");
		builder.append(type);
		builder.append("\r\n, field1=");
		builder.append(field1);
		builder.append("\r\n, field2=");
		builder.append(field2);
		builder.append("\r\n, op=");
		builder.append(op);
		builder.append("\r\n, relation=");
		builder.append(relation);
		builder.append("\r\n, excValue=");
		builder.append(excValue);
		builder.append("\r\n, leftBracket=");
		builder.append(leftBracket);
		builder.append("\r\n, rightBracket=");
		builder.append(rightBracket);
		builder.append("\r\n, condition=");
		builder.append(condition);
		builder.append("\r\n, switchValue=");
		builder.append(switchValue);
		builder.append("\r\n, switchCol=");
		builder.append(switchCol);
		builder.append("]");
		return builder.toString();
	}
	
}
