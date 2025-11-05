package br.com.payments.antifraud.domain;

import br.com.payments.contracts.enums.ReasonCode;

import java.math.BigDecimal;

public class RulesEngine {

    public Result evaluate(String carkToken, BigDecimal amount, String merchantId) {
        if (carkToken != null && carkToken.toLowerCase().contains("blacklist"))
            return Result.decline(ReasonCode.SUSPECTED_FRAUD);
        if (amount != null && amount.compareTo(new BigDecimal("2000")) > 0)
            return Result.decline(ReasonCode.SUSPECTED_FRAUD);
        if (merchantId != null && merchantId.endsWith("999"))
            return Result.decline(ReasonCode.SUSPECTED_FRAUD);
        return Result.approve();
    }

    public static class Result {
        public enum Verdict {APPROVE, DECLINE}

        public final Verdict verdict;
        public final ReasonCode reason;

        public Result(Verdict verdict, ReasonCode reason) {
            this.verdict = verdict;
            this.reason = reason;
        }

        public static Result approve() {
            return new Result(Verdict.APPROVE, ReasonCode.NONE);
        }

        public static Result decline(ReasonCode reason) {
            return new Result(Verdict.DECLINE, reason);
        }
    }
}
