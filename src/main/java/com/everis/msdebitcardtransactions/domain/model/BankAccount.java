package com.everis.msdebitcardtransactions.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BankAccount {
    private String id;

    private String accountNumber;

    private String accountType;

    private Double balance;
}
