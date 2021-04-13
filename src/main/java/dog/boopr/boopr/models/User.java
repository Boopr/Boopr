package dog.boopr.boopr.models;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.websocket.OnMessage;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Entity
@Table(name = "users")
public class User implements UserDetails{
    //implements userdetails so that we dont need a user principal when we do a service call on it

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "You must enter a username")
    @Size(min = 5, message = "Username must be between 5-16 characters")
    private String username;

    @Email(message = "Not a valid email address")
    @NotBlank(message = "You must enter an email")
    private String email;

    @NotBlank(message = "You must enter a password")
    @Size(min = 6, message = "Password must be longer than 6 characters")
    @Column(name = "password", nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<AuthGroup> authGroups;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<Dog> dogs;

    public User() {
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<AuthGroup> getAuthGroups() {
        return this.authGroups;
    }

    public void setAuthGroups(List<AuthGroup> authGroups) {
        this.authGroups = authGroups;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //if there are not authgroups found on the user return an empty collection
        if(null == this.authGroups){
            return Collections.emptySet();
        }

        //we create the set for granted authorities
        Set<SimpleGrantedAuthority> grantedAuthorities = new HashSet<>();

        //we loop though them and add them to the hashset
        for(int i = 0; i < authGroups.size()-1; i++){
            grantedAuthorities.add(
                new SimpleGrantedAuthority(authGroups.get(i).getAuthGroup())
            );
        }
    
        return grantedAuthorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
