package dog.boopr.boopr.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User {



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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<Dog> dogs;

    // @ManyToMany
    // @JoinTable(
    //     name="pup_pack",
    //     joinColumns={@JoinColumn(name="user_id")},
    //     inverseJoinColumns={@JoinColumn(name="dog_id")}
    // )
    // private List<Dog> pupPack;

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


    public List<Dog> getDogs() {
        return this.dogs;
    }

    public void setDogs(List<Dog> dogs) {
        this.dogs = dogs;
    }

}
