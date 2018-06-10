package nl.oscar.dpi.donutrater.hiscore.data;

import nl.oscar.dpi.donutrater.library.domain.Donut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DonutRepository extends JpaRepository<Donut, String> {
}
