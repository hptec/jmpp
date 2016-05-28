package cn.cerestech.middleware.location.ip;

import java.util.StringTokenizer;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.google.common.primitives.Ints;

@Embeddable
public class IP {

	@Column(length = 25)
	private String addr = "127.0.0.1";

	public static IP from(Object obj) {
		IP ip = new IP();
		if (obj == null) {
		} else if (obj instanceof String) {
			return IP.fromString(obj.toString());
		} else if (obj instanceof byte[]) {
			return IP.fromBytes((byte[]) obj);
		}
		return ip;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String ip) {
		this.addr = ip;
	}

	@Override
	public String toString() {
		return addr;
	}

	public byte[] toBytes() {
		byte[] ret = new byte[4];
		StringTokenizer st = new StringTokenizer(addr, ".");

		ret[0] = ((byte) (Ints.tryParse(st.nextToken()) & 0xFF));
		ret[1] = ((byte) (Ints.tryParse(st.nextToken()) & 0xFF));
		ret[2] = ((byte) (Ints.tryParse(st.nextToken()) & 0xFF));
		ret[3] = ((byte) (Ints.tryParse(st.nextToken()) & 0xFF));
		return ret;
	}

	public static IP fromBytes(byte[] ip) {
		IP i = new IP();
		StringBuffer sb = new StringBuffer();
		sb.append(ip[0] & 0xFF);
		sb.append('.');
		sb.append(ip[1] & 0xFF);
		sb.append('.');
		sb.append(ip[2] & 0xFF);
		sb.append('.');
		sb.append(ip[3] & 0xFF);
		i.setAddr(sb.toString());
		return i;
	}

	public static IP fromString(String ip) {
		IP i = new IP();
		i.setAddr(ip);
		return i;
	}

}
