package cn.cerestech.framework.platform.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@SuppressWarnings("serial")
@Embeddable
public class CustomerService implements Serializable {
	private String tel;

	private String qq;

	public String getTel() {
		return tel;
	}

	public CustomerService setTel(String tel) {
		this.tel = tel;
		return this;
	}

	public String getQq() {
		return qq;
	}

	public CustomerService setQq(String qq) {
		this.qq = qq;
		return this;
	}

}