package cn.cerestech.middleware.balance.web;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.primitives.Longs;

import cn.cerestech.console.annotation.LoginRequired;
import cn.cerestech.console.annotation.RequiredResource;
import cn.cerestech.console.web.WebConsoleSupport;
import cn.cerestech.framework.core.KV;
import cn.cerestech.framework.core.enums.DescribableEnum;
import cn.cerestech.framework.core.enums.EnumCollector;
import cn.cerestech.framework.core.enums.YesNo;
import cn.cerestech.framework.core.json.Jsons;
import cn.cerestech.framework.core.search.Sort;
import cn.cerestech.framework.core.service.Result;
import cn.cerestech.framework.persistence.entity.BaseEntity;
import cn.cerestech.middleware.balance.criteria.WithdrawCriteria;
import cn.cerestech.middleware.balance.entity.BankCard;
import cn.cerestech.middleware.balance.entity.Withdraw;
import cn.cerestech.middleware.balance.enums.BalanceConfigKeys;
import cn.cerestech.middleware.balance.enums.ErrorCodes;
import cn.cerestech.middleware.balance.service.BalanceConfigService;
import cn.cerestech.middleware.balance.service.BalanceService;

@RequestMapping("$$ceres_sys/console/balance")
@Controller
@RequiredResource(js = { "console/base/res/cp?id=console/res/middleware/balance/ceres-middleware-balance.js" })
public class BalanceConsoleCtrl extends WebConsoleSupport {

	@Autowired
	BalanceService balanceService;

	@Autowired
	BalanceConfigService configService;

	@RequestMapping("config/get")
	@LoginRequired
	public @ResponseBody void configGet() {
		KV retMap = KV.on();

		KV valueMap = KV.on();
		valueMap.put("account", configService.query(BalanceConfigKeys.BALANCE_WITHDRAW_ACCOUNT).stringValue());
		valueMap.put("singlelimit",
				configService.query(BalanceConfigKeys.BALANCE_WITHDRAW_SINGLE_LIMIT).decimalValue());
		valueMap.put("repeatsubmit",
				configService.query(BalanceConfigKeys.BALANCE_WITHDRAW_REPEAT_SUBMIT).stringValue());
		valueMap.put("feepersubmit",
				configService.query(BalanceConfigKeys.BALANCE_WITHDRAW_FEE_PER_SUBMIT).decimalValue());
		valueMap.put("feeratio", configService.withdrawFeeRatio());

		retMap.put("values", valueMap);
		retMap.put("accounts", EnumCollector.queryEnumByCategory(BalanceService.CATEGORY_KEYWORD).toList());
		retMap.put("yes_no", EnumCollector.forClass(YesNo.class).toList());

		zipOut(retMap.toJson());
	}

	@RequestMapping("config/update")
	@LoginRequired
	public @ResponseBody void configUpdate(@RequestParam(value = "account", required = false) String account,
			@RequestParam(value = "singlelimit", required = false) BigDecimal singlelimit,
			@RequestParam(value = "repeatsubmit", required = false) String repeatsubmit,
			@RequestParam(value = "feepersubmit", required = false) BigDecimal feepersubmit,
			@RequestParam(value = "feeratio", required = false) BigDecimal feeratio) {

		if (Strings.isNullOrEmpty(account)) {
			zipOut(Result.error(ErrorCodes.WITHDRAW_BALANCE_TYPE_REQUIRED).toJson());
			return;
		}

		// 保存提现账户
		DescribableEnum accountEnum = EnumCollector.queryEnumByCategory(BalanceService.CATEGORY_KEYWORD).keyOf(account);
		if (accountEnum == null) {
			zipOut(Result.error(ErrorCodes.WITHDRAW_BALANCE_TYPE_REQUIRED).toJson());
			return;
		} else {
			configService.update(BalanceConfigKeys.BALANCE_WITHDRAW_ACCOUNT, accountEnum.key());
		}

		// 单笔限额
		singlelimit = singlelimit == null ? BigDecimal.ZERO : singlelimit;
		configService.update(BalanceConfigKeys.BALANCE_WITHDRAW_SINGLE_LIMIT, singlelimit.toString());

		// 重复提交
		DescribableEnum repeat = YesNo.NO;
		if (!Strings.isNullOrEmpty(repeatsubmit)) {
			repeat = EnumCollector.forClass(YesNo.class).keyOf(repeatsubmit);
			if (repeat == null) {
				repeat = YesNo.NO;
			}
		}
		configService.update(BalanceConfigKeys.BALANCE_WITHDRAW_REPEAT_SUBMIT, repeat.key());

		// 每笔手续费
		feepersubmit = feepersubmit == null ? BigDecimal.ZERO : feepersubmit;
		configService.update(BalanceConfigKeys.BALANCE_WITHDRAW_FEE_PER_SUBMIT, feepersubmit.toString());

		// 提现费率
		feeratio = feeratio == null ? BigDecimal.ZERO : feeratio;
		configService.withdrawFeeRatio(feeratio);

		zipOut(Result.success().toJson());
	}

	@RequestMapping("search")
	public @ResponseBody void search(@RequestParam(value = "status", required = false) String statusStr,
			@RequestParam(value = "channel", required = false) String channelStr,
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "fromto", required = false) String fromto) {
		WithdrawCriteria<Withdraw> criteria = new WithdrawCriteria<Withdraw>();
		if (!Strings.isNullOrEmpty(statusStr)) {
			criteria.setState(statusStr);
		}

		if (!Strings.isNullOrEmpty(channelStr)) {
			criteria.setChannel(channelStr);
		}

		Date fromDate = null;
		Date toDate = null;
		if (!Strings.isNullOrEmpty(fromto)) {
			List<String> dateStrList = Splitter.on(" - ").trimResults().splitToList(fromto);
			if (dateStrList.size() > 0) {
				try {
					fromDate = FORMAT_DATE.parse(dateStrList.get(0));
					criteria.setDateFrom(fromDate);
				} catch (ParseException e) {
				}
			}
			if (dateStrList.size() > 1) {
				try {
					toDate = FORMAT_DATE.parse(dateStrList.get(1));
					criteria.setDateTo(toDate);
				} catch (ParseException e) {
				}
			}
		}

		criteria.setKeyword(keyword);
		criteria.setMaxRecords(BaseEntity.MAX_QUERY_RECOREDS);
		criteria.orderBy("submit_time", Sort.DESC);
		criteria = balanceService.searchWithdraw(Withdraw.class, criteria);

		zipOut(Jsons.toJson(criteria.getData()));
	}

	@RequestMapping("withdraw/approve")
	@LoginRequired
	public @ResponseBody void withdrawApprove(@RequestParam(value = "id") Long id) {
		Result<Withdraw> result = balanceService.approvedWithdraw(id, userId(), null);
		zipOut(result.toJson());
	}

	@RequestMapping("withdraw/deny")
	@LoginRequired
	public @ResponseBody void withdrawDeny(@RequestParam(value = "id") Long id) {
		Result<Withdraw> result = balanceService.denyWithdraw(id, userId(), null);
		zipOut(result.toJson());
	}

	@RequestMapping("withdraw/get")
	@LoginRequired
	public @ResponseBody void withdrawGet(@RequestParam(value = "id") Long id) {
		KV kv = KV.on();
		Withdraw wd = mysqlService.queryById(Withdraw.class, id);
		if (wd != null) {
			kv.put("wd", wd);
			BankCard card = mysqlService.queryById(BankCard.class, Longs.tryParse(wd.getChannel_to_resource_id()));
			kv.put("card", card);
		}

		zipOut(kv);
	}
}