package cn.cerestech.middleware.location.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cerestech.framework.core.service.Result;
import cn.cerestech.middleware.location.dao.AddressDao;
import cn.cerestech.middleware.location.entity.Address;

@Service
public class LocationService {
	
	@Autowired
	AddressDao addressDao;

	public Result<Address> saveAddress(Address address){
		if(address == null){
			return Result.error().setMessage("address Can not be empty");
		}
		if (address.getProvince()== null) {
			return Result.error().setMessage("省份不能为空");
		}
		if (address.getCity() == null) {
			return Result.error().setMessage("城市不能为空");
		}
		if (address.getCounty() == null) {
			return Result.error().setMessage("区/县不能为空");
		}
		
		addressDao.save(address);
		return Result.success().setObject(address);
	}
}
