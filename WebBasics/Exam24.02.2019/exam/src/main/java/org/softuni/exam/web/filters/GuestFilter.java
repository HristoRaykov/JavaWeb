package org.softuni.exam.web.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter({
		"/faces/view/home.xhtml",
		"/view/home.xhtml",
		"/faces/view/schedule.xhtml",
		"/view/schedule.xhtml",
		"/faces/view/print.xhtml",
		"/view/print.xhtml",
		"/faces/view/details.xhtml",
		"/view/details.xhtml",
})
public class GuestFilter implements Filter {
	
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) servletRequest;
		HttpServletResponse res = (HttpServletResponse) servletResponse;
		
		String userId = (String) ((HttpServletRequest) servletRequest).getSession().getAttribute("username");
		
		if (userId == null) {
			res.sendRedirect("/view/login.xhtml");
			return;
		}
		
		filterChain.doFilter(req, res);
	}
	
}
