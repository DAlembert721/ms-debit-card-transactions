package com.everis.msdebitcardtransactions.domain.service;

import com.everis.msdebitcardtransactions.domain.model.DebitCard;
import reactor.core.publisher.Mono;

public interface DebitCardWebClient {
    Mono<DebitCard> findDebitCardByCardNumber(String cardNumber);
}
