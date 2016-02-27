package cn.cerestech.middleware.carouseladvertising.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.common.base.Strings;

import cn.cerestech.framework.core.service.BaseService;
import cn.cerestech.framework.core.service.Result;
import cn.cerestech.middleware.carouseladvertising.entity.CarouselAdvertising;
import cn.cerestech.middleware.carouseladvertising.enums.ErrorCodes;

@Service
public class CarouselAdvertisingService extends BaseService {

	public static final String CATEGORY_KEYWORD = "CAROUSEL_ADVTISING";

	public <T extends CarouselAdvertising> List<T> search(Class<T> clazz, String namespace) {
		StringBuffer where = new StringBuffer(" 1=1 ");
		if (!Strings.isNullOrEmpty(namespace)) {
			where.append(" AND `namespace`='" + namespace + "'");
		}

		where.append(" ORDER BY sort");
		List<T> retList = mysqlService.queryBy(clazz, where.toString());
		return retList;
	}

	public Result<CarouselAdvertising> addPic(String namespace, String uri) {
		if (Strings.isNullOrEmpty(namespace)) {
			return Result.error(ErrorCodes.NAMESPACE_NOT_EXIST);
		}
		if (Strings.isNullOrEmpty(uri)) {
			return Result.error(ErrorCodes.URI_NOT_EXIST);
		}

		List<CarouselAdvertising> retList = search(CarouselAdvertising.class, namespace);
		CarouselAdvertising ca = new CarouselAdvertising();
		ca.setCreate_time(new Date());
		ca.setFile_id(uri);
		ca.setNamespace(namespace);
		ca.setSort(retList.size());
		mysqlService.insert(ca);
		return Result.success(ca);
	}

	public Result<CarouselAdvertising> moveLeft(String namespace, Long picid) {
		if (Strings.isNullOrEmpty(namespace)) {
			return Result.error(ErrorCodes.NAMESPACE_NOT_EXIST);
		}
		if (picid == null) {
			return Result.error(ErrorCodes.PIC_NOT_EXIST);
		}

		CarouselAdvertising ca = mysqlService.queryById(CarouselAdvertising.class, picid);
		if (ca != null) {
			if (ca.getSort() <= 0) {
				// 第一位
			} else {
				StringBuffer where = new StringBuffer();
				where.append(" `namespace`='" + namespace + "'");
				where.append(" AND sort=" + (ca.getSort() - 1));

				CarouselAdvertising caPrevious = mysqlService.queryUnique(CarouselAdvertising.class, where.toString());
				if (caPrevious != null) {
					caPrevious.setSort(caPrevious.getSort() + 1);
					mysqlService.update(caPrevious);
				}
				ca.setSort(ca.getSort() - 1);
				mysqlService.update(ca);
			}
		}

		return Result.success(ca);
	}

	public Result<CarouselAdvertising> moveRight(String namespace, Long picid) {
		if (Strings.isNullOrEmpty(namespace)) {
			return Result.error(ErrorCodes.NAMESPACE_NOT_EXIST);
		}
		if (picid == null) {
			return Result.error(ErrorCodes.PIC_NOT_EXIST);
		}

		CarouselAdvertising ca = mysqlService.queryById(CarouselAdvertising.class, picid);
		if (ca != null) {
			StringBuffer where = new StringBuffer();
			where.append(" `namespace`='" + namespace + "'");
			where.append(" AND sort=" + (ca.getSort() + 1));
			CarouselAdvertising caNext = mysqlService.queryUnique(CarouselAdvertising.class, where.toString());
			if (caNext == null) {
				// 最后一位
			} else {
				caNext.setSort(caNext.getSort() - 1);
				mysqlService.update(caNext);
				ca.setSort(ca.getSort() + 1);
				mysqlService.update(ca);
			}
		}

		return Result.success(ca);
	}

	public Result<CarouselAdvertising> remove(String namespace, Long picid) {
		if (Strings.isNullOrEmpty(namespace)) {
			return Result.error(ErrorCodes.NAMESPACE_NOT_EXIST);
		}
		if (picid == null) {
			return Result.error(ErrorCodes.PIC_NOT_EXIST);
		}

		CarouselAdvertising ca = mysqlService.queryById(CarouselAdvertising.class, picid);
		if (ca != null) {
			mysqlService
					.queryBy(CarouselAdvertising.class, " `namespace`='" + namespace + "' AND sort > " + ca.getSort())
					.forEach(car -> {
						car.setSort(car.getSort() - 1);
						mysqlService.update(car);
					});
			mysqlService.delete(CarouselAdvertising.class, picid);
		}

		return Result.success();
	}
}
