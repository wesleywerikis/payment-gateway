package br.com.payments.merchant.api;

import br.com.payments.merchant.api.dto.CreatePaymentRequest;
import br.com.payments.merchant.api.dto.PaymentResponse;
import br.com.payments.merchant.domain.Payment;
import br.com.payments.merchant.domain.PaymentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService service;

    public PaymentController(PaymentService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<PaymentResponse> create(@Valid @RequestBody CreatePaymentRequest req) {
        Payment p = service.createAndPublish(req.getAmount(), req.getCurrency(), req.getCardToken(), req.getMerchantId());
        return ResponseEntity.accepted().body(new PaymentResponse(p.getId(), p.getAmount(), p.getCurrency(), p.getMerchantId(),
                p.getStatus(), p.getCreatedAt(), p.getUpdatedAt()));

    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponse> get(@PathVariable String id) {
        return service.findById(id)
                .map(p -> ResponseEntity.ok(new PaymentResponse(
                        p.getId(), p.getAmount(), p.getCurrency(), p.getMerchantId(), p.getStatus(),
                        p.getCreatedAt(), p.getUpdatedAt()
                ))).orElse(ResponseEntity.notFound().build());
    }
}