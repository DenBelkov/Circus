package circus.model;

import jakarta.persistence.*;

@Entity
@Table(name = "human_acts")
public class HumanAct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Название номера.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "performance_id", nullable = false)
    private Performance performance;

    /**
     * Тип номера.
     */
    @Column(nullable = false)
    private String type;

    /**
     * Глвный постановщик номера.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id", nullable = false)
    private Employee mainPerformer;

    public HumanAct() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    ;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Employee getMainPerformer() {
        return mainPerformer;
    }

    public void setMainPerformer(Employee mainPerformer) {
        this.mainPerformer = mainPerformer;
    }

    public Performance getPerformance() {
        return performance;
    }

    public void setPerformance(Performance performance) {
        this.performance = performance;
    }

}

