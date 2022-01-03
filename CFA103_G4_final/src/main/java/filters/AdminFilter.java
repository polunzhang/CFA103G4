package filters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.emperDetail.model.EmperDetailVO;

public class AdminFilter implements Filter{

	private FilterConfig config;

	public void init(FilterConfig config) {
		this.config = config;
	}

	public void destroy() {
		config = null;
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		// 【取得 session】
		HttpSession session = req.getSession();
		// 【從 session 判斷此user是否具備權限5管理員】
		List<EmperDetailVO> list = (List<EmperDetailVO>) session.getAttribute("list");
		List<Integer> list2 = new ArrayList<Integer>();
		for(EmperDetailVO aEmperDetail : list) {
			list2.add(aEmperDetail.getEmperno());
		}
	
		
		if(list2.contains(5) ) {
			chain.doFilter(request, response);
		} else {
			session.setAttribute("location", req.getRequestURI());
			res.sendRedirect(req.getContextPath() + "/back-end/back-end-home.jsp");
			return;
		}
	}
	

}
