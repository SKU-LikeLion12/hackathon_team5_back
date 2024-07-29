package goodorbad.goodorbad.service;

import goodorbad.goodorbad.domain.Diary;
import goodorbad.goodorbad.domain.User;
import goodorbad.goodorbad.repository.DiaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class DiaryService {

    @Autowired
    private DiaryRepository diaryRepository;

    // 다이어리 저장
    public Diary saveDiary(Diary diary) {
        return diaryRepository.save(diary);
    }

    // 특정 날짜와 사용자에 해당하는 다이어리 목록 조회
    public List<Diary> getDiariesByDate(LocalDate date, User user) {
        return diaryRepository.findByDateAndUser(date, user);
    }

    // 다이어리 ID로 다이어리 찾기
    public Diary findById(Long id) {
        Optional<Diary> optionalDiary = diaryRepository.findById(id);
        return optionalDiary.orElse(null);
    }

    // 다이어리 삭제
    public void deleteDiary(Long id) {
        diaryRepository.deleteById(id);
    }
}
