package nl.oscar.dpi.donutrater.gateway.service;

import nl.oscar.dpi.donutrater.library.domain.Donut;
import nl.oscar.dpi.donutrater.library.domain.DonutReview;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Component
public class ConcurrencyService {

    private Map<String, Donut> unvalidatedDonuts;
    private Map<String, Donut> validatedDonuts;

    public ConcurrencyService() {
        unvalidatedDonuts = new HashMap<>();
        validatedDonuts = new HashMap<>();
    }

    public void newDonut(Donut donut) {
        unvalidatedDonuts.put(donut.getName(), donut);
    }

    /**
     * @param donut
     * @return True if the donut is correct and doesn't need to be published to clients.
     */
    public boolean validateDonut(Donut donut) {
        Donut unvalDonut = unvalidatedDonuts.get(donut.getName());

        validatedDonuts.put(donut.getName(), donut);
        if (Objects.nonNull(unvalDonut))
            unvalidatedDonuts.remove(unvalDonut.getName(), unvalDonut);

        return donut.deepEquals(unvalDonut);
    }

    public Optional<Donut> reviewDonut(DonutReview review) {
        Donut donut = validatedDonuts.remove(review.getDonutId());

        if (Objects.isNull(donut)) {
            donut = unvalidatedDonuts.get(review.getDonutId());
            if (Objects.isNull(donut)) {
                return Optional.empty();
            }
        }

        if (review.isLike()) {
            donut.addLike(review.getClientId());
        } else {
            donut.addDislike(review.getClientId());
        }

        return Optional.of(donut);
    }
}
