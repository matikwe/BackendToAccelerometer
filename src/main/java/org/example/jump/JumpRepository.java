package org.example.jump;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JumpRepository
        extends JpaRepository<Jump, Long> {
    @Query("SELECT SUM(countJumps) FROM Jumps j WHERE j.userId = ?1 and j.date = ?2")
    String findJumpByUserIdAndCurrentDate(Long userId, String date);
}
