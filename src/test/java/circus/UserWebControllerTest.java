package circus;

import circus.controller.UserWebController;
import circus.model.Role;
import circus.model.User;
import circus.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Модульные тесты MVC-контроллера пользователей {@link UserWebController}.
 *
 * <p><b>Почему требуются моки:</b> В срезе {@link WebMvcTest} поднимаются только компоненты веб-слоя.
 * Все зависимости контроллера (репозитории, утилиты, фильтры безопасности и пр.)
 * необходимо предоставить как бины — в тестах это делается через моки.</p>
 *
 * <p>В данном проекте контроллер зависит от:
 * <ul>
 *   <li>{@link UserRepository} — источник данных;</li>
 *   <li>{@link JwtUtil} — утилита JWT, инжектится как бин;</li>
 *   <li>{@link JwtAuthenticationFilter} — фильтр безопасности (мокаем, чтобы не тянуть security-конфигурацию).</li>
 * </ul>
 * Поэтому эти бины объявлены как {@code @MockitoBean}.</p>
 */
@WebMvcTest(UserWebController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserWebControllerTest {

    /** Инструмент для имитации HTTP-вызовов к MVC-контроллерам. */
    @Autowired
    private MockMvc mvc;

    /** Мок репозитория пользователей, т.к. контроллер получает его через конструктор. */
    @MockitoBean
    private UserRepository userRepository;

    /**
     * GET /users — должна отрендериться вьюха "users" и модель содержать атрибуты "users" и "message".
     */
    @Test
    void userPageRendersUsersViewWithModel() throws Exception {
        User u = new User();
        u.setEmail("a@b.com");
        u.setRole(Role.VISITOR);

        when(userRepository.findAll()).thenReturn(List.of(u));

        mvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(view().name("users"))
                .andExpect(model().attributeExists("users"))
                .andExpect(model().attribute("message", nullValue()));
    }
}
