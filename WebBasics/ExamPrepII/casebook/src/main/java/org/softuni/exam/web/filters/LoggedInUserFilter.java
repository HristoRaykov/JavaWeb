package org.softuni.exam.web.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter({
		"/faces/view/index.xhtml",
		"/view/index.xhtml",
		"/faces/view/guestuser/login.xhtml",
		"/view/guestuser/login.xhtml",
		"/faces/view/guestuser/register.xhtml",
		"/view/guestuser/register.xhtml",
		"",
		"/"
})
public class LoggedInUserFilter implements Filter {
	
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		String userId = (String) ((HttpServletRequest) servletRequest).getSession().getAttribute("username");
		
		if (userId != null) {
			((HttpServletResponse) servletResponse).sendRedirect("/view/loginuser/home.xhtml");
			return;
		}
		
		filterChain.doFilter(servletRequest, servletResponse);
	}
	
}
