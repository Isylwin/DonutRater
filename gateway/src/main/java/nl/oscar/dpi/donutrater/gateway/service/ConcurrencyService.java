package nl.oscar.dpi.donutrater.gateway.service;

import nl.oscar.dpi.donutrater.library.domain.Donut;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;

@Component
public class ConcurrencyService {

    private Collection<Donut> unvalidatedDonuts;
    private Collection<Donut> validatedDonuts;

    public ConcurrencyService() {
        unvalidatedDonuts = new HashSet<>();
        validatedDonuts = new HashSet<>();
    }

    public void newDonut(Donut donut) {
        unvalidatedDonuts.add(donut);
    }
}
