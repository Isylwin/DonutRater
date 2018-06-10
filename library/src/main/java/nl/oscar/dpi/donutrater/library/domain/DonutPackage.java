package nl.oscar.dpi.donutrater.library.domain;

import java.util.ArrayList;
import java.util.Collection;

public class DonutPackage {

    private Collection<Donut> donuts;

    public DonutPackage() {
        donuts = new ArrayList<>();
    }

    public DonutPackage(Collection<Donut> donuts) {
        this.donuts = donuts;
    }

    public Collection<Donut> getDonuts() {
        return donuts;
    }

    public void setDonuts(Collection<Donut> donuts) {
        this.donuts = donuts;
    }
}
