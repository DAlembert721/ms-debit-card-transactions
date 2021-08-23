package com.everis.msdebitcardtransactions.domain.service;

import com.everis.msdebitcardtransactions.dto.request.CreateTransactionDto;
import com.everis.msdebitcardtransactions.dto.response.TransactionDto;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionService {
    Flux<TransactionDto> findAllTransactions();
    Mono<TransactionDto> findTransactionByTransactionNumber(String transactionNumber);
    Flux<TransactionDto> findAllTransactionsByCardNumber(String cardNumber);
    Mono<TransactionDto> createTransaction(String cardNumber, CreateTransactionDto createTransactionDto);
    Mono<ResponseEntity<?>> deleteTransaction(String transactionNumber);
}
