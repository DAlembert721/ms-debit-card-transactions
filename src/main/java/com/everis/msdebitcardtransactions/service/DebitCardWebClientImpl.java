package com.everis.msdebitcardtransactions.service;

import com.everis.msdebitcardtransactions.domain.model.DebitCard;
import com.everis.msdebitcardtransactions.domain.service.DebitCardWebClient;
import com.everis.msdebitcardtransactions.exception.NotFoundedException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class DebitCardWebClientImpl implements DebitCardWebClient {


    WebClient webClientDebitCard = WebClient.create("http://gateway:8090/api/ms-debit-cards/debit-cards");

    @Override
    public Mono<DebitCard> findDebitCardByCardNumber(String cardNumber) {
        return webClientDebitCard.get()
                .uri("/".concat(cardNumber))
                .retrieve()
                .bodyToMono(DebitCard.class)
                .switchIfEmpty(Mono.error(new NotFoundedException("Debit card", cardNumber)))
                .onErrorResume(Mono::error);
    }
}
