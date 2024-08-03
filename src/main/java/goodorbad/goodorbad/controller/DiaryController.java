package goodorbad.goodorbad.controller;

import goodorbad.goodorbad.DTO.DiaryDTO;
import goodorbad.goodorbad.domain.Diary;
import goodorbad.goodorbad.domain.User;
import goodorbad.goodorbad.service.DiaryService;
import goodorbad.goodorbad.service.UserService;
import lombok.RequiredArgsConstructor;
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

    public DiaryDTO.diaryResponse createDiary(@RequestBody DiaryDTO.diaryCreateRequest request, @RequestHeader("Authorization") String token) {
        User user = userService.tokenToMember(token);
        diaryService.saveDiary(request.getDate(),request.getContent(),request.getEmotion(),request.isGoodMemory(),user);
        return new DiaryDTO.diaryResponse(request.getDate(),request.getContent(), request.getEmotion());
    }

    // 날짜로 일기 찾기
    @GetMapping
    public DiaryDTO.diaryResponse getDiariesByDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestHeader("Authorization") String token) {

        User user = userService.tokenToMember(token);
        Diary diary = diaryService.getDiariesByDate(date, user);

        return new DiaryDTO.diaryResponse(diary.getDate(),diary.getContent(), diary.getEmotion());
    }

    // 다이어리 수정
    @PutMapping("/update")
    public DiaryDTO.diaryResponse updateDiary(@RequestBody DiaryDTO.diaryUpdateRequest request, @RequestHeader("Authorization") String token) {
        User user =userService.tokenToMember(token);

        Diary diary = diaryService.updateDiary( request.getDate(), request.getNewContent(), request.getNewEmotion(),user);
        return new DiaryDTO.diaryResponse(diary.getDate(), diary.getContent(), diary.getEmotion());
    }
}
