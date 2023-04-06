package org.example.jump;

import javax.persistence.*;

@Entity(name = "Jumps")
@Table(name = "jumps")
public class Jump {
    @Id
    @SequenceGenerator(name = "users_sequence",
            sequenceName = "users_sequence",
            allocationSize = 1)
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "users_sequence"
    )
    private Long id;
    private int countJumps;
    private String date;
    private Long userId;

    public Jump() {

    }

    public Jump(int countJumps, String date, Long userId) {
        this.countJumps = countJumps;
        this.date = date;
        this.userId = userId;
    }

    public Jump(int countJumps, Long userId) {
        this.countJumps = countJumps;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getCountJumps() {
        return countJumps;
    }

    public void setCountJumps(int countJumps) {
        this.countJumps = countJumps;
    }
}
