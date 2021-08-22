package com.everis.msdebitcardtransactions.domain.repository;

import com.everis.msdebitcardtransactions.domain.model.Transaction;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends BaseRepository<Transaction, String>{
}
