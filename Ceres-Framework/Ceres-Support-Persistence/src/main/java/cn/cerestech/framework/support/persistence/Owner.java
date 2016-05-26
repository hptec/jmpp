package cn.cerestech.framework.support.persistence;

import java.util.Date;

import javax.persistence.Embeddable;

import com.google.common.base.Strings;

import cn.cerestech.framework.core.date.Dates;
import cn.cerestech.framework.core.enums.DescribableEnum;

@Embeddable
public class Owner {

	private String type;
	private Long id;

	public Owner(DescribableEnum type, Long id) {
		if (type == null || id == null) {
			throw new IllegalArgumentException(" type or id is null");
		}
		this.setId(id);
		this.setType(type.key());
	}

	public Owner(String type, Long id) {
		this.setId(id);
		this.setType(type);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

		return this.getType().equals(other.getType()) && this.getId().equals(other.getId());
	}

	public Boolean isEmpty() {
		return Strings.isNullOrEmpty(type) || id == null;
	}

	public static void main(String[] arugs) {
		Date dt = Dates.now().addDate(52).getCalendar().getTime();
		System.out.println(dt);
	}
}