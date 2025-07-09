package com.crustwerk.restapi.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * ValidDateRange è un'annotazione di validazione personalizzata per assicurarsi che una data di inizio
 * sia cronologicamente precedente alla data di fine. Da usare su classi (es. record) che contengono entrambi i campi.
 *
 * <p><b>@Constraint</b> è un'annotazione meta: serve a dichiarare che questa è una constraint annotation.
 * Specifica, tramite "validatedBy", la classe che implementa la logica della validazione
 * (la classe deve implementare l'interfaccia {@code ConstraintValidator}).</p>
 *
 * <p><b>@Target({ElementType.TYPE})</b> indica lo scope dell'annotazione. In questo caso,
 * {@code TYPE} significa che si applica all'intera classe o record, perché la validazione riguarda più campi.
 * (Per validazioni su singoli campi si userebbe {@code FIELD} o {@code METHOD}).</p>
 *
 * <p><b>@Retention(RetentionPolicy.RUNTIME)</b> definisce fino a quando l’annotazione è conservata.
 * I possibili valori sono:</p>
 *
 * <ul>
 *   <li><b>SOURCE</b>: solo nel codice sorgente; scartata al compile time</li>
 *   <li><b>CLASS</b>: (default) conservata nel bytecode ma non accessibile a runtime</li>
 *   <li><b>RUNTIME</b>: conservata nel bytecode e disponibile a runtime tramite reflection</li>
 * </ul>
 *
 * <p>Hibernate Validator (e altri Bean Validation provider) leggono le annotazioni a runtime
 * per applicare la logica definita nei {@code ConstraintValidator}, quindi è necessario {@code RUNTIME}.</p>
 *
 * <p><b>@interface</b> è la sintassi per definire una nuova annotazione in Java.</p>
 *
 * <p><b>@message</b> Messaggio di errore restituito se la validazione fallisce.<p>
 * <p><b>@groups</b> Serve a definire scenari di validazione differenti (es. creazione vs aggiornamento).
 * È una funzionalità avanzata, spesso non usata nei casi base.<p>
 * <p><b>@payload</b> Permette di associare metadati al vincolo. Usato raramente in casi avanzati (es. logging o categorizzazione).<p>
 * <p>NB:
 * Le dichiarazioni `message`, `groups` e `payload` non sono metodi reali,
 * ma parametri configurabili dell'annotazione.
 * Questa è una sintassi specifica delle annotazioni (`@interface`) e non si usa altrove.
 * Il valore di `default` rappresenta il valore predefinito che verrà utilizzato
 * se il parametro non viene specificato quando si applica l’annotazione.</p>
 */


@Documented
@Constraint(validatedBy = DateRangeValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDateRange {
    String message() default "Start date must be before end date";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}