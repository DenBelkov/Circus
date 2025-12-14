package circus.controller;

import circus.model.Employee;
import circus.service.EmployeeService;
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
@RequestMapping("/employees")
public class EmployeeWebController {

    /**
     * Сервис для выполнения операций с объектами {@link Employee}.
     */
    @Autowired
    private EmployeeService employeeService;

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
        model.addAttribute("employee", new Employee());
        return "employee_form";
    }

    /**
     * Обрабатывает отправку формы добавления нового выступления.
     */
    @PostMapping("/add")
    public String addEmployee(@ModelAttribute Employee employee) {
        employeeService.save(employee);
        return "redirect:/employees";
    }

    /**
     * Удаляет выступление по его идентификатору.
     */
    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        employeeService.deleteById(id);
        return "redirect:/employees";
    }

    /**
     * Переключает интерфейс в режим редактирования выбранного выступления.
     */
    @GetMapping("/edit/{id}")
    public String editEmployee(@PathVariable Long id, Model model) {
        model.addAttribute("employees", employeeService.findAll());
        model.addAttribute("employee", employeeService.findById(id));
        model.addAttribute("editingId", id);
        return "employees";
    }

    /**
     * Обрабатывает сохранение изменений выступления (создание или обновление).
     */
    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute Employee employee) {
        employeeService.save(employee);
        return "redirect:/employees";
    }

    @GetMapping
    public String listEmployees(Model model,
                                @RequestParam(required = false) String msg) {
        model.addAttribute("employees", employeeService.findAll());
        model.addAttribute("employee", new Employee()); // для модального окна "Add"
        model.addAttribute("editingId", null);
        model.addAttribute("message", msg);
        return "employees"; // employees.html
    }

}
