package cn.cerestech.middleware.balance.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.primitives.Longs;

import cn.cerestech.framework.core.enums.DescribableEnum;
import cn.cerestech.framework.core.enums.EnumCollector;
import cn.cerestech.framework.core.enums.YesNo;
import cn.cerestech.framework.core.service.Result;
import cn.cerestech.framework.platform.service.PlatformService;
import cn.cerestech.framework.support.persistence.Owner;
import cn.cerestech.middleware.balance.criteria.LogCriteria;
import cn.cerestech.middleware.balance.criteria.WithdrawCriteria;
import cn.cerestech.middleware.balance.dao.BalanceAccountDao;
import cn.cerestech.middleware.balance.entity.Balance;
import cn.cerestech.middleware.balance.entity.BalanceAccount;
import cn.cerestech.middleware.balance.entity.BalanceTransaction;
import cn.cerestech.middleware.balance.entity.BankCard;
import cn.cerestech.middleware.balance.entity.Freeze;
import cn.cerestech.middleware.balance.entity.Log;
import cn.cerestech.middleware.balance.entity.Withdraw;
import cn.cerestech.middleware.balance.enums.ActionType;
import cn.cerestech.middleware.balance.enums.BalanceConfigKeys;
import cn.cerestech.middleware.balance.enums.ErrorCodes;
import cn.cerestech.middleware.balance.enums.FreezeState;
import cn.cerestech.middleware.balance.enums.TransactionStatus;
import cn.cerestech.middleware.balance.enums.WithdrawChannel;
import cn.cerestech.middleware.balance.enums.WithdrawState;
import cn.cerestech.middleware.balance.mapper.LogMapper;
import cn.cerestech.middleware.sms.service.SmsMessageService;

@Service
public class BalanceService {

	public static final String CATEGORY_KEYWORD = "BALANCE";

	@Autowired
	BalanceConfigService configService;

	@Autowired
	BalanceAccountDao balanceAccountDao;

	@Autowired
	LogMapper logMapper;
	@Autowired
	SmsMessageService smsService;

	// @Autowired
	// BalanceWithdrawMPMonitor balanceWithdrawMonitor;

	public Balance queryByOwner(Owner owner) {
		if (owner == null) {
			return null;
		}

		Balance balance = new Balance();
		balance.setOwner(owner);
		List<BalanceAccount> accounts = balanceAccountDao.findByOwnerIdAndOwnerType(owner.getId(), owner.getType());
		balance.put(accounts);

		return balance;
	}

	public BalanceAccount queryAccount(Owner owner, DescribableEnum type) {
		return queryAccount(owner, type.key(), type.desc());
	}

	public BalanceAccount queryAccount(Owner owner, String balance_type, String balance_desc) {
		BalanceAccount ba = mysqlService.queryUnique(BalanceAccount.class, "owner_type='" + owner.getOwner_type()
				+ "' AND owner_id=" + owner.getOwner_id() + " AND balance_type='" + balance_type + "'");
		if (ba == null) {
			ba = new BalanceAccount(owner);
			ba.setBalance_type(balance_type);
			ba.setBalance_desc(balance_desc);
			ba.setOwner_id(owner.getOwner_id());
			ba.setOwner_type(owner.getOwner_type());
			ba.setCreate_time(new Date());
			mysqlService.insert(ba);
		}
		return ba;
	}

	public Map<Owner, Balance> queryByOwners(Set<Owner> owners) {
		Map<Owner, Balance> retMap = Maps.newHashMap();

		if (owners != null && !owners.isEmpty()) {
			StringBuffer where = new StringBuffer(" 1=1 AND ( ");
			owners.forEach(o -> {
				where.append(" ( owner_type='" + o.getOwner_type() + "' AND owner_id=" + o.getOwner_id() + ") OR ");
			});
			where.delete(where.length() - 4, where.length());
			where.append(")");
			// 查询的结果合并入Balance
			mysqlService.queryBy(BalanceAccount.class, where.toString()).forEach(ba -> {
				Owner o = new Owner(ba.getOwner_type(), ba.getOwner_id());
				Balance balance = retMap.get(o);
				if (balance == null) {
					balance = new Balance();
					balance.setOwner(o);
					retMap.put(o, balance);
				}
				balance.put(ba);
			});
		}

		return retMap;
	}

	public Map<Owner, Balance> queryByOwners(Owner... owners) {
		if (owners != null && owners.length > 0) {
			Set<Owner> idSet = Sets.newHashSet();
			for (Owner o : owners) {
				idSet.add(o);
			}
			return queryByOwners(idSet);
		}
		return Maps.newHashMap();
	}

	/**
	 * 冻结只扣减余额，不追加日志记录
	 * 
	 * @param owner
	 * @param amount
	 */
	public Long freeze(DescribableEnum balanceType, Owner owner, BigDecimal amount, DescribableEnum reason,
			String remark) {
		return freeze(balanceType.key(), balanceType.desc(), owner, amount, reason.key(), remark);
	}

	@Transactional
	public Long freeze(String typeKey, String typeDesc, Owner owner, BigDecimal amount, String reason, String remark) {
		if (Strings.isNullOrEmpty(typeKey) || owner == null || amount == null) {
			throw new IllegalArgumentException("freeze arguments is null");
		}
		// 扣减余额
		BalanceAccount acc = queryAccount(owner, typeKey, typeDesc);
		acc.setAmount(acc.getAmount().subtract(amount));// 扣减余额
		acc.setAmount_freeze(acc.getAmount_freeze().add(amount));// 增加冻结金额总数

		// 保存账户余额
		if (acc.getId() == null) {
			mysqlService.insert(acc);
		} else {
			mysqlService.update(acc);
		}

		// 添加冻结记录
		Freeze freeze = new Freeze();
		freeze.setAmount(amount);
		freeze.setBalance_type(typeKey);
		freeze.setBalance_desc(typeDesc);
		freeze.setFreeze_time(new Date());
		freeze.setFreeze_remark(remark);
		freeze.setFreeze_reason(reason);
		freeze.setState(FreezeState.FREEZING.key());
		freeze.setOwner_id(owner.getOwner_id());
		freeze.setOwner_type(owner.getOwner_type());
		mysqlService.insert(freeze);
		return freeze.getId();
	}

	public Result<Freeze> commitFreeze(Long freezeId) {
		return commitFreeze(freezeId, null, null);
	}

	public Result<Freeze> rollbackFreeze(Long freezeId) {
		return rollbackFreeze(freezeId, null, null);
	}

	/**
	 * 被解冻的金额不退回账户余额，不生成日志
	 * 
	 * @param freezeId
	 * @param reason
	 * @param remark
	 * @return
	 */
	@Transactional
	public Result<Freeze> commitFreeze(Long freezeId, DescribableEnum reason, String remark) {
		if (freezeId == null) {
			return Result.error(ErrorCodes.FREEZE_NOT_FOUND);
		}
		Freeze freeze = mysqlService.queryById(Freeze.class, freezeId);
		if (freeze == null) {
			return Result.error(ErrorCodes.FREEZE_NOT_FOUND);
		}
		if (FreezeState.FREEZING.key().equals(freeze.getState())) {
			// 被冻结中才能解冻
			freeze.setState(FreezeState.COMMIT.key());
			freeze.setUnfreeze_reason(reason == null ? null : reason.key());
			freeze.setUnfreeze_remark(remark);
			freeze.setUnfreeze_time(new Date());
			mysqlService.update(freeze);
			// 解冻后金额不需要回加入账户余额
			// 但要把当前冻结金额扣减掉
			// 解冻后金额回加入账户余额
			BalanceAccount ba = queryAccount(freeze.getOwner(), freeze.getBalance_type(), freeze.getBalance_desc());
			ba.setAmount_freeze(ba.getAmount_freeze().subtract(freeze.getAmount()));
			mysqlService.update(ba);
		}
		return Result.success().setObject(freeze);
	}

	@Transactional
	public Result<Freeze> rollbackFreeze(Long freezeId, DescribableEnum reason, String remark) {
		if (freezeId == null) {
			return Result.error(ErrorCodes.FREEZE_NOT_FOUND);
		}
		Freeze freeze = mysqlService.queryById(Freeze.class, freezeId);
		if (freeze == null) {
			return Result.error(ErrorCodes.FREEZE_NOT_FOUND);
		}
		if (FreezeState.FREEZING.key().equals(freeze.getState())) {
			// 被冻结中才能退回
			freeze.setState(FreezeState.ROLLBACK.key());
			freeze.setUnfreeze_reason(reason == null ? null : reason.key());
			freeze.setUnfreeze_remark(remark);
			freeze.setUnfreeze_time(new Date());
			mysqlService.update(freeze);
			// 解冻后金额回加入账户余额
			BalanceAccount ba = queryAccount(freeze.getOwner(), freeze.getBalance_type(), freeze.getBalance_desc());
			ba.setAmount(ba.getAmount().add(freeze.getAmount()));// 冻结金额加回去
			ba.setAmount_freeze(ba.getAmount_freeze().subtract(freeze.getAmount()));
			mysqlService.update(ba);
		}
		return Result.success().setObject(freeze);
	}

	public List<BankCard> queryBankCardByOwner(Owner owner) {
		List<BankCard> retList = Lists.newArrayList();
		if (owner != null) {
			retList = mysqlService.queryBy(BankCard.class, "owner_id=" + owner.getOwner_id() + " AND owner_type='"
					+ owner.getOwner_type() + "' AND deleted='" + YesNo.NO.key() + "'");
		}
		return retList;
	}

	public BankCard queryBankCardByOwner(Owner owner, Long cardId) {
		if (owner != null && cardId != null) {
			return mysqlService.queryUnique(BankCard.class, "owner_id=" + owner.getOwner_id() + " AND owner_type='"
					+ owner.getOwner_type() + "' AND deleted='" + YesNo.NO.key() + "' AND id=" + cardId);
		}
		return null;
	}

	public Result<BankCard> removeBankCardByOwner(Owner owner, Long cardId) {
		BankCard card = queryBankCardByOwner(owner, cardId);
		if (card == null) {
			return Result.error(ErrorCodes.BANKCARD_UNAVAILABLE);
		} else {
			card.setDeleted(YesNo.YES.key());
			mysqlService.update(card);
			return Result.success(card);
		}
	}

	/**
	 * 保存或修改卡片
	 * 
	 * @param card
	 * @return
	 */
	public Result<BankCard> persitBankCard(BankCard card) {

		// 做校验
		if (card == null) {
			return Result.success(card);
		}

		if (card.getOwner_id() == null) {
			return Result.error(ErrorCodes.BANKCARD_OWNER_CANNOT_EMPTY);
		}

		if (Strings.isNullOrEmpty(card.getName())) {
			return Result.error(ErrorCodes.BANKCARD_OWNER_CANNOT_EMPTY);
		}

		if (Strings.isNullOrEmpty(card.getCard_no()) || Strings.isNullOrEmpty(card.getCard_no_confirm())
				|| !card.getCard_no().equals(card.getCard_no_confirm())) {
			return Result.error(ErrorCodes.BANKCARD_CODE_NOTEQUAL);
		}

		int cardNoLen = card.getCard_no().length();
		if (cardNoLen != 16 && cardNoLen != 19) {
			return Result.error(ErrorCodes.BANKCARD_CODE_1619_REQUIRED);
		}

		if (Strings.isNullOrEmpty(card.getBank_code()) || Strings.isNullOrEmpty(card.getBank_name())) {
			return Result.error(ErrorCodes.BANKCARD_NAME_CANNOT_EMPTY);
		}
		if (Strings.isNullOrEmpty(card.getProvince()) || Strings.isNullOrEmpty(card.getCity())) {
			return Result.error(ErrorCodes.BANKCARD_PROVINCE_CITY_REQUIRED);
		}

		if (Strings.isNullOrEmpty(card.getBank_branch())) {
			return Result.error(ErrorCodes.BANKCARD_BANKBRANCH_REQUIRED);
		}

		if (card.getId() == null) {
			mysqlService.insert(card);
		} else {
			mysqlService.update(card);
		}
		return Result.success().setObject(card);
	}

	public Result<BankCard> removeBankCard(Long cardId) {
		BankCard card = mysqlService.queryById(BankCard.class, cardId);
		if (card != null) {
			card.setDeleted(YesNo.YES.key());
			mysqlService.update(card);
		}
		return Result.success().setObject(card);
	}

	public Result<BankCard> freezeBankCard(Long cardId) {
		BankCard card = mysqlService.queryById(BankCard.class, cardId);
		if (card != null && YesNo.YES.key().equals(card.getFrozen())) {
			card.setFrozen(YesNo.YES.key());
			mysqlService.update(card);
		}
		return Result.success().setObject(card);
	}

	public Result<BankCard> unfreezeBankCard(Long cardId) {
		BankCard card = mysqlService.queryById(BankCard.class, cardId);
		if (card == null) {
			return Result.error(ErrorCodes.BANKCARD_UNAVAILABLE);
		} else {
			card.setFrozen(YesNo.NO.key());
			mysqlService.update(card);
		}
		return Result.success().setObject(card);
	}

	public Result<Withdraw> approvedWithdraw(Long id, Long byWhom, String reason) {
		Withdraw wd = mysqlService.queryById(Withdraw.class, id);
		if (wd == null) {
			return Result.error(ErrorCodes.WITHDRAW_NOT_FOUND);
		}

		wd.setAudit_time(new Date());
		wd.setState(WithdrawState.APPROVED.key());
		wd.setAudit_by(byWhom);
		wd.setAudit_reason(reason);

		mysqlService.update(wd);

		if (wd.channelEqual(WithdrawChannel.BANK)) {
			// 如果是银行卡提现就自动通过
			finishWithdraw(id, byWhom);
		}

		// if (wd.channelEqual(WithdrawChannel.MP)) {
		// balanceWithdrawMonitor.add(wd);// 放入监控池
		// }
		return Result.success(wd);
	}

	/**
	 * 提现处理完成
	 * 
	 * @param id
	 * @param byWhom
	 * @return
	 */
	@Transactional
	public Result<Withdraw> finishWithdraw(Long id, Long byWhom) {
		Withdraw wd = mysqlService.queryById(Withdraw.class, id);
		if (wd == null) {
			return Result.error(ErrorCodes.WITHDRAW_NOT_FOUND);
		}

		wd.setFinish_time(new Date());
		wd.setFinish_by(byWhom);
		wd.setState(WithdrawState.FINISH.key());
		mysqlService.update(wd);
		commitTransaction(wd.getTrans_id());// 确认交易完成

		if (configService.smsOpen()) {// 打开则通知
			try {
				if (PhoneUtils.isPhone(wd.getPhone())) {
					smsService.send(wd.getPhone(), "withdraw_ok", wd.getAmount().doubleValue() + "");
				}
			} catch (Exception e) {
			}
		}

		return Result.success(wd);
	}

	@Transactional
	public Result<Withdraw> cancelWithdraw(Owner owner, Long wdId) {
		if (owner == null || wdId == null) {
			return Result.error(ErrorCodes.WITHDRAW_NOT_FOUND);
		}
		Withdraw wd = mysqlService.queryById(Withdraw.class, wdId);
		if (wd == null) {
			return Result.error(ErrorCodes.WITHDRAW_NOT_FOUND);
		}
		// 必须是属于自己的
		if (!wd.getOwner().equals(owner)) {
			return Result.error(ErrorCodes.WITHDRAW_NOT_FOUND);
		}

		wd.setCancel_time(new Date());
		wd.setCancel_by(owner.getOwner_id());
		wd.setState(WithdrawState.CANCEL.key());
		mysqlService.update(wd);
		rollbackTransaction(wd.getTrans_id());// 取消交易
		return Result.success(wd);
	}

	@Transactional
	public Result<Withdraw> denyWithdraw(Long id, Long byWhom, String reason) {
		Withdraw wd = mysqlService.queryById(Withdraw.class, id);
		if (wd == null) {
			return Result.error(ErrorCodes.WITHDRAW_NOT_FOUND);
		}

		wd.setAudit_time(new Date());
		wd.setState(WithdrawState.DENY.key());
		wd.setAudit_by(byWhom);
		wd.setAudit_reason(reason);
		mysqlService.update(wd);

		rollbackTransaction(wd.getTrans_id());// 回滚交易

		return Result.success(wd);
	}

	/**
	 * 这个方法要校验余额是否足够
	 * 
	 */
	@Transactional
	public Result<Withdraw> withdraw(WithdrawChannel channel, Owner owner, BigDecimal withdrawAmount, String username,
			String phone, String resId, DescribableEnum reson, String remark) {

		if (!configService.isWithdrawWayOfBankCardSupport()) {
			// 不支持银行卡提现
			return Result.error(ErrorCodes.WITHDRAW_WAY_NOT_SUPPORTED);
		}

		DescribableEnum balanceType = configService.withdrawAccountType();
		if (balanceType == null) {
			return Result.error(ErrorCodes.WITHDRAW_BALANCE_TYPE_REQUIRED);
		}

		if (resId == null) {
			return Result.error(ErrorCodes.BANKCARD_UNAVAILABLE);
		}

		BankCard card = null;
		switch (channel) {
		case BANK:
			// 必须是自己的卡片
			card = mysqlService.queryUnique(BankCard.class, "owner_id=" + owner.getOwner_id() + " AND owner_type='"
					+ owner.getOwner_type() + "' AND id=" + Longs.tryParse(resId));

			// BankCard card = mysqlService.queryById(BankCard.class,
			// Longs.tryParse(resId));
			if (card == null || YesNo.YES.key().equals(card.getDeleted()) || YesNo.YES.key().equals(card.getFrozen())) {
				return Result.error(ErrorCodes.BANKCARD_UNAVAILABLE);
			}
			break;
		// case MP:
		// TODO 微信的openid校验
		}

		// 校验提现规则
		BigDecimal singleLimit = configService.query(BalanceConfigKeys.BALANCE_WITHDRAW_SINGLE_LIMIT).decimalValue();
		if (singleLimit.compareTo(BigDecimal.ZERO) == 0) {
			// 不允许提现
			return Result.error(ErrorCodes.WITHDRAW_NOT_ALLOWED);
		} else if (singleLimit.compareTo(new BigDecimal(-1)) == 0) {
			// -1不限制提现金额
		} else if (withdrawAmount.compareTo(singleLimit) > 0) {
			// 提现金额超过限制
			return Result.error(ErrorCodes.WITHDRAW_AMOUNT_OVER_LIMIT);
		}

		YesNo repeat = EnumCollector.forClass(YesNo.class)
				.keyOf(configService.query(BalanceConfigKeys.BALANCE_WITHDRAW_REPEAT_SUBMIT).stringValue());
		repeat = repeat == null ? YesNo.NO : repeat;
		if (YesNo.NO.equals(repeat)) {
			// 不允许重复提交
			Withdraw wd = mysqlService.queryUnique(Withdraw.class, " owner_type='" + owner.getOwner_type()
					+ "' AND owner_id=" + owner.getOwner_id() + " AND `state` = '" + WithdrawState.SUBMIT.key() + "'");
			if (wd != null) {
				// 有重复提交
				return Result.error(ErrorCodes.WITHDRAW_ONLY_ONE_LIMIT);
			}
		}

		BigDecimal feePerSubmit = configService.query(BalanceConfigKeys.BALANCE_WITHDRAW_FEE_PER_SUBMIT).decimalValue()
				.setScale(2);
		BigDecimal feeRatio = configService.query(BalanceConfigKeys.BALANCE_WITHDRAW_FEE_RATIO).decimalValue();
		BigDecimal fee = BigDecimal.ZERO;
		BigDecimal fact = withdrawAmount.add(BigDecimal.ZERO).setScale(2, RoundingMode.DOWN);

		// 实得金额必须大于0
		if (feePerSubmit.compareTo(BigDecimal.ZERO) > 0) {
			fee = fee.add(feePerSubmit);
			fact = fact.subtract(feePerSubmit);
		}
		if (feeRatio.compareTo(BigDecimal.ZERO) > 0) {
			BigDecimal feeOfRatio = withdrawAmount.multiply(feeRatio).setScale(2, RoundingMode.DOWN);
			fee = fee.add(feeOfRatio);
			fact = fact.subtract(feeOfRatio);
		}
		if (fact.compareTo(BigDecimal.ZERO) <= 0) {
			// 没有实得金额
			return Result.error(ErrorCodes.WITHDRAW_NO_FACT_AMOUNT_LEFT);
		}

		// 冻结金额
		BalanceTransaction trans = BalanceTransaction.init(null, null, null);
		trans.substract(balanceType, owner, withdrawAmount, BigDecimal.ZERO, null, reson, remark);// 提现余额不能为负
		Result<BalanceTransaction> transResult = insertTransaction(trans);
		if (!transResult.isSuccess()) {
			return Result.error(transResult);
		}

		// 冻结金额成功，计算费率

		Withdraw wd = new Withdraw();
		wd.setOwner(owner);
		wd.setAmount(withdrawAmount);
		wd.setFee(fee);
		wd.setFact_amount(fact);
		wd.setTrans_id(trans.getId());
		wd.setSubmit_remark(remark);
		wd.setSubmit_time(new Date());
		wd.setUsername(username);
		wd.setPhone(phone);
		wd.setChannel(channel.key());
		wd.setChannel_to_resource_id(resId);
		wd.setChannel_to_resource_desc(card.getBank_name() + "  尾号:"
				+ card.getCard_no().substring(card.getCard_no().length() - 4, card.getCard_no().length()));
		mysqlService.insert(wd);

		if (configService.smsOpen() && PhoneUtils.isPhone(wd.getPhone())) {
			try {
				// 您的账户提交提现申请{0}元，本人操作请忽略，如非本人操作，请立即修改账户安全密码
				smsService.send(wd.getPhone(), "withdraw_submit", wd.getAmount().doubleValue() + "");
			} catch (Exception e) {
			}
		}

		return Result.success(wd);
	}

	/**
	 * 系统配置是否支持微信方式的提现
	 * 
	 * @return
	 */
	public Boolean isWithdrawWayOfMPSupport() {
		return configService.isWithdrawWayOfMPSupport();
	}

	public BalanceTransaction queryTransactionById(Long id) {
		BalanceTransaction trans = mysqlService.queryById(BalanceTransaction.class, id);
		if (trans != null) {
			List<Log> logs = mysqlService.queryBy(Log.class, " transaction_id=" + trans.getId());
			trans.setLogs(logs);
		}
		return trans;
	}

	/**
	 * 交易只能新增 不能修改
	 * 
	 * @param trans
	 * @return
	 */
	@Transactional
	public Result<BalanceTransaction> insertTransaction(BalanceTransaction trans) {

		// 校验
		if (trans == null || trans.getLogs() == null || trans.getLogs().isEmpty()) {
			return Result.error(ErrorCodes.TRANS_IS_EMPTY).setObject(trans);
		}

		// 出账余额必须足够
		List<Log> subLogs = trans.getLogs().stream().filter(log -> log.actionEquals(ActionType.OUT))
				.collect(Collectors.toList());
		for (Log log : subLogs) {
			if (log.getMinimalAmount() != null) {
				Owner o = log.getOwner();
				Balance balance = queryByOwner(o);
				BigDecimal amountHas = balance.getAmount(log.getBalance_type());
				if (amountHas.subtract(log.getAmount()).compareTo(log.getMinimalAmount()) < 0) {
					// 如果账户余额减去扣减金额小于最小限制金额，则报错余额不足
					return Result.error(ErrorCodes.AMOUNT_NOT_ENOUGH);
				}
			}
		}

		// 新增
		trans.setCreate_time(new Date());
		trans.setStatus(TransactionStatus.FINISH.key());
		mysqlService.insert(trans);
		trans.getLogs().forEach(log -> {
			// 如果是出账必须冻结余额
			if (log.actionEquals(ActionType.OUT)) {
				Long freezeHandle = freeze(log.getBalance_type(), log.getBalance_desc(), log.getOwner(),
						log.getAmount(), log.getReason(), log.getRemark());
				log.setFreeze_id(freezeHandle);
			}
			log.setTransaction_id(trans.getId());
			log.setLog_time(new Date());
			mysqlService.insert(log);
		});

		return Result.success(trans);
	}

	/**
	 * 应用交易及所有相关账户变动(实时)
	 * 
	 * @param id
	 * @return
	 * @return
	 */
	@Transactional
	public Result<BalanceTransaction> commitInstantTransaction(BalanceTransaction trans) {
		// 校验
		if (trans == null || trans.getLogs() == null || trans.getLogs().isEmpty()) {
			return Result.error(ErrorCodes.TRANS_IS_EMPTY).setObject(trans);
		}

		// 出账余额必须足够
		List<Log> subLogs = trans.getLogs().stream().filter(log -> log.actionEquals(ActionType.OUT))
				.collect(Collectors.toList());
		for (Log log : subLogs) {
			if (log.getMinimalAmount() != null) {
				Owner o = log.getOwner();
				Balance balance = queryByOwner(o);
				BigDecimal amountHas = balance.getAmount(log.getBalance_type());
				if (amountHas.subtract(log.getAmount()).compareTo(log.getMinimalAmount()) < 0) {
					// 如果账户余额减去扣减金额小于最小限制金额，则报错余额不足
					return Result.error(ErrorCodes.AMOUNT_NOT_ENOUGH);
				}
			}
		}

		// 设置当前trans的状态
		trans.setStatus(TransactionStatus.FINISH.key());
		trans.setFinish_time(new Date());
		mysqlService.insert(trans);

		// 所有添加的记录都添加到账户中
		List<Log> addLogs = trans.getLogs().stream().filter(log -> log.actionEquals(ActionType.IN))
				.collect(Collectors.toList());
		List<Log> substractLogs = trans.getLogs().stream().filter(log -> log.actionEquals(ActionType.OUT))
				.collect(Collectors.toList());
		addLogs.forEach(log -> {
			BalanceAccount ba = queryAccount(log.getOwner(), log.getBalance_type(), log.getBalance_desc());
			// 入账操作
			BigDecimal open = ba.getAmount();
			BigDecimal end = open.add(log.getAmount());

			ba.setAmount(end);
			ba.setAmount_history_all(ba.getAmount_history_all().add(log.getAmount()));// 添加总计
			if (ba.getId() == null) {
				mysqlService.insert(ba);
			} else {
				mysqlService.update(ba);
			}

			// 记录期初 期末
			log.setAmount_openning(open);
			log.setAmount_closing(end);

			// 修改当前日志
			log.setAccount_time(new Date());
			log.setLog_time(new Date());
			log.setStatus(TransactionStatus.FINISH.key());
			log.setTransaction_id(trans.getId());
			if (log.getId() == null) {
				mysqlService.insert(log);
			} else {
				mysqlService.update(log);
			}

		});

		substractLogs.forEach(log -> {
			// 出账操作
			BalanceAccount ba = queryAccount(log.getOwner(), log.getBalance_type(), log.getBalance_desc());
			BigDecimal open = ba.getAmount();
			BigDecimal end = open.subtract(log.getAmount());

			ba.setAmount(end);
			if (ba.getId() == null) {
				mysqlService.insert(ba);
			} else {
				mysqlService.update(ba);
			}

			// 记录期初 期末
			log.setAmount_openning(open);
			log.setAmount_closing(end);

			// 修改当前日志
			log.setAccount_time(new Date());
			log.setLog_time(new Date());
			log.setStatus(TransactionStatus.FINISH.key());
			log.setTransaction_id(trans.getId());
			if (log.getId() == null) {
				mysqlService.insert(log);
			} else {
				mysqlService.update(log);
			}
		});

		return Result.success(trans);
	}

	/**
	 * 应用交易及所有相关账户变动
	 * 
	 * @param id
	 * @return
	 */
	@Transactional
	public void commitTransaction(Long id) {
		BalanceTransaction trans = queryTransactionById(id);
		if (trans != null) {

			// 所有添加的记录都添加到账户中
			trans.getLogs().stream().forEach(log -> {
				BalanceAccount ba = queryAccount(log.getOwner(), log.getBalance_type(), log.getBalance_desc());
				if (log.actionEquals(ActionType.IN)) {
					// 入账操作
					BigDecimal open = ba.getAmount();
					BigDecimal end = open.add(log.getAmount());

					ba.setAmount(end);
					ba.setAmount_history_all(ba.getAmount_history_all().add(log.getAmount()));// 添加总计
					if (ba.getId() == null) {
						mysqlService.insert(ba);
					} else {
						mysqlService.update(ba);
					}

					// 记录期初 期末
					log.setAmount_openning(open);
					log.setAmount_closing(end);

				} else {
					// 出账操作
					// 确认冻结记录完成
					if (log.getFreeze_id() != null) {
						commitFreeze(log.getFreeze_id());
					}
					// 记录期初 期末
					log.setAmount_openning(ba.getAmount().add(log.getAmount()));
					log.setAmount_closing(ba.getAmount());
				}
				// 修改当前日志
				log.setAccount_time(new Date());
				log.setStatus(TransactionStatus.FINISH.key());
				mysqlService.update(log);
			});

			// 设置当前trans的状态
			trans.setStatus(TransactionStatus.FINISH.key());
			trans.setFinish_time(new Date());
			mysqlService.update(trans);
		}
	}

	/**
	 * 提交应用所有该交易的所有日志
	 * 
	 * @param id
	 * @return
	 */
	@Transactional
	public void rollbackTransaction(Long id) {
		BalanceTransaction trans = queryTransactionById(id);
		if (trans != null) {
			// 所有添加的记录都添加到账户中
			trans.getLogs().stream().forEach(log -> {
				if (log.actionEquals(ActionType.IN)) {
					// 入账操作什么都不做，
				} else {
					// 如果冻结就解冻
					if (log.getFreeze_id() != null) {
						rollbackFreeze(log.getFreeze_id());
					}
				}

				log.setAccount_time(new Date());
				log.setStatus(TransactionStatus.ROLLBACK.key());
			});

			// 设置当前trans的状态
			trans.setStatus(TransactionStatus.FINISH.key());
			trans.setFinish_time(new Date());
			mysqlService.update(trans);
		}
	}

	public <T extends Withdraw> WithdrawCriteria<T> searchWithdraw(Class<T> clazz, WithdrawCriteria<T> criteria) {
		StringBuffer where = new StringBuffer(" 1=1");

		if (criteria.getOwner_id() != null) {
			where.append(" AND owner_type='" + criteria.getOwner_type() + "' AND owner_id=" + criteria.getOwner_id());
		}

		if (!Strings.isNullOrEmpty(criteria.getState())) {
			where.append(" AND `state`='" + criteria.getState() + "' ");
		}

		if (!Strings.isNullOrEmpty(criteria.getChannel())) {
			where.append(" AND `channel`='" + criteria.getChannel() + "' ");
		}

		if (!Strings.isNullOrEmpty(criteria.getKeyword())) {
			String keyword = criteria.getKeyword().replace("'", "");
			where.append(" AND ( `username` LIKE '%" + keyword + "%' OR phone LIKE '%" + keyword
					+ "%' OR submit_remark LIKE '%" + keyword + "%' ) ");
		}

		if (criteria.getDateFrom() != null) {
			where.append(
					" AND submit_time >= timestamp('" + FORMAT_DATE.format(criteria.getDateFrom()) + " 00:00:00')");
		}
		if (criteria.getDateTo() != null) {
			where.append(" AND submit_time <= timestamp('" + FORMAT_DATE.format(criteria.getDateTo()) + " 23:59:59')");
		}

		// 查询数量
		int count = mysqlService.count(Withdraw.class, where.toString());
		criteria.setCount(count);

		if (!Strings.isNullOrEmpty(criteria.getOrderBy())) {
			where.append(" ORDER BY " + criteria.getOrderBy());
		}
		if (criteria.getPage() != null) {
			where.append(" LIMIT " + criteria.getOffset() + "," + criteria.getPageSize());
		}

		List<T> searchList = mysqlService.queryBy(clazz, where.toString());
		criteria.addData(searchList);
		return criteria;
	}

	/**
	 * 如果不指定owner就是所有人<br/>
	 * 
	 * @param balanceType
	 * @param owner
	 * @return
	 */
	public List<Log> queryLogByOwner(DescribableEnum balanceType, Owner owner) {
		if (balanceType == null) {
			throw new IllegalArgumentException("Balance Type is null");
		}

		StringBuffer where = new StringBuffer();
		where.append(" balance_type = '" + balanceType.key() + "'");
		if (owner != null) {
			where.append(" AND owner_type='" + owner.getOwner_type() + "' AND owner_id=" + owner.getOwner_id());
		}

		where.append(" ORDER BY log_time DESC");
		return mysqlService.queryBy(Log.class, where.toString());
	}

	public LogCriteria<Log> searchLog(LogCriteria<Log> ceriter) {

		if (ceriter.getCount() == null || ceriter.getCount().compareTo(new Integer(0)) <= 0) {
			Long count = logMapper.searchLogCount(ceriter);
			ceriter.setCount(count.intValue());
		}

		List<Log> list = logMapper.searchLog(ceriter);
		ceriter.addData(list);
		return ceriter;
	}

	public List<Withdraw> queryWithdrawByOwner(Owner owner) {
		if (owner == null) {
			throw new IllegalArgumentException("owner is null");
		}

		return mysqlService.queryBy(Withdraw.class, " owner_type='" + owner.getOwner_type() + "' AND owner_id="
				+ owner.getOwner_id() + " ORDER BY submit_time DESC");
	}
}
