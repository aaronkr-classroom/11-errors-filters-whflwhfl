package ch12.com.filter;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
public class initParamFilter implements Filter {
	private FilterConfig filterConfig = null;
	@Override
	public void init(FilterConfig filterConfig) 
			throws ServletException {
		System.out.println("Filter01 초기화");
	}
	@Override
	public void doFilter(
			ServletRequest req,
			ServletResponse res,
			FilterChain filterChain
			) throws IOException, ServletException {
		System.out.println("Filter02.jsp 수행...");
		String id = req.getParameter("id");
		String pw = req.getParameter("passwd");
		
		String param1 = filterConfig.getInitParameter("param1");
		String param2 = filterConfig.getInitParameter("param2");
		
		String message;
		
		res.setCharacterEncoding("UTF-8");
		res.setContentType("text/html; charset=UTF-8");
		PrintWriter writer = res.getWriter();
		
		
		if (id.equals(param1) && pw.equals(param2)) {
			message = "<h3>로그인 성공했습니다!</h3>";
		}else {
			message = "<h3 style=\"color:red\">로그인 실패했습니다!</h3>";
		}
			
			writer.println(message);
			
		filterChain.doFilter(req,  res); // 없으면 여기까지 수행하고 멈춤
		
	}
	@Override
	public void destroy() {
		System.out.println("Filter02 해제...");
	}
}
