package restaurant.abc.core;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import restaurant.abc.core.domain.entity.Reservation;
import restaurant.abc.core.domain.entity.UserProfile;
import restaurant.abc.core.domain.menu.Menu;
import restaurant.abc.core.domain.menu.MenuComponent;
import restaurant.abc.core.domain.entity.MenuItem;
import restaurant.abc.core.domain.type.ServiceType;
import restaurant.abc.core.repo.jpa.MenuItemRepo;
import restaurant.abc.core.service.common.Result;
import restaurant.abc.core.service.module.MenuItemService;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RestaurantMenuTest {

    @Autowired
    private MenuItemRepo repo;

    @Autowired
    private MenuItemService menuItemService;

    @Test
    public void menuItemCreateTest() {

        // clearing existing menu items
        repo.deleteAll();

        // Menu items
        MenuItem menuItem1 = new MenuItem("Steak", "Grilled steak with vegetables", false, 2500.00);
        MenuItem menuItem2 = new MenuItem("Salad", "Fresh garden salad", true, 600.00);

        // creating menu items
        Result<MenuItem> result1 = menuItemService.create(menuItem1);
        Result<MenuItem> result2 = menuItemService.create(menuItem2);

        // validating the result2
        assertTrue(result1.code().isSuccess());
        assertTrue(result2.code().isSuccess());

        // fetch created menu items
        List<MenuItem> menuItems = menuItemService.query();
        assertEquals(2, menuItems.size());
    }

    public static void main(String[] args) {
        MenuComponent dinnerMenu = new Menu("Dinner Menu", "Dinner selections");
        MenuComponent dessertMenu = new Menu("Dessert Menu", "Sweet treats");

        MenuComponent allMenus = new Menu("Restaurant Menu", "All available options");

        allMenus.add(dinnerMenu);
        allMenus.add(dessertMenu);

        dinnerMenu.add(new MenuItem("Steak", "Grilled steak with vegetables", false, 2500.00));
        dinnerMenu.add(new MenuItem("Salad", "Fresh garden salad", true, 600.00));
        
        dessertMenu.add(new MenuItem("Ice Cream", "Vanilla ice cream with chocolate sauce", true, 450.00));

        // Print the entire menu structure
        allMenus.print();
    }
}
