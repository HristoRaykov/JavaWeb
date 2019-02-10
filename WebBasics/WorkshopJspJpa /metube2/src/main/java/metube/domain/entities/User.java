package metube.domain.entities;

import metube.domain.entities.enums.UserRole;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

    private String username;
    private String email;
    private String password;
    private List<Tube> tubes;
    private UserRole userRole;

    public User() {
    }

    @Column(name = "username", nullable = false, unique = true)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "email", nullable = false)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "password", nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @OneToMany(targetEntity = Tube.class, mappedBy = "uploader", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    public List<Tube> getTubes() {
        return tubes;
    }

    public void setTubes(List<Tube> tubes) {
        this.tubes = tubes;
    }
	
    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
	public UserRole getUserRole() {
		return userRole;
	}
	
	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}
}
