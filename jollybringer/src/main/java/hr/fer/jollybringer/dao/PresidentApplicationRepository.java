package hr.fer.jollybringer.dao;

import hr.fer.jollybringer.domain.PresidentApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PresidentApplicationRepository extends JpaRepository<PresidentApplication, UUID> {
    Optional<PresidentApplication> findByUserId(UUID userId);
}