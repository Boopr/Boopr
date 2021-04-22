package dog.boopr.boopr.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="leader")
public class PackLeader {
    

    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private long id;

    @OneToOne
    private User user;

    @ManyToMany
    @JoinTable(
        name="pup_pack",
        joinColumns={@JoinColumn(name="leader_id")},
        inverseJoinColumns={@JoinColumn(name="dog_id")}
    )
    private List<Dog> pack;

    public PackLeader(){};

    public PackLeader(long id, User user) {
        this.id = id;
        this.user = user;
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



    
}
