package cn.cerestech.middleware.weixin.mp.msg.message;

import java.util.List;

import com.google.common.collect.Lists;

import cn.cerestech.middleware.weixin.mp.enums.MessageKey;
import cn.cerestech.middleware.weixin.mp.msg.Message;

/**
 * @author <a href="mailto:royrxc@gmail.com">Ranxc</a> 该Bean 只用于消息回复，回复类型为NEWS
 *         图文信息
 */
public class NewsMsg extends Message {
	/**
	 * 多条图文消息信息，默认第一个item为大图 注意，如果图文数超过10，则将会无响应 必须项
	 */
	private List<Article> articles;

	/**
	 * 无参构造函数，用户消息回复
	 */
	public NewsMsg() {
		super();
		this.setMsgType(MessageKey.NEWS);
	}

	public Integer getArticleCount() {
		if (articles == null) {
			return 0;
		} else {
			return articles.size();
		}
	}

	public List<Article> getArticles() {
		return articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}

	public void addArticles(Article article) {
		if (this.articles == null) {
			this.articles = Lists.newArrayList();
		}
		this.articles.add(article);
	}

	@Override
	protected String getIndividualXml() {
		if (articles == null || articles.isEmpty()) {
			return null;
		}

		StringBuffer xml = new StringBuffer();
		xml.append("<ArticleCount><![CDATA[" + articles.size() + "]]></ArticleCount>");
		xml.append("<Articles>");
		articles.forEach(a -> {
			xml.append("<item>");
			xml.append("<Title><![CDATA[" + a.getTitle() + "]]></Title>");
			xml.append("<Description><![CDATA[" + a.getDescription() + "]]></Description>");
			xml.append("<PicUrl><![CDATA[" + a.getPicUrl() + "]]></PicUrl>");
			xml.append("<Url><![CDATA[" + a.getUrl() + "]]></Url>");
			xml.append("</item>");
		});

		xml.append("</Articles>");
		return xml.toString();
	}

	public class Article {
		/**
		 * 图文消息标题
		 */
		private String Title;
		/**
		 * 图文消息描述
		 */
		private String Description;
		/**
		 * 图片链接，支持JPG、PNG格式 较好的效果为大图360*200，小图200*200
		 */
		private String PicUrl;
		/**
		 * 点击图文消息跳转链接
		 */
		private String Url;

		public Article() {
		}

		public String getTitle() {
			return Title;
		}

		public void setTitle(String title) {
			Title = title;
		}

		public String getDescription() {
			return Description;
		}

		public void setDescription(String description) {
			Description = description;
		}

		public String getPicUrl() {
			return PicUrl;
		}

		public void setPicUrl(String picUrl) {
			PicUrl = picUrl;
		}

		public String getUrl() {
			return Url;
		}

		public void setUrl(String url) {
			Url = url;
		}
	}

}
