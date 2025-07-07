package com.crustwerk.restapi.dto.subscription.response;

import com.crustwerk.restapi.model.SubscriptionDuration;
import com.crustwerk.restapi.model.SubscriptionTier;

import java.time.LocalDate;

/**
 * Questo record rappresenta un DTO (Data Transfer Object) immutabile.
 *
 * La keyword {@code record}, introdotta in Java 16, definisce una classe compatta e finalizzata
 * alla gestione di dati "puri", come richieste o risposte REST, coppie chiave/valore, o configurazioni.
 *
 * Caratteristiche principali:
 * - I campi sono finali e immutabili per design
 * - Vengono generati automaticamente:
 *   - costruttore
 *   - metodi accessori ({@code fieldName()})
 *   - {@code equals()}, {@code hashCode()}, {@code toString()}
 * - Perfetto per DTO, semplice da leggere e manutenere
 *
 * ⚠️ Non adatto per:
 * - Entity JPA (a causa della mutabilità e del supporto limitato)
 * - Classi con logica complessa o stato variabile
 *
 * Esempio d’uso nel controller:
 * {@code
 *     public record CreateUserRequest(String name, String email) {}
 * }
 *
 * NB: usare record unicamente per DTO in ingresso. In uscita usare record solo se il DTO ha pochi campi,
 * altrimenti usare una classe standard (l'alternativa è fare un builder ma si rischia di avere più codice di prima).
 */

public record CreateSubscriptionResponse(Long id, LocalDate start, LocalDate end, SubscriptionTier subscriptionTier,
                                         SubscriptionDuration subscriptionDuration) {
}

