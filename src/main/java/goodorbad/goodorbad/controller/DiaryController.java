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
        return new DiaryDTO.diaryResponse(diary.getDate(),diary.getContent(), diary.getEmotion(), diary.getId());
    }

    // 날짜로 일기 찾기
    @GetMapping
    public List<DiaryDTO.diaryResponse> getDiariesByDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestHeader("Authorization") String token) {

        User user = userService.tokenToMember(token);
        List<Diary> diaries = diaryService.getDiariesByDate(date, user);
        List<DiaryDTO.diaryResponse> response = diaries.stream()
                .map(diary -> new DiaryDTO.diaryResponse(diary.getDate(), diary.getContent(), diary.getEmotion(), diary.getId()))
                .collect(Collectors.toList());

        return response;
    }

    // 특정 다이어리 업데이트 (PUT 메서드)
    @PutMapping("/update")
    public DiaryDTO.diaryResponse updateDiary(@RequestBody DiaryDTO.diaryUpdateRequest request, @RequestHeader("Authorization") String token) {
        User user = userService.tokenToMember(token);

        Diary diary = diaryService.updateDiary(request.getDiaryId(), request.getDate(), request.getNewContent(), request.getNewEmotion(),user);
        return new DiaryDTO.diaryResponse(diary.getDate(), diary.getContent(), diary.getEmotion(), diary.getId());
    }

//    // 특정 다이어리 삭제 (DELETE 메서드)
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteDiary(@PathVariable Long id) {
//        Diary diary = diaryService.findById(id);
//        if (diary == null) {
//            return ResponseEntity.notFound().build();
//        }
//        diaryService.deleteDiary(id);
//        return ResponseEntity.noContent().build();
//    }
}
