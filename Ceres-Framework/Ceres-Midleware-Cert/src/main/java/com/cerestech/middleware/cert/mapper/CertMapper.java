package com.cerestech.middleware.cert.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cerestech.middleware.cert.entity.CertRecord;
import com.cerestech.middleware.cert.entity.Owner;


/**
 * 认证
 * @author <a href="mailto:royrxc@gmail.com">Roy</a>
 * @since  2015年12月21日上午11:04:55
 */
public interface CertMapper {
	
	/**
	 * 搜索认证状态
	 * @param customer_id: null for null
	 * @param cert_state: null for all
	 * @param types: null for all
	 * @return
	 */
	public List<CertRecord> search(@Param("owner")Owner owner,@Param("cert_state")String cert_state, @Param("cert_types")String...types);
	
}
