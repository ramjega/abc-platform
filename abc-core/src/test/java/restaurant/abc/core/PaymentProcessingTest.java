package restaurant.abc.core;

import restaurant.abc.core.service.payment.*;

public class PaymentProcessingTest {
    public static void main(String[] args) {
        PaymentProcessor paymentProcessor = new PaymentProcessor();

        // Customer chooses to pay with a credit card
        PaymentMethod creditCardPayment = new CreditCardPayment("1234-5678-9876-5432", "Sriram", "12/24");
        paymentProcessor.setPaymentMethod(creditCardPayment);
        paymentProcessor.pay(1200);

        // Customer switches to cash payment
        PaymentMethod cashPayment = new CashPayment();
        paymentProcessor.setPaymentMethod(cashPayment);
        paymentProcessor.pay(1200);

        // Customer switches to a digital wallet payment
        PaymentMethod walletPayment = new GiftVoucherPayment("GIF-0868");
        paymentProcessor.setPaymentMethod(walletPayment);
        paymentProcessor.pay(1200);
    }
}
