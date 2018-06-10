package nl.oscar.dpi.donutrater.gateway.service;

import nl.oscar.dpi.donutrater.library.domain.Donut;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Destination;

@Component
public class JmsService {

    private final JmsTemplate template;

    @Autowired
    public JmsService(JmsTemplate template) {
        this.template = template;
    }

    public void notifyHiscoreNewDonut(Donut donut) {
        template.convertAndSend("submit_new_donut", donut);
    }

    public void updateClients(Donut donut) {
        Destination destination = new ActiveMQTopic("update_donut");
    }
}
