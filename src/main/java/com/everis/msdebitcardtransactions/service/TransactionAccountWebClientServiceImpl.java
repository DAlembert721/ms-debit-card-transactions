package com.everis.msdebitcardtransactions.service;

import com.everis.msdebitcardtransactions.domain.model.AccountTransaction;
import com.everis.msdebitcardtransactions.domain.model.BankAccount;
import com.everis.msdebitcardtransactions.domain.model.Transaction;
import com.everis.msdebitcardtransactions.domain.service.TransactionAccountWebClientService;
import com.everis.msdebitcardtransactions.exception.BadRequestException;
import com.everis.msdebitcardtransactions.exception.WebClientException;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class TransactionAccountWebClientServiceImpl implements TransactionAccountWebClientService {

    private final ReactiveCircuitBreaker reactiveCircuitBreaker;

    WebClient webClientCurrent = WebClient.create("http://localhost:8090/api/ms-current-account-transaction/transactionCurrentAccount");

    WebClient webClientFixed = WebClient.create("http://localhost:8090/api/ms-fixed-term-transaction/transactionFixedTerm");

    WebClient webClientSaving = WebClient.create("http://localhost:8090/api/ms-saving-account-transaction/transactionSavingAccount");

    public TransactionAccountWebClientServiceImpl(ReactiveResilience4JCircuitBreakerFactory circuitBreakerFactory) {
        this.reactiveCircuitBreaker = circuitBreakerFactory.create("ms-account-transactions");
    }

    @Override
    public Mono<AccountTransaction> sendTransactionByTypeAccount(Transaction transaction, BankAccount bankAccount) {
        switch (bankAccount.getAccountType()) {
            case "Current": {
                return reactiveCircuitBreaker.run(
                        webClientCurrent.post()
                        .uri("/create")
                        .accept(MediaType.APPLICATION_JSON)
                        .body(Mono.just(AccountTransaction.buildAccountTransaction(transaction, bankAccount))
                                , AccountTransaction.class)
                        .retrieve()
                        .bodyToMono(AccountTransaction.class)
                        .switchIfEmpty(Mono.empty())
                        .onErrorResume(Mono::error), Mono::error);
            }
            case "Fixed": {
                return reactiveCircuitBreaker.run(
                        webClientFixed.post()
                        .uri("/create")
                        .accept(MediaType.APPLICATION_JSON)
                        .body(Mono.just(AccountTransaction.buildAccountTransaction(transaction, bankAccount))
                                , AccountTransaction.class)
                        .retrieve()
                        .bodyToMono(AccountTransaction.class)
                        .switchIfEmpty(Mono.error(new WebClientException("Error on create fixed term transaction")))
                        .onErrorResume(Mono::error), Mono::error);
            }
            case "Saving": {
                return reactiveCircuitBreaker.run(
                        webClientSaving.post()
                        .uri("/create")
                        .accept(MediaType.APPLICATION_JSON)
                        .body(Mono.just(AccountTransaction.buildAccountTransaction(transaction, bankAccount))
                                , AccountTransaction.class)
                        .retrieve()
                        .bodyToMono(AccountTransaction.class)
                        .switchIfEmpty(Mono.error(new WebClientException("Error on create fixed term transaction")))
                        .onErrorResume(Mono::error), Mono::error);
            }
            default: {
                throw new BadRequestException("Account type is wrong");
            }
        }
    }


}
