package cn.cerestech.framework.support.mp.msg.client.initiative.servicemsg;

import java.util.List;

import cn.cerestech.framework.core.json.Jsons;
import cn.cerestech.framework.support.mp.enums.SIMsgType;
import cn.cerestech.framework.support.mp.msg.client.initiative.servicemsg.comm.MpServiceMsg;

/**
 *
 * @author <a mailto="royrxc@gmail.com">Roy</a>
 * @since 2016年11月4日
 */
public class MpServiceNewsMsg extends MpServiceMsg{
	private List<Article> news;
	public MpServiceNewsMsg(){
		this.setMsgType(SIMsgType.NEWS);
	}
	public List<Article> getNews() {
		return news;
	}
	public void setNews(List<Article> news) {
		this.news = news;
	}
	@Override
	protected String replyContent() {
		StringBuffer sb = new StringBuffer();
		sb.append("\"news\":{\"articles\":");
		sb.append(Jsons.from(this.getNews()).toJson());
		sb.append("}");
		return sb.toString();
	}
	
	public class Article{
		private String title;
		private String description;
		private String url;
		private String picurl;
		public Article(){}
		public Article(String title, String description, String url, String picurl){
			this.title = title;
			this.description = description;
			this.url = url;
			this.picurl = picurl;
		}
		
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public String getPicurl() {
			return picurl;
		}
		public void setPicurl(String picurl) {
			this.picurl = picurl;
		}
	}
	
}
