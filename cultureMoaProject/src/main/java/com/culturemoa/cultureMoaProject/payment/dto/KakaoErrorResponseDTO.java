package com.culturemoa.cultureMoaProject.payment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KakaoErrorResponseDTO {
    @JsonProperty("error_code")
    private int errorCode;
    @JsonProperty("error_message")
    private String errorMessage;
    private ErrorExtras extras;

    @Getter
    @Setter
    public static class ErrorExtras {
        @JsonProperty("method_result_code")
        private String methodResultCode;
        @JsonProperty("method_result_message")
        private String methodResultMessage;
    }
}
