package hr.fer.jollybringer.entities;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "\"PresidentApplication\"")
public class PresidentApplication {
    @Id
    @Lob
    @Column(name = "id", nullable = false)
    private String id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "\"userId\"", nullable = false)
    private User user;

    @Lob
    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "\"createdAt\"", nullable = false)
    private Instant createdAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

}