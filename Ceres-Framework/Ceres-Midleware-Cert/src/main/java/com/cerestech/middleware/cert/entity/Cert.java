package com.cerestech.middleware.cert.entity;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;

/**
 * 认证工具类
 * @author <a href="mailto:royrxc@gmail.com">Roy</a>
 * @since  2015年12月21日上午11:09:49
 */
public class Cert{
	private Map<String, CertRecord> pool = Maps.newHashMap();
	
	public static Cert of(List<CertRecord> certes){
		return new Cert(certes);
	}
	
	public Cert(List<CertRecord> certes){
		this.put(certes);
	}
	
	public void put(List<CertRecord> certes) {
		if (certes != null) {
			certes.forEach(cert->{
				pool.put(cert.getType(), cert);
			});
		}
	}
}
