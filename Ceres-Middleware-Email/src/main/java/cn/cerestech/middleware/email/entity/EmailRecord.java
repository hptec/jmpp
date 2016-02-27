package cn.cerestech.middleware.email.entity;

import java.util.Date;

import cn.cerestech.framework.persistence.annotation.Column;
import cn.cerestech.framework.persistence.annotation.Table;
import cn.cerestech.framework.persistence.entity.BaseEntity;
import cn.cerestech.framework.persistence.enums.ColumnDataType;
import cn.cerestech.middleware.email.enums.EmailType;
/**
 * 邮件记录表
 * @author <a href="mailto:royrxc@gmail.com">Roy</a>
 * @since  2015年11月22日下午8:39:12
 */
@SuppressWarnings("serial")
@Table(value="$$email_record", comment="邮件记录")
public class EmailRecord extends BaseEntity{
	
	@Column(title="邮件类型")
	public String type = EmailType.PLAN.key();//枚举类型 EmailType
	
	@Column(title="发送时间")
	public Date send_time;//发送时间
	
	@Column(title="接收邮件账号")
	public String send_to;
	
	@Column(title="邮件主题")
	public String subject;
	
	@Column(title="邮件内容" , type=ColumnDataType.TEXT)
	public String content;
	
	@Column(title="附件文件", length=500)
	public String attach_files;
	
	@Column(title="用途key标注")
	public String key;//枚举类型
	
	@Column(title="模板id")
	public Long template_id;
	
	@Column(title="邮件发送状态")
	public String state;
	
	@Column(title="处理描述")
	public String desc;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getSend_time() {
		return send_time;
	}

	public void setSend_time(Date send_time) {
		this.send_time = send_time;
	}

	public String getSend_to() {
		return send_to;
	}

	public void setSend_to(String send_to) {
		this.send_to = send_to;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAttach_files() {
		return attach_files;
	}

	public void setAttach_files(String attach_files) {
		this.attach_files = attach_files;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Long getTemplate_id() {
		return template_id;
	}

	public void setTemplate_id(Long template_id) {
		this.template_id = template_id;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
