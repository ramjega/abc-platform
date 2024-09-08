package restaurant.abc.core;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import restaurant.abc.core.domain.entity.UserProfile;
import restaurant.abc.core.repo.jpa.UserProfileRepo;
import restaurant.abc.core.service.common.Result;
import restaurant.abc.core.service.factory.UserProfileFactory;
import restaurant.abc.core.service.notification.SendGridAdapter;
import restaurant.abc.core.service.notification.TwilioAdapter;
import restaurant.abc.core.service.ordering.CustomerObserver;
import restaurant.abc.core.service.ordering.Observer;
import restaurant.abc.core.service.ordering.Order;
import restaurant.abc.core.service.ordering.StaffObserver;

import static org.junit.Assert.assertTrue;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RestaurantOrderNotifyTest {

    @Test
    public void orderStatusUpdate() {

//        Order order = new Order();
//
//        Observer customerObserver = new CustomerObserver(sendGridAdapter, "Sriram", "0775228995");
//        Observer staffObserver = new StaffObserver(twilioAdapter, "Jegan", "0779010066");
//
//        // Register observers to the order
//        order.registerObserver(customerObserver);
//        order.registerObserver(staffObserver);
//
//        // Change order status and notify observers
//        order.setOrderStatus("Processing");
//        order.setOrderStatus("Ready for Pickup");
//        order.setOrderStatus("Delivered");
    }
}
