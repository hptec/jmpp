package cn.cerestech.framework.core;

import java.util.List;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.primitives.Ints;

public class Version {

	String major = "0";
	String minor = "0";
	String revision = "0";
	String build = "0";

	public static Version jdk() {
		Version v = new Version();

		String javaVersion = Strings.nullToEmpty(System.getProperty("java.version"));
		List<String> fullSet = Splitter.on(".").trimResults().splitToList(javaVersion);
		if (fullSet.size() >= 1) {
			v.setMajor(fullSet.get(0));
		}
		if (fullSet.size() >= 2) {
			v.setMinor(fullSet.get(1));
		}
		if (fullSet.size() >= 3) {
			String str = fullSet.get(2);
			List<String> lastList = Splitter.on("_").trimResults().splitToList(str);
			if (lastList.size() >= 1) {
				v.setRevision(lastList.get(0));
			}
			if (lastList.size() >= 2) {
				v.setBuild(lastList.get(1));
			}
		}
		return v;
	}

	public static Version from(String major, String minor, String revision, String build) {
		Version v = new Version();
		v.setMajor(major);
		v.setMinor(minor);
		v.setRevision(revision);
		v.setBuild(build);
		return v;
	}

	public static Version from(String ver) {
		Version v = new Version();
		List<String> fullSet = Splitter.on(".").trimResults().splitToList(ver);
		if (fullSet.size() >= 1) {
			v.setMajor(fullSet.get(0));
		}
		if (fullSet.size() >= 2) {
			v.setMinor(fullSet.get(1));
		}
		if (fullSet.size() >= 3) {
			v.setRevision(fullSet.get(2));
		}
		if (fullSet.size() >= 4) {
			v.setBuild(fullSet.get(3));
		}
		return v;
	}

	public static Version from(Integer major, Integer minor, Integer revision, Integer build) {
		return from(major == null ? "0" : major.toString(), minor == null ? "0" : minor.toString(), revision == null ? "0" : revision.toString(), build == null ? "0" : build.toString());
	}

	public int compareTo(Version o2) {
		// 比较major
		int ret = compare(getMajor(), o2.getMajor());
		if (ret == 0) {
			// 如果major相等继续比较
			ret = compare(getMinor(), o2.getMinor());
			if (ret == 0) {
				// 如果minor相等继续比较
				ret = compare(getRevision(), o2.getRevision());
				if (ret == 0) {
					ret = compare(getBuild(), o2.getBuild());
				}
			}
		}
		return ret;
	}

	/**
	 * 优先按照数字进行比对，否则按照字符串进行比较
	 * 
	 * @param str1
	 * @param str2
	 * @return
	 */
	private static int compare(String str1, String str2) {
		str1 = Strings.nullToEmpty(str1);
		str2 = Strings.nullToEmpty(str2);
		// 比较major
		Integer majorInt1 = Ints.tryParse(str1);
		Integer majorInt2 = Ints.tryParse(str2);
		if (majorInt1 != null && majorInt2 != null) {
			return majorInt1.compareTo(majorInt2);
		} else {
			return str1.compareTo(str2);
		}
	}
	
	public String getFullcode(){
		return toString();
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		if (!Strings.isNullOrEmpty(getMajor())) {
			buffer.append(getMajor() + ".");
			if (!Strings.isNullOrEmpty(getMinor())) {
				buffer.append(getMinor() + ".");
				if (!Strings.isNullOrEmpty(getRevision())) {
					buffer.append(getRevision() + ".");
					if (!Strings.isNullOrEmpty(getBuild())) {
						buffer.append(getBuild() + ".");
					}
				}
			}
		}

		// 删除末尾的 '.'
		if (buffer.length() >= 1) {
			buffer.deleteCharAt(buffer.length() - 1);
		}

		return buffer.toString();
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getMinor() {
		return minor;
	}

	public void setMinor(String minor) {
		this.minor = minor;
	}

	public String getRevision() {
		return revision;
	}

	public void setRevision(String revision) {
		this.revision = revision;
	}

	public String getBuild() {
		return build;
	}

	public void setBuild(String build) {
		this.build = build;
	}

}
