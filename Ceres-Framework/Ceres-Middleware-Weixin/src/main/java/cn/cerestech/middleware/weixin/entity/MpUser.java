package cn.cerestech.middleware.weixin.entity;

import java.util.Date;

import cn.cerestech.framework.persistence.annotation.Column;
import cn.cerestech.framework.persistence.annotation.Table;
import cn.cerestech.framework.persistence.entity.BaseEntity;
import cn.cerestech.framework.persistence.enums.ColumnDataType;

/**
 * 微信用户基本信息
 * 
 * @author bird
 *
 */
@SuppressWarnings("serial")
@Table("$$mpuser")
public class MpUser extends BaseEntity {
	@Column(title = "微信openid", length = 45)
	public String openid;// 用户的标识，对当前公众号唯一
	@Column(type = ColumnDataType.VARBINARY, title = "微信昵称", length = 200)
	public String nickname;// 用户的昵称
	@Column(title = "性别", comment = "用户的性别，值为1时是男性，值为2时是女性，值为0时是未知", precision = 1)
	public int sex;// 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
	@Column(title = "省", length = 45)
	public String province;// 用户所在省份
	@Column(title = "城市", length = 45)
	public String city;// 用户所在城市
	@Column(title = "国家", length = 45)
	public String country;// 用户所在国家
	@Column(title = "头像url", length = 200)
	public String headimgurl;// 用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。
	@Column(type = ColumnDataType.CHAR, title = "是否已经关注公众号", comment = "用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。")
	public String subscribe;// 用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。
	@Column(title = "语言", length = 20)
	public String language;// 用户的语言，简体中文为zh_CN
	@Column(title = "关注时间", precision = 16, comment = "用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间")
	public Long subscribe_time;// 用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间
	@Column(title = "开放平台的唯一编码", length = 64)
	public String unionid;// 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。详见：获取用户个人信息（UnionID机制）
	@Column(title = "备注信息", length = 500, comment = "公众号运营者对粉丝的备注，公众号运营者可在微信公众平台用户管理界面对粉丝添加备注")
	public String remark;// 公众号运营者对粉丝的备注，公众号运营者可在微信公众平台用户管理界面对粉丝添加备注
	@Column(title = "分组id", length = 20)
	public String groupid;// 用户所在的分组ID

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
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

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public String getSubscribe() {
		return subscribe;
	}

	public void setSubscribe(String subscribe) {
		this.subscribe = subscribe;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Long getSubscribe_time() {
		return subscribe_time;
	}

	public void setSubscribe_time(Long subscribe_time) {
		this.subscribe_time = subscribe_time;
	}

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getGroupid() {
		return groupid;
	}

	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}
}
