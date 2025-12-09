package circus.controller;

import circus.model.Performance;
import circus.service.EmployeeService;
import circus.service.PerformanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
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
@RequestMapping("/performances")
public class PerformanceWebController {

    /** Сервис для выполнения операций с объектами {@link Performance}. */
    @Autowired
    private PerformanceService performanceService;

    @Autowired
    private EmployeeService employeeService;
    /**
     * Отображает список всех выступлений и форму добавления нового выступления.
     *
     * @param model объект {@link Model} для передачи данных в шаблон
     * @return имя шаблона страницы со списком выступлений
     */

    @GetMapping
    public String listPerformances(Model model, Authentication authentication) {
        boolean isUserOnly = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_USER"));

        List<Performance> performances = isUserOnly
                ? performanceService.findAllUpcoming()
                : performanceService.findAll();

        model.addAttribute("performances", performances);
        model.addAttribute("performance", new Performance());
        model.addAttribute("artists", employeeService.findAll());
        return "performances";
    }

    /**
     * Отображает отдельную форму добавления выступления.
     * <p>Не используется, если форма добавления встроена в основную страницу.</p>
     */
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("performance", new Performance());
        return "performance_form";
    }

    /**
     * Обрабатывает отправку формы добавления нового выступления.
     */
    @PostMapping("/add")
    public String addPerformance(@ModelAttribute Performance performance) {
        performanceService.save(performance);
        return "redirect:/performances";
    }

    /**
     * Удаляет выступление по его идентификатору.
     */
    @GetMapping("/delete/{id}")
    public String deletePerformance(@PathVariable Long id) {
        performanceService.deleteById(id);
        return "redirect:/performances";
    }

    /**
     * Переключает интерфейс в режим редактирования выбранного выступления.
     */
    @GetMapping("/edit/{id}")
    public String editPerformance(@PathVariable Long id, Model model) {
        model.addAttribute("performances", performanceService.findAll());
        model.addAttribute("performance", performanceService.findById(id));
        model.addAttribute("artists", employeeService.findAll());
        model.addAttribute("editingId", id);
        return "performances";
    }



    /**
     * Обрабатывает сохранение изменений выступления (создание или обновление).
     */
    @PostMapping("/save")
    public String savePerformance(@ModelAttribute Performance performance) {
        performanceService.save(performance);
        return "redirect:/performances";
    }
}
