package nl.oscar.dpi.donutrater.gateway.service;

import nl.oscar.dpi.donutrater.library.domain.Donut;
import nl.oscar.dpi.donutrater.library.domain.DonutReview;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RelayService {

    private final JmsService jmsService;
    private final ConcurrencyService concurrencyService;

    @Autowired
    public RelayService(JmsService jmsService, ConcurrencyService concurrencyService) {
        this.jmsService = jmsService;
        this.concurrencyService = concurrencyService;
    }

    @JmsListener(destination = "submit_new_donut")
    public void addDonut(Donut donut) {
        concurrencyService.newDonut(donut);

        jmsService.notifyHiscoreNewDonut(donut);
        jmsService.updateClients(donut);
    }

    @JmsListener(destination = "submit_donut_review")
    public void reviewDonut(DonutReview review) {
        Optional<Donut> donut = concurrencyService.reviewDonut(review);

        jmsService.notifyHiscoreReviewDonut(review);
        donut.ifPresent(jmsService::updateClients);
    }

    @JmsListener(destination = "update_donut_gateway", containerFactory = "myFactory")
    public void updateDonut(Donut donut) {

        if (!concurrencyService.validateDonut(donut))
            jmsService.updateClients(donut);
    }
}
