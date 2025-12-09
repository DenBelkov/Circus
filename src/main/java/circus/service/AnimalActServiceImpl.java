package circus.service;

import circus.model.AnimalAct;
import circus.repository.AnimalActRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimalActServiceImpl implements AnimalActService {

    private final AnimalActRepository animalActRepository;

    public AnimalActServiceImpl(AnimalActRepository animalActRepository) {
        this.animalActRepository = animalActRepository;
    }

    @Override
    public List<AnimalAct> findAll() {
        return animalActRepository.findAll();
    }

    @Override
    public AnimalAct findById(Long id) {
        return animalActRepository.findById(id).orElse(null);
    }

    @Override
    public AnimalAct save(AnimalAct animalAct) {
        return animalActRepository.save(animalAct);
    }

    @Override
    public void deleteById(Long id) {
        animalActRepository.deleteById(id);
    }
}
