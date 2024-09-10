package restaurant.abc.core.service.payment;

public class PaymentProcessor {
    private PaymentMethod paymentMethod;

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String pay(double amount) {
        if (paymentMethod == null) {
            return "No payment method set";
        }
        return paymentMethod.processPayment(amount);
    }
}
