package cn.cerestech.middleware.balance.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.cerestech.middleware.balance.entity.BufferedTransaction;

public interface BufferedTransactionDao extends JpaRepository<BufferedTransaction, Long> {

}
