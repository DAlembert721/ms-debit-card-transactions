package com.everis.msdebitcardtransactions.handler;

import com.everis.msdebitcardtransactions.domain.service.TransactionService;
import com.everis.msdebitcardtransactions.dto.request.CreateTransactionDto;
import com.everis.msdebitcardtransactions.dto.response.ErrorResponse;
import com.everis.msdebitcardtransactions.dto.response.TransactionDto;
import com.everis.msdebitcardtransactions.exception.NoParamsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Date;

@Component
@Slf4j(topic = "TRANSACTION_HANDLER")
public class TransactionsHandler {

    @Autowired
    private TransactionService transactionService;

    public Mono<ServerResponse> findAllTransactions(ServerRequest serverRequest) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(transactionService.findAllTransactions(),
                        TransactionDto.class);
    }

    public Mono<ServerResponse> findAllTransactionsByDebitCard(ServerRequest serverRequest) {
        String cardNumber = serverRequest.pathVariable("debitCardNumber");
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(transactionService.findAllTransactionsByCardNumber(cardNumber),
                        TransactionDto.class)
                .switchIfEmpty(ErrorResponse.buildBadResponse("The debit card with number: ".concat(cardNumber),HttpStatus.NOT_FOUND))
                .onErrorResume(throwable ->
                        ErrorResponse.buildBadResponse(throwable.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
    }

    public Mono<ServerResponse> findTransactionByTransactionNumber(ServerRequest serverRequest) {
        String transactionNumber = serverRequest.pathVariable("transactionNumber");
        return transactionService.findTransactionByTransactionNumber(transactionNumber)
                .flatMap(transactionDto -> ServerResponse.status(HttpStatus.FOUND)
                        .contentType(MediaType.APPLICATION_JSON).bodyValue(transactionDto))
                .switchIfEmpty(
                        ErrorResponse.buildBadResponse("The transaction with number: ".concat(transactionNumber).concat("can't be founded"),HttpStatus.NOT_FOUND))
                .onErrorResume(throwable ->
                        ErrorResponse.buildBadResponse(throwable.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
    }

    public Mono<ServerResponse> createTransaction(ServerRequest serverRequest) {
        String debitCardNumber = serverRequest.queryParam("cardNumber")
                .orElseThrow(() -> new NoParamsException("Must be include card number params"));

        return serverRequest.bodyToMono(CreateTransactionDto.class)
                .flatMap(createTransactionDto -> {
                    return transactionService.createTransaction(debitCardNumber, createTransactionDto)
                            .flatMap(transactionDto -> ServerResponse.status(HttpStatus.OK)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .bodyValue(transactionDto))
                            .switchIfEmpty(
                                    ErrorResponse
                                            .buildBadResponse("The debit card with number: "
                                                            .concat(debitCardNumber).concat("can't be founded"),
                                                    HttpStatus.NOT_FOUND))
                            .onErrorResume(throwable ->
                                    ErrorResponse.buildBadResponse(throwable.getMessage(), HttpStatus.BAD_REQUEST));
                }).switchIfEmpty(ErrorResponse
                        .buildBadResponse("The body is empty or wrong",
                                HttpStatus.BAD_REQUEST))
                .onErrorResume(throwable ->
                        ErrorResponse.buildBadResponse(throwable.getMessage(), HttpStatus.BAD_REQUEST));

    }

    public Mono<ServerResponse> deleteTransaction(ServerRequest serverRequest) {
        String transactionNumber = serverRequest.queryParam("transactionNumber")
                .orElseThrow(() -> new NoParamsException("Must be include transaction number params"));
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(transactionService.deleteTransaction(transactionNumber), ResponseEntity.class);
    }

}
