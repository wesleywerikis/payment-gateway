package br.com.payments.merchant.domain;

import br.com.payments.contracts.events.PaymentCreatedEvent;
import br.com.payments.merchant.events.PaymentEventsPublisher;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaymentServiceTest {

    @Test
    void shouldCreatePaymentAndPublishEvent() {

        PaymentRepository repo = mock(PaymentRepository.class);
        PaymentEventsPublisher publisher = mock(PaymentEventsPublisher.class);

        PaymentService service = new PaymentService(repo, publisher);

        when(repo.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        var payment = service.createAndPublish(
                new BigDecimal("100"),
                "BRL",
                "token-123",
                "merchant-1"
        );

        assertNotNull(payment.getId());
        verify(repo, times(1)).save(any());

        ArgumentCaptor<PaymentCreatedEvent> captor =
                ArgumentCaptor.forClass(PaymentCreatedEvent.class);

        verify(publisher).publishCreated(captor.capture());

        assertEquals("merchant-1", captor.getValue().getMerchantId());
    }
}
