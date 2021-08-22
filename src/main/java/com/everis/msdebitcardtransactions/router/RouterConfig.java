package com.everis.msdebitcardtransactions.router;

import com.everis.msdebitcardtransactions.handler.TransactionsHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {
    @Bean
    public RouterFunction<ServerResponse> transactionsRoutes(TransactionsHandler handler) {
        return route(GET("/ms-debit-card-transactions/transactions"), handler::findAllTransactions)
                .andRoute(GET("/ms-debit-card-transactions/debit-cards/transactions}"), handler::findAllTransactionsByDebitCard)
                .andRoute(GET("/ms-debit-card-transactions/transactions/{transactionNumber}"), handler::findTransactionByTransactionNumber)
                .andRoute(POST("/ms-debit-card-transactions/transactions"), handler::createTransaction)
                .andRoute(DELETE("/ms-debit-card-transactions/transactions"), handler::deleteTransaction);
    }
}
