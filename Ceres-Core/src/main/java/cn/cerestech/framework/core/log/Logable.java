package cn.cerestech.framework.core.log;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public interface Logable {

	public Logger log = LogManager.getLogger();
}
