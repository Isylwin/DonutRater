package nl.oscar.dpi.donutrater.library.domain;

import org.junit.Assert;
import org.junit.Test;

public class DonutTest {

    @Test
    public void deepEquals() {
        Donut a = new Donut("Henk", "Henk");
        Donut b = new Donut("Henk", "Henk");

        a.addLike(1);
        b.addLike(1);

        Assert.assertTrue(a.deepEquals(b));
    }
}