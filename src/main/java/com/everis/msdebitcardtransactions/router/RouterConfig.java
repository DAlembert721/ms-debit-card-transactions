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
        return route(GET("/transactions"), handler::findAllTransactions)
                .andRoute(GET("/debit-cards/{debitCardNumber}/transactions"), handler::findAllTransactionsByDebitCard)
                .andRoute(GET("/transactions/{transactionNumber}"), handler::findTransactionByTransactionNumber)
                .andRoute(POST("/transactions"), handler::createTransaction)
                .andRoute(DELETE("/transactions"), handler::deleteTransaction);
    }
}
