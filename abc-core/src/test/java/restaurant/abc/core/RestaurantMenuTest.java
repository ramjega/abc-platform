package restaurant.abc.core;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import restaurant.abc.core.domain.menu.Menu;
import restaurant.abc.core.domain.menu.MenuComponent;
import restaurant.abc.core.domain.entity.MenuItem;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RestaurantMenuTest {
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
