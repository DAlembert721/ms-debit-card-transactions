package com.everis.msdebitcardtransactions.service;

import com.everis.msdebitcardtransactions.domain.model.DebitCard;
import com.everis.msdebitcardtransactions.domain.service.DebitCardWebClient;
import com.everis.msdebitcardtransactions.exception.NotFoundedException;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class DebitCardWebClientImpl implements DebitCardWebClient {

    private final ReactiveCircuitBreaker reactiveCircuitBreaker;

    WebClient webClientDebitCard = WebClient.create("http://localhost:8090/api/ms-debit-card/debit-cards");

    public DebitCardWebClientImpl(ReactiveResilience4JCircuitBreakerFactory circuitBreakerFactory) {
        this.reactiveCircuitBreaker = circuitBreakerFactory.create("ms-debit-card");
    }

    @Override
    public Mono<DebitCard> findDebitCardByCardNumber(String cardNumber) {
        return reactiveCircuitBreaker.run(
                webClientDebitCard.get()
                .uri("/".concat(cardNumber))
                .retrieve()
                .bodyToMono(DebitCard.class)
                .switchIfEmpty(Mono.error(new NotFoundedException("Debit card", cardNumber)))
                .onErrorResume(Mono::error), Mono::error);
    }
}
