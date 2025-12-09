package circus.controller;

import circus.model.Animal;
import circus.service.AnimalService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/animals")
public class AnimalWebController {

    private final AnimalService animalService;

    public AnimalWebController(AnimalService animalService) {
        this.animalService = animalService;
    }

    // Показать список животных
    @GetMapping
    public String listAnimals(Model model) {
        List<Animal> animals = animalService.findAll();
        model.addAttribute("animals", animals);
        model.addAttribute("animal", new Animal()); // для формы добавления
        return "animals"; // animals.html
    }

    // Сохранить новое или отредактированное животное
    @PostMapping("/save")
    public String saveAnimal(@ModelAttribute("animal") Animal animal) {
        animalService.save(animal);
        return "redirect:/animals";
    }

    // Открыть страницу с формой редактирования (или тот же шаблон с editingId)
    @GetMapping("/edit/{id}")
    public String editAnimal(@PathVariable Long id, Model model) {
        Animal animal = animalService.findById(id);
        model.addAttribute("animals", animalService.findAll());
        model.addAttribute("animal", animal);
        model.addAttribute("editingId", id); // если понадобиться выделять строку
        return "animals";
    }

    // Удалить животное
    @GetMapping("/delete/{id}")
    public String deleteAnimal(@PathVariable Long id) {
        animalService.deleteById(id);
        return "redirect:/animals";
    }
}
