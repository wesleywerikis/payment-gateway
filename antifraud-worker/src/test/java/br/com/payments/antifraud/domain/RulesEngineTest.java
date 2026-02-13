package br.com.payments.antifraud.domain;

import br.com.payments.contracts.enums.ReasonCode;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class RulesEngineTest {

    private final RulesEngine engine = new RulesEngine();

    @Test
    void shouldDeclineWhenBlacklistedToken() {
        var result = engine.evaluate("blacklist-token", new BigDecimal("100"), "merchant-1");

        assertEquals(RulesEngine.Result.Verdict.DECLINE, result.verdict);
        assertEquals(ReasonCode.SUSPECTED_FRAUD, result.reason);
    }

    @Test
    void shouldDeclineWhenAmountAbove2000() {
        var result = engine.evaluate("valid", new BigDecimal("3000"), "merchant-1");

        assertEquals(RulesEngine.Result.Verdict.DECLINE, result.verdict);
    }

    @Test
    void shouldApproveWhenEverythingValid() {
        var result = engine.evaluate("valid", new BigDecimal("100"), "merchant-1");

        assertEquals(RulesEngine.Result.Verdict.APPROVE, result.verdict);
    }
}
