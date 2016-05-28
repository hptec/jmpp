package cn.cerestech.framework.platform.entity;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

import cn.cerestech.framework.support.persistence.IdEntity;

@Entity
@Table(name = "$$sys_platform")
public class Platform extends IdEntity {

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

}
