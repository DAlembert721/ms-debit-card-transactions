package com.everis.msdebitcardtransactions.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@Slf4j(topic = "TRANSACTION_HANDLER")
public class TransactionsHandler {

    public Mono<ServerResponse> findAllTransactions(ServerRequest serverRequest) {
        return null;
    }

    public Mono<ServerResponse> findAllTransactionsByDebitCard(ServerRequest serverRequest) {
        return null;
    }

    public Mono<ServerResponse> findTransactionByTransactionNumber(ServerRequest serverRequest) {
        return null;
    }

    public Mono<ServerResponse> createTransaction(ServerRequest serverRequest) {
        return null;
    }

    public Mono<ServerResponse> deleteTransaction(ServerRequest serverRequest) {
        return null;
    }

}
