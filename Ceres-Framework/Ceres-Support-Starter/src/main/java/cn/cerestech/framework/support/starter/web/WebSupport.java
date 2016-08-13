package cn.cerestech.framework.support.starter.web;

import java.io.IOException;

import com.google.common.base.Strings;

import cn.cerestech.framework.support.starter.operator.RequestOperator;
import cn.cerestech.framework.support.starter.operator.ResponseOperator;
import cn.cerestech.framework.support.starter.operator.SessionOperator;
import cn.cerestech.framework.support.starter.operator.ZipOutOperator;

public abstract class WebSupport implements RequestOperator, ResponseOperator, ZipOutOperator, SessionOperator {

	protected void redirect(String url, String params) {
		try {
			getResponse().sendRedirect(url + (Strings.isNullOrEmpty(params) ? "" : ("?" + params)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void redirect(String url) {
		redirect(url, null);
	}

	protected void forward(String url) {
		try {
			getRequest().getRequestDispatcher(url).forward(getRequest(), getResponse());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
