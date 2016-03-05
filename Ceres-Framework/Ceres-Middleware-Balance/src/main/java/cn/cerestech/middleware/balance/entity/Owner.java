package cn.cerestech.middleware.balance.entity;

import com.google.common.base.Strings;

import cn.cerestech.framework.core.enums.DescribableEnum;

public class Owner {

	private String owner_type;
	private Long owner_id;

	public Owner(DescribableEnum type, Long id) {
		if (type == null || id == null) {
			throw new IllegalArgumentException(" type or id is null");
		}
		this.setOwner_id(id);
		this.setOwner_type(type.key());
	}

	public Owner(String type, Long id) {
		this.setOwner_id(id);
		this.setOwner_type(type);
	}

	public String getOwner_type() {
		return owner_type;
	}

	public void setOwner_type(String owner_type) {
		this.owner_type = owner_type;
	}

	public Long getOwner_id() {
		return owner_id;
	}

	public void setOwner_id(Long owner_id) {
		this.owner_id = owner_id;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Owner other = (Owner) obj;

		return this.getOwner_type().equals(other.getOwner_type()) && this.getOwner_id().equals(other.getOwner_id());
	}

	public Boolean isEmpty() {
		return Strings.isNullOrEmpty(owner_type) || owner_id == null;
	}

}