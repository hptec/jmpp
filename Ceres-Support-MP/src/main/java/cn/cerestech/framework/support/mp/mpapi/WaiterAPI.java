package cn.cerestech.framework.support.mp.mpapi;

import java.util.List;

import org.apache.http.entity.ByteArrayEntity;

import com.google.gson.reflect.TypeToken;

import cn.cerestech.framework.core.http.Https;
import cn.cerestech.framework.core.json.Jsons;
import cn.cerestech.framework.support.mp.entity.base.MpWaiter;
import cn.cerestech.framework.support.mp.entity.base.Status;
import cn.cerestech.framework.support.mp.mpapi.cache.strategy.MemoryStrategy;
import cn.cerestech.framework.support.mp.msg.client.initiative.servicemsg.MpServiceTextMsg;
import cn.cerestech.framework.support.mp.msg.client.initiative.servicemsg.comm.MpServiceMsg;

/**
 * 微信客服API
 * 
 * @author <a mailto="royrxc@gmail.com">Roy</a>
 * @since 2016年5月24日
 */
public class WaiterAPI extends API {

	public WaiterAPI(String appid, String appsecret) {
		super(appid, appsecret);
	}

	public Status<MpWaiter> create(String access_token, String acc, String pwd_md5, String nick) {
		String url = "https://api.weixin.qq.com/customservice/kfaccount/add?access_token=" + access_token;
		MpWaiter waiter = new MpWaiter();
		waiter.setKf_account(acc);
		waiter.setPassword(pwd_md5);
		waiter.setNickname(nick);

		String ret = Https.of().post(url, new ByteArrayEntity(Jsons.from(waiter).toJson().getBytes())).readString();
		Status<MpWaiter> status = Status.as(ret);
		if (status.isSuccess()) {// 成功
			status.setObject(waiter);
		}
		return status;
	}

	public Status<MpWaiter> update(String access_token, String acc, String pwd_md5, String nick) {
		String url = "https://api.weixin.qq.com/customservice/kfaccount/update?access_token=" + access_token;
		MpWaiter waiter = new MpWaiter();
		waiter.setKf_account(acc);
		waiter.setPassword(pwd_md5);
		waiter.setNickname(nick);

		String ret = Https.of().post(url, new ByteArrayEntity(Jsons.from(waiter).toJson().getBytes())).readString();
		Status<MpWaiter> status = Status.as(ret);
		if (status.isSuccess()) {// 成功
			status.setObject(waiter);
		}
		return status;
	}

	public Status<String> delete(String access_token, String acc, String pwd_md5, String nick) {
		String url = "https://api.weixin.qq.com/customservice/kfaccount/del?access_token=" + access_token;
		MpWaiter waiter = new MpWaiter();
		waiter.setKf_account(acc);
		waiter.setPassword(pwd_md5);
		waiter.setNickname(nick);

		String ret = Https.of().post(url, new ByteArrayEntity(Jsons.from(waiter).toJson().getBytes())).readString();
		Status<String> status = Status.as(ret);
		if (status.isSuccess()) {// 成功
		}
		return status;
	}

	public Status setHeaderImg(String access_token, String acc, String headerImgPath) {
		String url = "http://api.weixin.qq.com/customservice/kfaccount/uploadheadimg?access_token=" + access_token
				+ "&kf_account=" + acc;
		String[] arr = new String[1];
		arr[0] = headerImgPath;
		String ret = Https.of().upload(url, arr).readString();
		Status<String> status = Status.as(ret);
		return status;
	}

	public Status<List<MpWaiter>> getAll(String access_token) {
		String url = "https://api.weixin.qq.com/cgi-bin/customservice/getkflist?access_token=" + access_token;
		String ret = Https.of().post(url).readString();
		Status<List<MpWaiter>> status = Status.as(ret);
		if (status.isSuccess()) {
			status.setObject(Jsons.from(status.jsonValue("kf_list")).to(new TypeToken<List<MpWaiter>>() {
			}));
		}
		return status;
	}

	public Status<String> sendMsg(MpServiceMsg msg, String access_token) {
		String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token="+access_token;
		String ret = Https.of().post(url, new ByteArrayEntity(msg.reply().getBytes())).readString();
		return Status.as(ret);
	}

	public static void main(String[] args) {
		String appid = "wx9d02e7a1789151b2";
		String appsecret = "6b96902b4fff22b974dc136277462509";
		
		MpServiceTextMsg text = new MpServiceTextMsg();
		text.setToUser("otwEVt-T63dncpcxHkGsnIwTZePE");
//		text.setKf_account("ranran1@gh_c377e0f1bcf5");
		text.setContent("下雨了，回家收衣服咯！！");
		
//		String pwd_md5 = Encrypts.md5("123qwe");
//		Status<Waiter> s = MemoryStrategy.of(appid, appsecret).WAITERAPI().create("ranran1@gh_c377e0f1bcf5", pwd_md5,
//				"冉冉");
		Status<String> s = MemoryStrategy.of(appid, appsecret).WAITERAPI().sendMsg(text);
		
		System.out.println(s.asString());
	}

}
