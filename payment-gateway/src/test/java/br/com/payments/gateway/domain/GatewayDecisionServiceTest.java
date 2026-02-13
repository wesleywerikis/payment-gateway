package br.com.payments.gateway.domain;

import br.com.payments.contracts.enums.ReasonCode;
import br.com.payments.contracts.events.PaymentCreatedEvent;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GatewayDecisionServiceTest {

    private final GatewayDecisionService service = new GatewayDecisionService();

    @Test
    void shouldApproveWhenAmountBelowLimit() {
        var evt = new PaymentCreatedEvent(
                "1",
                new BigDecimal("100.00"),
                "BRL",
                "valid-token",
                "merchant-1"
        );

        var decision = service.decide(evt);

        assertEquals(GatewayDecisionService.Decision.Kind.APPROVE, decision.kind);
        assertEquals(ReasonCode.NONE, decision.reason);
    }

    @Test
    void shouldRequireAntifraudWhenAmountAboveLimit() {
        var evt = new PaymentCreatedEvent(
                "1",
                new BigDecimal("500.00"),
                "BRL",
                "valid-token",
                "merchant-1"
        );

        var decision = service.decide(evt);

        assertEquals(GatewayDecisionService.Decision.Kind.REQUIRE_ANTIFRAUD, decision.kind);
    }

    @Test
    void shouldReturnErrorWhenTokenIsBlank() {
        var evt = new PaymentCreatedEvent(
                "1",
                new BigDecimal("100.00"),
                "BRL",
                "",
                "merchant-1"
        );

        var decision = service.decide(evt);

        assertEquals(GatewayDecisionService.Decision.Kind.ERROR, decision.kind);
        assertEquals(ReasonCode.INVALID_TOKEN, decision.reason);
    }
}
