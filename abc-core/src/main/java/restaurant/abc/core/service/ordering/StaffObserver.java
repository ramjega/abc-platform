package restaurant.abc.core.service.ordering;

import restaurant.abc.core.service.notification.NotificationAdapter;

public class StaffObserver implements Observer {
    private NotificationAdapter notificationAdapter;
    private String name;
    private String recipient;

    public StaffObserver(NotificationAdapter notificationAdapter, String name, String recipient) {
        this.notificationAdapter = notificationAdapter;
        this.name = name;
        this.recipient = recipient;
    }

    @Override
    public void notify(String orderStatus) {
        String message = "Staff member " + name + ", the order status is now " + orderStatus;
        notificationAdapter.sendNotification("Order Status Update", message, recipient);
    }
}
