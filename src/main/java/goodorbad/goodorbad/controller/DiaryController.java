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

    @PostMapping
    public DiaryDTO.diaryResponse createDiary(@RequestBody Diary diary, @RequestParam String userId) {
        User user = userService.findByUserId(userId);
        diary.setUser(user);
        diaryService.saveDiary(diary);
        return new DiaryDTO.diaryResponse(diary.getDate(),diary.getContent());
    }

    @GetMapping
    public List<DiaryDTO.diaryResponse> getDiariesByDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam String userId) {
        User user = userService.findByUserId(userId);
        List<Diary> diaries = diaryService.getDiariesByDate(date, user);
        List<DiaryDTO.diaryResponse> response = diaries.stream()
                .map(diary -> new DiaryDTO.diaryResponse(diary.getDate(), diary.getContent()))
                .collect(Collectors.toList());

        return response;
    }
}
