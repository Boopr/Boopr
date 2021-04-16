package dog.boopr.boopr.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String url;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "image")
    private List<Boop> boops;

    @ManyToOne
    @JoinColumn(name="dog_id")
    private Dog dog;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    public Image() {
    }

    public Image(User user, Dog dog, String url) {
        this.url = url;
        this.dog = dog;
        this.user = user;
    }

    public Image(String url, List<Boop> boops) {

        this.url = url;
        this.boops = boops;
    }

    public Image(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + id + "'" +
            ", url='" + url + "'" +
            ", boops='" + boops + "'" +
            "}";
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return "/" + this.url;
    }

    public void setUrl(String url) {
        this.url =  url;
    }

    public List<Boop> getBoops() {
        return this.boops;
    }

    public void setBoops(List<Boop> boops) {
        this.boops = boops;
    }

    public Dog getDog() {
        return this.dog;
    }

    public void setDog(Dog dog) {
        this.dog = dog;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
}
