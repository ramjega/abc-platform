package restaurant.abc.core;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import restaurant.abc.core.service.payment.*;

public class PaymentProcessingTest {

    private PaymentProcessor paymentProcessor;

    @Before
    public void setUp() {
        paymentProcessor = new PaymentProcessor();
    }

    @Test
    public void testCreditCardPayment() {
        PaymentMethod creditCardPayment = new CreditCardPayment("1234-5678-9876-5432", "Sriram", "12/24");
        paymentProcessor.setPaymentMethod(creditCardPayment);

        String result = paymentProcessor.pay(1200);

        assertEquals("Payment processed with Credit Card", result);
    }

    @Test
    public void testCashPayment() {
        PaymentMethod cashPayment = new CashPayment();
        paymentProcessor.setPaymentMethod(cashPayment);

        String result = paymentProcessor.pay(1200);

        assertEquals("Payment processed with Cash", result);
    }

    @Test
    public void testWalletPayment() {
        PaymentMethod walletPayment = new GiftVoucherPayment("GIF-0868");
        paymentProcessor.setPaymentMethod(walletPayment);

        String result = paymentProcessor.pay(1200);

        assertEquals("Payment processed with Gift Voucher", result);
    }
}