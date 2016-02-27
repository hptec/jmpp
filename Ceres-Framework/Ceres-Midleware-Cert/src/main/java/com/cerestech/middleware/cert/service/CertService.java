package com.cerestech.middleware.cert.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cerestech.middleware.cert.entity.Cert;
import com.cerestech.middleware.cert.entity.CertRecord;
import com.cerestech.middleware.cert.entity.Owner;
import com.cerestech.middleware.cert.enums.CertState;
import com.cerestech.middleware.cert.enums.CertType;
import com.cerestech.middleware.cert.mapper.CertMapper;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import cn.cerestech.framework.core.service.BaseService;
import cn.cerestech.framework.core.service.Result;

/**
 * 认证
 * @author <a href="mailto:royrxc@gmail.com">Roy</a>
 * @since  2015年12月21日上午10:51:32
 */
@Service
public class CertService extends BaseService{
	
	@Autowired
	CertMapper certMapper;
	
	/**
	 * 返回一个认证记录，不存在则创建
	 * @param customer_id
	 * @param type: 不能为空
	 * @return
	 */
	public CertRecord queryCert(Owner owner, CertType type){
		List<CertRecord> certes = Lists.newArrayList();
		if(type == null){
			return null; 
		}else{
			certes = certMapper.search(owner, null, type.key());
		}
		
		CertRecord rec = null;
		if(certes.isEmpty()){
			rec = new CertRecord();
			rec.setType(type.key());
			rec.setOwner_id(owner.getOwner_id());
			rec.setOwner_type(owner.getOwner_type());
			rec.setCert_state(CertState.REJECT.key());
			rec.setDesc("系统建立");
			mysqlService.insert(rec);
		}else{
			rec = certes.get(0);
		}
		return rec;
	}
	
	/**
	 * 查询所有认证记录
	 * @param customer_id
	 * @param state
	 * @param certTypes
	 * @return
	 */
	public Cert queryCertes(Owner owner, CertState state, CertType...certTypes){
		List<CertRecord> certes = Lists.newArrayList();
		List<String> tps = Lists.newArrayList();
		if(certTypes != null){
			for (CertType tp : certTypes) {
				tps.add(tp.key());
			}
		}
		String[] types = new String[tps.size()];
		tps.toArray(types);
		certes = certMapper.search(owner, state!=null?state.key():null, types);
		
		return Cert.of(certes);
	}
	
	/**
	 * 创建或者修改认证记录,已经认证通过不允许再次认证
	 * @param cert
	 * @return
	 */
	public Result<CertRecord> create(CertRecord cert){
		if(cert == null){
			return Result.error().setMessage("认证记录为空");
		}
		if(cert.getOwner_id() == null || Strings.isNullOrEmpty(cert.getOwner_type())){
			return Result.error().setMessage("用户为空");
		}
		if(CertType.keyOf(cert.getType()) == null){
			return Result.error().setMessage("认证类型错误！");
		}
		cert.setCert_state(CertState.SUBMIT.key());
		CertRecord old = queryCert(new Owner(cert.getOwner_type(), cert.getOwner_id()), CertType.keyOf(cert.getType()));
		if(old != null){
			if(CertState.SUCCESS.equals(CertState.keyOf(old.getCert_state()))){
				return Result.error().setMessage("已经认证通过，无需重复提交认证");
			}
			cert.setId(old.getId());
			mysqlService.update(cert);
		}else{
			mysqlService.insert(cert);
		}
		return Result.success().setObject(cert);
	}
	
	/**
	 * 审核认证
	 * @param cert_id：审验记录
	 * @param judgState: 判定结果
	 * @param by_employee：审验人
	 * @return
	 */
	public Result<CertRecord> verify(Long cert_id, CertState judgState, Long by_employee, String veri_desc){
		if(judgState == null){
			return Result.error().setMessage("请填写审核结果");
		}
		if(cert_id == null){
			return Result.error().setMessage("认证记录未找到");
		}
		CertRecord cert = mysqlService.queryById(CertRecord.class, cert_id);
		if(cert == null){
			return Result.error().setMessage("认证记录未找到");
		}
		if(!CertState.SUBMIT.equals(CertState.keyOf(cert.getCert_state()))){
			return Result.error().setMessage("当前状态不允许审核");
		}
//		Customer customer = mysqlService.queryById(Customer.class, cert.getCustomer_id());
		cert.setCert_state(judgState.key());
//		if(judgState.equals(CertState.SUCCESS)){//审核通过，根据不同类型进行设置
//			CertType type = CertType.keyOf(cert.getType());
//			switch (type) {//处理认证高逻辑
//				case EMAIL:
//					customer.setEmail(cert.getNoa());
//					mysqlService.update(customer);
//					break;
//				case IDCARD:
//					customer.setGender(cert.getGender());
//					customer.setAge(cert.getNuma());
//					customer.setName(cert.getNamea());
//					customer.setIdcard(cert.getNoa());
//					mysqlService.update(customer);
//					break;
//				case MOBILE:
//					customer.setPhone(cert.getNoa());
//					mysqlService.update(customer);
//					break;
//				case PASSPORT:
//					break;
//				case QQ:
//					customer.setQq(cert.getNoa());
//					mysqlService.update(customer);
//					break;
//			}
//		}
		//修正认证记录
		cert.setEmployee_id(by_employee);
		cert.setDesc(veri_desc);
		mysqlService.update(cert);
		return Result.success().setObject(cert);
	}
	
	/**
	 * 重新认证，解绑，等操作，不关心以前的状态，直接重置为submit 状态
	 * @param cert
	 * @return
	 */
	public Result<CertRecord> reCert(CertRecord cert){
		if(cert == null){
			return Result.error().setMessage("参数错误");
		}
		if(cert.getOwner_id() == null || Strings.isNullOrEmpty(cert.getOwner_type())){
			return Result.error().setMessage("用户不存在");
		}
		if(CertType.keyOf(cert.getType()) == null){
			return Result.error().setMessage("请填写正确的认证类型");
		}
		CertType type = CertType.keyOf(cert.getType());
		
		CertRecord old = queryCert(new Owner(cert.getOwner_type(), cert.getOwner_id()), type);
		cert.setId(old.getId());
		cert.setCert_state(CertState.SUBMIT.key());
		mysqlService.update(cert);
		
		return Result.success().setObject(cert);
	}
	
	/**
	 * 查询用户某个认证是否已经通过
	 * @return
	 */
	public boolean passed(Owner owner, CertType type){
		if(owner == null || type == null){
			return false;
		}
		return certMapper.search(owner, CertState.SUCCESS.key(), type.key()).size()>0;
	}
	
	
	
	
	
	
	
}
