package restaurant.abc.core.service.notification;

public class TwilioAdapter implements NotificationAdapter {

    private TwilioService twilioService;

    public TwilioAdapter(TwilioService twilioService) {
        this.twilioService = twilioService;
    }

    @Override
    public void sendNotification(String subject, String message, String recipient) {
        // Adapting the sendSMS method to match the NotificationService interface
        twilioService.sendSMS(subject, message, recipient);
    }
}
