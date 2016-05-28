package cn.cerestech.middleware.location.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import cn.cerestech.middleware.location.entity.Address;

public interface AddressDao extends JpaRepository<Address, Long> {
	

}
