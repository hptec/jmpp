package cn.cerestech.framework.support.mp.entity;

import java.util.Date;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

import cn.cerestech.framework.core.enums.YesNo;
import cn.cerestech.framework.support.mp.enums.MpMediaType;
import cn.cerestech.framework.support.persistence.Owner;
import cn.cerestech.framework.support.persistence.entity.IdEntity;

/**
 * 微信素材表
 * @author <a mailto="royrxc@gmail.com">Roy</a>
 * @since 2016年11月12日
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "$$sys_mp_material")
public class MpMaterial extends IdEntity {
	private String media_id;//微信官方的id
	private String media_url;//获取的url，微信官方的url
	private String key;//区分用途的key
	private String remark;//备注
	private MpMediaType type;//素材类型
	private Date expired_time;//过期时间，如果是永久的则永不过期
	private YesNo temporary;//是否是临时的素材
	@Embedded
	private Owner owner;
	public String getMedia_id() {
		return media_id;
	}
	public void setMedia_id(String media_id) {
		this.media_id = media_id;
	}
	public String getMedia_url() {
		return media_url;
	}
	public void setMedia_url(String media_url) {
		this.media_url = media_url;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public MpMediaType getType() {
		return type;
	}
	public void setType(MpMediaType type) {
		this.type = type;
	}
	public Date getExpired_time() {
		return expired_time;
	}
	public void setExpired_time(Date expired_time) {
		this.expired_time = expired_time;
	}
	public YesNo getTemporary() {
		return temporary;
	}
	public void setTemporary(YesNo temporary) {
		this.temporary = temporary;
	}
	public Owner getOwner() {
		return owner;
	}
	public void setOwner(Owner owner) {
		this.owner = owner;
	}
	
}
