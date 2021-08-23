package com.everis.msdebitcardtransactions.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Random;

@Document("Transaction")
@Getter
@Setter
@NoArgsConstructor
public class Transaction {
    @Id
    private String id;
    @NotNull
    private Double amount;
    private String transactionNumber;
    private DebitCard debitCard;
    private LocalDate date;
    private String concept;
    private TypeTransaction typeTransaction;

    static public String generateTransactionNumber() {
        final String PREFIX = "DCT-";
        Random random = new Random();
        return PREFIX + random.nextInt(999999999);
    }

}
