package cn.cerestech.middleware.location.entity;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

import cn.cerestech.framework.support.persistence.entity.IdEntity;
import cn.cerestech.middleware.location.enums.AdminLevel;

@SuppressWarnings("serial")
@Entity
@Table(name = "$$sys_address")
public class Address extends IdEntity {

	//省份
	@Embedded
	private Division province;

	//市
	@Embedded
	private Division city;

	//区、县
	@Embedded
	private Division county;

	//坐标
	@Embedded
	private Coordinate coordinate;

	//街道
	private String street;

	private String streetNumber;

	//邮编号码
	private String zipcode;

	public Coordinate getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(Coordinate coordinate) {
		this.coordinate = coordinate;
	}

	public Division getProvince() {
		return province;
	}

	public void setProvince(Division province) {
		this.province = province;
	}

	public Division getCity() {
		return city;
	}

	public void setCity(Division city) {
		this.city = city;
	}

	public Division getCounty() {
		return county;
	}

	public void setCounty(Division county) {
		this.county = county;
	}

	public String getStreetNumber() {
		return streetNumber;
	}

	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public Boolean isCoordinateEmpty() {
		if (coordinate == null) {
			return Boolean.TRUE;
		} else {
			return coordinate.isEmpty();
		}
	}

	public Boolean isDivisionEmpty(AdminLevel level) {
		switch (level) {
		case PROVINCE:
			return province == null ? Boolean.TRUE : province.isEmpty();
		case CITY:
			return city == null ? Boolean.TRUE : city.isEmpty();
		case COUNTY:
			return county == null ? Boolean.TRUE : county.isEmpty();
		default:
			return Boolean.TRUE;
		}
	}
}
