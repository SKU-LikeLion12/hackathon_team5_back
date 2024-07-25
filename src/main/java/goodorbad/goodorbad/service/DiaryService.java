package goodorbad.goodorbad.service;

import goodorbad.goodorbad.domain.Diary;
import goodorbad.goodorbad.domain.User;
import goodorbad.goodorbad.repository.DiaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class DiaryService {

    @Autowired
    private DiaryRepository diaryRepository;

    public Diary saveDiary(Diary diary) {
        return diaryRepository.save(diary);
    }

    public List<Diary> getDiariesByDate(LocalDate date, User user) {
        return diaryRepository.findByDateAndUser(date, user);
    }
}
