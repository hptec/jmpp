package cn.cerestech.middleware.email.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.cerestech.middleware.email.entity.EmailRecord;

public interface EmailMapper {
	/**
	 * 搜索
	 * @param type： 类型 EmailType
	 * @param seconds_closely : 接近多少毫秒将要计划发送
	 * @param status：邮件状态类型
	 * @return
	 */
	public List<EmailRecord> search(@Param("type")String type, @Param("seconds_delay")Long seconds_delay, @Param("offset")Integer offset, @Param("pageSize")Integer pageSize,  @Param("status")String... status);
}
