package cn.cerestech.framework.pay.gopay.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import com.google.common.base.Strings;

import cn.cerestech.framework.core.Encrypts;
import cn.cerestech.framework.pay.gopay.bean.PayEntity;

public class GopaySignUtil {
	
	public static String signPay(PayEntity entity, String VerficationCode){
		StringBuffer sb = new StringBuffer();
		
		sb.append("version").append("=[").append(Strings.nullToEmpty(entity.getVersion())).append("]");//version=[]
		sb.append("tranCode").append("=[").append(Strings.nullToEmpty(entity.getTranCode())).append("]");//tranCode=[]
		sb.append("merchantID").append("=[").append(Strings.nullToEmpty(entity.getMerchantID())).append("]");//merchantID=[]
		sb.append("merOrderNum").append("=[").append(Strings.nullToEmpty(entity.getMerOrderNum())).append("]");//merOrderNum=[]
		sb.append("tranAmt").append("=[").append(Strings.nullToEmpty(entity.getTranAmt())).append("]");//tranAmt=[]
		sb.append("feeAmt").append("=[").append(Strings.nullToEmpty(entity.getFeeAmt())).append("]");//feeAmt=[]
		sb.append("tranDateTime").append("=[").append(Strings.nullToEmpty(entity.getTranDateTime())).append("]");//tranDateTime=[]
		sb.append("frontMerUrl").append("=[").append(Strings.nullToEmpty(entity.getFrontMerUrl())).append("]");//frontMerUrl=[]
		sb.append("backgroundMerUrl").append("=[").append(Strings.nullToEmpty(entity.getBackgroundMerUrl())).append("]");//backgroundMerUrl=[]
		
		sb.append("orderId").append("=[").append("").append("]");//必须为空//orderId=[]
		sb.append("gopayOutOrderId").append("=[").append("").append("]");//必须为空//gopayOutOrderId=[]
		sb.append("tranIP").append("=[").append(entity.getTranIP()).append("]");//tranIP=[]
		sb.append("respCode").append("=[").append("").append("]");//必须为空//respCode=[]
		sb.append("gopayServerTime").append("=[").append(Strings.nullToEmpty(entity.getGopayServerTime())).append("]");//gopayServerTime=[]
		sb.append("VerficationCode").append("=[").append(VerficationCode).append("]");//VerficationCode=[]
		System.out.println("签名字符串："+sb.toString());
		String pwd = Encrypts.md5(sb.toString());
		System.out.println("签名结果："+ pwd);
		return pwd;
	}
	
	public static String signNotify(Notify notify, String VerficationCode){
		StringBuffer sb = new StringBuffer();
		sb.append("version").append("=[").append(Strings.nullToEmpty(notify.getVersion())).append("]");
		sb.append("tranCode").append("=[").append(Strings.nullToEmpty(notify.getTranCode())).append("]");
		sb.append("merchantID").append("=[").append(Strings.nullToEmpty(notify.getMerchantID())).append("]");
		sb.append("merOrderNum").append("=[").append(Strings.nullToEmpty(notify.getMerOrderNum())).append("]");
		sb.append("tranAmt").append("=[").append(Strings.nullToEmpty(notify.getTranAmt())).append("]");
		sb.append("feeAmt").append("=[").append(Strings.nullToEmpty(notify.getFeeAmt())).append("]");
		sb.append("tranDateTime").append("=[").append(Strings.nullToEmpty(notify.getTranDateTime())).append("]");
		sb.append("frontMerUrl").append("=[").append(Strings.nullToEmpty(notify.getFrontMerUrl())).append("]");//可以空
		sb.append("backgroundMerUrl").append("=[").append(Strings.nullToEmpty(notify.getBackgroundMerUrl())).append("]");
		sb.append("orderId").append("=[").append(Strings.nullToEmpty(notify.getOrderId())).append("]");//orderId=[]
		sb.append("gopayOutOrderId").append("=[").append(Strings.nullToEmpty(notify.getGopayOutOrderId())).append("]");//必须不为空
		sb.append("tranIP").append("=[").append(notify.getTranIP()).append("]");
		sb.append("respCode").append("=[").append(Strings.nullToEmpty(notify.getRespCode())).append("]");
		sb.append("gopayServerTime").append("=[").append("").append("]");
		sb.append("VerficationCode").append("=[").append(VerficationCode).append("]");
		
		return Encrypts.md5(sb.toString());
	}
	
	public static boolean checkNotify(Notify notify , String VerficationCode){
		String signn = notify.getSignValue();
		String signlocal = signNotify(notify, VerficationCode);
		return Strings.nullToEmpty(signn).equals(signlocal);
	}
	
	
	public static class Notify{
		private String version;
		private String charset;
		private String language;
		private String signType;
		private String tranCode;
		private String merchantID;
		private String merOrderNum;
		private String tranAmt;
		private String feeAmt;
		private String frontMerUrl;
		private String backgroundMerUrl;
		private String tranDateTime;
		private String tranIP;
		private String respCode;
		private String msgExt;
		private String orderId;
		private String gopayOutOrderId;
		private String bankCode;
		private String tranFinishTime;
		private String merRemark1;
		private String merRemark2;
		private String signValue;
		
		public static Notify from(HttpServletRequest request){
			Notify notify = new Notify();
			Arrays.asList(Notify.class.getDeclaredFields()).forEach(filed->{
				PropertyDescriptor des;
				try {
					des = new PropertyDescriptor(filed.getName(), Notify.class);
					Method set = des.getWriteMethod();
					set.invoke(notify,request.getParameter(filed.getName()));
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			return notify;
		}
		
		public String getVersion() {
			return version;
		}
		public void setVersion(String version) {
			this.version = version;
		}
		public String getTranCode() {
			return tranCode;
		}
		public void setTranCode(String tranCode) {
			this.tranCode = tranCode;
		}
		public String getMerchantID() {
			return merchantID;
		}
		public void setMerchantID(String merchantID) {
			this.merchantID = merchantID;
		}
		public String getMerOrderNum() {
			return merOrderNum;
		}
		public void setMerOrderNum(String merOrderNum) {
			this.merOrderNum = merOrderNum;
		}
		public String getTranAmt() {
			return tranAmt;
		}
		public void setTranAmt(String tranAmt) {
			this.tranAmt = tranAmt;
		}
		public String getFeeAmt() {
			return feeAmt;
		}
		public void setFeeAmt(String feeAmt) {
			this.feeAmt = feeAmt;
		}
		public String getTranDateTime() {
			return tranDateTime;
		}
		public void setTranDateTime(String tranDateTime) {
			this.tranDateTime = tranDateTime;
		}
		public String getFrontMerUrl() {
			return frontMerUrl;
		}
		public void setFrontMerUrl(String frontMerUrl) {
			this.frontMerUrl = frontMerUrl;
		}
		public String getBackgroundMerUrl() {
			return backgroundMerUrl;
		}
		public void setBackgroundMerUrl(String backgroundMerUrl) {
			this.backgroundMerUrl = backgroundMerUrl;
		}
		public String getOrderId() {
			return orderId;
		}
		public void setOrderId(String orderId) {
			this.orderId = orderId;
		}
		public String getGopayOutOrderId() {
			return gopayOutOrderId;
		}
		public void setGopayOutOrderId(String gopayOutOrderId) {
			this.gopayOutOrderId = gopayOutOrderId;
		}
		public String getTranIP() {
			return tranIP;
		}
		public void setTranIP(String tranIP) {
			this.tranIP = tranIP;
		}
		public String getRespCode() {
			return respCode;
		}
		public void setRespCode(String respCode) {
			this.respCode = respCode;
		}

		public String getCharset() {
			return charset;
		}

		public void setCharset(String charset) {
			this.charset = charset;
		}

		public String getLanguage() {
			return language;
		}

		public void setLanguage(String language) {
			this.language = language;
		}

		public String getSignType() {
			return signType;
		}

		public void setSignType(String signType) {
			this.signType = signType;
		}

		public String getMsgExt() {
			return msgExt;
		}

		public void setMsgExt(String msgExt) {
			this.msgExt = msgExt;
		}

		public String getBankCode() {
			return bankCode;
		}

		public void setBankCode(String bankCode) {
			this.bankCode = bankCode;
		}

		public String getTranFinishTime() {
			return tranFinishTime;
		}

		public void setTranFinishTime(String tranFinishTime) {
			this.tranFinishTime = tranFinishTime;
		}

		public String getMerRemark1() {
			return merRemark1;
		}

		public void setMerRemark1(String merRemark1) {
			this.merRemark1 = merRemark1;
		}

		public String getMerRemark2() {
			return merRemark2;
		}

		public void setMerRemark2(String merRemark2) {
			this.merRemark2 = merRemark2;
		}

		public String getSignValue() {
			return signValue;
		}

		public void setSignValue(String signValue) {
			this.signValue = signValue;
		}
	}
	
//	public static class Pay{
//		private String version = "2.1";
//	    private String tranCode = "8888";
//	    private String merchantID;
//	    private String merOrderNum;
//	    private String tranAmt;
//	    private String feeAmt="";
//	    private String tranDateTime;
//	    private String frontMerUrl;
//	    private String backgroundMerUrl;
//	    private String orderId;
//	    private String gopayOutOrderId;
//	    private String tranIP;
//	    private String respCode;
//	    private String gopayServerTime;
//	}
}
