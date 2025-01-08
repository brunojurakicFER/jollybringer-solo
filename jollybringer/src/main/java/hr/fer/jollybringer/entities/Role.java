package hr.fer.jollybringer.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "\"Role\"")
public class Role {
    @Id
    @Lob
    @Column(name = "id", nullable = false)
    private String id;

    @Lob
    @Column(name = "name", nullable = false)
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}