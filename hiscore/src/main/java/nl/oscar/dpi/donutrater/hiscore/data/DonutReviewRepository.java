package nl.oscar.dpi.donutrater.hiscore.data;

import nl.oscar.dpi.donutrater.library.domain.DonutReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DonutReviewRepository extends JpaRepository<DonutReview, Long> {
}
