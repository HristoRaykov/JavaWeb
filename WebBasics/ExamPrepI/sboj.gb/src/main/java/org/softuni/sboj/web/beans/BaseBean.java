package org.softuni.sboj.web.beans;

import javax.faces.context.FacesContext;
import java.io.IOException;

public abstract class BaseBean {
    protected void redirect(String url) {
        try {
            FacesContext
                    .getCurrentInstance()
                    .getExternalContext()
                    .redirect(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
