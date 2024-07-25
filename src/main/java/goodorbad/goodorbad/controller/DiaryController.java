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

    @PostMapping
    public ResponseEntity<Diary> createDiary(@RequestBody Diary diary, @RequestParam String userId) {
        User user = userService.findByUserId(userId);
        if (user == null) {
            return ResponseEntity.badRequest().body(null);
        }
        diary.setUser(user);
        Diary savedDiary = diaryService.saveDiary(diary);
        return ResponseEntity.ok(savedDiary);
    }

    @GetMapping
    public ResponseEntity<List<Diary>> getDiariesByDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam String userId) {
        User user = userService.findByUserId(userId);
        if (user == null) {
            return ResponseEntity.badRequest().body(null);
        }
        List<Diary> diaries = diaryService.getDiariesByDate(date, user);
        return ResponseEntity.ok(diaries);
    }
}
