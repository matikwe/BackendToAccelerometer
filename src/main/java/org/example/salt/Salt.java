package org.example.salt;



import org.example.user.User;

import javax.persistence.*;

@Entity(name = "Salt")
@Table(
        name = "salt"
)
public class Salt {
    @Id
    @SequenceGenerator(name = "salt_sequence",
            sequenceName = "salt_sequence",
            allocationSize = 1)
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "salt_sequence"
    )
    private Long id;
    private String salt;

    @OneToOne(cascade = CascadeType.ALL)
    private User user;

    public Salt() {
    }

    public Salt(String salt) {
        this.salt = salt;
    }

    public String getSalt() {
        return salt;
    }

    public Long getId() {
        return id;
    }
}
