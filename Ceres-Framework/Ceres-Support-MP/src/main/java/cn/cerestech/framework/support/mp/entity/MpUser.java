package cn.cerestech.framework.support.mp.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.google.common.base.Strings;

import cn.cerestech.framework.core.enums.Gender;
import cn.cerestech.framework.core.enums.YesNo;
import cn.cerestech.framework.support.persistence.entity.IdEntity;

@SuppressWarnings("serial")
@Entity
@Table(name = "$$sys_mp_user")
public class MpUser extends IdEntity {

	/**
	 * 微信公众号的接入APPID
	 */
	private String appId;

	/**
	 * 是否订阅公众号
	 */
	private YesNo subscribe;

	/**
	 * 微信OpenId
	 */
	private String openId;

	/**
	 * 用户微信昵称
	 */
	@Lob
	private byte[] nickname;

	/**
	 * 文本类型的微信昵称
	 */
	@Transient
	private String nicknameStr;

	/**
	 * 用户性别
	 */
	private Gender gender;

	/**
	 * 城市
	 */
	private String city;

	/**
	 * 国家
	 */
	private String country;

	/**
	 * 省份
	 */
	private String province;

	/**
	 * 语言
	 */
	private String language;

	/**
	 * 头像
	 */
	private String headImgUrl;

	/**
	 * 用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date subscribeTime;

	/**
	 * 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。
	 */
	private String unionId;

	/**
	 * 公众号运营者对粉丝的备注，公众号运营者可在微信公众平台用户管理界面对粉丝添加备注
	 */
	private String remark;

	/**
	 * 用户所在的分组ID
	 */
	private String groupId;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public YesNo getSubscribe() {
		return subscribe;
	}

	public void setSubscribe(YesNo subscribe) {
		this.subscribe = subscribe;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public byte[] getNickname() {
		return nickname;
	}

	public void setNickname(byte[] nickname) {
		this.nickname = nickname;
		if (nickname != null) {
			this.nicknameStr = new String(nickname);
		}
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getHeadImgUrl() {
		return headImgUrl;
	}

	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}

	public Date getSubscribeTime() {
		return subscribeTime;
	}

	public void setSubscribeTime(Date subscribeTime) {
		this.subscribeTime = subscribeTime;
	}

	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getNicknameStr() {
		this.nicknameStr = nickname == null ? null : new String(nickname);
		return this.nicknameStr;
	}

	public void setNicknameStr(String nicknameStr) {
		this.nicknameStr = nicknameStr;
	}
	
	/**
	 * copy 除了id  和 openid 和  appid 之外的不为空的值
	 * @param mpuser
	 */
	public void copyNotNull(MpUser mpuser, boolean all){
		if(mpuser != null){
			if(all){
				if(mpuser.getId() != null){
					this.setId(mpuser.getId());
				}
				if(!Strings.isNullOrEmpty(mpuser.getAppId())){
					this.setAppId(mpuser.getAppId());
				}
				if(!Strings.isNullOrEmpty(mpuser.getOpenId())){
					this.setOpenId(mpuser.getOpenId());
				}
			}
			if(!Strings.isNullOrEmpty(mpuser.getCity())){
				this.setCity(mpuser.getCity());
			}
			if(!Strings.isNullOrEmpty(mpuser.getCountry())){
				this.setCountry(mpuser.getCountry());
			}
			if(!Strings.isNullOrEmpty(mpuser.getGroupId())){
				this.setGroupId(mpuser.getGroupId());
			}
			if(!Strings.isNullOrEmpty(mpuser.getHeadImgUrl())){
				this.setHeadImgUrl(mpuser.getHeadImgUrl());
			}
			if(!Strings.isNullOrEmpty(mpuser.getLanguage())){
				this.setLanguage(mpuser.getLanguage());
			}
			if(!Strings.isNullOrEmpty(mpuser.getNicknameStr())){
				this.setNicknameStr(mpuser.getNicknameStr());
			}
			if(!Strings.isNullOrEmpty(mpuser.getProvince())){
				this.setProvince(mpuser.getProvince());
			}
			if(!Strings.isNullOrEmpty(mpuser.getRemark())){
				this.setRemark(mpuser.getRemark());
			}
			if(!Strings.isNullOrEmpty(mpuser.getUnionId())){
				this.setUnionId(mpuser.getUnionId());
			}
			if(mpuser.getGender() != null){
				this.setGender(mpuser.getGender());
			}
			if(mpuser.getNickname() != null && mpuser.getNickname().length > 0){
				this.setNickname(mpuser.getNickname());
			}
			if(mpuser.getSubscribe() != null){
				this.setSubscribe(mpuser.getSubscribe());
			}
		}
	}
	
	

}
