package circus.service;

import circus.model.Performance;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Интерфейс сервиса для управления сущностями {@link Performance}.
 * <p>
 * Определяет базовые операции для работы с данными выступлений —
 * получение, сохранение, поиск и удаление записей.
 * </p>
 */
public interface PerformanceService {

    /**
     * Возвращает список всех выступлений.
     *
     * @return список сущностей {@link Performance}
     */
    List<Performance> findAll();
    List<Performance> findAllUpcoming(); // только status = false
    /**
     * Сохраняет новое или обновляет существующее выступление.
     *
     * @param performance объект {@link Performance}, который нужно сохранить
     * @return сохранённый экземпляр {@link Performance}
     */
    Performance save(Performance performance);

    List<Performance> findByDateRange(LocalDateTime fromDate, LocalDateTime toDate);
    /**
     * Находит выступление по его идентификатору.
     *
     * @param id идентификатор выступления
     * @return найденное {@link Performance}, либо {@code null}, если выступление не найдено
     */
    Performance findById(Long id);

    /**
     * Удаляет выступление по его идентификатору.
     * <p>
     * Если выступление с таким ID не найдено, метод не вызывает ошибок.
     * </p>
     *
     * @param id идентификатор выступления
     */
    void deleteById(Long id);
}
