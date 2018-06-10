package nl.oscar.dpi.donutrater.client.javafx;

import com.google.common.base.Objects;
import nl.oscar.dpi.donutrater.library.domain.Donut;

public class DisplayDonut {

    private Donut donut;

    public DisplayDonut(Donut donut) {
        this.donut = donut;
    }

    public Donut getDonut() {
        return donut;
    }

    public void setDonut(Donut donut) {
        this.donut = donut;
    }

    public String getName() {
        return donut.getName();
    }

    public String getDescription() {
        return donut.getDescription();
    }

    public Integer getLikes() {
        return donut.getLikes().size() - donut.getDislikes().size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DisplayDonut)) return false;
        DisplayDonut that = (DisplayDonut) o;
        return Objects.equal(donut, that.donut);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(donut);
    }

    @Override
    public String toString() {
        int likes = donut.getLikes().size() - donut.getDislikes().size();

        return String.format("Name: %s, Description: %s, Likes: %d", donut.getName(), donut.getDescription(), likes);
    }
}
