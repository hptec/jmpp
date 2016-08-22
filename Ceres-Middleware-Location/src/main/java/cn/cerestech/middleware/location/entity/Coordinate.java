package cn.cerestech.middleware.location.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import cn.cerestech.middleware.location.enums.GeodeticSystem;

@SuppressWarnings("serial")
@Embeddable
public class Coordinate implements Serializable {
	public static final int COORDINATE_SCALE = 14;

	private GeodeticSystem standard;

	@Column(precision = 19, scale = COORDINATE_SCALE)
	private BigDecimal longitude;

	@Column(precision = 19, scale = COORDINATE_SCALE)
	private BigDecimal latitude;

	public GeodeticSystem getStandard() {
		return standard;
	}

	public Coordinate setStandard(GeodeticSystem standard) {
		this.standard = standard;
		return this;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public Coordinate setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
		return this;
	}

	public BigDecimal getLatitude() {
		return latitude;
	}

	public Coordinate setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
		return this;
	}

	public static Coordinate fromWGS84(BigDecimal longtitude, BigDecimal latitude) {
		return from(GeodeticSystem.WGS84, longtitude, latitude);
	}

	public static Coordinate fromWGS84(double longtitude, double latitude) {
		return from(GeodeticSystem.WGS84, longtitude, latitude);
	}

	public static Coordinate fromGCJ02(BigDecimal longtitude, BigDecimal latitude) {
		return from(GeodeticSystem.GCJ02, longtitude, latitude);
	}

	public static Coordinate fromGCJ02(double longtitude, double latitude) {
		return from(GeodeticSystem.GCJ02, longtitude, latitude);
	}

	public static Coordinate fromBD09(BigDecimal longtitude, BigDecimal latitude) {
		return from(GeodeticSystem.BD09, longtitude, latitude);
	}

	public static Coordinate fromBD09(double longtitude, double latitude) {
		return from(GeodeticSystem.BD09, longtitude, latitude);
	}

	public static Coordinate from(GeodeticSystem standard, BigDecimal longtitude, BigDecimal latitude) {
		Coordinate coord = new Coordinate();
		return coord.setStandard(standard).setLongitude(longtitude).setLatitude(latitude);
	}

	public static Coordinate from(GeodeticSystem standard, double longtitude, double latitude) {
		return from(standard, new BigDecimal(longtitude), new BigDecimal(latitude));
	}

	public Boolean isEmpty() {
		if (standard == null || longitude == null || latitude == null) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}
}
