package circus.model;

import jakarta.persistence.*;

@Entity
@Table(name = "human_acts")
public class HumanAct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Название номера. */
    @Column(nullable = false)
    private String title;

    /** Тип номера. */
    @Column(nullable = false)
    private String type;

    /** Глвный постановщик номера. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id", nullable = false)
    private Employee mainPerformer;

    public HumanAct() { }

    public Long getId() {
        return id;
    }
    public void setId(Long id){this.id = id;};

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public Employee getMainPerformer() { return mainPerformer; }
    public void setMainPerformer(Employee mainPerformer) { this.mainPerformer = mainPerformer; }

}

