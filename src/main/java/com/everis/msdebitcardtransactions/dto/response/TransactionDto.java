package com.everis.msdebitcardtransactions.dto.response;

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
}
