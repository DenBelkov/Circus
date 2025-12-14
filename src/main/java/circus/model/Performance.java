package circus.model;

import jakarta.persistence.*;

import java.lang.annotation.ElementType;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.Formula;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Выступление в цирке.
 */
@Entity
@Table(name = "performances")
public class Performance {

    /**
     * Уникальный идентификатор выступления.
     */

    @OneToMany(mappedBy = "performance",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Ticket> tickets;

    @OneToMany(mappedBy = "performance",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<HumanAct> humanActs;

    @OneToMany(mappedBy = "performance",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<AnimalAct> animalActs;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Название выступления.
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * Дата и время выступления.
     */
    @Column(name = "date_time", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime dateTime;

    /**
     * Главный артист выступления.
     * В БД хранится только внешний ключ main_artist_id (BIGINT),
     * а в Java-коде мы работаем с объектом Employee.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "main_artist_id", nullable = false)
    private Employee mainArtist;

    /**
     * Длительность выступления (например, в минутах).
     */
    @Column(name = "duration_minutes", nullable = false)
    private Integer durationMinutes;

    @Column(name = "status", nullable = false)
    private boolean status;

    @Formula("(SELECT COALESCE(SUM(t.total_price), 0) " +
            "FROM tickets t " +
            "WHERE t.performance_id = id)")
    private Long revenue;

    @Column(name = "description",
            nullable = false,
            columnDefinition = "TEXT")
    private String description;

    public Long getRevenue() {
        return revenue;
    }

    // Конструктор без аргументов обязателен для JPA
    public Performance() {
    }

    // Геттеры и сеттеры
    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    // Геттеры и сеттеры
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Employee getMainArtist() {
        return mainArtist;
    }

    public void setMainArtist(Employee mainArtist) {
        this.mainArtist = mainArtist;
    }

    public Integer getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(Integer durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
