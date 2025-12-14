package circus.config;

import circus.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Конфигурационный класс безопасности Spring Security.
 * <p>
 * Определяет:
 * <ul>
 *     <li>правила доступа к ресурсам приложения,</li>
 *     <li>настройку страниц входа и выхода,</li>>
 *     <li>обработку ошибок доступа и аутентификации.</li>
 * </ul>
 * </p>
 */
@Configuration
public class SecurityConfig {

    /**
     * Кастомная реализация {@link org.springframework.security.core.userdetails.UserDetailsService} для загрузки пользователей.
     */
    @Autowired
    private CustomUserDetailsService userDetailsService;

    /**
     * Компонент, обрабатывающий ошибки при отсутствии аутентификации.
     */
    @Autowired
    private CustomAuthenticationEntryPoint authEntryPoint;

    /**
     * Компонент, обрабатывающий ошибки при отказе в доступе (403 Forbidden).
     */
    @Autowired
    private CustomAccessDeniedHandler accessDeniedHandler;

    /**
     * Конфигурирует цепочку фильтров безопасности и правила авторизации.
     * <p>
     * Настройка включает:
     * <ul>
     *     <li>разрешённые маршруты для регистрации и входа;</li>
     *     <li>обработку ошибок безопасности;</li>
     *     <li>настройку форм логина и логаута;</li>
     * </ul>
     * </p>
     *
     * @param http объект {@link HttpSecurity}, используемый для настройки безопасности
     * @return настроенная цепочка {@link SecurityFilterChain}
     * @throws Exception если произошла ошибка конфигурации
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(authEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler)
                )
                .authorizeHttpRequests(auth -> auth
                        // Страницы логина/регистрации доступны всем
                        .requestMatchers("/login", "/register", "/api/auth/**", "/tickets/save", "/pattern.png", "/tent.png").permitAll()

                        // Страница управления пользователями – только SUPER_ADMIN
                        .requestMatchers("/users/**").hasRole("SUPER_ADMIN")

                        // Все таблицы, кроме performances, доступны всем ролям, КРОМЕ VISITOR.
                        // Предположим, что HTML для этих таблиц:
                        // /employees, /animals, /students, /tickets и т.п.
                        .requestMatchers("/animals/**", "/tickets/**", "/humanActs/**", "/animalActs/**")
                        .hasAnyRole("EMPLOYEE", "BOSS", "SUPER_ADMIN")

                        // Страница выступлений (performances) – доступна всем аутентифицированным,
                        // включая VISITOR, если такая роль существует
                        .requestMatchers("/employees/**")
                        .hasAnyRole("BOSS", "SUPER_ADMIN")

                        // Остальное – просто требуем логин
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/performances", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )
                .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

    /**
     * Регистрирует кодировщик паролей, использующий алгоритм BCrypt.
     *
     * @return экземпляр {@link PasswordEncoder}
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Возвращает менеджер аутентификации, основанный на текущей конфигурации.
     *
     * @param config объект {@link AuthenticationConfiguration}, предоставляемый Spring Security
     * @return экземпляр {@link AuthenticationManager}
     * @throws Exception в случае ошибки инициализации
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }
}
