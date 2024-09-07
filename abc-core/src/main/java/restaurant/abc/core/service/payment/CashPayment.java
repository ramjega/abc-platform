package restaurant.abc.core.service.payment;

public class CashPayment implements PaymentMethod {
    @Override
    public void processPayment(double amount) {
        System.out.println("Paid " + amount + " in cash.");
    }
}
