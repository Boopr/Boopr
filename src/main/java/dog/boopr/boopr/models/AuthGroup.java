package dog.boopr.boopr.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table( name = "auth_groups")
public class AuthGroup {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    private User user;
    
    private String authGroup;

    public AuthGroup() {
    }

    public AuthGroup(User user, String authGroup) {

        this.user = user;
        this.authGroup = authGroup;
    }


    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAuthGroup() {
        return this.authGroup;
    }

    public void setAuthGroup(String authGroup) {
        this.authGroup = authGroup;
    }


}
