package cn.cerestech.middleware.weixin.mp.api;

import org.apache.http.Header;
import org.apache.http.HttpResponse;

import com.google.gson.JsonObject;

import cn.cerestech.framework.core.HttpUtils;
import cn.cerestech.framework.core.HttpUtils.IMAGE_TYPE;
import cn.cerestech.framework.core.storage.FileStorage;
import cn.cerestech.middleware.weixin.entity.Status;

public class MaterialApi {

	private MpApi parent;

	public MaterialApi(MpApi mpApi) {
		parent = mpApi;
	}

	/**
	 * 获取临时素材
	 * 
	 * @return
	 */
	public Status getMedia(String media_id, String filePath, String name) {
		return parent.execute(token -> {
			StringBuffer url = new StringBuffer();
			url.append("http://file.api.weixin.qq.com/cgi-bin/media/get?");
			url.append("access_token=" + token);
			url.append("&media_id=" + media_id);

			HttpResponse response = HttpUtils.get(url.toString());
			Header[] type = response.getHeaders("Content-Type");

			if (type.length > 0) {
				String t = type[0].getValue();
				t = t.split("/").length > 1 ? t.split("/")[1] : t;

				IMAGE_TYPE imgtype = IMAGE_TYPE.valueOfContent(t);
				if (imgtype.equals(IMAGE_TYPE.UNKNOW)) {
					return Status.as(HttpUtils.readHttpResponse(response));
				} else {
					String ext = imgtype.suffixOf();
					String fileId = FileStorage.newFilename(ext);
					String fullpath = FileStorage.path(fileId);
					HttpUtils.saveFile(response, fullpath);
					Status sb = new Status(0);
					JsonObject obj = new JsonObject();
					obj.addProperty("path", fullpath);
					return sb;
				}
			}
			return Status.as(HttpUtils.readHttpResponse(response));
		});
	}
}
