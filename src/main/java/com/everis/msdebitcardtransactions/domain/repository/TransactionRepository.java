package com.everis.msdebitcardtransactions.domain.repository;

import com.everis.msdebitcardtransactions.domain.model.DebitCard;
import com.everis.msdebitcardtransactions.domain.model.Transaction;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface TransactionRepository extends BaseRepository<Transaction, String>{
    Mono<Transaction> findTransactionByTransactionNumber(String transactionNumber);
    Flux<Transaction> findAllByDebitCardCardNumber(String cardNumber);
    Mono<Void> deleteByTransactionNumber(String transactionNumber);
}
