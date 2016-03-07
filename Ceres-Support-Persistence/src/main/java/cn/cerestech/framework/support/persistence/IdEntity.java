package cn.cerestech.framework.support.persistence;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * 是否支持ID为主键类型的实体记录
 * 
 * @author harryhe
 *
 */
@MappedSuperclass
public abstract class IdEntity implements PlatformEntitySupport {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(precision = 19, scale = 0)
	private Long id;
	@Column(precision = 19, scale = 0)
	private Long platformId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPlatformId() {
		return platformId;
	}

	public void setPlatformId(Long id) {
		platformId = id;
	}

}
