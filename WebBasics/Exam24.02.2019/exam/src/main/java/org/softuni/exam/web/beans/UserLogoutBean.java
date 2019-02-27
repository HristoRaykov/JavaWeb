package org.softuni.exam.web.beans;

import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named
public class UserLogoutBean extends BaseBean {
	
    public UserLogoutBean() {
    }

    public void logout() {
        FacesContext
                .getCurrentInstance()
                .getExternalContext()
                .invalidateSession();
        
        this.redirect("/view/index.xhtml");
    }
}
