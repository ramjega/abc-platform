package restaurant.abc.core.service.payment;

public class GiftVoucherPayment implements PaymentMethod {
    private String voucherId;

    public GiftVoucherPayment(String walletId) {
        this.voucherId = walletId;
    }

    @Override
    public void processPayment(double amount) {
        System.out.println("Paid " + amount + " using Digital Wallet: " + voucherId);
    }
}
