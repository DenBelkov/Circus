package circus;

import circus.controller.UserRestController;
import circus.model.Role;
import circus.model.User;
import circus.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Модульные тесты REST-контроллера пользователей {@link UserRestController}.
 * <p>
 * Проверяются эндпоинты выдачи, изменения роли и удаления.
 * Сервис мокируется, JWT-фильтр замокан, фильтры отключены.
 * </p>
 */
@WebMvcTest(UserRestController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserRestControllerTest {

    @Autowired
    private MockMvc mvc;

    /** Мок пользовательского сервиса. */
    @MockitoBean
    private UserService userService;

    /**
     * GET /api/users — успешная выдача списка.
     */
    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void getAllUsers_ok() throws Exception {
        when(userService.findAll()).thenReturn(List.of(new User()));
        mvc.perform(get("/api/users").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    /**
     * PUT /api/users/{id}/role — успешная смена роли.
     */
    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void changeRole_ok() throws Exception {
        User u = new User();
        u.setId(7L);
        u.setRole(Role.VISITOR);
        when(userService.findById(7L)).thenReturn(u);
        when(userService.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        mvc.perform(put("/api/users/7/role").param("role", "ADMIN"))
                .andExpect(status().isOk());

        verify(userService).findById(7L);
        verify(userService).save(any(User.class));
    }

    /**
     * PUT /api/users/{id}/role — пользователь не найден (404).
     */
    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void changeRole_notFound() throws Exception {
        when(userService.findById(777L)).thenReturn(null);

        mvc.perform(put("/api/users/777/role").param("role", "ADMIN"))
                .andExpect(status().isNotFound());
    }

    /**
     * DELETE /api/users/{id} — успешное удаление.
     */
    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void delete_ok() throws Exception {
        User u = new User();
        u.setId(9L);
        when(userService.findById(9L)).thenReturn(u);

        mvc.perform(delete("/api/users/9"))
                .andExpect(status().isOk());

        verify(userService).deleteById(9L);
    }

    /**
     * DELETE /api/users/{id} — пользователь не найден (404).
     */
    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void delete_notFound() throws Exception {
        when(userService.findById(404L)).thenReturn(null);

        mvc.perform(delete("/api/users/404"))
                .andExpect(status().isNotFound());

        verify(userService, never()).deleteById(anyLong());
    }
}
