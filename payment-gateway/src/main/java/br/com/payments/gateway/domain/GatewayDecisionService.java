package br.com.payments.gateway.domain;

import br.com.payments.contracts.enums.ReasonCode;
import br.com.payments.contracts.events.PaymentCreatedEvent;

import java.math.BigDecimal;

public class GatewayDecisionService {
    private final BigDecimal instantApprovalLimit = new BigDecimal("200.00");

    public Decision decide(PaymentCreatedEvent evt) {
        if (evt.getCardToken() == null || evt.getCardToken().isBlank())
            return Decision.error(ReasonCode.INVALID_TOKEN);
        if (evt.getAmount() == null || evt.getAmount().compareTo(BigDecimal.ZERO) <= 0)
            return Decision.error(ReasonCode.SYSTEM_ERROR);

        if (evt.getAmount().compareTo(instantApprovalLimit) <= 0) {
            return Decision.approve();
        } else {
            return Decision.requireAntifraud();
        }
    }

    public static class Decision {
        public enum Kind {APPROVE, REQUIRE_ANTIFRAUD, ERROR}

        public final Kind kind;
        public final ReasonCode reason;

        public Decision(Kind kind, ReasonCode reason) {
            this.kind = kind;
            this.reason = reason;
        }

        public static Decision approve() {
            return new Decision(Kind.APPROVE, ReasonCode.NONE);
        }

        public static Decision requireAntifraud() {
            return new Decision(Kind.REQUIRE_ANTIFRAUD, ReasonCode.NONE);
        }

        public static Decision error(ReasonCode r) {
            return new Decision(Kind.ERROR, r);
        }
    }
}
