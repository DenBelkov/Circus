package circus.model;

import jakarta.persistence.*;

@Entity
@Table(name = "animals")
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Кличка. */
    @Column(nullable = false)
    private String name;

    /** Вид (тигр, слон и т.п.). */
    @Column(nullable = false)
    private String species;

    /** Возраст в годах. */
    @Column(nullable = false)
    private Integer age;

    public Animal() { }

    // getters/setters

    public Long getId() { return id; }
    public void setId(Long id){this.id = id;};

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSpecies() { return species; }
    public void setSpecies(String species) { this.species = species; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

}
