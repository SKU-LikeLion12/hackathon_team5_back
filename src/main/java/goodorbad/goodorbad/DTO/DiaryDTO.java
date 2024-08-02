package goodorbad.goodorbad.DTO;

import goodorbad.goodorbad.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

public class DiaryDTO {
    @Data
    @AllArgsConstructor
    public static class diaryResponse {
        private LocalDate date;
        private String content;
        private String emotion;
        private Long diaryId;
    }

    @Data
    public static class diaryUpdateRequest{
        private LocalDate date;
        private Long diaryId;
        private String newContent;
        private String newEmotion;
    }
}
