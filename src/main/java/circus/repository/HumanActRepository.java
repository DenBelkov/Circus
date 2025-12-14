package circus.repository;

import circus.model.Animal;
import circus.model.AnimalAct;
import circus.model.HumanAct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HumanActRepository extends JpaRepository<HumanAct, Long> {
    // при необходимости можно добавить методы, например:
        List<HumanAct> findByMainPerformer_Id(Long performerId);
}
