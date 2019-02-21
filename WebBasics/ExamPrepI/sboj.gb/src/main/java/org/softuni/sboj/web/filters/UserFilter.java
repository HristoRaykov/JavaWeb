package org.softuni.sboj.web.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter({
		"/view/loginuser/home.xhtml",
		"/view/jobs/job-delete.xhtml",
		"/view/jobs/job-details.xhtml",
		"/view/loginuser/add-job.xhtml"
})
public class UserFilter implements Filter {
	
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		String username = (String) ((HttpServletRequest)	request).getSession().getAttribute("username");
		
		if (username==null){
			((HttpServletResponse)response).sendRedirect("/view/guestuser/login.xhtml");
			return;
		}
		
		chain.doFilter(request,response);
	}
	
	
}
