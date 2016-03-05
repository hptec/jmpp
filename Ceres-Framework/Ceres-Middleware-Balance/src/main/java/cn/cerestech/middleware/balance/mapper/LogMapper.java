package cn.cerestech.middleware.balance.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.cerestech.middleware.balance.criteria.LogCriteria;
import cn.cerestech.middleware.balance.entity.Log;

public interface LogMapper {
	
	public List<Log> search(@Param("owner_id")Long owner_id, @Param("owner_type")String owner_type,@Param("balance_type")String balance_type, @Param("pageSize")Integer page, @Param("offset")Integer offset);
	
	public List<Log> searchLog(@Param("ceriter")LogCriteria<Log> ceriter);
	public Long searchLogCount(@Param("ceriter")LogCriteria<Log> ceriter);
}
