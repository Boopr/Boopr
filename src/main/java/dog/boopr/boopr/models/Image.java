package dog.boopr.boopr.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

    public Image() {
    }

    public Image(String url, List<Boop> boops) {

        this.url = url;
        this.boops = boops;
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
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Boop> getBoops() {
        return this.boops;
    }

    public void setBoops(List<Boop> boops) {
        this.boops = boops;
    }


    
}
