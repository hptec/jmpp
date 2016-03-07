package cn.cerestech.middleware.sms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.cerestech.middleware.sms.console.queryforms.SmsCriteria;
import cn.cerestech.middleware.sms.entity.SmsRecord;

/**
 * 短信查询Mapper
 * @author <a href="mailto:royrxc@gmail.com">Roy</a>
 * @since  2016年1月3日上午11:34:24
 */
public interface SmsMapper {
	
	public <T extends SmsRecord> List<SmsRecord> searchSms(@Param("criteria")SmsCriteria<T> criteria);
	public <T extends SmsRecord> long searchSmsCount(@Param("criteria")SmsCriteria<T> criteria);
	
}
