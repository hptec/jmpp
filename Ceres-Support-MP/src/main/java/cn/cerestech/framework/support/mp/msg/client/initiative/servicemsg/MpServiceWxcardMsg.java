package cn.cerestech.framework.support.mp.msg.client.initiative.servicemsg;

import com.google.common.base.Strings;

import cn.cerestech.framework.core.json.Jsons;
import cn.cerestech.framework.support.mp.enums.SIMsgType;
import cn.cerestech.framework.support.mp.msg.client.initiative.servicemsg.comm.MpServiceMsg;

/**
 *
 * @author <a mailto="royrxc@gmail.com">Roy</a>
 * @since 2016年11月4日
 */
public class MpServiceWxcardMsg extends MpServiceMsg {
	private String card_id;
	private CardExt card_ext;
	public MpServiceWxcardMsg(){
		this.setMsgType(SIMsgType.WXCARD);
	}
	public String getCard_id() {
		return card_id;
	}

	public void setCard_id(String card_id) {
		this.card_id = card_id;
	}

	public CardExt getCard_ext() {
		return card_ext;
	}

	public void setCard_ext(CardExt card_ext) {
		this.card_ext = card_ext;
	}

	@Override
	protected String replyContent() {
		StringBuffer sb  = new StringBuffer();
		sb.append("\"wxcard\":{")
		.append("\"card_id\":\"").append(Strings.nullToEmpty(this.getCard_id())).append("\",")
		.append("\"card_ext\": \"").append(Jsons.from(this.getCard_ext()).toJson()).append("\"}");
		return sb.toString();
	}
	
	public class CardExt{
		private String code;
		private String openid;
		private String timestamp;
		private String signature;
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getOpenid() {
			return openid;
		}
		public void setOpenid(String openid) {
			this.openid = openid;
		}
		public String getTimestamp() {
			return timestamp;
		}
		public void setTimestamp(String timestamp) {
			this.timestamp = timestamp;
		}
		public String getSignature() {
			return signature;
		}
		public void setSignature(String signature) {
			this.signature = signature;
		}
	}

}
