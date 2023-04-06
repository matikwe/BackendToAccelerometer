package org.example.jump;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class JumpService {
    private final JumpRepository jumpRepository;

    @Autowired
    public JumpService(JumpRepository jumpRepository) {
        this.jumpRepository = jumpRepository;
    }

    public List<Jump> getJumps() {
        return jumpRepository.findAll();
    }

    public int addJumps(Jump jump) {
        if (jump.getCountJumps() > 0) {
            jump.setDate(getCurrentDate());
            jumpRepository.save(jump);
        }
        String dailyJumpsString = jumpRepository.findJumpByUserIdAndCurrentDate(jump.getUserId(), getCurrentDate());
        return dailyJumpsString != null ? Integer.parseInt(dailyJumpsString) : 0;
    }

    private String getCurrentDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }
}
