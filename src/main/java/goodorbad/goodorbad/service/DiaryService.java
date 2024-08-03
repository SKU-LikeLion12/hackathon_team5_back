package goodorbad.goodorbad.service;

import goodorbad.goodorbad.domain.Diary;
import goodorbad.goodorbad.domain.User;
import goodorbad.goodorbad.exception.DateNotFoundException;
import goodorbad.goodorbad.exception.DiaryAlreadyExistsException;
import goodorbad.goodorbad.repository.DiaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DiaryService {

    @Autowired
    private DiaryRepository diaryRepository;
    private UserService userService;

    // 다이어리 저장
    public Diary saveDiary(LocalDate date,String content,String emotion,boolean goodMemory,User user) {
        Diary saveDiary=diaryRepository.findByDateAndUser(date,user);
        if(saveDiary!=null) throw new DiaryAlreadyExistsException();

        return diaryRepository.save(new Diary(date,content,emotion,goodMemory,user));
    }

    // 특정 날짜와 사용자에 해당하는 다이어리 목록 조회
    public Diary getDiariesByDate(LocalDate date, User user) {
        Diary diary=diaryRepository.findByDateAndUser(date,user);
        if(diary==null) throw new DateNotFoundException();
        return diary;
    }

    //다이어리 수정
    public Diary updateDiary(LocalDate date, String newContent, String newEmotion,User user) {
        Diary diary=diaryRepository.findByDateAndUser(date,user);

        if(diary!=null){
            diary.setContent(newContent);
            diary.setEmotion(newEmotion);
            return diaryRepository.save(diary);
        }else{
            throw new DateNotFoundException();
        }
    }

}
