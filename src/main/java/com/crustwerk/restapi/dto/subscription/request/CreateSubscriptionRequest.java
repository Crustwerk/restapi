package com.crustwerk.restapi.dto.subscription.request;

import com.crustwerk.restapi.model.SubscriptionDuration;
import com.crustwerk.restapi.model.SubscriptionTier;
import com.crustwerk.restapi.validation.ValidEnum;
import jakarta.validation.constraints.NotBlank;

/**
 * Un enum non può essere "blank" perché non è una String.
 * <p>
 * Se ometti completamente il campo nel JSON, il valore sarà null, quindi @NotNull è l'annotazione corretta.
 * <p>
 * Se invece specifichi un valore non valido, Jackson fallirà già in fase di deserializzazione (lanciando un <b>HttpMessageNotReadableException</b>), prima ancora della validazione.
 */
public record CreateSubscriptionRequest(
        @NotBlank(message = "Subscription tier is required")
        @ValidEnum(enumClass = SubscriptionTier.class)
        String subscriptionTier,

        @NotBlank(message = "Subscription duration is required")
        @ValidEnum(enumClass = SubscriptionDuration.class)
        String subscriptionDuration
) {

    public static final class CreateSubscriptionRequestBuilder {
        private String subscriptionTier;
        private String subscriptionDuration;

        private CreateSubscriptionRequestBuilder() {
        }

        public static CreateSubscriptionRequestBuilder aCreateSubscriptionRequest() {
            return new CreateSubscriptionRequestBuilder();
        }

        public CreateSubscriptionRequestBuilder withSubscriptionTier(String subscriptionTier) {
            this.subscriptionTier = subscriptionTier;
            return this;
        }

        public CreateSubscriptionRequestBuilder withSubscriptionDuration(String subscriptionDuration) {
            this.subscriptionDuration = subscriptionDuration;
            return this;
        }

        public CreateSubscriptionRequest build() {
            return new CreateSubscriptionRequest(subscriptionTier, subscriptionDuration);
        }
    }
}
