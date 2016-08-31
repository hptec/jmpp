package cn.cerestech.framework.core.exporter;

import java.lang.reflect.Field;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExportColumn {
	private Logger log = LogManager.getLogger();

	private String propertyName;
	private String title;
	private ExportColumFormat columnFormat;
	private Class<?> extraClass;

	@SuppressWarnings("unchecked")
	public <T> T getValue(Object obj) {
		Field f;
		Object o = null;
		try {
//			log.info("对象:" + obj + " ;属性:" + propertyName);
			f = obj.getClass().getField(propertyName);
			o = f.get(obj);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}

		return (T) o;
	}

	public ExportColumn(String propertyName, String title, ExportColumFormat columnFormat) {
		super();
		this.propertyName = propertyName;
		this.title = title;
		this.columnFormat = columnFormat;
	}

	public ExportColumn(String propertyName, String title, ExportColumFormat columnFormat, Class<?> extraClass) {
		super();
		this.propertyName = propertyName;
		this.title = title;
		this.columnFormat = columnFormat;
		this.extraClass = extraClass;
	}

	public ExportColumn(String propertyName, String title) {
		super();
		this.propertyName = propertyName;
		this.title = title;
	}

	public ExportColumn() {
		super();
	}

	public ExportColumn extraClass(Class<?> clazz) {
		this.extraClass = clazz;
		return this;
	}

	public Class<?> extraClass() {
		return this.extraClass;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ExportColumFormat getColumnFormat() {
		return columnFormat;
	}

	public void setColumnFormat(ExportColumFormat columnFormat) {
		this.columnFormat = columnFormat;
	}

}
