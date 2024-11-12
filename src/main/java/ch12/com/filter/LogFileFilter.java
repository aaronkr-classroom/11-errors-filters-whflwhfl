package ch12.com.filter;

import jakarta.servlet.*;
import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;





public class LogFileFilter implements Filter {
	@Override
	public void init(FilterConfig filterConfig) 
			throws ServletException {
		System.out.println("Filter03 초기화");
		
		String filename = filterConfig.getInitParameter("filename");
		if (filename == null ) throw new ServletException(
				"로그 파일의 이름을 찾을 수 없습니다.");
		
		try {
			writer = new PrintWriter(
					new FileWriter(filename, true), true);
			
					
		}catch (IOException e) {
			throw new ServletException("로그 파일을 열 수 없습니다.");
		}
		
	}
	@Override
	public void doFilter(
			ServletRequest req,
			ServletResponse res,
			FilterChain filterChain
			) throws IOException, ServletException {
		System.out.println("Filter03.jsp 수행...");
		
		FileWriter.printf("현재일시: %s %n", getCurrentTime());
		String clientAddr = req.getRemoteAddr();
		writer.println("클라인언트 주소: %s %n ", clientAddr);
		
		
		filterChain.doFilter(req,  res); // 없으면 여기까지 수행하고 멈춤
		
		String contentType = res.getContentType();
		writer.printf("문서의 콘텐츠 유형: %S %n", contentType);
		writer.println("-----------------------------------------------");
		
	}
	@Override
	public void destroy() {
		System.out.println("Filter03 해제...");
		writer.close();
	}
	
	private String getCurrentTime() {
		DateFormat formatter = new SimpleDateFormat("yyyy/MM//dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		
		return formatter.format(calendar.getTime());
	}
}
