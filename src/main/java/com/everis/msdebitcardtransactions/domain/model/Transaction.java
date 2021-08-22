package com.everis.msdebitcardtransactions.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

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
    private LocalDate date;
    private String concept;

}
