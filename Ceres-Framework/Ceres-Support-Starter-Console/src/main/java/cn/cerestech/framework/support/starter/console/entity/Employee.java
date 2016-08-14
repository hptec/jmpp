package cn.cerestech.framework.support.starter.console.entity;

import java.util.Date;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.cerestech.framework.core.enums.Gender;
import cn.cerestech.framework.core.enums.YesNo;
import cn.cerestech.framework.core.json.JsonIgnore;
import cn.cerestech.framework.core.json.Jsons;
import cn.cerestech.framework.support.login.entity.Login;
import cn.cerestech.framework.support.login.entity.Loginable;
import cn.cerestech.framework.support.persistence.entity.Confidential;
import cn.cerestech.framework.support.persistence.entity.IdEntity;
import cn.cerestech.framework.support.persistence.entity.SoftDelete;
import cn.cerestech.middleware.location.mobile.Mobile;

@SuppressWarnings("serial")
@Entity
@Table(name = "$$sys_employee")
public class Employee extends IdEntity implements Confidential<Employee>, Loginable, SoftDelete {

	private String platform;

	private String name;
	private Mobile phone;
	private String email;
	private String work_num;
	@Embedded
	private Login login;
	private Long positionId;
	// private Long departmentId;

	private Gender gender = Gender.UNKNOWN;

	private Date deleteTime;

	// 上级
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id", referencedColumnName = "id")
	@JsonIgnore
	private Employee parent;

	private YesNo isSuperAdmin;

	// private LocalFile avatar;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWork_num() {
		return work_num;
	}

	public void setWork_num(String work_num) {
		this.work_num = work_num;
	}

	public Long getPositionId() {
		return positionId;
	}

	public void setPositionId(Long positionId) {
		this.positionId = positionId;
	}

	public Mobile getPhone() {
		return phone;
	}

	public void setPhone(Mobile phone) {
		this.phone = phone;
	}

	@Override
	public Login getLogin() {
		return login;
	}

	public void setLogin(Login login) {
		this.login = login;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	@Override
	public Employee safty() {
		Employee employee = Jsons.from(this).to(Employee.class);
		if (employee.getLogin() != null) {
			Login loginTmp = new Login();
			loginTmp.setId(employee.getLogin().getId());
			loginTmp.setFrozen(employee.getLogin().getFrozen());
			employee.setLogin(loginTmp);
		}

		return employee;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public YesNo getIsSuperAdmin() {
		return isSuperAdmin;
	}

	public void setIsSuperAdmin(YesNo isSuperAdmin) {
		this.isSuperAdmin = isSuperAdmin;
	}

	public Employee getParent() {
		return parent;
	}

	public void setParent(Employee parent) {
		this.parent = parent;
	}

	public Date getDeleteTime() {
		return deleteTime;
	}

	public void setDeleteTime(Date deleteTime) {
		this.deleteTime = deleteTime;
	}

}
