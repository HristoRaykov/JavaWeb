package org.softuni.sboj.web.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter({
		"/view/index.xhtml",
		"/view/guestuser/login.xhtml",
		"/view/guestuser/register.xhtml",
		"/",
		""
})
public class GuestFilter implements Filter {
	
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		String username = (String) ((HttpServletRequest)	request).getSession().getAttribute("username");
		
		if (username!=null){
			((HttpServletResponse)response).sendRedirect("/view/loginuser/home.xhtml");
			return;
		}
		
		chain.doFilter(request,response);
	}
	
	
}
