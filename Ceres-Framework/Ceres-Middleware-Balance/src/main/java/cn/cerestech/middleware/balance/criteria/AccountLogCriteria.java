package cn.cerestech.middleware.balance.criteria;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;

import com.google.common.collect.Lists;

import cn.cerestech.framework.core.enums.DescribableEnum;
import cn.cerestech.framework.support.persistence.search.AbstractCriteria;
import cn.cerestech.middleware.balance.entity.Account;
import cn.cerestech.middleware.balance.entity.AccountLog;

public class AccountLogCriteria extends AbstractCriteria<AccountLog> {

	private Account account;
	
	// 入账原因
	private DescribableEnum reason;
		
	@Override
	public Sort sort() {
		Sort createTime = new Sort(Direction.DESC, "createTime");
		Sort id = new Sort(Direction.DESC, "id");
		return createTime.and(id);
	}


	@Override
	public Specification<AccountLog> specification() {
		return new Specification<AccountLog>() {
			@SuppressWarnings("unchecked")
			@Override
			public Predicate toPredicate(Root<AccountLog> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				return builder.and(toArray(root,query,builder,equalAccount()));
			}
		};
	}
	
	protected Specification<AccountLog> equalAccount(){
		return new Specification<AccountLog>() {
			@Override
			public Predicate toPredicate(Root<AccountLog> root,CriteriaQuery<?> query, CriteriaBuilder builder) {
				List<Predicate> sets = Lists.newArrayList();
				if(account != null){
					sets.add(builder.equal(root.get("account").get("id"),account.getId()));
				}
				if (sets.isEmpty()) {
					return null;
				} else {
					return builder.and(toArray(sets));
				}
			}
		};
	}
	
	protected Specification<AccountLog> equalReason(){
		return new Specification<AccountLog>() {
			@Override
			public Predicate toPredicate(Root<AccountLog> root,CriteriaQuery<?> query, CriteriaBuilder builder) {
				if(reason != null){
					return builder.equal(root.get("reason"),reason.key());
				}
				return null;
			}
		};
	}


	public Account getAccount() {
		return account;
	}


	public void setAccount(Account account) {
		this.account = account;
	}


	public DescribableEnum getReason() {
		return reason;
	}


	public void setReason(DescribableEnum reason) {
		this.reason = reason;
	}
}
