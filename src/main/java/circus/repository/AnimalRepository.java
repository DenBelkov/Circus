package circus.repository;

import circus.model.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {
    // при необходимости можно добавить методы, например:
    // List<Animal> findBySpecies(String species);
}
