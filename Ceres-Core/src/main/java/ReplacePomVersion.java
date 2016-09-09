import java.io.File;
import java.io.FileWriter;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.jdom.xpath.XPath;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

public class ReplacePomVersion {

	public static final String VERSION = "3.0.19";

	public static final String DPLOY_CMD = "mvn clean deploy -Dmaven.test.skip=true";

	public static void main(String[] args) throws Throwable {
		File root = new File("..");
		System.out.println("根路径位置：" + root.getCanonicalPath());

		List<String> poms = searchPom(root.getCanonicalPath());
		for (String pom : poms) {
			setToVersion(pom, VERSION);
		}
	}

	private static List<String> searchPom(String root) throws Throwable {
		List<String> poms = Lists.newArrayList();
		File rootFile = new File(root);
		File[] pom = rootFile.listFiles(pathname -> pathname.getName()
				.endsWith("pom.xml"));

		for (File f : pom) {
			poms.add(f.getCanonicalPath());
		}

		File[] subDir = rootFile.listFiles(pathname -> pathname.isDirectory());

		for (File f : subDir) {
			File[] subPom = f.listFiles(pathname -> pathname.getName()
					.endsWith("pom.xml"));
			for (File sf : subPom) {
				poms.add(sf.getCanonicalPath());
			}
		}

		poms.forEach(str -> {
			System.out.println("发现POM文件: " + str);
		});

		return poms;
	}

	private static void setToVersion(String file, String version)
			throws Throwable {
		SAXBuilder parser = new SAXBuilder();
		Document document = parser.build(file);

		System.out.println("\n修改 POM [" + file + "]");
		// get root element
		Element root = document.getRootElement();
		String path = "/*/*[name()='version']";
		modifyPath(root, path, VERSION);

		path = "/*/*/*[name()='ceres.version']";
		modifyPath(root, path, VERSION);

		path = "/*/*/*[name()='version']";
		modifyPath(root, path, VERSION);

		XMLOutputter xmlopt = new XMLOutputter();
		FileWriter writer = new FileWriter(file);

		Format fm = Format.getPrettyFormat();

		xmlopt.setFormat(fm);

		xmlopt.output(document, writer);

		writer.close();

	}

	//
	private static void modifyPath(Element root, String path, String toText)
			throws Throwable {
		List<Element> eleList = XPath.selectNodes(root, path);
		eleList.forEach(ele -> {
			String oldVersion = ele.getText();
			if (!Strings.nullToEmpty(oldVersion).startsWith("$")) {
				ele.setText(toText);
				System.out.println("\t [" + path + "]: [" + ele.getName()
						+ "] " + oldVersion + " --> " + toText);
			}
		});
	}

}
