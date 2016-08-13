package cn.cerestech.framework.support.starter.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.cerestech.framework.support.starter.provider.MainPageProvider;

@RestController
@RequestMapping("")
public class DefaultWeb extends WebSupport {

	@Autowired
	MainPageProvider mainPageProvider;

	@RequestMapping("")
	public String index() {
		return mainPageProvider.get();
	}

}
