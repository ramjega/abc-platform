package restaurant.abc.core.service.payment;

public class GiftVoucherPayment implements PaymentMethod {
    private String voucherCode;

    public GiftVoucherPayment(String voucherCode) {
        this.voucherCode = voucherCode;
    }

    @Override
    public String processPayment(double amount) {
        return "Payment processed with Gift Voucher";
    }
}