package cn.cerestech.middleware.geetest.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.cerestech.framework.core.service.Result;
import cn.cerestech.framework.support.starter.web.WebSupport;
import cn.cerestech.middleware.geetest.sdk.GeetestLib;

@RestController
@RequestMapping("/api/geetest")
@ConfigurationProperties(prefix = "ceres.geetest")
public class GeetestApi extends WebSupport {

	private Logger log = LogManager.getLogger();

	public static final String GEETEST_SERVER_STATUS = "GEETEST_SERVER_STATUS";

	private String apiId;
	private String apiKey;

	private GeetestLib gtSdk ;

	@RequestMapping("/init")
	protected void init(@RequestParam("transId") String transId) {

		String resStr = "{}";
		// 将服务器状态设置到session中
		putSession(GEETEST_SERVER_STATUS, getLib().preProcess(transId));

		resStr = getLib().getResponseStr();
		zipOut(resStr);
	}

	@RequestMapping("/verify")
	protected void verify(@RequestParam("challenge") String challenge, @RequestParam("validate") String validate,
			@RequestParam("seccode") String seccode, @RequestParam("transId") String transId) {

		// 从session中获取gt-server状态
		Integer gt_server_status_code = (Integer) getSession(GEETEST_SERVER_STATUS);

		// 从session中获取userid

		int gtResult = 0;

		if (gt_server_status_code == 1) {
			// gt-server正常，向gt-server进行二次验证

			gtResult = getLib().enhencedValidateRequest(challenge, validate, seccode, transId);
			log.trace("极验 验证结果: " + gtResult);
		} else {
			// gt-server非正常情况下，进行failback模式验证

			log.trace("failback:use your own server captcha validate");
			gtResult = getLib().failbackValidateRequest(challenge, validate, seccode);
			log.trace("极验 验证结果: " + gtResult);
		}

		if (gtResult == 1) {
			zipOut(Result.success());
		} else {
			zipOut(Result.error());
		}

	}

	public String getApiId() {
		return apiId;
	}

	public void setApiId(String apiId) {
		this.apiId = apiId;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public GeetestLib getLib() {
		if (gtSdk == null) {
			gtSdk = new GeetestLib(apiId, apiKey);
		}
		return gtSdk;
	}
}