package cn.cerestech.framework.core.http;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.routing.HttpRoutePlanner;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.FormBodyPartBuilder;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cn.cerestech.framework.core.json.Jsons;

public class Https {

	private String encoding = "UTF-8";
	private CookieStore cookieStore = new BasicCookieStore();;
	private HttpClient client;
	private List<Header> headers = Lists.newArrayList();
	private HttpRoutePlanner proxy;

	public static Https of() {
		Https https = new Https(null, null, null, 80);
		return https;
	}

	public static Https of(String encoding) {
		Https https = new Https(null, null, null, 80);
		https.encoding(encoding);
		return https;
	}

	public static Https of(String proxyHost, int port, List<Cookie> cookies, List<Header> headers) {
		Https https = new Https(cookies, headers, proxyHost, port);
		return https;
	}

	public Https encoding(String encoding) {
		if (!Strings.isNullOrEmpty(encoding) && Charset.isSupported(encoding)) {
			this.encoding = encoding;
		}
		return this;
	}

	private Https(List<Cookie> cookies, List<Header> headers, String proxyHost, int port) {
		if (cookies != null && !cookies.isEmpty()) {
			cookies.forEach(cookie -> {
				cookieStore.addCookie(cookie);
			});
		}
		if (headers != null && !headers.isEmpty()) {
			this.headers.addAll(headers);
		}
		RequestConfig clientConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.DEFAULT).build();
		HttpClientBuilder builder = HttpClientBuilder.create().setDefaultRequestConfig(clientConfig)
				.setDefaultHeaders(this.headers).setDefaultCookieStore(this.cookieStore);
		// 代理设置
		if (!Strings.isNullOrEmpty(proxyHost)) {
			this.proxy = new HttpRoutePlanner() {
				@Override
				public HttpRoute determineRoute(HttpHost target, HttpRequest request, HttpContext context)
						throws HttpException {
					return new HttpRoute(target, null, new HttpHost(proxyHost, port),
							"https".equalsIgnoreCase(target.getSchemeName()));
				}
			};
			builder.setRoutePlanner(this.proxy);
		}

		this.client = builder.build();
	}

	public Response get(String url, Map<String, String> params, List<Cookie> cookies, List<Header> headers) {
		if (Strings.isNullOrEmpty(url)) {
			return Response.of(null);
		}
		HttpGet get = new HttpGet(url(url, "", params));
		if (headers != null && !headers.isEmpty()) {
			headers.forEach(itm -> {
				get.addHeader(itm);
			});
		}

		if (cookies != null && !cookies.isEmpty()) {
			cookies.forEach(cookie -> {
				this.cookieStore.addCookie(cookie);
			});
		}

		try {
			Response res = Response.of(this.client.execute(get));
			return res;
		} catch (Exception e) {
			return Response.of(null);
		}
	}

	public Response get(String url, Map<String, String> params) {
		return get(url, params, null, null);
	}

	public Response get(String url) {
		return get(url, null, null, null);
	}

	public Response post(String url, HttpEntity entity, List<Cookie> cookies, List<Header> headers) {
		if (Strings.isNullOrEmpty(url)) {
			return new Response(null);
		}
		HttpPost post = new HttpPost(url);
		if (headers != null && !headers.isEmpty()) {
			headers.forEach(itm -> {
				post.addHeader(itm);
			});
		}
		if (cookies != null && !cookies.isEmpty()) {
			cookies.forEach(cookie -> {
				this.cookieStore.addCookie(cookie);
			});
		}
		if (entity != null) {
			post.setEntity(entity);
		}
		try {
			HttpResponse response = this.client.execute(post);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_MOVED_TEMPORARILY
					|| response.getStatusLine().getStatusCode() == HttpStatus.SC_MOVED_PERMANENTLY) {
				String url_redirect = response.getLastHeader("Location").getValue();
				return post(url_redirect, entity, cookies, headers);
			}
			return Response.of(response);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.of(null);
		}
	}

	public Response post(String url, Map<String, Object> params, List<Cookie> cookies, List<Header> headers) {
		HttpEntity entity = null;

		if (params != null && !params.isEmpty()) {
			List<BasicNameValuePair> nvp = Lists.newArrayList();
			// MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			params.forEach((k, v) -> {
				String val = Jsons.from(v).disableUnicode().toJson();
				val = Strings.nullToEmpty(val);
				if (val.startsWith("\"")) {
					val = val.substring(1, val.length());
				}
				if (val.endsWith("\"")) {
					val = val.substring(0, val.length() - 1);
				}
				try {
					// builder.addPart(FormBodyPartBuilder.create(k, new
					// StringBody(val,
					// ContentType.APPLICATION_FORM_URLENCODED.withCharset(encoding))).build());
					nvp.add(new BasicNameValuePair(k, val));
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			try {
				entity = new UrlEncodedFormEntity(nvp, this.encoding);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}

		return post(url, entity, cookies, headers);
	}

	public Response post(String url, String postContent) {
		return post(url, new ByteArrayEntity(postContent.getBytes()));
	}

	public Response post(String url, HttpEntity entity) {
		return post(url, entity, null, null);
	}

	public Response post(String url, Map<String, Object> params) {
		return post(url, params, null, null);
	}

	public Response post(String url) {
		return post(url, (HttpEntity) null, null, null);
	}
	
	public Response upload(String url, List<Cookie> cookies, List<Header> headers, Map<String, Object> params, String fileBodyNameSpec, File...files){
		HttpEntity entity = null;
		
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		if(params != null && !params.isEmpty()){
			params.forEach((k,v)->{
				String val = Jsons.from(v).disableUnicode().toJson();
				val = Strings.nullToEmpty(val);
				if (val.startsWith("\"")) {
					val = val.substring(1, val.length());
				}
				if (val.endsWith("\"")) {
					val = val.substring(0, val.length() - 1);
				}
				try {
					builder.addPart(FormBodyPartBuilder.create(k, new StringBody(val, ContentType.MULTIPART_FORM_DATA.withCharset(encoding))).build());
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		}
		
		if(files != null && files.length > 0){
			for (File file : files) {
				try{
//					builder.addBinaryBody(file.getName(), new FileInputStream(file), ContentType.MULTIPART_FORM_DATA, file.getName());
					builder.addPart(FormBodyPartBuilder.create(Strings.isNullOrEmpty(fileBodyNameSpec)?file.getName():fileBodyNameSpec,new FileBody(file)).build());
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		
		entity = builder.build();
		
		return post(url, entity);
	}
	
	public Response upload(String url, Map<String, Object> params, String fileBodySpecName, File...files){
		return upload(url, null, null, params,fileBodySpecName, files);
	}
	
	public Response upload(String url, String...filesPath){
		List<File> files = Lists.newArrayList();
		for (String fp : filesPath) {
			try{
				files.add(new File(fp));
			}catch(Exception e){}
		}
		
		return upload(url, (String)null, files.toArray(new File[files.size()]));
	}
	
	public Response upload(String url,String fileBodySpecName, File...files){
		return upload(url, null, null, null,fileBodySpecName, files);
	}
	
	public String url(String host, String uri, Map<String, String> params) {
		StringBuffer buffer = new StringBuffer();
		if (!Pattern.compile("(?i)^http(s{0,1})://.{0,}").matcher(host).matches()) {
			buffer.append("http://");
		}
		buffer.append(host);
		if (!Strings.isNullOrEmpty(uri)) {
			uri = uri.startsWith("/") ? uri.substring(1) : uri;
			if (!host.endsWith("/")) {
				buffer.append("/");
			}
			buffer.append(uri);
		}
		StringBuffer psb = new StringBuffer();
		if (params != null && !params.isEmpty()) {
			params.forEach((k, v) -> {
				if (psb.length() > 0) {
					psb.append("&");
				}
				try {
					psb.append(k).append("=").append(URLEncoder.encode(v, encoding));
				} catch (Exception e) {
				}
			});
		}

		if (psb.length() > 0) {
			if (buffer.indexOf("?") > 0) {
				buffer.append("&").append(psb);
			} else {
				buffer.append("?").append(psb);
			}
		}
		return buffer.toString();
	}

	public static class Response {
		private HttpResponse response;
		private int http_code;
		/* private String response_encoding = "ISO-8859-1"; */

		public static Response of(HttpResponse response) {
			return new Response(response);
		}
		public int httpCode(){
			if(this.response != null){
				this.http_code = response.getStatusLine().getStatusCode();
			}
			return this.http_code;
		}
		private Response(HttpResponse response) {
			this.response = response;
			parseEncoding();
		}

		private void parseEncoding() {
			if (response != null) {
				// TODO 解析网页的编码格式
			}
		}

		public String readString() {
			return readString(null);
		}

		public String readString(String charEncoding) {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			readByte(out);
			try {
				if (Strings.isNullOrEmpty(charEncoding)) {
					return new String(out.toByteArray());
				} else {
					return new String(out.toByteArray(), charEncoding);
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return new String(out.toByteArray());
			}
		}

		public void readByte(OutputStream out) {
			if (this.response == null) {
				return;
			}
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {// 请求成功
				System.out.println("【网络请求失败：】返回码" + response.getStatusLine().getStatusCode());
				return;
			}

			HttpEntity entity = this.response.getEntity();
			InputStream inputStream;
			try {
				inputStream = entity.getContent();

				Header header = response.getFirstHeader("Content-Encoding");
				if (header != null && header.getValue().toLowerCase().indexOf("gzip") > -1) {
					inputStream = new GZIPInputStream(inputStream);
				}

				int readBytes = 0;
				byte[] sBuffer = new byte[512];
				while ((readBytes = inputStream.read(sBuffer)) != -1) {
					out.write(sBuffer, 0, readBytes);
					out.flush();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public byte[] readByte() {
			ByteArrayOutputStream ba = new ByteArrayOutputStream();
			readByte(ba);
			return ba.toByteArray();
		}

		public void readFile(FileOutputStream fout) {
			readByte(fout);
		}
		
		public boolean success(){
			if(this.response == null){
				return false;
			}
			this.http_code = response.getStatusLine().getStatusCode();
			if(response.getStatusLine().getStatusCode() != HttpStatus.SC_OK){//请求成功
				System.out.println("【网络请求失败：】返回码"+response.getStatusLine().getStatusCode());
				return false;
			}
			return true;
		}
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		// System.out.println(Https.of().get("https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN").readString());
		// System.out.println(HttpUtils.post("https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN"));

		// Map<String, Object> params = Maps.newHashMap();
		// params.put("financial_product_id", "ALLLAL");
		// Response ret = Https.of().post("http://ranxc.e7db.com/loan/datas",
		// params);
		Map<String, Object> params = Maps.newHashMap();
		// params.put("financial_product_id", "ALLLAL");
		// Response ret = Https.of().post("http://ranxc.e7db.com/loan/datas",
		// params);
		// System.out.println(Https.of().get("http://wx.idsh.cn/sdfsdfd").readString("gbk"));;

		// params.put("acc", "15281036309");
		// params.put("pwd","123qwe");
		// params.put("phoneCode","1111");
		// params.put("user_register", "15281036309");
		// params.put("user_register_phone", "1111");
		System.out.println(Https.of()
				.post("http://www.baidu.com", params/* , cookies, null */).readString());
		// System.out.println(Charset.isSupported("fsdf sd"));
	}
}
