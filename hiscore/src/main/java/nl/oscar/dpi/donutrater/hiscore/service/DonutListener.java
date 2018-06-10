package nl.oscar.dpi.donutrater.hiscore.service;

import nl.oscar.dpi.donutrater.library.domain.Donut;

public interface DonutListener {

    void onNewDonut(Donut donut);
}
