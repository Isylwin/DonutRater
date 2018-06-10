package nl.oscar.dpi.donutrater.hiscore.service;

import nl.oscar.dpi.donutrater.hiscore.data.DonutRepository;
import nl.oscar.dpi.donutrater.hiscore.data.DonutReviewRepository;
import nl.oscar.dpi.donutrater.library.domain.Donut;
import nl.oscar.dpi.donutrater.library.domain.DonutReview;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;

@Component
@Transactional
public class DonutJmsListener {

    private final DonutRepository donutRepository;
    private final DonutReviewRepository donutReviewRepository;
    private final DonutUpdateService service;
    private final DonutListener donutListener;
    private final ReviewListener reviewListener;

    @Autowired
    public DonutJmsListener(DonutRepository donutRepository, DonutReviewRepository donutReviewRepository, DonutUpdateService service, DonutListener donutListener, ReviewListener reviewListener) {
        this.donutRepository = donutRepository;
        this.donutReviewRepository = donutReviewRepository;
        this.service = service;
        this.donutListener = donutListener;
        this.reviewListener = reviewListener;
    }

    @JmsListener(destination = "submit_hiscore_new_donut")
    public void addDonut(Donut donut) {
        if (donutRepository.existsById(donut.getName())) {
            service.updateDonut(donutRepository.findById(donut.getName()).orElseThrow(RuntimeException::new));
        } else {
            donutRepository.save(donut);

            donutListener.onNewDonut(donut);
            service.updateDonut(donut);
        }
    }

    @JmsListener(destination = "submit_hiscore_donut_review")
    public void addReview(DonutReview review) {
        Donut donut = donutRepository.findById(review.getDonutId()).orElseThrow(RuntimeException::new);

        boolean success;

        if (review.isLike()) {
            success = donut.addLike(review.getClientId());
        } else {
            success = donut.addDislike(review.getClientId());
        }

        review.setSuccess(success);
        review.setTimestamp(LocalDateTime.now());

        donutRepository.save(donut);
        donutReviewRepository.save(review);

        reviewListener.onNewReview(review, donut);
        service.updateDonut(donut);
    }

    @JmsListener(destination = "request_all_donuts")
    public void sendAllDonuts(long id) {
        Collection<Donut> donuts = donutRepository.findAll();

        service.sendAllDonuts(donuts, id);
    }

}
