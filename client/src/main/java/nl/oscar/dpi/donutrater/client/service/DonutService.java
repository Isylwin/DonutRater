package nl.oscar.dpi.donutrater.client.service;

import nl.oscar.dpi.donutrater.library.domain.Donut;
import nl.oscar.dpi.donutrater.library.domain.DonutPackage;
import nl.oscar.dpi.donutrater.library.domain.DonutReview;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class DonutService {

    private final JmsTemplate template;

    @Autowired
    public DonutService(JmsTemplate template) {
        this.template = template;
    }

    public void reviewDonut(Donut donut, boolean liked) {
        DonutReview review = new DonutReview(ClientIdGenerator.getId(), donut.getName(), liked);

        template.convertAndSend("submit_donut_review", review);
    }

    public void addDonut(String name, String description) {
        Donut donut = new Donut(name, description);

        template.convertAndSend("submit_new_donut", donut);
    }

    public DonutPackage initDonuts() {
        template.convertAndSend("request_all_donuts", ClientIdGenerator.getId());
        template.setReceiveTimeout(5000L);
        return (DonutPackage) template.receiveAndConvert("response_all_donuts_" + ClientIdGenerator.getId());
    }
}
