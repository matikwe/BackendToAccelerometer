package org.example.user;

import org.example.salt.Salt;

import javax.persistence.*;

@Entity(name = "Users")
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(name = "login_unique", columnNames = "login")
        }
)
public class User {
    @Id
    @SequenceGenerator(name = "users_sequence",
            sequenceName = "users_sequence",
            allocationSize = 1)
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "users_sequence"
    )
    private Long id;
    private String login;
    private String password;
    private Long totalJumps;
    @OneToOne()
    @JoinColumn(name = "salt_id")
    private Salt salt;


    public User() {
    }

    public User(Long id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;

    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public User(Long totalJumps) {
        this.totalJumps = totalJumps;
    }

    public User(String login, String password, Long totalJumps) {
        this.login = login;
        this.password = password;
        this.totalJumps = totalJumps;
    }

    public Long getSalt() {
        return salt.getId();
    }

    public void setSalt(Salt salt) {
        this.salt = salt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getTotalJumps() {
        return totalJumps;
    }

    public void setTotalJumps(Long totalJumps) {
        this.totalJumps = totalJumps;
    }
}
