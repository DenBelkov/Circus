package circus.model;

import jakarta.persistence.*;

@Entity
@Table(name = "animal_acts")
public class AnimalAct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Название номера. */
    @Column(nullable = false)
    private String title;

    /** Выступающее животное */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "animal_id", nullable = false)
    private Animal animal;

    /** Дрессировщик */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id", nullable = false)
    private Employee animalTrainer;

    public AnimalAct() { }

    public Long getId() {
        return id;
    }

    public void setId(Long id){this.id = id;};

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public Animal getAnimal() { return animal; }
    public void setAnimal(Animal animal) { this.animal = animal; }

    public Employee getAnimalTrainer() { return animalTrainer; }
    public void setAnimalTrainer(Employee animalTrainer) { this.animalTrainer = animalTrainer; }
}