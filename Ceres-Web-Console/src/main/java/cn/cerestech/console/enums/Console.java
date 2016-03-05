package cn.cerestech.console.enums;

import cn.cerestech.framework.core.enums.DescribableEnum;

public class Console {

	public static class Employee {

		public enum ActionType implements DescribableEnum {
			LOGIN_SUCCESS("LOGIN_SUCCESS", "登录成功"), //
			LOGIN_PWD_ERROR("LOGIN_PWD_ERROR", "登录密码错误"), //
			LOGIN_FROZEN("LOGIN_FROZEN", "登录被冻结"),//

			;
			private String key, desc;

			private ActionType(String key, String desc) {
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

		public enum Scope implements DescribableEnum {
			REGISTER("R", "注册时间"), //
			LOGIN("L", "登录时间"), //

			;
			private String key, desc;

			private Scope(String key, String desc) {
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

	}

	public enum SysObj implements DescribableEnum {
		LOGIN("LOGIN", "登录"), //

		;
		private String key, desc;

		private SysObj(String key, String desc) {
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

	public static class Department {

		public enum Move implements DescribableEnum {
			UP("U", "上移"), //
			DOWN("D", "下移"), //
			LEFT("L", "升级"), //
			RIGHT("R", "降级"),//

			;
			private String key, desc;

			private Move(String key, String desc) {
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

	}

	public static class QueryForm {

		public enum TermType implements DescribableEnum {
			ENUM("ENUM", "枚举"), //
			ENUMLIST("ENUMLIST", "枚举列表"), //
			DATE("DATE", "日期"), //
			TEXT("TEXT", "文本"), //
			DATERANGE("DATERANGE", "日期范围"), //
			REMOTELIST("REMOTELIST", "远程列表"),//
			;
			private String key, desc;

			private TermType(String key, String desc) {
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

		public enum ColumnType implements DescribableEnum {
			TEXT("TEXT", "文本"), //
			CURRENCY("CURRENCY", "货币"), //
			DATE("DATE", "日期"), //
			ENUM("ENUM", "枚举"), //
			IMAGE("IMAGE", "图像"), //
			IDIN("IDIN", "数据"), //
			ICON("ICON", "图标"), //
			PERCENTAGE("PERCENTAGE", "百分数"), //
			CURRENCYTENK("CURRENCYTENK", "货币(万)"),//
			;
			private String key, desc;

			private ColumnType(String key, String desc) {
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

	}

	public static class SysMenu {

		public enum Type implements DescribableEnum {
			MENU("Y", "菜单"), //
			AUTHORITY("N", "权限"), //

			;
			private String key, desc;

			private Type(String key, String desc) {
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

	}

}
