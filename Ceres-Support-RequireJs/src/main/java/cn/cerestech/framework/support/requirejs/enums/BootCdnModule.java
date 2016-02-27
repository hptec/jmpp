package cn.cerestech.framework.support.requirejs.enums;

public enum BootCdnModule {
	angularJs("angular.js", "1.5.0"), //
	angularUiRouter("angular-ui-router", "1.0.0-alpha0"), //
	angularUiUtil("angular-ui-utils", "0.1.1"), //
	bootstrap("bootstrap", "3.3.6"), //
	jquery("jquery", "2.2.1"), //
	flot("flot", "0.8.3"), //
	jquerySlimScroll("jquery-slimScroll", "1.3.7"), //
	jqueryUi("jqueryui", "1.11.4"), //
	metisMenu("metisMenu", "2.4.0"), //
	sweetAlert("sweetalert", "1.1.3"), //
	iCheck("iCheck", "1.0.2"), //
	peity("peity", "3.2.0"), //
	moment("moment", "2.11.2"), //
	fullcalendar("fullcalendar", "2.6.1"), //
	angularUiCalendar("angular-ui-calendar", "1.0.0"), //
	summernote("summernote", "0.8.1"), //
	angularSummernote("angular-summernote", "0.7.1"), //
	ngGrid("ng-grid", "2.0.11"), //
	angularUiTree("angular-ui-tree", "2.15.0"), //
	bootstrapTour("bootstrap-tour", "0.10.3"), //
	datatables("datatables", "1.10.11"), //
	pdfmake("pdfmake", "0.1.20"), //
	angularXeditable("angular-xeditable", "0.1.10"), //
	bootstrapTouchspin("bootstrap-touchspin", "3.1.1"), //
	blueimpGallery("blueimp-gallery", "2.18.1"), //
	angularUiSortable("angular-ui-sortable", "0.13.4"), //
	chartist("chartist", "0.9.7"), //
	codemirror("codemirror", "5.12.0"), //
	d3("d3", "3.5.16"), //
	c3("c3", "0.4.10"), //
	ladda("ladda", "0.9.8"), //
	requireCss("require-css", "0.1.8"),//

	//
	;
	private String module;
	private String version;

	private BootCdnModule(String module, String version) {
		this.module = module;
		this.version = version;
	}

	public String version() {
		return this.version;
	}

	public String module() {
		return this.module;
	}

	public String getUri(String filename) {
		StringBuffer buffer = new StringBuffer("//cdn.bootcss.com/");
		buffer.append(this.module + '/');
		buffer.append(version + '/');
		buffer.append(filename);
		return buffer.toString();
	}

}
