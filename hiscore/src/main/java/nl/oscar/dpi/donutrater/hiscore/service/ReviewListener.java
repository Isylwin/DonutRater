package nl.oscar.dpi.donutrater.hiscore.service;

import nl.oscar.dpi.donutrater.library.domain.Donut;
import nl.oscar.dpi.donutrater.library.domain.DonutReview;

public interface ReviewListener {

    void onNewReview(DonutReview review, Donut donut);
}
