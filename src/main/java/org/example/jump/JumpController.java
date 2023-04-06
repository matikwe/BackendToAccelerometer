package org.example.jump;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping(path = "api/v1/jump")
public class JumpController {
    private final JumpService jumpService;

    @Autowired
    public JumpController(JumpService jumpService) {
        this.jumpService = jumpService;
    }

    @GetMapping
    public List<Jump> getJump() {
        return jumpService.getJumps();
    }

    @PostMapping
    public int addJumps(
            @RequestBody Jump jump){
        return jumpService.addJumps(jump);
    }
}
