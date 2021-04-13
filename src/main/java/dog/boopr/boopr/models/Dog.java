package dog.boopr.boopr.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="dogs")
public class Dog {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name="dog_breeds",
        joinColumns={@JoinColumn(name="dog_id")},
        inverseJoinColumns={@JoinColumn(name="breed_id")}
    )
    private List<Breed> breeds;

    @Column
    private boolean sex;

    //0 for female, 1 for male
    @Column
    private String bio;

    @Column
    private float lon;

    @Column 
    private float lat;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private  User owner;

    

    
}
