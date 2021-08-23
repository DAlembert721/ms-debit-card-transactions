package com.everis.msdebitcardtransactions.domain.service;

import com.everis.msdebitcardtransactions.domain.model.AccountTransaction;
import com.everis.msdebitcardtransactions.domain.model.BankAccount;
import com.everis.msdebitcardtransactions.domain.model.DebitCard;
import com.everis.msdebitcardtransactions.domain.model.Transaction;
import reactor.core.publisher.Mono;

public interface TransactionAccountWebClientService {
    Mono<AccountTransaction> sendTransactionByTypeAccount(Transaction transaction, BankAccount bankAccount);
}
