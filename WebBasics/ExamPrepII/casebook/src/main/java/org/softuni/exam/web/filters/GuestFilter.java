package org.softuni.exam.web.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter({
		"/faces/view/loginuser/home.xhtml",
		"/view/loginuser/home.xhtml",
		"/faces/view/loginuser/friends.xhtml",
		"/view/loginuser/friends.xhtml",
		"/faces/view/loginuser/profile.xhtml",
		"/view/loginuser/profile.xhtml"
		
})
public class GuestFilter implements Filter {
	
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		String userId = (String) ((HttpServletRequest) servletRequest).getSession().getAttribute("username");
		
		if (userId == null) {
			((HttpServletResponse) servletResponse).sendRedirect("/view/guestuser/login.xhtml");
			return;
		}
		
		filterChain.doFilter(servletRequest, servletResponse);
	}
	
}
