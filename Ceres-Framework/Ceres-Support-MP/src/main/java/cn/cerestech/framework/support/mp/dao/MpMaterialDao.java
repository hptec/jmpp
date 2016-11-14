package cn.cerestech.framework.support.mp.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.cerestech.framework.support.mp.entity.MpMaterial;
import cn.cerestech.framework.support.persistence.Owner;

/**
 * 素材管理
 * @author <a mailto="royrxc@gmail.com">Roy</a>
 * @since 2016年11月13日
 */
public interface MpMaterialDao  extends JpaRepository<MpMaterial, Long>{
	/**
	 * 根据拥有这，和 key 进行查找素材
	 * @param owner
	 * @param key
	 * @return
	 */
	public MpMaterial findUniqueByOwnerAndKey(Owner owner, String key);
	
}
