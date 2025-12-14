package circus.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * Глобальный обработчик исключений для всего приложения.
 * <p>
 * Отвечает за перехват и корректную обработку всех непредвиденных ошибок для веб-запросов.
 * </p>
 * <ul>
 *     <li>Для веб-запросов отображает страницу с сообщением об ошибке через шаблон {@code error.html}.</li>
 * </ul>
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Обрабатывает все необработанные исключения приложения.
     * <p>
     * Если запрос веб-страничный — рендерится шаблон {@code error.html} с сообщением об ошибке.
     * </p>
     *
     * @param ex      возникшее исключение
     * @param request текущий HTTP-запрос
     * @param model   модель для передачи данных в шаблон (веб-запросы)
     * @return {@link ResponseEntity} с ошибкой для API или имя шаблона ошибки для веб
     */
    @ExceptionHandler(Exception.class)
    public Object handleException(Exception ex, HttpServletRequest request, Model model) {
            model.addAttribute("message", ex.getMessage());
            return "error"; // шаблон error.html
    }

    /**
     * Обрабатывает ошибки 404 (ресурс не найден).
     * <p>
     *     Отображается страница с сообщением о ненайденной странице.
     * </p>
     *
     * @param ex      исключение {@link NoHandlerFoundException}, возникающее при отсутствии маршрута
     * @param request текущий HTTP-запрос
     * @param model   модель для передачи данных в шаблон
     * @return имя шаблона для веб-запроса
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public Object handleNotFound(NoHandlerFoundException ex, HttpServletRequest request, Model model) {
            model.addAttribute("message", "Страница не найдена: " + request.getRequestURI());
            return "error"; // тот же шаблон
    }
}
