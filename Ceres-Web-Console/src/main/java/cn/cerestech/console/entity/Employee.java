package cn.cerestech.console.entity;

import java.util.Date;

import cn.cerestech.framework.core.Encrypts;
import cn.cerestech.framework.core.enums.Gender;
import cn.cerestech.framework.core.enums.YesNo;
import cn.cerestech.framework.persistence.annotation.Column;
import cn.cerestech.framework.persistence.annotation.Index;
import cn.cerestech.framework.persistence.annotation.Table;
import cn.cerestech.framework.persistence.entity.BaseEntity;
import cn.cerestech.framework.persistence.enums.ColumnDataType;

@SuppressWarnings("serial")
@Table(value = "$$employee")
public class Employee extends BaseEntity {
	static {
		addInit(new Employee(1L, "admin", "111111", "超级管理员"));
	}

	public Employee(Long id, String usr, String pwd, String nickname) {
		super();
		this.id = id;
		this.usr = usr;
		this.pwd = Encrypts.md5(pwd);
		this.nickname = nickname;
		this.create_time = new Date();
	}

	public Employee() {
		super();
	}

	@Index
	@Column(comment = "登录用户名", title = "登录名")
	public String usr;
	@Column(comment = "登录密码", title = "密码")
	public String pwd;
	@Column(title = "工号")
	public String work_num;
	@Column(title = "部门")
	public Long department_id;
	@Index
	@Column(title = "联系电话")
	public String phone;
	@Index
	@Column(title = "电子邮箱")
	public String email;
	@Index
	@Column(title = "昵称")
	public String nickname;
	@Index
	@Column(title = "性别")
	public String gender = Gender.UNKNOWN.key();
	@Column(comment = "用户是否冻结")
	public String frozen = YesNo.NO.key();
	@Column(title = "岗位ID")
	public Long position_id;
	@Column(length = 500, title = "头像")
	public String avatar_img;
	@Column(length = 70, title = "登录令牌")
	public String remember_token;
	@Column(title = "令牌过期时间")
	public Date remember_expired;
	@Column(title = "最后登录时间")
	public Date last_login_time;
	@Column(type = ColumnDataType.TEXT, title = "备注信息")
	public String remark;

	public String getUsr() {
		return usr;
	}

	public void setUsr(String usr) {
		this.usr = usr;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getAvatar_img() {
		return avatar_img;
	}

	public void setAvatar_img(String avatar_img) {
		this.avatar_img = avatar_img;
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

	public String getRemember_token() {
		return remember_token;
	}

	public void setRemember_token(String remember_token) {
		this.remember_token = remember_token;
	}

	public Date getRemember_expired() {
		return remember_expired;
	}

	public void setRemember_expired(Date remember_expired) {
		this.remember_expired = remember_expired;
	}

	public Long getPosition_id() {
		return position_id;
	}

	public void setPosition_id(Long position_id) {
		this.position_id = position_id;
	}

	public Date getLast_login_time() {
		return last_login_time;
	}

	public void setLast_login_time(Date last_login_time) {
		this.last_login_time = last_login_time;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getWork_num() {
		return work_num;
	}

	public void setWork_num(String work_num) {
		this.work_num = work_num;
	}

	public Long getDepartment_id() {
		return department_id;
	}

	public void setDepartment_id(Long department_id) {
		this.department_id = department_id;
	}

}
