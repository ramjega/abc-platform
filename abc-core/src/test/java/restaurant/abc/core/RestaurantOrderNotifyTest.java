package restaurant.abc.core;

import restaurant.abc.core.service.ordering.Customer;
import restaurant.abc.core.service.ordering.Observer;
import restaurant.abc.core.service.ordering.Order;
import restaurant.abc.core.service.ordering.Staff;

public class RestaurantOrderNotifyTest {
    public static void main(String[] args) {
        // Create the order subject
        Order order = new Order();

        // Create observers
        Observer customer = new Customer("Sriram");
        Observer staff = new Staff("Jegan");

        // Register observers to the order
        order.registerObserver(customer);
        order.registerObserver(staff);

        // Change order status and notify observers
        order.setOrderStatus("Processing");
        order.setOrderStatus("Ready for Pickup");
        order.setOrderStatus("Delivered");
    }
}
