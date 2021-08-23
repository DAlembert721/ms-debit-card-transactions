package com.everis.msdebitcardtransactions.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private String message;
    private Date date;
    private Integer status;

    static public Mono<ServerResponse> buildBadResponse(String message, HttpStatus status) {
        return ServerResponse.status(status).bodyValue(new ErrorResponse(message, new Date(),status.value()));
    }

}
