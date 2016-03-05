package cn.cerestech.middleware.weixin.mapper;

import org.apache.ibatis.annotations.Param;

import cn.cerestech.middleware.weixin.entity.MpUser;
/**
 * 特殊处理微信表
 * @author bird
 *
 */
public interface MpUserMapper {
	
	public MpUser queryMpUserById(@Param("id")Long id);
	
	public MpUser queryMpUserByOpenId(@Param("openid")String openid);
	
	public void insert(@Param("mpuser")MpUser mpuser);
	
	public void delete(@Param("id")Long id);
	
	public void update(@Param("mpuser")MpUser mpuser);
}
