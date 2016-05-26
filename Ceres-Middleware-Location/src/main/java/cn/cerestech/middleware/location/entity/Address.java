package cn.cerestech.middleware.location.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.JoinColumn;

import com.google.common.base.Strings;

import cn.cerestech.middleware.location.enums.AdminLevel;

@Embeddable
public class Address {

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "code", column = @Column(name = "addr_province_code")),
			@AttributeOverride(name = "name", column = @Column(name = "addr_province_name")) })
	@JoinColumn(insertable = false, updatable = false)
	private Division province;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "code", column = @Column(name = "addr_city_code")),
			@AttributeOverride(name = "name", column = @Column(name = "addr_city_name")) })
	@JoinColumn(insertable = false, updatable = false)
	private Division city;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "code", column = @Column(name = "addr_county_code")),
			@AttributeOverride(name = "name", column = @Column(name = "addr_county_name")) })
	@JoinColumn(insertable = false, updatable = false)
	private Division county;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "standard", column = @Column(name = "addr_coord_standard")),
			@AttributeOverride(name = "longitude", column = @Column(name = "addr_coord_longitude")),
			@AttributeOverride(name = "latitude", column = @Column(name = "addr_coord_latitude")) })
	private Coordinate coordinate;

	private String street;

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

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
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
