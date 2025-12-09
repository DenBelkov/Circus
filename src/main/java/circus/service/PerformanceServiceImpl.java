package circus.service;

import circus.model.Performance;
import circus.repository.PerformanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Реализация интерфейса {@link PerformanceService}.
 * <p>
 * Предоставляет бизнес-логику для работы с сущностями {@link Performance},
 * включая операции создания, получения, обновления и удаления выступлений.
 * </p>
 *
 * <p>
 * Взаимодействует с уровнем доступа к данным через {@link PerformanceRepository}.
 * </p>
 */
@Service
public class PerformanceServiceImpl implements PerformanceService {

    /** Репозиторий для выполнения операций с таблицей выступлений. */
    private final PerformanceRepository performanceRepository;

    /**
     * Конструктор сервиса выступлений.
     *
     * @param performanceRepository репозиторий выступлений
     */
    @Autowired
    public PerformanceServiceImpl(PerformanceRepository performanceRepository) {
        this.performanceRepository = performanceRepository;
    }

    public List<Performance> findByDateRange(LocalDateTime fromDate, LocalDateTime toDate) {
        return performanceRepository.findByDateRange(fromDate, toDate);
    }

    /**
     * Возвращает список всех выступлений из базы данных.
     *
     * @return список сущностей {@link Performance}
     */
    @Override
    public List<Performance> findAll() {
        return performanceRepository.findAll();
    }

    /**
     * Сохраняет новое или обновляет существующее выступление.
     *
     * @param performance объект {@link Performance}, который необходимо сохранить
     * @return сохранённый экземпляр {@link Performance}
     */
    @Override
    public List<Performance> findAllUpcoming() {
        return performanceRepository.findByStatusFalse();
    }

    @Override
    public Performance save(Performance performance) {
        return performanceRepository.save(performance);
    }

    /**
     * Находит выступление по его уникальному идентификатору.
     *
     * @param id идентификатор выступления
     * @return найденное {@link Performance}, либо {@code null}, если выступление не найдено
     */
    @Override
    public Performance findById(Long id) {
        return performanceRepository.findById(id).orElse(null);
    }

    /**
     * Удаляет выступление по его идентификатору.
     * <p>
     * Если выступление с указанным ID не найдено, метод просто завершает работу без ошибки.
     * </p>
     *
     * @param id идентификатор выступления
     */
    @Override
    public void deleteById(Long id) {
        performanceRepository.deleteById(id);
    }
}
