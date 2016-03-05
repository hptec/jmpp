package cn.cerestech.framework.web.filters;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.zip.GZIPOutputStream;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class GzipFilter implements Filter{

	public void destroy() {
		System.out.println("压缩过滤开始了");
	}

	public void doFilter(ServletRequest req, ServletResponse res,
		FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest request=(HttpServletRequest)req;
		HttpServletResponse response=(HttpServletResponse)res;
		
		MyResponse mresponse=new MyResponse(response);
		chain.doFilter(request, mresponse);
		byte[] b=mresponse.getBytes();
		System.out.println("压缩前："+b.length);
		ByteArrayOutputStream out=new ByteArrayOutputStream();
		
		GZIPOutputStream gzipOutputStream=new GZIPOutputStream(out);
		gzipOutputStream.write(b);
		gzipOutputStream.close();//将数据刷出，如果没有则不显示
		
		byte[]bu=out.toByteArray();
		System.out.println("压缩后："+bu.length);
		response.setHeader("Content-Encoding","gzip");
		response.getOutputStream().write(bu);
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("压缩过滤结束了。");
	}

}


	class MyResponse extends HttpServletResponseWrapper{
	
	private ByteArrayOutputStream bytes=new ByteArrayOutputStream();//用bytes保存数据
	private HttpServletResponse response;
	
	private PrintWriter pw;
	public MyResponse(HttpServletResponse response) {
		super(response);
		this.response=response;
	}
	
	@Override
	public ServletOutputStream getOutputStream(){
		return new MyServletOutputStream(bytes);//调用自定义类将数据写到bytes中
	}
	
	@Override
	public PrintWriter getWriter(){
		try {
			pw=new PrintWriter(new OutputStreamWriter(bytes,"utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return pw;
	}
	
	public byte[] getBytes(){//向外提供一个接口得到bytes数据。
		if(pw!=null){
			pw.close();
			return bytes.toByteArray();
		}
		
		if(bytes!=null){
			try {
				bytes.flush();
			} catch (IOException e) {
			e.printStackTrace();
			}
		}
		return bytes.toByteArray();
	}
	
}
class MyServletOutputStream extends ServletOutputStream{
	private ByteArrayOutputStream stream;
	public MyServletOutputStream(ByteArrayOutputStream stream){
	this.stream=stream;
	}
	@Override
	public void write(int b) throws IOException {
		stream.write(b);
	}
	@Override
	public boolean isReady() {
		return false;
	}
	@Override
	public void setWriteListener(WriteListener arg0) {
		
	}

}
