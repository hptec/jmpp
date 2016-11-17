package cn.cerestech.framework.support.mp.schema;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.beust.jcommander.internal.Lists;

import cn.cerestech.framework.support.mp.service.MpuserService;

/**
 *
 * @author <a mailto="royrxc@gmail.com">Roy</a>
 * @since 2016年11月17日
 */
@Component
public class SyncSchema {
	@Autowired
	MpuserService mpuserService;
	private static List<Long> pool = Lists.newArrayList();
	private static Object locker = new byte[1];
	public static void put(Long mpuser_id){
		synchronized (locker) {
			if(mpuser_id != null && !pool.contains(mpuser_id)){
				pool.add(mpuser_id);
			}
		}
	}
	private static Long get(){
		synchronized (locker) {
			if(!pool.isEmpty()){
				Long id = pool.get(0);
				pool.remove(0);
				return id;
			}
			return null;
		}
	}
	/**
	 * 从库里面获取一个微信用户进行更新。(一分钟）
	 */
	@Scheduled(fixedDelay = 60 * 1000)
	@Transactional
	public void sync() {
		Long id = get();
		mpuserService.fetchByMpuserId(id);
	}
}
