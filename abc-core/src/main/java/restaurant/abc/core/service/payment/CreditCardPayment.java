package restaurant.abc.core.service.payment;

public class CreditCardPayment implements PaymentMethod {
    private String cardNumber;
    private String name;
    private String expiryDate;

    public CreditCardPayment(String cardNumber, String name, String expiryDate) {
        this.cardNumber = cardNumber;
        this.name = name;
        this.expiryDate = expiryDate;
    }

    @Override
    public String processPayment(double amount) {
        return "Payment processed with Credit Card";
    }
}
