package circus.controller;

import circus.model.Role;
import circus.model.User;
import circus.repository.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/users")
@PreAuthorize("hasRole('SUPER_ADMIN')")
public class UserWebController {

    private final UserRepository userRepository;

    public UserWebController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public String userPage(Model model,
                           @RequestParam(required = false) String msg) {
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("message", msg);
        return "users";
    }

    /**
     * Изменение роли пользователя (POST /users/change-role).
     */
    @PostMapping("/change-role")
    public String changeRole(@RequestParam("userId") Long userId,
                             @RequestParam("role") Role role,
                             RedirectAttributes redirectAttributes) {

        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setRole(role);
            userRepository.save(user);
            redirectAttributes.addAttribute("msg", "Роль успешно обновлена.");
        } else {
            redirectAttributes.addAttribute("msg", "Пользователь не найден.");
        }
        return "redirect:/users";
    }

    /**
     * Удаление пользователя (POST /users/delete).
     */
    @PostMapping("/delete")
    public String deleteUser(@RequestParam("userId") Long userId,
                             RedirectAttributes redirectAttributes) {

        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
            redirectAttributes.addAttribute("msg", "Пользователь удалён.");
        } else {
            redirectAttributes.addAttribute("msg", "Пользователь не найден.");
        }
        return "redirect:/users";
    }
}
