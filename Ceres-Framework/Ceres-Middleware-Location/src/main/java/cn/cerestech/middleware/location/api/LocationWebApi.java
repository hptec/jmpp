package cn.cerestech.middleware.location.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.cerestech.framework.support.web.web.WebApi;
import cn.cerestech.middleware.location.Divisions;

@RestController
@RequestMapping("/api/location")
public class LocationWebApi extends WebApi {

	@RequestMapping("divisions")
	public void divisions() {
		zipOut(Divisions.getRootJson());
	}

}
