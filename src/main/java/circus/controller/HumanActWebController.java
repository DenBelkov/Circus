package circus.controller;

import circus.model.HumanAct;
import circus.service.EmployeeService;
import circus.service.HumanActService;
import circus.service.PerformanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Веб-контроллер для работы с сущностями выступлений.
 * <p>
 * Обеспечивает обработку HTTP-запросов, связанных с добавлением, редактированием,
 * удалением и отображением списка выступлений. Использует Thymeleaf-шаблоны
 * для отображения данных в пользовательском интерфейсе.
 * </p>
 */
@Controller
@RequestMapping("/humanActs")
public class HumanActWebController {

    /**
     * Сервис для выполнения операций с объектами {@link HumanAct}.
     */
    @Autowired
    private HumanActService humanActService;


    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private PerformanceService performanceService;
    /**
     * Отображает список всех выступлений и форму добавления нового выступления.
     *
     * @param model объект {@link Model} для передачи данных в шаблон
     * @return имя шаблона страницы со списком выступлений
     */


    /**
     * Отображает отдельную форму добавления выступления.
     * <p>Не используется, если форма добавления встроена в основную страницу.</p>
     */
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("humanAct", new HumanAct());
        return "humanAct_form";
    }

    /**
     * Обрабатывает отправку формы добавления нового выступления.
     */
    @PostMapping("/add")
    public String addHumanAct(@ModelAttribute HumanAct humanAct) {
        humanActService.save(humanAct);
        return "redirect:/humanActs";
    }

    /**
     * Удаляет выступление по его идентификатору.
     */
    @GetMapping("/delete/{id}")
    public String deleteHumanAct(@PathVariable Long id) {
        humanActService.deleteById(id);
        return "redirect:/humanActs";
    }

    /**
     * Переключает интерфейс в режим редактирования выбранного выступления.
     */
    @GetMapping("/edit/{id}")
    public String editHumanAct(@PathVariable Long id, Model model) {
        model.addAttribute("humanActs", humanActService.findAll());
        model.addAttribute("humanAct", humanActService.findById(id));
        model.addAttribute("editingId", id);
        model.addAttribute("employees", employeeService.findAll());
        model.addAttribute("performances", performanceService.findAll());
        return "humanActs";
    }


    /**
     * Обрабатывает сохранение изменений выступления (создание или обновление).
     */
    @PostMapping("/save")
    public String saveHumanAct(@ModelAttribute("humanAct") HumanAct humanAct) {
        humanActService.save(humanAct); // repository.save(...)
        return "redirect:/humanActs";
    }


    @GetMapping
    public String listHumanActs(Model model,
                                @RequestParam(required = false) Long performerId) {
        List<HumanAct> humanActs = humanActService.findAll();

        if (performerId != null) {
            humanActs = humanActService.findByMainPerformer(performerId);
        } else {
            humanActs = humanActService.findAll();
        }


        model.addAttribute("humanActs", humanActs);
        model.addAttribute("humanAct", new HumanAct());
        model.addAttribute("editingId", null);
        model.addAttribute("message", null);
        model.addAttribute("employees", employeeService.findAll());
        model.addAttribute("performances", performanceService.findAll());
        model.addAttribute("performerId", performerId);
        return "humanActs";
    }

}
