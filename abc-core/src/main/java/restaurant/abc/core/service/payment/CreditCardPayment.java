package restaurant.abc.core.service.payment;

public class CreditCardPayment implements PaymentMethod {
    private String cardNumber;
    private String cardHolderName;
    private String expiryDate;

    public CreditCardPayment(String cardNumber, String cardHolderName, String expiryDate) {
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.expiryDate = expiryDate;
    }

    @Override
    public void processPayment(double amount) {
        System.out.println("Paid " + amount + " using Credit Card: " + cardNumber);
    }
}
