package circus.controller;

import circus.model.Performance;
import circus.model.Ticket;
import circus.service.EmployeeService;
import circus.service.PerformanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

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

    /**
     * Сервис для выполнения операций с объектами {@link Performance}.
     */
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
    public String listPerformances(Model model,
                                   Authentication authentication,
                                   @RequestParam(required = false) String fromDate,
                                   @RequestParam(required = false) String toDate,
//                                   @RequestParam(required = false) String byDate,
                                   @RequestParam(required = false) String sortBy) {
        boolean isUserOnly = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_VISITOR"));

        performanceService.updateStatusesForPastPerformances();
        List<Performance> performances;

        // Парсим даты, если они переданы
        java.time.LocalDateTime from = null;
        java.time.LocalDateTime to = null;
        try {
            if (fromDate != null && !fromDate.isEmpty()) {
                from = java.time.LocalDateTime.parse(fromDate);
            }
            if (toDate != null && !toDate.isEmpty()) {
                to = java.time.LocalDateTime.parse(toDate);
            }
        } catch (Exception e) {
            // Если ошибка парсинга, показываем все
        }

        // Копируем в final переменные для использования в lambda
        final java.time.LocalDateTime fromFinal = from;
        final java.time.LocalDateTime toFinal = to;

        // Выбираем данные в зависимости от параметров
        if (from != null || to != null) {
            if (isUserOnly) {
                performances = performanceService.findAllUpcomingSorted();
            } else {
                performances = performanceService.findAllSorted();
            }

            // Фильтруем вручную
            if (fromFinal != null && toFinal != null) {
                performances = performances.stream()
                        .filter(p -> !p.getDateTime().isBefore(fromFinal) && !p.getDateTime().isAfter(toFinal))
                        .toList();
            } else if (fromFinal != null) {
                performances = performances.stream()
                        .filter(p -> !p.getDateTime().isBefore(fromFinal))
                        .toList();
            } else if (toFinal != null) {
                performances = performances.stream()
                        .filter(p -> !p.getDateTime().isAfter(toFinal))
                        .toList();
            }
        } else {
            performances = isUserOnly
                    ? performanceService.findAllUpcomingSorted()
                    : performanceService.findAllSorted();
        }

//        if (byDate != null ) {
//            java.time.LocalDateTime date = java.time.LocalDateTime.parse(byDate);
//            System.out.println(date);
//            performances = performances.stream()
//                    .filter(p -> p.getDateTime().equals(date))
//                    .toList();
//        }

        // Сортировка по Revenue
        if ("revenue_asc".equals(sortBy)) {
            performances = performances.stream()
                    .sorted(java.util.Comparator.comparing(Performance::getRevenue))
                    .toList();
        } else if ("revenue_desc".equals(sortBy)) {
            performances = performances.stream()
                    .sorted(java.util.Comparator.comparing(Performance::getRevenue).reversed())
                    .toList();
        }

        model.addAttribute("performances", performances);
        model.addAttribute("performance", new Performance());
        model.addAttribute("artists", employeeService.findAll());
        model.addAttribute("ticket", new Ticket());  // ← добавить
        model.addAttribute("fromDate", fromDate);
        model.addAttribute("toDate", toDate);
//        model.addAttribute("byDate", byDate);
        model.addAttribute("sortBy", sortBy);
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
