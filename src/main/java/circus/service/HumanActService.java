package circus.service;

import circus.model.HumanAct;

import java.util.List;

public interface HumanActService {
    List<HumanAct> findAll();

    HumanAct findById(Long id);

    HumanAct save(HumanAct humanAct);

    void deleteById(Long id);
}
