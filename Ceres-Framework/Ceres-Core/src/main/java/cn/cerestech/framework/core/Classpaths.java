package cn.cerestech.framework.core;

import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.Scanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import com.google.common.base.Predicate;
import com.google.common.collect.Sets;

public class Classpaths {

	private static ConfigurationBuilder getBuilder(Scanner... scanners) {
		ConfigurationBuilder builder = new ConfigurationBuilder();
		if (scanners.length > 0) {
			builder.addScanners(scanners);
		}
		return builder;
	}

	public static Set<Class<?>> getTypesAnnotatedWith(Class<? extends Annotation> cls, Set<String> set) {
		Set<URL> urls = Sets.newHashSet();
		if (set.size() > 0) {
			for (String pkg : set) {
				urls.addAll(ClasspathHelper.forPackage(pkg));
			}
		}
		Reflections reflections = new Reflections(getBuilder().setScanners(new TypeAnnotationsScanner()).addUrls(urls));
		return reflections.getTypesAnnotatedWith(cls);
	}

	public static Set<?> getSubTypeWith(Class<?> cls, Set<String> set) {
		Set<URL> urls = Sets.newHashSet();
		if (set.size() > 0) {
			for (String pkg : set) {
				urls.addAll(ClasspathHelper.forPackage(pkg));
			}
		}
		Reflections reflections = new Reflections(getBuilder().setScanners(new SubTypesScanner()).addUrls(urls));
		return reflections.getSubTypesOf(cls);
	}

	public static Set<String> getResources(Predicate<String> filter, String... set) {
		Set<URL> urls = Sets.newHashSet();
		for (String pkg : set) {
			urls.addAll(ClasspathHelper.forPackage(pkg));
		}
		Reflections reflections = new Reflections(getBuilder(new ResourcesScanner()).addUrls(urls));
		return reflections.getResources(filter);
	}

}
