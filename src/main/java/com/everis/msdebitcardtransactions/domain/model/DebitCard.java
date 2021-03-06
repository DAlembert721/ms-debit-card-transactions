package com.everis.msdebitcardtransactions.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


@Getter
@Setter
@NoArgsConstructor
public class DebitCard {
    private String id;
    private String cardNumber;
    private List<BankAccount> registeredAccounts = new ArrayList<>();
    private LocalDate expirationDate;

    static public BankAccount findToUseAccount(DebitCard debitCard, Double amount) {
        return debitCard.getRegisteredAccounts()
                .stream()
                .filter(bankAccount -> bankAccount.getBalance() >= amount)
                .collect(Collectors.toList()).get(0);
    }
}
