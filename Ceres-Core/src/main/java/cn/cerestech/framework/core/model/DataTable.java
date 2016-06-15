package cn.cerestech.framework.core.model;

import java.util.Collection;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.google.common.collect.Lists;

import cn.cerestech.framework.core.json.Jsons;

public class DataTable {

	private List<Jsons> dataSet = Lists.newArrayList();
	private List<DataColumn> columns = Lists.newArrayList();

	/**
	 * 如果不设置此选项，则默认的Json直接对应的方式<br/>
	 * 如果设置此选项，则自己处理对象转换，比如复杂对象的转换问题。
	 */
	// private Function<Jsons, T> mapper;

	/**
	 * 数据从第几行开始，如果第一行为标题行，数据则从1（第2行）开始。
	 */
	private int rowStart = 0;
	private int sheetAt = 0;

	public static DataTable of() {
		DataTable dt = new DataTable();
		return dt;
	}

	/**
	 * 添加一列
	 * 
	 * @param ognl
	 * @param title
	 * @return
	 */
	public DataTable column(String property, String title) {
		DataColumn dc = DataColumn.from(property, title);
		columns.add(dc);
		return this;
	}

	public DataTable from(XSSFWorkbook book) {
		XSSFSheet sheet = book.getSheetAt(sheetAt);
		int rownum = sheet.getLastRowNum();
		List<Jsons> data = Lists.newArrayList();
		for (int i = rowStart; i < rownum; i++) {
			// 解析每一行
			XSSFRow row = sheet.getRow(i);
			Jsons rowJson = Jsons.empty();
			for (int ci = 0, size = columns.size(); ci < size; ci++) {
				// 取每个列的值
				XSSFCell cell = row.getCell(ci);
				String key = columns.get(ci).getProperty();
				if (cell == null) {
					continue;
				}
				switch (cell.getCellType()) {
				case XSSFCell.CELL_TYPE_NUMERIC:
					rowJson.put(key, cell.getNumericCellValue());
					break;
				case XSSFCell.CELL_TYPE_BLANK:
					break;
				case XSSFCell.CELL_TYPE_BOOLEAN:
					rowJson.put(key, cell.getBooleanCellValue());
				case XSSFCell.CELL_TYPE_ERROR:
					break;
				case XSSFCell.CELL_TYPE_FORMULA:
					XSSFFormulaEvaluator e = new XSSFFormulaEvaluator(book);
					switch (e.evaluate(cell).getCellType()) {
					case XSSFCell.CELL_TYPE_BOOLEAN:
						rowJson.put(key, cell.getBooleanCellValue());
						break;
					case XSSFCell.CELL_TYPE_NUMERIC:
						rowJson.put(key, cell.getNumericCellValue());
						break;
					case XSSFCell.CELL_TYPE_STRING:
						rowJson.put(key, cell.getStringCellValue());
						break;
					case XSSFCell.CELL_TYPE_ERROR:
						rowJson.put(key, cell.getErrorCellString());
						break;
					}
					break;
				case XSSFCell.CELL_TYPE_STRING:
					rowJson.put(key, cell.getStringCellValue());
					break;
				}
			}

			data.add(rowJson);
		}

		dataSet.clear();
		dataSet.addAll(data);

		return this;

	}

	public DataTable from(HSSFWorkbook book) {
		HSSFSheet sheet = book.getSheetAt(sheetAt);
		int rownum = sheet.getLastRowNum();
		List<Jsons> data = Lists.newArrayList();
		for (int i = rowStart; i < rownum; i++) {
			// 解析每一行
			HSSFRow row = sheet.getRow(i);
			Jsons rowJson = Jsons.empty();
			for (int ci = 0, size = columns.size(); ci < size; ci++) {
				// 取每个列的值
				HSSFCell cell = row.getCell(ci);
				String key = columns.get(ci).getProperty();
				if (cell == null) {
					continue;
				}
				switch (cell.getCellType()) {
				case HSSFCell.CELL_TYPE_NUMERIC:
					rowJson.put(key, cell.getNumericCellValue());
					break;
				case HSSFCell.CELL_TYPE_BLANK:
					break;
				case HSSFCell.CELL_TYPE_BOOLEAN:
					rowJson.put(key, cell.getBooleanCellValue());
				case HSSFCell.CELL_TYPE_ERROR:
					break;
				case HSSFCell.CELL_TYPE_FORMULA:
					HSSFFormulaEvaluator e = new HSSFFormulaEvaluator(book);
					switch (e.evaluate(cell).getCellType()) {
					case HSSFCell.CELL_TYPE_BOOLEAN:
						rowJson.put(key, cell.getBooleanCellValue());
						break;
					case HSSFCell.CELL_TYPE_NUMERIC:
						rowJson.put(key, cell.getNumericCellValue());
						break;
					case HSSFCell.CELL_TYPE_STRING:
						rowJson.put(key, cell.getStringCellValue());
						break;
					case HSSFCell.CELL_TYPE_ERROR:
						rowJson.put(key, cell.getErrorCellValue());
						break;
					}
					break;
				case HSSFCell.CELL_TYPE_STRING:
					rowJson.put(key, cell.getStringCellValue());
					break;
				}
			}

			data.add(rowJson);
		}

		dataSet.clear();
		dataSet.addAll(data);

		return this;
	}

	public DataTable from(Collection<Jsons> data) {
		dataSet.clear();
		dataSet.addAll(data);
		return this;
	}

	public DataTable rowStart(int start) {
		rowStart = start;
		return this;
	}

	public DataTable sheetAt(int at) {
		sheetAt = at;
		return this;
	}

	public List<Jsons> getDataSet() {
		return dataSet;
	}

}
