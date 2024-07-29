package goodorbad.goodorbad.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

public class DiaryDTO {
    @Data
    @AllArgsConstructor
    public static class diaryResponse {
        private LocalDate date;
        private String content;
    }
}
