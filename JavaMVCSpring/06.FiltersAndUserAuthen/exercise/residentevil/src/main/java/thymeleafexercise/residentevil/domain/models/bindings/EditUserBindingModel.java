package thymeleafexercise.residentevil.domain.models.bindings;

import thymeleafexercise.residentevil.domain.entities.Role;

import java.util.Set;

public class EditUserBindingModel {

    private String id;
    private String username;

    private Set<String> authorities;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<String> authorities) {
        this.authorities = authorities;
    }
}
