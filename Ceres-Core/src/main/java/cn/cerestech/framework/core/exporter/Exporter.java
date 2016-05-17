package cn.cerestech.framework.core.exporter;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import cn.cerestech.framework.core.date.Moment;
import cn.cerestech.framework.core.enums.DescribableEnum;
import cn.cerestech.framework.core.enums.EnumCollector;
import cn.cerestech.framework.core.utils.Random;

/**
 * 导出
 * 
 * @author harryhe
 *
 */
public class Exporter<T>{

	private List<T> dataList = Lists.newArrayList();
	private List<ExportColumn> columnList = Lists.newArrayList();

	private ExportFileFormat fileFormat = ExportFileFormat.XLSX;

	/**
	 * 输出下载的文件名
	 */
	private String exportFilename;

	private String sheetTitle = "sheet1";

	public static <T> Exporter<T> addSheet(List<T> list) {
		Exporter<T> e = new Exporter<T>();
		e.dataList.addAll(list);
		return e;
	}

	public static <T> Exporter<T> addSheet(String title, List<T> list) {
		Exporter<T> e = new Exporter<T>();
		e.dataList.addAll(list);
		e.title(title);
		return e;
	}

	public Exporter<T> addColumnBoolean(String prop, String title) {
		columnList.add(new ExportColumn(prop, title, ExportColumFormat.BOOLEAN));
		return this;
	}

	public Exporter<T> addColumnBigDecimal(String prop, String title) {
		columnList.add(new ExportColumn(prop, title, ExportColumFormat.BIGDECIMAL));
		return this;
	}

	public Exporter<T> addColumnPercentage(String prop, String title) {
		columnList.add(new ExportColumn(prop, title, ExportColumFormat.PERCENTAGE));
		return this;
	}

	public Exporter<T> addColumnDate(String prop, String title) {
		columnList.add(new ExportColumn(prop, title, ExportColumFormat.DATE));
		return this;
	}

	public Exporter<T> addColumnInteger(String prop, String title) {
		columnList.add(new ExportColumn(prop, title, ExportColumFormat.INTEGER));
		return this;
	}

	public Exporter<T> addColumnLong(String prop, String title) {
		columnList.add(new ExportColumn(prop, title, ExportColumFormat.LONG));
		return this;
	}

	public Exporter<T> addColumnRichText(String prop, String title) {
		columnList.add(new ExportColumn(prop, title, ExportColumFormat.RICHTEXT));
		return this;
	}

	public Exporter<T> addColumnText(String prop, String title) {
		columnList.add(new ExportColumn(prop, title, ExportColumFormat.TEXT));
		return this;
	}

	public Exporter<T> addColumnEnum(String prop, String title, Class<?> clazz) {
		ExportColumn ec = new ExportColumn(prop, title, ExportColumFormat.ENUM, clazz);
		columnList.add(ec);
		return this;
	}

	public Exporter<T> fileFormat(ExportFileFormat format) {
		fileFormat = format;
		return this;
	}

	public Exporter<T> title(String title) {
		sheetTitle = title;
		return this;
	}

	public void exportTo(HttpServletResponse response) throws IOException {
		byte[] bytes = null;
		switch (fileFormat) {
		case CSV:
			break;
		case XLS:
		case XLSX:
			bytes = exportToExcel();
		default:
			break;
		}

		String filename = Strings.isNullOrEmpty(exportFilename) ? Random.uuid() + "." + fileFormat.key().toLowerCase()
				: exportFilename;

		if (bytes != null) {
			// 清空response
			response.reset();
			// 设置response的Header
			response.addHeader("Content-Disposition", "attachment;filename=" + filename);
			response.addHeader("Content-Length", "" + bytes.length);
			OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
			response.setContentType("application/octet-stream");
			toClient.write(bytes);
			toClient.flush();
		}

	}

	public byte[] exportToExcel() {
		Workbook wb = null;
		switch (fileFormat) {
		case XLS:
			wb = new HSSFWorkbook();
			break;
		case XLSX:
			wb = new XSSFWorkbook();
			break;
		default:
			throw new IllegalArgumentException("文档格式不正确");
		}
		// 创建sheet对象
		Sheet sheet1 = wb.createSheet(sheetTitle);

		// 写入标题行
		Row titleRow = sheet1.createRow(0);
		int size = columnList.size();
		for (int c = 0; c < size; c++) {
			ExportColumn column = columnList.get(c);
			Cell cell = titleRow.createCell(c);
			cell.setCellValue(column.getTitle());
		}

		// 循环写入行数据
		size = dataList.size();
		if (size > 0) {
			for (int i = 0; i < size; i++) {
				T t = dataList.get(i);

				Row row = sheet1.createRow(i + 1);
				// 循环写入列数据
				int colSize = columnList.size();
				for (int c = 0; c < colSize; c++) {
					Cell cell = row.createCell(c);
					try {
						ExportColumn column = columnList.get(c);
						switch (column.getColumnFormat()) {
						case BOOLEAN:
							Boolean vBool = column.getValue(t);
							cell.setCellValue(vBool);
							break;
						case DATE:
							Date vDate = column.getValue(t);
							if (vDate != null) {
								cell.setCellValue(Moment.from(vDate).formatDateTime());
							}
							break;
						case BIGDECIMAL:
							BigDecimal vNumber = column.getValue(t);
							if (vNumber != null) {
								cell.setCellValue(vNumber.toString());
							}
							break;
						case INTEGER:
							Integer vInt = column.getValue(t);
							cell.setCellValue(vInt);
							break;
						case LONG:
							Long vLong = column.getValue(t);
							cell.setCellValue(vLong);
							break;
						case TEXT:
						case RICHTEXT:
							String vStr = column.getValue(t);
							cell.setCellValue(vStr);
							break;
						case ENUM:
							String vEnum = column.getValue(t);
							DescribableEnum de = EnumCollector.forClass(column.extraClass()).keyOf(vEnum);
							cell.setCellValue(de.desc());
							break;
						case PERCENTAGE:
							BigDecimal vPercentage = column.getValue(t);
							if (vPercentage != null) {
								BigDecimal v = vPercentage.multiply(new BigDecimal(100)).setScale(2, RoundingMode.DOWN);
								cell.setCellValue(v.toString() + "%");
							}
							break;
						}

					} catch (SecurityException | IllegalArgumentException e) {
					}
				}

			}
		}

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			wb.write(os);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return os.toByteArray();
	}

}
