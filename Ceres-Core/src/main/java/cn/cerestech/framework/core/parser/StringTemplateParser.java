package cn.cerestech.framework.core.parser;

public class StringTemplateParser extends TemplateParser implements Parser {
	private String tpl = "";

	@Override
	String getTemplate() {
		return tpl;
	}

	public StringTemplateParser(String tpl) {
		super();
		this.tpl = tpl;
	}

	public StringTemplateParser() {
		super();
	}

}
