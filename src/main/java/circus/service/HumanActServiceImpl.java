package circus.service;

import circus.model.HumanAct;
import circus.repository.HumanActRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HumanActServiceImpl implements HumanActService {

    private final HumanActRepository humanActRepository;

    public HumanActServiceImpl(HumanActRepository humanActRepository) {
        this.humanActRepository = humanActRepository;
    }

    @Override
    public List<HumanAct> findAll() {
        return humanActRepository.findAll();
    }

    @Override
    public HumanAct findById(Long id) {
        return humanActRepository.findById(id).orElse(null);
    }

    @Override
    public HumanAct save(HumanAct humanAct) {
        return humanActRepository.save(humanAct);
    }

    @Override
    public void deleteById(Long id) {
        humanActRepository.deleteById(id);
    }
}
