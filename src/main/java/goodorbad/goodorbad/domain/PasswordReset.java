package goodorbad.goodorbad.domain;

import jakarta.persistence.*;
import lombok.Setter;

import java.util.Date;

@Entity
@Setter
public class PasswordReset {
    @Id
    private String token;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private Date expiryDate;

}
