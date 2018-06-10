package nl.oscar.dpi.donutrater.gateway.service;

import nl.oscar.dpi.donutrater.library.domain.Donut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

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
}
