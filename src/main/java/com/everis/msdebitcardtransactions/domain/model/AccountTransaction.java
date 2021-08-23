package com.everis.msdebitcardtransactions.domain.model;

import com.everis.msdebitcardtransactions.exception.BadRequestException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Random;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountTransaction {
    private BankAccount savingAccount;
    private BankAccount fixedTerm;
    private BankAccount currentAccount;
    private String transactionCode;
    private TypeTransaction typeTransaction;
    private Double transactionAmount;
    private Double commissionAmount;

    static public AccountTransaction buildAccountTransaction(Transaction transaction, BankAccount bankAccount) {
        AccountTransaction accountTransaction = new AccountTransaction();
        accountTransaction.setTransactionAmount(transaction.getAmount());
        accountTransaction.setTransactionCode(transaction.getTransactionNumber());
        accountTransaction.setTypeTransaction(transaction.getTypeTransaction());
        switch(bankAccount.getAccountType()) {
            case "Current": {
                accountTransaction.setCurrentAccount(bankAccount);
                return accountTransaction;
            }
            case "Fixed": {
                accountTransaction.setFixedTerm(bankAccount);
                return accountTransaction;
            }
            case "Saving": {
                accountTransaction.setSavingAccount(bankAccount);
                return accountTransaction;
            }
            default: {
                throw new BadRequestException("Account type is wrong");
            }
        }

    }


}
