package cn.cerestech.framework.support.persistence;

import java.util.Date;

import javax.persistence.Embeddable;

import com.google.common.base.Strings;

import cn.cerestech.framework.core.date.Dates;
import cn.cerestech.framework.core.enums.DescribableEnum;

@Embeddable
public class Owner {

	private String ownerType;
	private Long ownerId;

	public Owner(DescribableEnum type, Long id) {
		if (type == null || id == null) {
			throw new IllegalArgumentException(" type or id is null");
		}
		this.setOwnerId(id);
		this.setOwnerType(type.key());
	}

	public Owner(String type, Long id) {
		this.setOwnerId(id);
		this.setOwnerType(type);
	}

	public String getOwnerType() {
		return ownerType;
	}

	public void setOwnerType(String ownerType) {
		this.ownerType = ownerType;
	}

	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
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

		return this.getOwnerType().equals(other.getOwnerType()) && this.getOwnerId().equals(other.getOwnerId());
	}

	public Boolean isEmpty() {
		return Strings.isNullOrEmpty(ownerType) || ownerId == null;
	}

	public static void main(String[] arugs) {
		Date dt = Dates.now().addDate(52).getCalendar().getTime();
		System.out.println(dt);
	}
}