package cn.cerestech.middleware.balance.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.cerestech.middleware.balance.entity.Withdraw;

public interface WithdrawMapper {
	public List<Withdraw> search(@Param("owner_type")String owner_type, @Param("owner_id")Long owner_id, @Param("balance_id")Long balance_id
			, @Param("offset")Integer offset, @Param("pageSize")Integer pageSize);
}
