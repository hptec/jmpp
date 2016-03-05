package cn.cerestech.middleware.balance.queryforms;

import cn.cerestech.console.queryform.QueryForm;

public class BankCardQueryForm extends QueryForm {

	public BankCardQueryForm() {
		setTitle("银行卡");

		addColumnText("name", "户名");
		addColumnText("card_no", "卡号");
		addColumnText("bank_name", "银行名称");
		addColumnText("province", "开户省");
		addColumnText("city", "开户市");
		addColumnText("bank_branch", "开户支行");
		addColumnDate("create_time", "创建时间");

		addTermText("keyword", "户名、卡号、开户行", null);
	}

}
