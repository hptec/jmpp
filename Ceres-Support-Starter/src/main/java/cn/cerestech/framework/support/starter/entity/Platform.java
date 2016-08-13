package cn.cerestech.framework.support.starter.entity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

import cn.cerestech.framework.core.enums.PlatformCategory;
import cn.cerestech.framework.support.persistence.entity.IdEntity;

@SuppressWarnings("serial")
@Entity
@Table(name = "$$sys_platform")
public class Platform extends IdEntity {

	private PlatformCategory category;

	@Column(name = "[key]")
	private String key;

	@Embedded
	private Developer developer;
	@Embedded
	private Frontend frontend;
	@Embedded
	private CustomerService customerService;
	@Embedded
	private Seo seo;

	public Developer getDeveloper() {
		if (developer == null) {
			developer = new Developer();
		}
		return developer;
	}

	public Platform setDeveloper(Developer developer) {
		this.developer = developer;
		return this;
	}

	public Frontend getFrontend() {
		if (frontend == null) {
			frontend = new Frontend();
		}
		return frontend;
	}

	public Platform setFrontend(Frontend frontend) {
		this.frontend = frontend;
		return this;
	}

	public CustomerService getCustomerService() {
		if (customerService == null) {
			customerService = new CustomerService();
		}
		return customerService;
	}

	public Platform setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
		return this;
	}

	public Seo getSeo() {
		if (seo == null) {
			seo = new Seo();
		}
		return seo;
	}

	public Platform setSeo(Seo seo) {
		this.seo = seo;
		return this;
	}

	public PlatformCategory getCategory() {
		return category;
	}

	public void setCategory(PlatformCategory category) {
		this.category = category;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
