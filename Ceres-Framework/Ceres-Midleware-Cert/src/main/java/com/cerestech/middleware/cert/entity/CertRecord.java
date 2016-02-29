package com.cerestech.middleware.cert.entity;

import cn.cerestech.framework.persistence.annotation.Column;
import cn.cerestech.framework.persistence.annotation.Table;
import cn.cerestech.framework.persistence.entity.BaseEntity;
import cn.cerestech.framework.persistence.enums.ColumnDataType;

@SuppressWarnings("serial")
@Table(value="$$cert_rec", comment="认证记录")
public class CertRecord extends BaseEntity{
	
	@Column(title="用户id")
	public Long owner_id;
	
	@Column(title="用户类型")
	public String owner_type;
	
	@Column(title="认证状态", length=20, defaultValue="'SUBMIT'")
	public String cert_state;//使用枚举类型 CertState
	
	@Column(title="操作员Id")
	public Long employee_id;//操作员
	
	@Column(title="审核描述", length= 200)
	public String desc;//操作描述
	
	@Column(title="认证类型")
	public String type;//使用枚举类型 CertType
	
	@Column(title="证件图片", comment="以|分割的图片uri", length=200)
	public String images;//证件图片
	
	@Column(title="性别",nullable=false, defaultValue="'U'", type=ColumnDataType.CHAR)
	public String gender;
	
	@Column(title="类别A")
	public String namea;
	
	@Column(title="类别B")
	public String nameb;
	
	@Column(title="类别C")
	public String namec;
	
	@Column(title="类别D")
	public String named;
	
	@Column(title="类别E")
	public String namee;
	
	@Column(title="号码A")
	public String noa;
	
	@Column(title="号码B")
	public String nob;
	
	@Column(title="号码C")
	public String noc;
	
	@Column(title="号码D")
	public String nod;
	
	@Column(title="号码E")
	public String noe;
	
	@Column(title="数字A")
	public Integer numa;

	@Column(title="数字B")
	public Integer numb;

	@Column(title="数字C")
	public Integer numc;

	@Column(title="数字D")
	public Integer numd;

	@Column(title="数字E")
	public Integer nume;
	public Long getOwner_id() {
		return owner_id;
	}

	public void setOwner_id(Long owner_id) {
		this.owner_id = owner_id;
	}

	public String getOwner_type() {
		return owner_type;
	}

	public void setOwner_type(String owner_type) {
		this.owner_type = owner_type;
	}

	public String getCert_state() {
		return cert_state;
	}

	public void setCert_state(String cert_state) {
		this.cert_state = cert_state;
	}

	public Long getEmployee_id() {
		return employee_id;
	}

	public void setEmployee_id(Long employee_id) {
		this.employee_id = employee_id;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

	public String getNamea() {
		return namea;
	}

	public void setNamea(String namea) {
		this.namea = namea;
	}

	public String getNameb() {
		return nameb;
	}

	public void setNameb(String nameb) {
		this.nameb = nameb;
	}

	public String getNamec() {
		return namec;
	}

	public void setNamec(String namec) {
		this.namec = namec;
	}

	public String getNamed() {
		return named;
	}

	public void setNamed(String named) {
		this.named = named;
	}

	public String getNamee() {
		return namee;
	}

	public void setNamee(String namee) {
		this.namee = namee;
	}

	public String getNoa() {
		return noa;
	}

	public void setNoa(String noa) {
		this.noa = noa;
	}

	public String getNob() {
		return nob;
	}

	public void setNob(String nob) {
		this.nob = nob;
	}

	public String getNoc() {
		return noc;
	}

	public void setNoc(String noc) {
		this.noc = noc;
	}

	public String getNod() {
		return nod;
	}

	public void setNod(String nod) {
		this.nod = nod;
	}

	public String getNoe() {
		return noe;
	}

	public void setNoe(String noe) {
		this.noe = noe;
	}

	public Integer getNuma() {
		return numa;
	}

	public void setNuma(Integer numa) {
		this.numa = numa;
	}

	public Integer getNumb() {
		return numb;
	}

	public void setNumb(Integer numb) {
		this.numb = numb;
	}

	public Integer getNumc() {
		return numc;
	}

	public void setNumc(Integer numc) {
		this.numc = numc;
	}

	public Integer getNumd() {
		return numd;
	}

	public void setNumd(Integer numd) {
		this.numd = numd;
	}

	public Integer getNume() {
		return nume;
	}

	public void setNume(Integer nume) {
		this.nume = nume;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
}
