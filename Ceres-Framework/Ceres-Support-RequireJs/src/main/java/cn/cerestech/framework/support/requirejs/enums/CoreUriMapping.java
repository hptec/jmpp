package cn.cerestech.framework.support.requirejs.enums;

public enum CoreUriMapping implements RequireJsUriMapping {
	angular(BootCdnModule.angularJs.getUri("angular.min")), //
	angularAnimate(BootCdnModule.angularJs.getUri("angular-animate.min")), //
	angularAsyncLoader(ClasspathModule.angularAsyncLoader.getUri("angular-async-loader.min")), //
	angularJsLocal(BootCdnModule.angularJs.getUri("i18n/angular-locale_zh-cn")), //
	angularSanitize(BootCdnModule.angularJs.getUri("angular-sanitize.min")), //
	angularSummernote(BootCdnModule.angularSummernote.getUri("angular-summernote.min")), //
	angularUiCalendar(BootCdnModule.angularUiCalendar.getUri("calendar.min")), //
	angularUiRouter(BootCdnModule.angularUiRouter.getUri("angular-ui-router.min")), //
	angularUiSortable(BootCdnModule.angularUiSortable.getUri("sortable.min")), //
	angularUiTree(BootCdnModule.angularUiTree.getUri("angular-ui-tree.min")), //
	angularUiUtil(BootCdnModule.angularUiRouter.getUri("angular-ui-utils.min")), //
	angularXeditable(BootCdnModule.angularXeditable.getUri("js/xeditable.min")), //
	app(ClasspathModule.applicationCore.getUri("app")), //
	blueimpGallery(BootCdnModule.blueimpGallery.getUri("js/jquery.blueimp-gallery.min")), //
	bootstrap(BootCdnModule.bootstrap.getUri("/js/bootstrap.min")), //
	bootstrapTouchspin(BootCdnModule.bootstrapTouchspin.getUri("jquery.bootstrap-touchspin.min")), //
	bootstrapTour(BootCdnModule.bootstrapTour.getUri("js/bootstrap-tour.min")), //
	c3(BootCdnModule.c3.getUri("c3.min")), //
	chartist(BootCdnModule.chartist.getUri("chartist.min")), //
	codeMirror(BootCdnModule.codemirror.getUri("codemirror.min")), //
	codeMirrorModeJavascript(BootCdnModule.codemirror.getUri("mode/javascript/javascript.min")), //
	d3(BootCdnModule.d3.getUri("d3.min")), //
	datatables(BootCdnModule.datatables.getUri("js/jquery.dataTables.min")), //
	datatablesBootstrap(BootCdnModule.datatables.getUri("js/dataTables.bootstrap.min")), //
	fullcalendar(BootCdnModule.fullcalendar.getUri("fullcalendar.min")), //
	jquery(BootCdnModule.jquery.getUri("jquery.min")), //
	jqueryFlot(BootCdnModule.flot.getUri("jquery.flot.min")), //
	jqueryFlotPie(BootCdnModule.flot.getUri("jquery.flot.pie.min")), //
	jqueryFlotResize(BootCdnModule.flot.getUri("jquery.flot.resize.min")), //
	jquerySlimScroll(BootCdnModule.jquerySlimScroll.getUri("jquery.slimscroll.min")), //
	jqueryUi(BootCdnModule.jqueryUi.getUri("jquery-ui.min")), //
	ladda(BootCdnModule.ladda.getUri("ladda.min")), //
	laddaJquery(BootCdnModule.ladda.getUri("ladda.jquery.min")), //
	laddaSpin(BootCdnModule.ladda.getUri("spin.min")), //
	metisMenu(BootCdnModule.metisMenu.getUri("metisMenu.min")), //
	moment(BootCdnModule.moment.getUri("moment.min")), //
	ngGrid(BootCdnModule.ngGrid.getUri("ng-grid-2.0.14.min")), //
	pdfmake(BootCdnModule.pdfmake.getUri("pdfmake.min")), //
	pdfmakeVsfFont(BootCdnModule.pdfmake.getUri("vfs_fonts")), //
	peity(BootCdnModule.peity.getUri("jquery.peity.min")), //
	requireCss(BootCdnModule.requireCss.getUri("css.min")), //
	summernote(BootCdnModule.summernote.getUri("summernote.min")), //
	sweetAlert(BootCdnModule.sweetAlert.getUri("sweetalert.min")),//
	//
	;

	private String uri;

	private CoreUriMapping(String uri) {
		this.uri = uri;
	}

	@Override
	public String uri() {
		return uri;
	}

}
