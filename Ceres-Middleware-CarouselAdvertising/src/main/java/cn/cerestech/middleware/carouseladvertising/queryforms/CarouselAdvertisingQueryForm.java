package cn.cerestech.middleware.carouseladvertising.queryforms;

import cn.cerestech.console.queryform.QueryForm;

public class CarouselAdvertisingQueryForm extends QueryForm {

	public CarouselAdvertisingQueryForm() {
		setTitle("广告轮播");

		addColumnText("desc", "名称");

		addRowButton("fa fa-edit", null, null, "#/carouseladvertising/update?id={{row.key}}");

	}

}
