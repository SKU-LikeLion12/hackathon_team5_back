package goodorbad.goodorbad.controller;

import goodorbad.goodorbad.DTO.DiaryDTO;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/diaries")
public class DiaryController {

    @Autowired
    private DiaryService diaryService;

    @Autowired
    private UserService userService;

    // 새로운 다이어리 작성
    @PostMapping

    public DiaryDTO.diaryResponse createDiary(@RequestBody Diary diary, @RequestHeader("Authorization") String token) {
        User user = userService.tokenToMember(token);

        diary.setUser(user);
        diaryService.saveDiary(diary);
        return new DiaryDTO.diaryResponse(diary.getDate(),diary.getContent());
    }

    // 날짜로 일기 찾기
    @GetMapping
    public List<DiaryDTO.diaryResponse> getDiariesByDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestHeader("Authorization") String token) {
        // userId로 사용자를 찾습니다.
        User user = userService.tokenToMember(token);
        List<Diary> diaries = diaryService.getDiariesByDate(date, user);
        List<DiaryDTO.diaryResponse> response = diaries.stream()
                .map(diary -> new DiaryDTO.diaryResponse(diary.getDate(), diary.getContent()))
                .collect(Collectors.toList());

        return response;
    }
/////////////////////////////////////////////////////
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
