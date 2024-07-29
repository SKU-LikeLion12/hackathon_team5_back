package goodorbad.goodorbad.controller;

import goodorbad.goodorbad.domain.Diary;
import goodorbad.goodorbad.domain.User;
import goodorbad.goodorbad.service.DiaryService;
import goodorbad.goodorbad.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/diaries")
public class DiaryController {

    @Autowired
    private DiaryService diaryService;

    @Autowired
    private UserService userService;

    // 새로운 다이어리 작성
    @PostMapping
    public ResponseEntity<Diary> createDiary(@RequestBody Diary diary) {
        // UserService를 사용하여 diary에 설정된 userId에 해당하는 사용자를 찾습니다.
        User user = userService.findByUserId(diary.getUser().getUserId());
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }
        diary.setUser(user);
        Diary savedDiary = diaryService.saveDiary(diary);
        return ResponseEntity.ok(savedDiary);
    }

    // 특정 날짜와 사용자에 해당하는 다이어리 목록 조회
    @GetMapping
    public ResponseEntity<List<Diary>> getDiariesByDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam String userId) {
        // userId로 사용자를 찾습니다.
        User user = userService.findByUserId(userId);
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }
        List<Diary> diaries = diaryService.getDiariesByDate(date, user);
        return ResponseEntity.ok(diaries);
    }

    // 특정 다이어리 업데이트 (PUT 메서드)
    @PutMapping("/{id}")
    public ResponseEntity<Diary> updateDiary(
            @PathVariable Long id,
            @RequestBody Diary updatedDiary) {
        // 해당 ID의 다이어리를 검색합니다.
        Diary diary = diaryService.findById(id);
        if (diary == null) {
            return ResponseEntity.notFound().build();
        }
        // 다이어리 업데이트
        diary.setDate(updatedDiary.getDate());
        diary.setContent(updatedDiary.getContent());
        diary.setGoodMemory(updatedDiary.isGoodMemory());
        Diary savedDiary = diaryService.saveDiary(diary);
        return ResponseEntity.ok(savedDiary);
    }

    // 특정 다이어리 삭제 (DELETE 메서드)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiary(@PathVariable Long id) {
        Diary diary = diaryService.findById(id);
        if (diary == null) {
            return ResponseEntity.notFound().build();
        }
        diaryService.deleteDiary(id);
        return ResponseEntity.noContent().build();
    }
}
