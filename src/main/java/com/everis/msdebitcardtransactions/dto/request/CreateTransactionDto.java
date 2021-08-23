package com.everis.msdebitcardtransactions.dto.request;

import com.everis.msdebitcardtransactions.domain.model.DebitCard;
import com.everis.msdebitcardtransactions.domain.model.Transaction;
import com.everis.msdebitcardtransactions.domain.model.TypeTransaction;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class CreateTransactionDto {
    @NotNull
    private Double amount;
    @NotNull
    private TypeTransaction typeTransaction;
    @NotNull
    private String concept;

    static public Transaction dtoToEntity(DebitCard debitCard, CreateTransactionDto createTransactionDto) {
        Transaction transaction = new Transaction();
        transaction.setDebitCard(debitCard);
        transaction.setAmount(createTransactionDto.getAmount());
        transaction.setDate(LocalDate.now());
        transaction.setTypeTransaction(createTransactionDto.getTypeTransaction());
        transaction.setConcept(createTransactionDto.getConcept());
        transaction.setTransactionNumber(Transaction.generateTransactionNumber());
        return transaction;
    }

}
