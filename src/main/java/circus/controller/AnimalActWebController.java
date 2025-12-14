package circus.controller;

import circus.model.Animal;
import circus.model.AnimalAct;
import circus.model.Employee;
import circus.service.AnimalActService;
import circus.service.AnimalService;
import circus.service.EmployeeService;
import circus.service.PerformanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Веб-контроллер для работы с сущностями выступлений.
 * <p>
 * Обеспечивает обработку HTTP-запросов, связанных с добавлением, редактированием,
 * удалением и отображением списка выступлений. Использует Thymeleaf-шаблоны
 * для отображения данных в пользовательском интерфейсе.
 * </p>
 */
@Controller
@RequestMapping("/animalActs")
public class AnimalActWebController {

    @ModelAttribute("animalAct")
    public AnimalAct prepareAnimalAct() {
        AnimalAct act = new AnimalAct();
        act.setAnimal(new Animal());
        act.setAnimalTrainer(new Employee());
        return act;
    }

    @Autowired
    private AnimalActService animalActService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private PerformanceService performanceService;

    @Autowired
    private AnimalService animalService;// ← добавить это поле

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("animalAct", new AnimalAct());
        model.addAttribute("employees", employeeService.findAll()); // чтобы был список тренеров
        return "animalAct_form";
    }

    @GetMapping("/edit/{id}")
    public String editAnimalAct(@PathVariable Long id, Model model) {
        model.addAttribute("animalActs", animalActService.findAll());
        model.addAttribute("animalAct", animalActService.findById(id)); // не new AnimalAct()
        model.addAttribute("editingId", id);
        model.addAttribute("employees", employeeService.findAll());
        model.addAttribute("animals", animalService.findAll());
        model.addAttribute("performances", performanceService.findAll());
        return "animalActs";
    }


    @GetMapping
    public String listAnimalActs(Model model,
                                 @RequestParam(required = false) String msg) {
        model.addAttribute("animalActs", animalActService.findAll());
        model.addAttribute("animalAct", new AnimalAct());
        model.addAttribute("editingId", null);
        model.addAttribute("message", msg);
        model.addAttribute("employees", employeeService.findAll());
        model.addAttribute("performances", performanceService.findAll());
        model.addAttribute("animals", animalService.findAll());// уже есть
        return "animalActs";
    }

    @GetMapping("/delete/{id}")
    public String deleteAnimalAct(@PathVariable Long id) {
        animalActService.deleteById(id);
        return "redirect:/animalActs";
    }

    @PostMapping("/save")
    public String saveAnimalAct(@ModelAttribute("animalAct") AnimalAct animalAct) {
        animalActService.save(animalAct);
        System.out.println("SAVE animalAct id = " + animalAct.getId());
        return "redirect:/animalActs";
    }


}
