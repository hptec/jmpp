package cn.cerestech.middleware.email.sender;

import java.io.File;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.mail.internet.MimeMultipart;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import cn.cerestech.framework.core.EmailUtils;
import cn.cerestech.framework.core.service.Result;
import cn.cerestech.middleware.email.entity.MailAuthenticator;

/**
 * 邮件发送服务器
 * @author <a href="mailto:royrxc@gmail.com">Roy</a>
 * @since  2015年11月21日下午6:40:01
 */
public class EmailServer{
	private Session _session;
	private Authenticator _authcation;
	private Properties props = new Properties();
	
	private String _from;//发件人邮箱账号
	private String _pwd;//发件人密码
	private String _sender_nick;//发件人昵称
	private String _server;//发件邮件服务器
	private Protocol _protocol;//发送协议
	private String _encoding = "utf-8";
	/**
	 * 协议
	 * @author <a href="mailto:royrxc@gmail.com">Roy</a>
	 * @since  2015年11月21日上午10:43:01
	 */
	public enum Protocol{
		SMTP("smtp");
		private String key;
		private Protocol(String key){
			this.key = key;
		}
		public String key(){
			return key;
		}
	}
	
	public EmailServer(){}
	public EmailServer(String from, String pwd){
		this._from = from;
		this._pwd = pwd;
	}
	
	public EmailServer(String from, String pwd, Protocol protocol){
		this._from = from;
		this._pwd = pwd;
		this._protocol = protocol;
	}
	
	public static EmailServer of(String from, String pwd, Protocol protocol){
		return new EmailServer(from, pwd, protocol);
	}
	
	public static EmailServer of(String from, String pwd){
		return new EmailServer(from, pwd);
	}
	
	public static EmailServer of(){
		return new EmailServer();
	}
	
	/**
	 * 添加邮件设置
	 * @param key
	 * @param val
	 * @return
	 */
	public EmailServer prop(String key, String val){
		this.props.put(key, val);
		return this;
	}
	
	public EmailServer setFrom(String from){
		this._from = from;
		return this;
	}
	
	public EmailServer setPwd(String pwd){
		this._pwd = pwd;
		return this;
	}
	
	public EmailServer setProtocol(Protocol protocol){
		this._protocol = protocol;
		return this;
	}
	
	public EmailServer setServer(String server){
		this._server = server;
		return this;
	}
	
	public EmailServer setAuthcation(Authenticator authcation){
		this._authcation = authcation;
		return this;
	}
	
	public EmailServer setSenderNick(String nick){
		this._sender_nick = nick;
		return this;
	}
	
	public EmailServer setEncoding(String encoding){
		this._encoding = encoding;
		return this;
	}
	
	private void init(){
		if(Strings.isNullOrEmpty(this._encoding)){
			this.setEncoding("utf-8");
		}
		if(this._protocol == null){
			this._protocol = Protocol.SMTP;
		}
		if(Strings.isNullOrEmpty(this._server)){
			String s = this._protocol.key()+"."+this._from.substring(this._from.indexOf("@")+1);
			this.setServer(s);
		}
		
		// 初始化props
		String serverkey = "mail."+this._protocol.toString().toLowerCase()+".host"; 
		if(!props.containsKey(serverkey)){
			props.put(serverkey, this._server);
		}
		String authkey = "mail."+this._protocol.toString().toLowerCase()+".auth";
		if(!props.containsKey(authkey)){
			props.put(authkey, "true");
		}
		if(this._authcation == null){
			this._authcation = new MailAuthenticator(this._from, this._pwd);
		}
		 props.setProperty("mail.debug", "true"); 
	    // 验证
	    // 创建session
	    this._session = Session.getInstance(props, this._authcation);
	}
	
	private Result validate(){
		if(!EmailUtils.isEmail(this._from)){
			return Result.error().setMessage("请设置发件邮箱");
		}
		if(Strings.isNullOrEmpty(this._pwd)){
			return Result.error().setMessage("请设置发件邮箱密码");
		}
		return Result.success();
	}
	
	public Result send(String to, String theme, String content){
		List<String> toes = Lists.newArrayList();
		toes.add(to);
		return send(toes, null, null, theme, content, null);
	}
	
	public Result send(String to, String content){
		List<String> toes = Lists.newArrayList();
		toes.add(to);
		return send(toes, null, null, null, content, null);
	}
	/**
	 * 发送邮件
	 * @param toAcc 发送到邮箱
	 * @param cces 抄送到邮箱
	 * @param bcces 暗送邮箱
	 * @param nick 发件人昵称
	 * @param theme 主体
	 * @param content 内容
	 * @return 返回是否发送成功
	 */
	public Result send(List<String> toes, List<String> cces, List<String> bcces, String theme, String content, List<File> attaches){
		init();//初始化基本参数
		//发件前检查
		Result validate = validate();
		if(!validate.isSuccess()){
			return validate;
		}
		// 创建mime类型邮件
	    final MimeMessage message = new MimeMessage(this._session);
	    
	    // 设置发信人
	    try {
	    	String  serverAcc = Strings.isNullOrEmpty(this._sender_nick)?this._from:(this._sender_nick+"<"+this._from+">");
			message.setFrom(new InternetAddress(serverAcc));
			// 设置收件人
			if(toes == null || toes.size() <=0){
				return Result.error().setMessage("不存在收件人");
			}
			//收件人
			Address[] toads = new Address[toes.size()];
			for (int i =0 ; i< toes.size() ; i++) {
				toads[i] = new InternetAddress(toes.get(i));
			}
			message.setRecipients(RecipientType.TO, toads);
			//设置抄送
			if(cces!=null && cces.size() >0){
				for (String e : cces) {
					message.addRecipient(RecipientType.CC, new InternetAddress(e));
				}
			}
			//设置暗送
			if(bcces != null && bcces.size() >0){
				for (String e : bcces) {
					message.addRecipient(RecipientType.BCC, new InternetAddress(e));
				}
			}
			// 设置主题
			if(!Strings.isNullOrEmpty(theme)){
				message.setSubject(theme);
			}
			// 设置邮件内容
			MimeMultipart ct = new MimeMultipart();//邮件内容
			//正文
			MimeBodyPart text = new MimeBodyPart();
			text.setContent(content, "text/html;charset="+this._encoding);
			ct.addBodyPart(text);
			//附件
			if(attaches != null && !attaches.isEmpty()){
				for (File file : attaches) {
					MimeBodyPart attach = new MimeBodyPart();
					DataHandler dh = new DataHandler(new FileDataSource(file));
					attach.setDataHandler(dh);
					attach.setFileName(dh.getName());
					ct.addBodyPart(attach);
				}
			}
			ct.setSubType("mixed");
			message.setContent(ct);
			// 发送
			Transport.send(message);
			
			return Result.success();
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error().setMessage("发送异常");
		}
	}
	
	public static void main(String[] args) {
		//qiangliangqiao7@163.com:jizhi6681105
		
		String from="qiangliangqiao7@163.com",
				pwd="jizhi6681105";
//		String from="397326520@qq.com",
//				pwd="520126";
		EmailServer server  = EmailServer.of(from, pwd, Protocol.SMTP);
		List<String> to = Lists.newArrayList();
		to.add("397326520@qq.com");
		
		List<File> files = Lists.newArrayList();
		files.add(new File("/Users/bird/Desktop/1.jpg"));
		
		Result res = server.send("397326520@qq.com", "测试发送邮件内容xxxxxxx");
		System.out.println("发送结果："+res.isSuccess());
		
		/*
		 Providers Listed By Protocol: 
		{
			imaps=javax.mail.Provider[STORE,imaps,com.sun.mail.imap.IMAPSSLStore,Oracle], 
			imap=javax.mail.Provider[STORE,imap,com.sun.mail.imap.IMAPStore,Oracle], 
			smtps=javax.mail.Provider[TRANSPORT,smtps,com.sun.mail.smtp.SMTPSSLTransport,Oracle], 
			pop3=javax.mail.Provider[STORE,pop3,com.sun.mail.pop3.POP3Store,Oracle], 
			pop3s=javax.mail.Provider[STORE,pop3s,com.sun.mail.pop3.POP3SSLStore,Oracle], 
			smtp=javax.mail.Provider[TRANSPORT,smtp,com.sun.mail.smtp.SMTPTransport,Oracle]
		}
		 
		 */
	}
}
