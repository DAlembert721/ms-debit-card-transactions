package com.everis.msdebitcardtransactions.service;

import com.everis.msdebitcardtransactions.domain.model.AccountTransaction;
import com.everis.msdebitcardtransactions.domain.model.BankAccount;
import com.everis.msdebitcardtransactions.domain.model.DebitCard;
import com.everis.msdebitcardtransactions.domain.model.Transaction;
import com.everis.msdebitcardtransactions.domain.repository.TransactionRepository;
import com.everis.msdebitcardtransactions.domain.service.DebitCardWebClient;
import com.everis.msdebitcardtransactions.domain.service.TransactionAccountWebClientService;
import com.everis.msdebitcardtransactions.domain.service.TransactionService;
import com.everis.msdebitcardtransactions.dto.request.CreateTransactionDto;
import com.everis.msdebitcardtransactions.dto.response.TransactionDto;
import com.everis.msdebitcardtransactions.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private DebitCardWebClient debitCardWebClient;

    @Autowired
    private TransactionAccountWebClientService transactionAccountWebClientService;

    @Autowired
    private TransactionRepository transactionRepository;


    @Override
    public Flux<TransactionDto> findAllTransactions() {
        return transactionRepository.findAll()
                .map(TransactionDto::entityToDto);
    }

    @Override
    public Mono<TransactionDto> findTransactionByTransactionNumber(String transactionNumber) {
        return transactionRepository.findTransactionByTransactionNumber(transactionNumber)
                .map(TransactionDto::entityToDto);
    }

    @Override
    public Flux<TransactionDto> findAllTransactionsByCardNumber(String cardNumber) {
        return transactionRepository.findAllByDebitCardCardNumber(cardNumber)
                        .map(TransactionDto::entityToDto);
    }

    @Override
    public Mono<TransactionDto> createTransaction(String cardNumber, CreateTransactionDto createTransactionDto) {
        return debitCardWebClient.findDebitCardByCardNumber(cardNumber)
                .flatMap(debitCard -> {
                    Transaction transaction = CreateTransactionDto.dtoToEntity(debitCard, createTransactionDto);
                    return transactionRepository.save(transaction);
                }).flatMap(transaction -> {
                    BankAccount bankAccount = DebitCard.findToUseAccount(transaction.getDebitCard(), transaction.getAmount());
                    return transactionAccountWebClientService.sendTransactionByTypeAccount(transaction, bankAccount)
                            .map(accountTransaction -> TransactionDto.entityToDto(transaction))
                            .switchIfEmpty(Mono.error(new RuntimeException("Error on create transaction Account")))
                            .onErrorResume(Mono::error);
                })
                .switchIfEmpty(Mono.empty())
                .onErrorResume(throwable -> Mono.error(new BadRequestException(throwable.getMessage())));
    }

    @Override
    public Mono<ResponseEntity<?>> deleteTransaction(String transactionNumber) {
        transactionRepository.deleteByTransactionNumber(transactionNumber);
        return Mono.just(ResponseEntity.ok().build());
    }
}
