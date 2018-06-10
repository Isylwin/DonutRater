package nl.oscar.dpi.donutrater.hiscore.service;

import nl.oscar.dpi.donutrater.library.domain.Donut;
import nl.oscar.dpi.donutrater.library.domain.DonutPackage;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Destination;
import java.util.Collection;

@Component
public class DonutUpdateService {

    private final JmsTemplate template;

    @Autowired
    public DonutUpdateService(JmsTemplate template) {
        this.template = template;
    }

    public void updateDonut(Donut donut) {
        Destination destination = new ActiveMQTopic("update_donut_gateway");

        template.convertAndSend(destination, donut);
    }

    public void sendAllDonuts(Collection<Donut> donuts, long id) {
        template.convertAndSend("response_all_donuts_" + id, new DonutPackage(donuts));
    }
}
