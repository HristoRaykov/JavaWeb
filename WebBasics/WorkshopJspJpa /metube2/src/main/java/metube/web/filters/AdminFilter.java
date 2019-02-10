package metube.web.filters;

import metube.domain.entities.enums.UserRole;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebFilter("/admin/tube/*")
public class AdminFilter implements Filter {
	
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		String username = (String) req.getSession().getAttribute("username");
		UserRole userRole = (UserRole) req.getSession().getAttribute("userrole");
		if (username==null){
			resp.sendRedirect("/login");
			return;
		}
		if (userRole==UserRole.USER){
			resp.sendRedirect("/");
			return;
		}
		chain.doFilter(req,resp);
	}
}
