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


@Getter
@Setter
@NoArgsConstructor
public class DebitCard {
    private String id;
    private String cardNumber;
    private List<BankAccount> registeredAccounts = new ArrayList<>();
    private LocalDate expirationDate;
}
