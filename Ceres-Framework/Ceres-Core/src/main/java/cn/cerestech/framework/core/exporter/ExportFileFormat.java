package cn.cerestech.framework.core.exporter;

import cn.cerestech.framework.core.enums.DescribableEnum;

public enum ExportFileFormat implements DescribableEnum {

	CSV("CSV", "CSV文件"), //
	XLS("XLS", "EXCEL2007"), //
	XLSX("XLSX", "EXCEL2010"),//
	;
	private String key, desc;

	private ExportFileFormat(String key, String desc) {
		this.key = key;
		this.desc = desc;
	}

	@Override
	public String key() {
		return key;
	}

	@Override
	public String desc() {
		return desc;
	}
}
