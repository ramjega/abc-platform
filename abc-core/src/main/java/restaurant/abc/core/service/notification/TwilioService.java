package restaurant.abc.core.service.notification;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class TwilioService {

    private static final String ACCOUNT_SID = System.getenv("TWILIO_ACCOUNT_SID");
    private static final String AUTH_TOKEN = System.getenv("TWILIO_AUTH_TOKEN");
    private static final String TWILIO_PHONE_NUMBER = System.getenv("TWILIO_PHONE_NUMBER");

    static {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    public void sendSMS(String subject, String message, String phoneNumber) {
        try {
            // Create and send the message using Twilio API
            Message sms = Message.creator(
                    new PhoneNumber(phoneNumber),
                    new PhoneNumber(TWILIO_PHONE_NUMBER),
                    subject + ": " + message
            ).create();

            System.out.println("SMS sent successfully to " + phoneNumber + ": " + sms.getSid());
        } catch (Exception e) {
            System.out.println("Failed to send SMS to " + phoneNumber + ": " + e.getMessage());
        }
    }
}
