package restaurant.abc.core;

import restaurant.abc.core.domain.entity.OfferedService;
import restaurant.abc.core.domain.service.AddedService;
import restaurant.abc.core.domain.service.AdditionalServiceDecorator;
import restaurant.abc.core.domain.service.BasicService;
import restaurant.abc.core.domain.type.ServiceType;

public class AdditionalServiceTest {
    public static void main(String[] args) {
        // Start with the basic dining service
        OfferedService basicService = new OfferedService.Builder()
                .setName("Basic Dining Service")
                .setDescription("Standard dining experience")
                .setServiceType(ServiceType.dine_in)
                .build();
        basicService.setPrice(2000);

        // Additional service live music selected
        OfferedService liveMusicService = new OfferedService.Builder()
                .setName("Live Music")
                .setDescription("Enjoy live music")
                .setServiceType(ServiceType.additional)
                .build();
        liveMusicService.setPrice(1500);

        // Additional service table decoration selected
        OfferedService tableDecorationService = new OfferedService.Builder()
                .setName("Table Decoration")
                .setDescription("Beautiful table decorations")
                .setServiceType(ServiceType.additional)
                .build();
        tableDecorationService.setPrice(500);

        // Start with the basic service
        AddedService service = new BasicService(basicService);
        System.out.println(service.getDescription() + " Cost: LKR " + service.getPrice());

        // Add live music
        service = new AdditionalServiceDecorator(service, liveMusicService);
        System.out.println(service.getDescription() + " Cost: LKR " + service.getPrice());

        // Add table decoration
        service = new AdditionalServiceDecorator(service, tableDecorationService);
        System.out.println(service.getDescription() + " Cost: LKR " + service.getPrice());
    }
}
