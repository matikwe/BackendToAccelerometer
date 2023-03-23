package org.example.salt;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SaltRepository extends JpaRepository<Salt, Long> {
    @Query("SELECT s FROM Salt s WHERE s.id = ?1")
    Optional<Salt> checkExistSalt(Long id);
}
