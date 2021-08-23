package com.everis.msdebitcardtransactions.dto.response;

import com.everis.msdebitcardtransactions.domain.model.Transaction;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class TransactionDto {
    private Double amount;
    private String transactionNumber;
    private LocalDate date;
    private String concept;

    static public TransactionDto entityToDto(Transaction transaction) {
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setTransactionNumber(transaction.getTransactionNumber());
        transactionDto.setAmount(transaction.getAmount());
        transactionDto.setConcept(transaction.getConcept());
        transactionDto.setDate(transaction.getDate());
        return transactionDto;
    }
}
