package restaurant.abc.core.service.notification;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;

import java.io.IOException;

public class SendGridService {

    public void sendEmail(String subject, String message, String recipientEmail) {
        Email from = new Email(System.getenv("FROM_EMAIL_ADDRESS"));
        Email to = new Email(recipientEmail);
        Content content = new Content("text/plain", message);

        Mail mail = new Mail();
        mail.setFrom(from);
        mail.setSubject(subject);
        mail.addContent(content);

        Personalization personalization = new Personalization();
        personalization.addTo(to);
        mail.addPersonalization(personalization);

        SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println("Email sent! Status Code: " + response.getStatusCode());
        } catch (IOException ex) {
            System.err.println("Error sending email: " + ex.getMessage());
        }
    }
}
