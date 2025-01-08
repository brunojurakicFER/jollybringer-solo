package hr.fer.jollybringer.entities;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "\"Group\"")
public class Group {
    @Id
    @Lob
    @Column(name = "id", nullable = false)
    private String id;

    @Lob
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "\"createdAt\"", nullable = false)
    private Instant createdAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "\"createdById\"", nullable = false)
    private User createdBy;

    @Lob
    @Column(name = "description")
    private String description;

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

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}