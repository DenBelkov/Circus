package circus.config;

import circus.model.Role;
import circus.model.User;
import circus.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Класс инициализации приложения, создающий базовые данные при старте.
 * <p>
 * Выполняет автоматическое заполнение базы данных начальными пользователями и студентами:
 * </p>
 * <ul>
 *     <li>Создаёт супер-администратора, администратора и обычного пользователя, если их ещё нет.</li>
 *     <li>Добавляет несколько тестовых студентов, если таблица студентов пуста.</li>
 * </ul>
 * <p>
 * Данный компонент вызывается автоматически при запуске приложения благодаря аннотации {@link PostConstruct}.
 * </p>
 */
@Component
public class AppInitializer {

    /** Репозиторий пользователей. */
    @Autowired
    private UserRepository userRepository;

    /** Репозиторий студентов. */
    /** Кодировщик паролей, используемый для безопасного хранения паролей. */

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Метод, автоматически вызываемый после создания компонента Spring.
     * <p>
     * Проверяет наличие базовых пользователей и студентов, добавляя их при необходимости.
     * </p>
     */
    @PostConstruct
    public void init() {
        createUserIfNotExists("superadmin@example.com", "password", Role.SUPER_ADMIN);
        createUserIfNotExists("admin@example.com", "password", Role.EMPLOYEE);
        createUserIfNotExists("user@example.com", "password", Role.VISITOR);
    }

    /**
     * Создаёт пользователя с указанным email, паролем и ролью, если пользователь с таким email ещё не существует.
     * <p>
     * Пароль шифруется с использованием {@link PasswordEncoder}.
     * </p>
     *
     * @param email       адрес электронной почты пользователя
     * @param rawPassword исходный (нешифрованный) пароль
     * @param role        роль пользователя (например, {@link Role#EMPLOYEE})
     */
    private void createUserIfNotExists(String email, String rawPassword, Role role) {
        if (!userRepository.existsByEmail(email)) {
            User user = new User();
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode(rawPassword));
            user.setRole(role);
            userRepository.save(user);
            System.out.println("Создан пользователь " + role + ": " + email);
        }
    }

    /**
     * Добавляет несколько примерных записей студентов, если таблица студентов пуста.
     * <p>
     * Используется для демонстрационных целей, чтобы приложение сразу имело тестовые данные.
     * </p>
     */
    }

