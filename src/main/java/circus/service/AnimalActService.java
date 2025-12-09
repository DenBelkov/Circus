package circus.service;

import circus.model.AnimalAct;

import java.util.List;

public interface AnimalActService {
    List<AnimalAct> findAll();

    AnimalAct findById(Long id);

    AnimalAct save(AnimalAct animalAct);

    void deleteById(Long id);
}
