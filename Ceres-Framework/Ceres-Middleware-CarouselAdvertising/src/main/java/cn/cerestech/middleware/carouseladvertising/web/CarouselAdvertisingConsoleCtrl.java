package cn.cerestech.middleware.carouseladvertising.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Strings;

import cn.cerestech.console.annotation.LoginRequired;
import cn.cerestech.console.annotation.RequiredResource;
import cn.cerestech.console.web.WebConsoleSupport;
import cn.cerestech.framework.core.KV;
import cn.cerestech.framework.core.enums.EnumCollector;
import cn.cerestech.framework.core.enums.YesNo;
import cn.cerestech.framework.core.json.Jsons;
import cn.cerestech.framework.core.service.Result;
import cn.cerestech.middleware.carouseladvertising.entity.CarouselAdvertising;
import cn.cerestech.middleware.carouseladvertising.service.CarouselAdvertisingService;

@RequestMapping("$$ceres_sys/console/carouseladvertising")
@Controller
@RequiredResource(js = {
		"console/base/res/cp?id=console/res/middleware/carouseladvertising/ceres-middleware-carouseladvertising.js" })
public class CarouselAdvertisingConsoleCtrl extends WebConsoleSupport {

	@Autowired
	CarouselAdvertisingService carouselAdvtisingService;

	@RequestMapping("search")
	@LoginRequired
	public @ResponseBody void search() {
		List<KV> retList = EnumCollector
				.forClass(EnumCollector.queryClassByCategory(CarouselAdvertisingService.CATEGORY_KEYWORD)).toList();
		zipOut(Jsons.toJson(retList));
	}

	@RequestMapping("get")
	@LoginRequired
	public @ResponseBody void get(@RequestParam(value = "namespace") String namespace) {
		KV kvMap = KV.on();

		List<CarouselAdvertising> retList = carouselAdvtisingService.search(CarouselAdvertising.class, namespace);
		kvMap.put("data", retList);
		kvMap.put("namespace", namespace);
		zipOut(kvMap.toJson());
	}

	@RequestMapping("getpic")
	@LoginRequired
	public @ResponseBody void getPic(@RequestParam(value = "id") Long id) {
		KV kvMap = KV.on();

		kvMap.put("currentPic", mysqlService.queryById(CarouselAdvertising.class, id));
		zipOut(kvMap);
	}

	@RequestMapping("pic/add")
	@LoginRequired
	public @ResponseBody void picAdd(@RequestParam(value = "namespace") String namespace,
			@RequestParam(value = "uri") String uri) {
		zipOut(carouselAdvtisingService.addPic(namespace, uri).toJson());
	}

	@RequestMapping("pic/move")
	@LoginRequired
	public @ResponseBody void picMove(@RequestParam(value = "namespace") String namespace,
			@RequestParam(value = "picid") Long picid, @RequestParam(value = "direction") String direction) {
		Result<CarouselAdvertising> result = null;
		if ("LEFT".equals(direction)) {
			result = carouselAdvtisingService.moveLeft(namespace, picid);
		} else if ("RIGHT".equals(direction)) {
			result = carouselAdvtisingService.moveRight(namespace, picid);
		}
		if (result.isSuccess()) {
			KV retMap = KV.on();
			retMap.put("data", carouselAdvtisingService.search(CarouselAdvertising.class, namespace));
			Result<KV> ret = Result.success(retMap);
			zipOut(ret.toJson());
		} else {
			zipOut(result.toJson());
		}
	}

	@RequestMapping("pic/remove")
	@LoginRequired
	public @ResponseBody void picRemove(@RequestParam(value = "namespace") String namespace,
			@RequestParam(value = "picid") Long picid) {
		zipOut(carouselAdvtisingService.remove(namespace, picid).toJson());
	}

	@RequestMapping("pic/confirmconfig")
	@LoginRequired
	public @ResponseBody void picConfirmConfig(@RequestParam(value = "id") Long id,
			@RequestParam(value = "href") String href, @RequestParam(value = "newwin") String newwin) {

		CarouselAdvertising pic = mysqlService.queryById(CarouselAdvertising.class, id);
		if (pic != null) {
			pic.setHref(href);
			pic.setNewwin(Strings.isNullOrEmpty(newwin) ? YesNo.NO.key() : newwin);
			mysqlService.update(pic);
			Result<CarouselAdvertising> result = Result.success(pic);
			zipOut(result);
		} else {
			Result<CarouselAdvertising> result = Result.success();
			zipOut(result);
		}

	}

}
