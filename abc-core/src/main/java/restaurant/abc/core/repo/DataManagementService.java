package restaurant.abc.core.repo;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import restaurant.abc.core.domain.entity.MenuItem;
import restaurant.abc.core.domain.entity.OfferedService;
import restaurant.abc.core.domain.entity.UserProfile;
import restaurant.abc.core.domain.type.MenuItemType;
import restaurant.abc.core.domain.type.ServiceType;
import restaurant.abc.core.repo.jpa.*;
import restaurant.abc.core.service.common.Result;
import restaurant.abc.core.service.endpoint.auth.SecurityHolder;
import restaurant.abc.core.service.factory.UserProfileFactory;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Service
@Log4j2
public class DataManagementService {
    private final UserProfileRepo profileRepo;
    private final UserProfileFactory userProfileFactory;
    private final ServiceRepo serviceRepo;
    private final ReservationRepo reservationRepo;
    private final MenuItemRepo menuItemRepo;
    private final QueryRepo queryRepo;

    public DataManagementService(ApplicationContext ctx) {
        this.profileRepo = ctx.getBean(UserProfileRepo.class);
        this.userProfileFactory = ctx.getBean(UserProfileFactory.class);
        this.serviceRepo = ctx.getBean(ServiceRepo.class);
        this.reservationRepo = ctx.getBean(ReservationRepo.class);
        this.menuItemRepo = ctx.getBean(MenuItemRepo.class);
        this.queryRepo = ctx.getBean(QueryRepo.class);
    }

    @PostConstruct
    public void execute() {

        SecurityHolder.setProfile(UserProfile.SYSTEM);

//        serviceRepo.deleteAll();
//        reservationRepo.deleteAll();
//        menuItemRepo.deleteAll();
//        queryRepo.deleteAll();

        if (profileRepo.count() == 0) {
            log.debug("Creating initial users..");

            Result<UserProfile> systemUser = userProfileFactory.createSystemUser();
            if (systemUser.code().isSuccess()) {
                log.debug("Created system user");
            }

            Result<UserProfile> adminUser = userProfileFactory.createAdmin("Gowri", "0776914220", "Test@123");
            if (adminUser.code().isSuccess()) {
                log.debug("Created admin user");
            }

            Result<UserProfile> staffUser = userProfileFactory.createStaff("Jegan", "0779010066", "Test@123");
            if (staffUser.code().isSuccess()) {
                log.debug("Created staff user");
            }

            Result<UserProfile> customerUser = userProfileFactory.createCustomer("Sriram", "0775228995", "Test@123");
            if (customerUser.code().isSuccess()) {
                log.debug("Created customer user");
            }
        }

        if (serviceRepo.count() == 0) {
            log.debug("Creating initial services..");

            OfferedService dineIn = new OfferedService.Builder()
                    .setName("Dine-In")
                    .setServiceType(ServiceType.dine_in)
                    .setDescription("Enjoy a cozy atmosphere with our dine-in service, where you can savor your meals in comfort.")
                    .build();

            OfferedService privateDining = new OfferedService.Builder()
                    .setName("Private Dining")
                    .setServiceType(ServiceType.private_dine_in)
                    .setDescription("Book our private dining rooms for an exclusive and intimate experience.")
                    .build();

            OfferedService takeOut = new OfferedService.Builder()
                    .setName("Takeout")
                    .setServiceType(ServiceType.take_out)
                    .setDescription("Order your favorite dishes for pickup, perfectly prepared and ready to enjoy at home.")
                    .build();

            OfferedService delivery = new OfferedService.Builder()
                    .setName("Delivery")
                    .setServiceType(ServiceType.delivery)
                    .setDescription("Have our delicious meals delivered straight to your doorstep, ensuring you enjoy convenience and freshness with every bite.")
                    .build();

            OfferedService catering = new OfferedService.Builder()
                    .setName("catering")
                    .setServiceType(ServiceType.catering)
                    .setDescription("Elevate your events with our catering service, featuring a variety of dishes perfect for corporate meetings, weddings, and other special occasions.")
                    .build();

            OfferedService event = new OfferedService.Builder()
                    .setName("Event Hosting")
                    .setServiceType(ServiceType.event_hosting)
                    .setDescription("Host your events with ease, thanks to our customized packages and dedicated staff who ensure everything goes smoothly and exceeds expectations.")
                    .build();

            OfferedService liveMusic = new OfferedService.Builder()
                    .setName("Live Music")
                    .setDescription("Enjoy live music")
                    .setServiceType(ServiceType.additional)
                    .setPrice(1500)
                    .markAsAdditional()
                    .build();

            OfferedService tableDecoration = new OfferedService.Builder()
                    .setName("Table Decoration")
                    .setDescription("Beautiful table decorations")
                    .setServiceType(ServiceType.additional)
                    .setPrice(500)
                    .markAsAdditional()
                    .build();

            serviceRepo.saveAll(Arrays.asList(dineIn, privateDining, takeOut, delivery, catering, event, liveMusic, tableDecoration));
        }

        if (menuItemRepo.count() == 0) {
            // Grills
            MenuItem steak = new MenuItem("Steak", "Grilled steak with vegetables", false, 2500.00, MenuItemType.GRILL);
            MenuItem bbqChicken = new MenuItem("BBQ Chicken", "Grilled chicken with BBQ sauce", false, 2000.00, MenuItemType.GRILL);
            MenuItem lambChops = new MenuItem("Lamb Chops", "Grilled lamb chops with mint sauce", false, 2700.00, MenuItemType.GRILL);

// Salads
            MenuItem salad = new MenuItem("Salad", "Fresh garden salad", true, 600.00, MenuItemType.SALAD);
            MenuItem caesarSalad = new MenuItem("Caesar Salad", "Crisp romaine lettuce with Caesar dressing", true, 750.00, MenuItemType.SALAD);
            MenuItem greekSalad = new MenuItem("Greek Salad", "Salad with feta cheese, olives, and cucumbers", true, 800.00, MenuItemType.SALAD);

// Curries
            MenuItem chickenCurry = new MenuItem("Chicken Curry", "Spicy chicken curry served with rice", false, 1500.00, MenuItemType.CURRY);
            MenuItem muttonCurry = new MenuItem("Mutton Curry", "Rich mutton curry in a thick gravy", false, 2000.00, MenuItemType.CURRY);
            MenuItem vegetableCurry = new MenuItem("Vegetable Curry", "Mixed vegetable curry in a flavorful sauce", true, 1200.00, MenuItemType.CURRY);

// Desserts
            MenuItem iceCream = new MenuItem("Ice Cream", "Vanilla ice cream with chocolate sauce", true, 450.00, MenuItemType.DESSERT);
            MenuItem brownie = new MenuItem("Chocolate Brownie", "Warm brownie with vanilla ice cream", true, 500.00, MenuItemType.DESSERT);
            MenuItem cheesecake = new MenuItem("Cheesecake", "Classic New York-style cheesecake", true, 600.00, MenuItemType.DESSERT);
            MenuItem fruitSalad = new MenuItem("Fruit Salad", "Fresh seasonal fruits served chilled", true, 350.00, MenuItemType.DESSERT);

// Beverages
            MenuItem soda = new MenuItem("Soda", "Chilled soda with ice", true, 200.00, MenuItemType.BEVERAGE);
            MenuItem coffee = new MenuItem("Coffee", "Freshly brewed coffee", true, 300.00, MenuItemType.BEVERAGE);
            MenuItem juice = new MenuItem("Orange Juice", "Freshly squeezed orange juice", true, 400.00, MenuItemType.BEVERAGE);
            MenuItem wine = new MenuItem("Red Wine", "A glass of fine red wine", true, 800.00, MenuItemType.BEVERAGE);

// Starters
            MenuItem garlicBread = new MenuItem("Garlic Bread", "Crispy garlic bread", true, 300.00, MenuItemType.STARTER);
            MenuItem springRolls = new MenuItem("Spring Rolls", "Crispy vegetable spring rolls with dipping sauce", true, 450.00, MenuItemType.STARTER);
            MenuItem bruschetta = new MenuItem("Bruschetta", "Grilled bread with tomato and basil", true, 500.00, MenuItemType.STARTER);

// Pasta
            MenuItem pasta = new MenuItem("Pasta Alfredo", "Creamy Alfredo pasta", false, 1200.00, MenuItemType.PASTA);
            MenuItem spaghettiBolognese = new MenuItem("Spaghetti Bolognese", "Spaghetti with meat sauce", false, 1300.00, MenuItemType.PASTA);
            MenuItem penneArrabiata = new MenuItem("Penne Arrabiata", "Spicy tomato sauce with penne pasta", true, 1000.00, MenuItemType.PASTA);

// Pizza
            MenuItem margheritaPizza = new MenuItem("Margherita Pizza", "Classic margherita pizza with mozzarella and basil", false, 1000.00, MenuItemType.PIZZA);
            MenuItem pepperoniPizza = new MenuItem("Pepperoni Pizza", "Pizza topped with pepperoni and cheese", false, 1200.00, MenuItemType.PIZZA);
            MenuItem veggiePizza = new MenuItem("Veggie Pizza", "Pizza with bell peppers, onions, and mushrooms", true, 1100.00, MenuItemType.PIZZA);

// Seafood
            MenuItem seafoodPlatter = new MenuItem("Seafood Platter", "Mixed seafood grilled to perfection", false, 3000.00, MenuItemType.SEAFOOD);
            MenuItem shrimpScampi = new MenuItem("Shrimp Scampi", "Shrimp in a garlic butter sauce", false, 1800.00, MenuItemType.SEAFOOD);
            MenuItem grilledSalmon = new MenuItem("Grilled Salmon", "Salmon fillet with lemon butter", false, 2200.00, MenuItemType.SEAFOOD);

// Soups
            MenuItem tomatoSoup = new MenuItem("Tomato Soup", "Rich and creamy tomato soup", true, 500.00, MenuItemType.SOUP);
            MenuItem chickenSoup = new MenuItem("Chicken Soup", "Hearty chicken and vegetable soup", false, 600.00, MenuItemType.SOUP);
            MenuItem mushroomSoup = new MenuItem("Mushroom Soup", "Creamy mushroom soup with garlic croutons", true, 550.00, MenuItemType.SOUP);

// Snacks
            MenuItem fries = new MenuItem("Fries", "Crispy French fries with a side of ketchup", true, 400.00, MenuItemType.SNACK);
            MenuItem nachos = new MenuItem("Nachos", "Cheesy nachos with jalapenos and salsa", true, 500.00, MenuItemType.SNACK);
            MenuItem popcornChicken = new MenuItem("Popcorn Chicken", "Bite-sized crispy chicken pieces", false, 450.00, MenuItemType.SNACK);

// Others
            MenuItem risotto = new MenuItem("Mushroom Risotto", "Creamy risotto with mushrooms and parmesan", true, 1400.00, MenuItemType.RISOTTO);
            MenuItem tacos = new MenuItem("Beef Tacos", "Spicy beef tacos with salsa and guacamole", false, 1200.00, MenuItemType.TACOS);
            MenuItem falafelWrap = new MenuItem("Falafel Wrap", "Falafel with lettuce, tomatoes, and tahini", true, 900.00, MenuItemType.WRAP);
            MenuItem sushi = new MenuItem("Sushi Platter", "Assorted sushi with soy sauce and wasabi", false, 2000.00, MenuItemType.SUSHI);

            menuItemRepo.saveAll(Arrays.asList(
                    // Grills
                    steak, bbqChicken, lambChops,

                    // Salads
                    salad, caesarSalad, greekSalad,

                    // Curries
                    chickenCurry, muttonCurry, vegetableCurry,

                    // Desserts
                    iceCream, brownie, cheesecake, fruitSalad,

                    // Beverages
                    soda, coffee, juice, wine,

                    // Starters
                    garlicBread, springRolls, bruschetta,

                    // Pasta
                    pasta, spaghettiBolognese, penneArrabiata,

                    // Pizza
                    margheritaPizza, pepperoniPizza, veggiePizza,

                    // Seafood
                    seafoodPlatter, shrimpScampi, grilledSalmon,

                    // Soups
                    tomatoSoup, chickenSoup, mushroomSoup,

                    // Snacks
                    fries, nachos, popcornChicken,

                    // Others
                    risotto, tacos, falafelWrap, sushi
            ));

        }

    }
}
