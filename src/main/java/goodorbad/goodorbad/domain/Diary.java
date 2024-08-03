package goodorbad.goodorbad.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Entity
@Setter
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"date", "user_id"})}) //날짜와 사용자아이디를 유니크
public class Diary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    @Setter
    private String content; //일기 본문
    private boolean goodMemory;
    @Setter
    private String emotion; //감정

    @Setter
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // Reference to the User entity

    public Diary(LocalDate date,String content,String emotion,boolean goodMemory,User user){
        this.setDate(date);
        this.setContent(content);
        this.setEmotion(emotion);
        this.setGoodMemory(goodMemory);
        this.setUser(user);
    }

}
