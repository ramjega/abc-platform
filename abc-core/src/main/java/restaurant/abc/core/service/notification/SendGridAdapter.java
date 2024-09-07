package restaurant.abc.core.service.notification;

public class SendGridAdapter implements NotificationAdapter {

    private SendGridService sendGridService;

    public SendGridAdapter(SendGridService sendGridService) {
        this.sendGridService = sendGridService;
    }

    @Override
    public void sendNotification(String subject, String message, String recipient) {
        // Adapting the sendEmail method to match the NotificationService interface
        sendGridService.sendEmail(subject, message, recipient);
    }
}
