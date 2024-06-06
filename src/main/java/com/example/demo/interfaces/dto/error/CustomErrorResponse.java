package com.example.demo.interfaces.dto.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomErrorResponse {
    private List<ErrorDetail> error;

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ErrorDetail {
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
        private LocalDateTime timestamp;
        private int code;
        private String detail;
    }
}