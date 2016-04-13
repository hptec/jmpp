package cn.cerestech.framework.core.service;

import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseService implements Logable{

	@Autowired
	protected MysqlService mysqlService;

	@Autowired
	protected BaseMapper baseMapper;

	public static final SimpleDateFormat FORMAT_TIME = new SimpleDateFormat("HH:mm:ss");
	public static final SimpleDateFormat FORMAT_DATE = new SimpleDateFormat("yyyy-MM-dd");
	public static final SimpleDateFormat FORMAT_DATETIME = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static final SimpleDateFormat FORMAT_MILLISECOND = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");



}
