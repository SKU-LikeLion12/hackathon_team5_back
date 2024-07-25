package goodorbad.goodorbad.domain;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Diary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private String content;
    private boolean goodMemory;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // Reference to the User entity

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isGoodMemory() {
        return goodMemory;
    }

    public void setGoodMemory(boolean goodMemory) {
        this.goodMemory = goodMemory;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
