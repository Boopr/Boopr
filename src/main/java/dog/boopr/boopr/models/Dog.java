package dog.boopr.boopr.models;

import java.util.ArrayList;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name="dogs")
public class Dog {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Your dog must have a name")
    @Column
    private String name;

    @ManyToMany
    @JoinTable(
        name="dog_breeds",
        joinColumns={@JoinColumn(name="dog_id")},
        inverseJoinColumns={@JoinColumn(name="breed_id")}
    )
    private List<Breed> breeds;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dog")
    private List<Image> images;

    //0 for female, 1 for male
    // @NotBlank(message = "Please enter your dog's sex")
    @Column
    private boolean sex;

    @Size(max=500)
    @Column
    private String bio;

    @Column
    private float lon;

    @Column 
    private float lat;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    public Dog(){}

    public Dog(String name, List<Breed> breeds, boolean sex, String bio){
        this.name = name;
        this.breeds = breeds;
        this.sex = sex;
        this.bio = bio;
    }


    public List<Image> getImages() {
        if(this.images == null){

            List<Image> images = new ArrayList<Image>();
            images.add(new Image("img/noDog.png"));
            return images;

        }

        return this.images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Breed> getBreeds() {
        return this.breeds;
    }

    public void setBreeds(List<Breed> breeds) {
        this.breeds = breeds;
    }

    public boolean isSex() {
        return this.sex;
    }

    public boolean getSex() {
        return this.sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public String getBio() {
        return this.bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public float getLon() {
        return this.lon;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }

    public float getLat() {
        return this.lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public User getOwner() {
        return this.owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
    

    
}
