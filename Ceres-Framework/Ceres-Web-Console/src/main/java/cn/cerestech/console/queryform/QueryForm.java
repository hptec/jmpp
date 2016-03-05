package cn.cerestech.console.queryform;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import cn.cerestech.console.enums.Console;
import cn.cerestech.framework.core.Classpaths;
import cn.cerestech.framework.core.enums.DescribableEnum;
import cn.cerestech.framework.core.json.Jsonable;
import cn.cerestech.framework.core.json.Jsons;

public abstract class QueryForm implements Jsonable {

	public static Map<String, QueryForm> forms = Maps.newHashMap();

	protected List<QueryTerm> terms = Lists.newArrayList();

	protected List<QueryColumn> columns = Lists.newArrayList();

	protected List<OperateButton> rowBtns = Lists.newArrayList();
	protected List<OperateButton> toolBtns = Lists.newArrayList();

	protected String title;

	static {
		@SuppressWarnings("unchecked")
		Set<Class<? extends QueryForm>> set = (Set<Class<? extends QueryForm>>) Classpaths
				.getSubTypeWith(QueryForm.class, Sets.newHashSet(""));
		for (Class<? extends QueryForm> cls : set) {
			try {
				forms.put(cls.getName().replace('.', '_'), cls.newInstance());
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}

	}

	public QueryForm addTermEnum(String key, String title, DescribableEnum defaultValue,
			Class<?> describableEnumClass) {
		QueryTerm term = new QueryTerm();
		term.setType(Console.QueryForm.TermType.ENUM.key());
		if (defaultValue != null) {
			term.setValue(defaultValue.key());
		}
		term.setTitle(title);
		term.setKey(key);
		if (describableEnumClass != null) {
			term.setEnumClass(describableEnumClass.getName().replace('.', '_'));
		}
		terms.add(term);

		return this;
	}

	public QueryForm addTermText(String key, String title, String defaultValue) {
		QueryTerm term = new QueryTerm();
		term.setType(Console.QueryForm.TermType.TEXT.key());
		term.setKey(key);
		term.setTitle(title);
		term.setValue(defaultValue);
		terms.add(term);
		return this;
	}

	public QueryForm addTermDate(String key, String title, String defaultValue) {
		QueryTerm term = new QueryTerm();
		term.setType(Console.QueryForm.TermType.DATE.key());
		term.setKey(key);
		term.setTitle(title);
		term.setValue(defaultValue);
		terms.add(term);
		return this;
	}

	public QueryForm addTermDateRange(String key, String title, String defaultValue) {
		QueryTerm term = new QueryTerm();
		term.setType(Console.QueryForm.TermType.DATERANGE.key());
		term.setKey(key);
		term.setTitle(title);
		term.setValue(defaultValue);
		terms.add(term);
		return this;
	}

	public QueryForm addTermRemoteList(String key, String title, String defaultValue, QueryTermRemoteList remoteList) {
		QueryTerm term = new QueryTerm();
		term.setType(Console.QueryForm.TermType.REMOTELIST.key());
		term.setKey(key);
		term.setTitle(title);
		term.setValue(defaultValue);
		term.setRemoteList(remoteList);
		terms.add(term);
		return this;
	}

	public QueryForm addColumnDate(String key, String title, String pattern) {
		QueryColumn column = new QueryColumn();
		column.setType(Console.QueryForm.ColumnType.DATE.key());
		column.setKey(key);
		column.setPattern(Strings.isNullOrEmpty(pattern) ? "yyyy-MM-dd HH:mm:ss" : pattern);
		column.setTitle(title);
		columns.add(column);
		return this;
	}

	public QueryForm addColumnDate(String key, String title) {
		return addColumnDate(key, title, null);
	}

	public QueryForm addColumnPercentage(String key, String title) {
		QueryColumn column = new QueryColumn();
		column.setType(Console.QueryForm.ColumnType.PERCENTAGE.key());
		column.setKey(key);
		column.setTitle(title);
		columns.add(column);
		return this;
	}

	public QueryForm addColumnText(String key, String title) {
		QueryColumn column = new QueryColumn();
		column.setType(Console.QueryForm.ColumnType.TEXT.key());
		column.setKey(key);
		column.setTitle(title);
		columns.add(column);
		return this;
	}

	public QueryForm addColumnImage(String key, String title, Integer width, Integer height) {
		QueryColumn column = new QueryColumn();
		column.setType(Console.QueryForm.ColumnType.IMAGE.key());
		column.setKey(key);
		column.setTitle(title);
		column.setWidth(width);
		column.setHeight(height);
		columns.add(column);
		return this;
	}

	public QueryForm addColumnCurrency(String key, String title) {
		QueryColumn column = new QueryColumn();
		column.setType(Console.QueryForm.ColumnType.CURRENCY.key());
		column.setKey(key);
		column.setTitle(title);
		columns.add(column);
		return this;
	}

	public QueryForm addColumnCurrencyTenK(String key, String title) {
		QueryColumn column = new QueryColumn();
		column.setType(Console.QueryForm.ColumnType.CURRENCYTENK.key());
		column.setKey(key);
		column.setTitle(title);
		columns.add(column);
		return this;
	}

	public QueryForm addColumnEnum(String key, String title, Class<?> enumClass) {
		QueryColumn column = new QueryColumn();
		column.setType(Console.QueryForm.ColumnType.ENUM.key());
		column.setKey(key);
		column.setTitle(title);
		column.setPattern(enumClass.getName().replace('.', '_'));
		columns.add(column);
		return this;
	}

	public QueryForm addColumnIcon(String key, String title) {
		QueryColumn column = new QueryColumn();
		column.setType(Console.QueryForm.ColumnType.ICON.key());
		column.setKey(key);
		column.setTitle(title);
		columns.add(column);
		return this;
	}

	public QueryForm addColumnIdIn(String idName, String title, String listName, String name) {
		QueryColumn column = new QueryColumn();
		column.setType(Console.QueryForm.ColumnType.IDIN.key());
		column.setKey(idName);
		column.setTitle(title);
		column.setPattern(listName);
		column.setExtra(name);
		columns.add(column);
		return this;
	}

	public OperateButton newToolButton() {
		OperateButton ob = new OperateButton();
		toolBtns.add(ob);
		return ob;
	}

	public OperateButton newRowButton() {
		OperateButton ob = new OperateButton();
		rowBtns.add(ob);
		return ob;
	}

	public QueryForm addToolButton(String icon, String title, String action, String href) {

		return addToolButton(icon, title, action, href, null, null);
	}

	public QueryForm addToolButton(String icon, String title, String action, String href, String comment,
			String disableIf) {
		OperateButton btn = new OperateButton();
		btn.setIcon(icon);
		btn.setTitle(title);
		btn.setAction(action);
		btn.setHref(href);
		btn.setComment(comment);
		btn.setDisableIf(disableIf);
		toolBtns.add(btn);
		return this;
	}

	public QueryForm addRowButton(String icon, String title, String action, String href) {
		return addRowButton(icon, title, action, href, null, null);
	}

	public QueryForm addRowButton(String icon, String title, String action, String href, String comments,
			String disableIf) {
		OperateButton btn = new OperateButton();
		btn.setIcon(icon);
		btn.setTitle(title);
		btn.setAction(action);
		btn.setHref(href);
		btn.setComment(comments);
		btn.setDisableIf(disableIf);
		rowBtns.add(btn);
		return this;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
