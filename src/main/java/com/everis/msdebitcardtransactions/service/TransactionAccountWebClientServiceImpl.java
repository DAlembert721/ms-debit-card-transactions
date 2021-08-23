package com.everis.msdebitcardtransactions.service;

import com.everis.msdebitcardtransactions.domain.model.AccountTransaction;
import com.everis.msdebitcardtransactions.domain.model.BankAccount;
import com.everis.msdebitcardtransactions.domain.model.Transaction;
import com.everis.msdebitcardtransactions.domain.service.TransactionAccountWebClientService;
import com.everis.msdebitcardtransactions.exception.BadRequestException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class TransactionAccountWebClientServiceImpl implements TransactionAccountWebClientService {
    WebClient webClientCurrent = WebClient.create("http://gateway:8090/api/ms-current-account-transaction/transactionCurrentAccount");

    WebClient webClientFixed = WebClient.create("http://gateway:8090/api/ms-fixed-term-transaction/transactionFixedTerm");

    WebClient webClientSaving = WebClient.create("http://gateway:8090/api/ms-saving-account-transaction/transactionSavingAccount");

    @Override
    public Mono<AccountTransaction> sendTransactionByTypeAccount(Transaction transaction, BankAccount bankAccount) {
        switch (bankAccount.getAccountType()) {
            case "CURRENT": {
                return webClientCurrent.post()
                        .uri("/create")
                        .accept(MediaType.APPLICATION_JSON)
                        .body(Mono.just(AccountTransaction.buildAccountTransaction(transaction, bankAccount))
                                , AccountTransaction.class)
                        .retrieve()
                        .bodyToMono(AccountTransaction.class)
                        .switchIfEmpty(Mono.empty());
            }
            case "FIXED": {
                return webClientFixed.post()
                        .uri("/create")
                        .accept(MediaType.APPLICATION_JSON)
                        .body(Mono.just(AccountTransaction.buildAccountTransaction(transaction, bankAccount))
                                , AccountTransaction.class)
                        .retrieve()
                        .bodyToMono(AccountTransaction.class)
                        .switchIfEmpty(Mono.empty());
            }
            case "SAVING": {
                return webClientSaving.post()
                        .uri("/create")
                        .accept(MediaType.APPLICATION_JSON)
                        .body(Mono.just(AccountTransaction.buildAccountTransaction(transaction, bankAccount))
                                , AccountTransaction.class)
                        .retrieve()
                        .bodyToMono(AccountTransaction.class)
                        .switchIfEmpty(Mono.empty());
            }
            default: {
                throw new BadRequestException("Account type is wrong");
            }
        }
    }


}
