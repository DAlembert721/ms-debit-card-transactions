package com.everis.msdebitcardtransactions.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class CreateTransactionDto {
    @NotNull
    private Double amount;
}
