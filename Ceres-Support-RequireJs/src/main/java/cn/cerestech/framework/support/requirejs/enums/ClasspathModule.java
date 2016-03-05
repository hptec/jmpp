package cn.cerestech.framework.support.requirejs.enums;

public enum ClasspathModule {
	angularbootstrap("support/web/bower_components/angular-bootstrap"), //
	angularAsyncLoader("support/requirejs/js/angular-async-loader"), //
	applicationCore("support/requirejs/js")//
	;

	private String module_path;

	private ClasspathModule(String module_path) {
		this.module_path = module_path;
	}

	public String getUri(String filename) {
		StringBuffer buffer = new StringBuffer("/api/classpath/query/");
		buffer.append(this.module_path + '/');
		buffer.append(filename);
		return buffer.toString();
	}

}
