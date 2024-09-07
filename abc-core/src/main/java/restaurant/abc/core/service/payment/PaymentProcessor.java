package restaurant.abc.core.service.payment;

public class PaymentProcessor {
    private PaymentMethod paymentMethod;

    // Set the payment strategy dynamically
    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    // Execute the payment using the selected strategy
    public void pay(double amount) {
        paymentMethod.processPayment(amount);
    }
}
