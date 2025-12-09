package circus.repository;

import circus.model.Animal;
import circus.model.AnimalAct;
import circus.model.HumanAct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimalActRepository extends JpaRepository<AnimalAct, Long> {
    // при необходимости можно добавить методы, например:
    // List<Animal> findBySpecies(String species);
}