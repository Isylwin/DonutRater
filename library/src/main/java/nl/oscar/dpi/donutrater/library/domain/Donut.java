package nl.oscar.dpi.donutrater.library.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Donut {

    @Id
    private String name;
    private String description;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Long> likes;
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Long> dislikes;

    public Donut() {
        this(null, null);
    }

    public Donut(String name, String description) {
        this.name = name;
        this.description = description;
        likes = new HashSet<>();
        dislikes = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Collection<Long> getLikes() {
        return likes;
    }

    public Collection<Long> getDislikes() {
        return dislikes;
    }

    public boolean addLike(long like) {
        dislikes.remove(like);

        boolean alreadyPresent = likes.remove(like);
        if (!alreadyPresent) {
            likes.add(like);
        }

        return !alreadyPresent;
    }

    public boolean addDislike(long dislike) {
        likes.remove(dislike);

        boolean alreadyPresent = dislikes.remove(dislike);
        if (!alreadyPresent) {
            dislikes.add(dislike);
        }

        return !alreadyPresent;
    }

    public boolean deepEquals(Donut o) {
        return this.equals(o) && description.equals(o.description) && likes.equals(o.likes) && dislikes.equals(o.dislikes);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Donut donut = (Donut) o;
        return name.equals(donut.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
