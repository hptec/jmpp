package cn.cerestech.middleware.weixin;

import java.util.Base64;
import java.util.Formatter;
import java.util.UUID;

/**
 * 执行微信相关的方法
 * 
 * @author bird 公共状态执行管控
 */
public class WeiXinGateway {

//	public static Status execute(WeiXinApi api) {
//		Status s = api.execute();
//		if (s != null) {
//			Code code = Code.valueOf(s.getCode());
//			switch (code) {
//			case ill_at:// access_token 已经过期
//			case ill_at_timeout:// access_token 超时
//				AccessTokenApi._access_token.invalidateAll();// 清除AccessToken缓存
//				s = api.execute();
//				return s;
//			default:
//				return s;
//			}
//		} else {
//			return s;
//		}
//	}

	public static String secretUrl(String toParam) {
		return Base64.getUrlEncoder().encodeToString(toParam.getBytes());
	}

	public static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}

	public static String create_nonce_str() {
		return UUID.randomUUID().toString();
	}

	public static String create_timestamp() {
		return Long.toString(System.currentTimeMillis() / 1000);
	}
}
