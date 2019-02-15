package fdmc2.web.jsfbeans;

import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Named
public class OrderedCatsBean {
	
	private String orderBy;
	
	public String getOrderBy() {
		return orderBy;
	}
	
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	
	public void orderByColumn() throws IOException {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		session.setAttribute("orderByColumnName", orderBy);
		FacesContext.getCurrentInstance().getExternalContext().redirect("/jsf/all-cats.xhtml");
	}
}
