package circus.repository;

import circus.model.Performance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Репозиторий доступа к данным сущности {@link Performance}.
 * <p>
 * Предоставляет базовые CRUD-операции через {@link JpaRepository}.
 * Дополнительные методы выборки можно определить через ключевые слова Spring Data JPA.
 * </p>
 */
public interface PerformanceRepository extends JpaRepository<Performance, Long> {
    List<Performance> findByStatusFalse();

    @Query("SELECT p FROM Performance p WHERE " +
            "(:fromDate IS NULL OR p.dateTime >= CAST(:fromDate AS java.time.LocalDateTime)) AND " +
            "(:toDate IS NULL OR p.dateTime <= CAST(:toDate AS java.time.LocalDateTime)) " +
            "ORDER BY p.dateTime DESC")
    List<Performance> findByDateRange(
            @Param("fromDate") LocalDateTime fromDate,
            @Param("toDate") LocalDateTime toDate);

    List<Performance> findAll();
}
