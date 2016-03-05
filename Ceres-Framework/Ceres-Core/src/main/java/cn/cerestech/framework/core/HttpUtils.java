package cn.cerestech.framework.core;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.FormBodyPartBuilder;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import cn.cerestech.framework.core.json.Jsons;

public class HttpUtils {

	private static final String ENCODING = "UTF-8";
	public static final String HEADER_COOKIE_KEY = "Set-Cookie";
	public static final String HEADER_COOKIE_KEY_REQUEST = "Cookie";
	public static final String NETWORK_INTERRUPTED_MSG = "未连接网络";

	public static final Logger log = LogManager.getLogger();
	public static final Gson gson = new GsonBuilder().create();

	/**
	 * 证书HTTPS 请求 需要证书，如果没有证书则直接使用http协议 或者 https协议 不使用证书功能，可能导致签名失败
	 * 
	 * @param url
	 * @param sslcontext
	 * @param ver
	 * @param entity
	 * @return
	 */
	public static String postSsl(String url, SSLContext sslcontext, HostnameVerifier ver, HttpEntity entity) {
		String ret = null;
		SSLConnectionSocketFactory sslsf = null;
		CloseableHttpClient client = null;
		ver = ver == null ? getVerifier() : ver;

		if (sslcontext != null) {
			sslsf = new SSLConnectionSocketFactory(sslcontext, ver);
			client = HttpClients.custom().setSSLSocketFactory(sslsf).build();
		} else {
			client = HttpClients.custom().build();
		}

		HttpPost post = new HttpPost(url);
		post.setEntity(entity);
		HttpResponse response = null;
		try {
			response = client.execute(post);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ret = readHttpResponse(response);
		return ret;
	}

	/**
	 * 证书HTTPS 请求 需要证书，如果没有证书则直接使用http协议 或者 https协议 不使用证书功能，可能导致签名失败
	 * 
	 * @param url
	 * @param sslcontext
	 * @param entity
	 * @return
	 */
	public static String postSsl(String url, SSLContext sslcontext, HttpEntity entity) {
		return postSsl(url, sslcontext, null, entity);
	}

	public static SSLContext getSSLContext(String keyPassword, String certType, String certFilePath) {
		FileInputStream in = null;
		try {
			in = new FileInputStream(new File(certFilePath));
			KeyStore keyStrore = KeyStore.getInstance(certType);
			keyStrore.load(in, keyPassword.toCharArray());
			// 指定TLS版本
			SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStrore, keyPassword.toCharArray()).build();
			return sslcontext;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static HostnameVerifier getVerifier() {
		return SSLConnectionSocketFactory.getDefaultHostnameVerifier();
	}

	public static void get(final String url, final String queryString, final CallBack<HttpResponse> callBack) {
		new Thread() {
			@Override
			public void run() {
				CloseableHttpClient client = HttpClientBuilder.create().build();// new
																				// DefaultHttpClient();

				HttpGet get = new HttpGet(httpUri(url, queryString));
				HttpUriRequest request = get;
				try {
					HttpResponse response = client.execute(request);
					if (response.getStatusLine().getStatusCode() != 200) {
						callBack.failure(new Throwable("net work error:" + response.getStatusLine().getStatusCode()));
					} else {
						callBack.success(response);
					}
				} catch (Throwable t) {
					callBack.failure(t);
				}
			}
		}.start();
	}

	public static void post(final String url, final String queryString, HttpEntity entity,
			final CallBack<HttpResponse> callBack) {
		new Thread() {
			@Override
			public void run() {
				CloseableHttpClient client = HttpClientBuilder.create().build();
				HttpPost post = new HttpPost(httpUri(url, queryString));
				if (entity != null) {
					post.setEntity(entity);
				}
				try {
					HttpResponse response = client.execute(post);
					if (response.getStatusLine().getStatusCode() != 200) {
						callBack.failure(new Throwable("net work error:" + response.getStatusLine().getStatusCode()));
					} else {
						callBack.success(response);
					}
				} catch (Throwable t) {
					callBack.failure(t);
				}
			}
		}.start();
	}

	public static void post(final String url, final Map<String, Object> params, final CallBack<HttpResponse> callBack) {
		new Thread() {
			@Override
			public void run() {
				CloseableHttpClient client = HttpClientBuilder.create().build();
				HttpPost post = new HttpPost(url);

				try {
					post.setEntity(new UrlEncodedFormEntity(mapToNameValuePair(params), ENCODING));
					HttpResponse response = client.execute(post);
					if (response.getStatusLine().getStatusCode() != 200) {
						callBack.failure(new Throwable("net work error:" + response.getStatusLine().getStatusCode()));
					} else {
						callBack.success(response);
					}
				} catch (Throwable t) {
					callBack.failure(t);
				}
			}
		}.start();
	}

	public static void post(final String url, final HttpEntity entity, final CallBack<HttpResponse> callBack) {
		new Thread() {
			@Override
			public void run() {
				CloseableHttpClient client = HttpClientBuilder.create().build();

				HttpPost post = new HttpPost(url);
				try {

					if (entity != null) {
						post.setEntity(entity);
					}

					HttpResponse response = client.execute(post);
					if (response.getStatusLine().getStatusCode() != 200) {
						callBack.failure(new Throwable("net work error:" + response.getStatusLine().getStatusCode()));
					} else {
						callBack.success(response);
					}
				} catch (Throwable t) {
					callBack.failure(t);
				}
			}
		}.start();
	}

	public static String httpUri(String url, String queryString) {
		StringBuffer buffer = new StringBuffer();
		if (!Pattern.compile("(?i)^http(s{0,1})://.{0,}").matcher(url).matches()) {
			buffer.append("http://");
		}
		buffer.append(url);

		if (StringUtils.isNotBlank(queryString)) {
			if (url.contains("?") && url.contains("=")) {
				buffer.append("&" + queryString);
			} else {
				buffer.append("?" + queryString);
			}
		}
		return buffer.toString();
	}

	/**
	 * Read data from HttpResponse
	 * 
	 * @param response
	 * @return
	 */
	public static String readHttpResponse(HttpResponse response) {
		String result = "";
		if (response == null) {
			return result;
		}
		HttpEntity entity = response.getEntity();
		InputStream inputStream;
		try {
			inputStream = entity.getContent();
			ByteArrayOutputStream content = new ByteArrayOutputStream();

			Header header = response.getFirstHeader("Content-Encoding");
			if (header != null && header.getValue().toLowerCase().indexOf("gzip") > -1) {
				inputStream = new GZIPInputStream(inputStream);
			}

			int readBytes = 0;
			byte[] sBuffer = new byte[512];
			while ((readBytes = inputStream.read(sBuffer)) != -1) {
				content.write(sBuffer, 0, readBytes);
			}
			result = new String(content.toByteArray());
			return result;
		} catch (IllegalStateException e) {
		} catch (IOException e) {
		}
		return result;
	}

	public static void saveFile(HttpResponse response, String filePath) {
		HttpEntity entity = response.getEntity();
		InputStream inputStream = null;
		FileOutputStream out = null;
		try {
			File f = new File(filePath);
			if (!f.getParentFile().exists()) {
				f.getParentFile().mkdirs();
			}

			inputStream = entity.getContent();

			out = new FileOutputStream(f);

			Header header = response.getFirstHeader("Content-Encoding");
			if (header != null && header.getValue().toLowerCase().indexOf("gzip") > -1) {
				inputStream = new GZIPInputStream(inputStream);
			}

			int readBytes = 0;
			byte[] sBuffer = new byte[512];
			while ((readBytes = inputStream.read(sBuffer)) != -1) {
				out.write(sBuffer, 0, readBytes);
			}
		} catch (IllegalStateException e) {
		} catch (IOException e) {
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
				if (out != null) {
					out.flush();
					out.close();
				}
			} catch (Exception e) {
				//
			}
		}
	}

	public static String get(final String url, final String queryString) {
		String res = "";

		CloseableHttpClient client = HttpClientBuilder.create().build();
		HttpResponse response = null;
		String uri = httpUri(url, queryString);
		HttpGet get = new HttpGet(uri);
		try {
			response = client.execute(get);
		} catch (Throwable t) {
		}
		res = readHttpResponse(response);
		return res;
	}

	public static String post(final String url, final String queryString, HttpEntity entity) {
		String res = "";

		CloseableHttpClient client = HttpClientBuilder.create().build();
		HttpResponse response = null;
		HttpPost post = new HttpPost(httpUri(url, queryString));
		if (entity != null) {
			post.setEntity(entity);
		}
		try {
			response = client.execute(post);
		} catch (Throwable t) {
			// TODO nothing
		}
		res = readHttpResponse(response);
		return res;
	}

	public static String post(final String url) {
		return post(url, Maps.newHashMap());
	}

	public static String post(final String url, final Map<String, Object> params) {
		String res = "";

		CloseableHttpClient client = HttpClientBuilder.create().build();
		HttpResponse response = null;
		HttpPost post = new HttpPost(url);
		try {
			post.setEntity(new UrlEncodedFormEntity(mapToNameValuePair(params), ENCODING));
			response = client.execute(post);
		} catch (Throwable t) {
			// TODO nothing
		}
		res = readHttpResponse(response);
		return res;
	}

	public static HttpResponse get(String uri) {
		return get(uri, null, 1);
	}

	public static HttpResponse get(String uri, final String queryString, int nothing) {
		CloseableHttpClient client = HttpClientBuilder.create().build();
		HttpResponse response = null;
		uri = httpUri(uri, queryString);
		HttpGet get = new HttpGet(uri);
		try {
			response = client.execute(get);
			return response;
		} catch (Throwable t) {
			t.printStackTrace();
			// TODO nothing
			System.out.println("");
		}
		return null;
	}

	public static String post(final String url, HttpEntity entity) {
		String res = "";

		CloseableHttpClient client = HttpClientBuilder.create().build();
		HttpResponse response = null;
		HttpPost post = new HttpPost(url);
		try {
			if (entity != null) {
				post.setEntity(entity);
			}
			response = client.execute(post);
		} catch (Throwable t) {
			log.catching(t);
		}
		res = readHttpResponse(response);
		return res;
	}

	public static String uploadImg(final String uploadPath, final String filePath, final Map<String, String> params) {

		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		try {
			if (params != null && !params.isEmpty()) {
				params.forEach((k, v) -> {
					builder.addPart(FormBodyPartBuilder.create(k, new StringBody(v, ContentType.DEFAULT_TEXT)).build());
				});
			}
			File file = new File(filePath);
			builder.addPart(
					FormBodyPartBuilder
							.create("media", new FileBody(file,
									ContentType.create("image/" + Files.getFileExtension(filePath)), file.getName()))
					.build());
			return upload(uploadPath, builder);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String upload(final String uploadPath, MultipartEntityBuilder builder) {
		return post(uploadPath, builder != null ? builder.build() : null);
	}

	public static Map<String, String> urlParameters(String url) {
		if (!url.contains("?")) {
			return null;
		}
		Map<String, String> res = new HashMap<String, String>();
		String pStr = url.substring(url.indexOf("?") + 1, url.length());

		String kv[] = pStr.split("&");
		for (int i = 0; i < kv.length; i++) {
			String key = kv[i].substring(0, kv[i].indexOf("=")).trim();
			String val = "";
			try {
				val = URLDecoder.decode(kv[i].substring(kv[i].indexOf("=") + 1, kv[i].length()).trim(), ENCODING);
			} catch (UnsupportedEncodingException e) {
			}

			res.put(key, val);
		}
		return res;
	}

	public static List<NameValuePair> mapToNameValuePair(Map<String, Object> params) {
		List<NameValuePair> nvp = new ArrayList<NameValuePair>();

		if (params != null) {
			Iterator<String> keySet = params.keySet().iterator();
			while (keySet.hasNext()) {
				String key = keySet.next();
				String val = Jsons.toJson(params.get(key), false, false);
				val = Strings.nullToEmpty(val);
				if (val.startsWith("\"")) {
					val = val.substring(1, val.length());
				}
				if (val.endsWith("\"")) {
					val = val.substring(0, val.length() - 1);
				}

				NameValuePair nv = new BasicNameValuePair(key, val);
				nvp.add(nv);
			}
		}

		return nvp;
	}

	public interface CallBack<T> {
		public abstract void success(T res);

		public abstract void failure(Throwable t);
	}

	public enum IMAGE_TYPE {
		GIF("gif", ".gif"), TIF("tiff", ".tif"), FAX("fax", ".fax"), ICO("x-icon", ".ico"), JPG("jpg",
				".jpg"), JPEG("jpeg", ".jpeg"), NET("pnetvue", ".net"), PNG("png", ".png"), RP("vnd.rn-realpix",
						".rp"), WBMP("vnd.wap.wbmp", ".wbmp"), UNKNOW(null, null);

		private String contentType;
		private String suffix;

		private IMAGE_TYPE(String contentType, String suffix) {
			this.contentType = contentType;
			this.suffix = suffix;
		}

		public static IMAGE_TYPE valueOfContent(String contentType) {
			contentType = Strings.nullToEmpty(contentType).trim();

			if ("x-jpg".equalsIgnoreCase(contentType)) {
				return JPG;
			}

			for (IMAGE_TYPE ct : IMAGE_TYPE.values()) {
				if (!ct.equals(UNKNOW) && ct.contentType.equalsIgnoreCase(contentType)) {
					return ct;
				}
			}
			return UNKNOW;
		}

		public String suffixOf() {
			return this.suffix;
		}
	}

	// public static HttpEntity createHttpEntity(Object obj){
	// try{
	// if(obj.getClass().isAssignableFrom(String.class)){
	// return new StringEntity(String.valueOf(obj) , Consts.UTF_8);
	// }else if(obj.getClass().isAssignableFrom(byte.class)){
	// System.out.println("BYTE");
	// return new ByteArrayEntity((byte[]) obj);
	// }else if(obj.getClass().isAssignableFrom(File.class)){
	// System.out.println("FILE");
	// return new FileEntity((File) obj);
	// }else if(obj.getClass().isAssignableFrom(InputStream.class)){
	// System.out.println("INPUTStream");
	// return new InputStreamEntity((InputStream)obj);
	// }else if(obj instanceof Serializable){
	// System.out.println("序列化接口");
	// return new SerializableEntity((Serializable)obj);
	// }else{
	// BasicHttpEntity entity = new BasicHttpEntity();
	// InputStream in = new INputstream
	//
	// return new BasicHttpEntity().set;
	// }
	// }catch(Exception e){
	// return null;
	// }
	//
	// return null;
	// }

}
