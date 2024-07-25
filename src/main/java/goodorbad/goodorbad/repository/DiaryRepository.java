package goodorbad.goodorbad.repository;

import goodorbad.goodorbad.domain.Diary;
import goodorbad.goodorbad.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
    List<Diary> findByDateAndUser(LocalDate date, User user);
}
