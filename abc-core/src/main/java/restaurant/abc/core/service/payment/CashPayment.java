package restaurant.abc.core.service.payment;

public class CashPayment implements PaymentMethod {
    @Override
    public String processPayment(double amount) {
        return "Payment processed with Cash";
    }
}
