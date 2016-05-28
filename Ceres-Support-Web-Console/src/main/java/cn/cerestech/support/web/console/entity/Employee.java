package cn.cerestech.support.web.console.entity;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

import cn.cerestech.framework.core.enums.Gender;
import cn.cerestech.framework.core.json.Jsons;
import cn.cerestech.framework.support.login.entity.Login;
import cn.cerestech.framework.support.login.entity.Loginable;
import cn.cerestech.framework.support.persistence.IdEntity;
import cn.cerestech.framework.support.persistence.entity.Confidential;
import cn.cerestech.middleware.location.mobile.Mobile;

@Entity
@Table(name = "$$sys_employee")
public class Employee extends IdEntity implements Confidential<Employee>, Loginable {

	private String name;
	private Mobile phone;
	private String email;
	private String work_num;
	@Embedded
	private Login login;
	private Long positionId;
	// private Long departmentId;

	private Gender gender = Gender.UNKNOWN;

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

	// public LocalFile getAvatar() {
	// return avatar;
	// }
	//
	// public void setAvatar(LocalFile avatar) {
	// this.avatar = avatar;
	// }

	@Override
	public Employee safty() {
		Employee employee = Jsons.from(this).to(Employee.class);
		employee.setLogin(null);
		return employee;
	}

}
