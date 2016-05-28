package cn.cerestech.support.web.console.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import cn.cerestech.framework.core.enums.Gender;
import cn.cerestech.framework.core.enums.YesNo;
import cn.cerestech.framework.core.json.Jsons;
import cn.cerestech.framework.support.localstorage.entity.LocalFile;
import cn.cerestech.framework.support.login.entity.LoginEntity;
import cn.cerestech.framework.support.persistence.IdEntity;
import cn.cerestech.middleware.location.mobile.Mobile;

@Entity
@Table(name = "$$sys_employee")
public class Employee extends IdEntity implements LoginEntity {

	private String name;
	private Mobile phone;
	private String email;
	private String work_num;
	private String loginId;
	private String loginPwd;
	private Long positionId;
	// private Long departmentId;

	private Gender gender = Gender.UNKNOWN;
	private String frozen = YesNo.NO;
	private LocalFile avatar;
	@Column(length = 70)
	private String rememberToken;
	private Date rememberExpired;

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

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getLoginPwd() {
		return loginPwd;
	}

	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}

	public Long getPositionId() {
		return positionId;
	}

	public void setPositionId(Long positionId) {
		this.positionId = positionId;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getFrozen() {
		return frozen;
	}

	public void setFrozen(String frozen) {
		this.frozen = frozen;
	}

	public String getAvatarImage() {
		return avatarImage;
	}

	public void setAvatarImage(String avatarImage) {
		this.avatarImage = avatarImage;
	}

	public String getRememberToken() {
		return rememberToken;
	}

	public void setRememberToken(String rememberToken) {
		this.rememberToken = rememberToken;
	}

	public Date getRememberExpired() {
		return rememberExpired;
	}

	public void setRememberExpired(Date rememberExpired) {
		this.rememberExpired = rememberExpired;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public Employee maskMe() {
		Employee employee = Jsons.from(this).to(Employee.class);
		employee.setLoginPwd(null);
		employee.setRememberExpired(null);
		employee.setRememberToken(null);
		return employee;
	}

}
