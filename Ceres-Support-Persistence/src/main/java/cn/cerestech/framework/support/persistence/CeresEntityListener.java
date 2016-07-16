package cn.cerestech.framework.support.persistence;

import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cn.cerestech.framework.support.persistence.entity.HashTag;

public class CeresEntityListener {
	Logger log = LogManager.getLogger();

	@PrePersist
	void onPrePersist(Object o) {
		if (o instanceof HashTag) {
			// 只有创建的时候才生成hashTag
			((HashTag) o).generateHashTag();
		}
	}

	@PostPersist
	void onPostPersist(Object o) {
	}

	@PostLoad
	void onPostLoad(Object o) {
	}

	@PreUpdate
	void onPreUpdate(Object o) {
	}

	@PostUpdate
	void onPostUpdate(Object o) {
	}

	@PreRemove
	void onPreRemove(Object o) {
	}

	@PostRemove
	void onPostRemove(Object o) {
	}
}