package restaurant.abc.core;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import restaurant.abc.core.domain.entity.OfferedService;
import restaurant.abc.core.domain.service.AddedService;
import restaurant.abc.core.domain.service.AdditionalServiceDecorator;
import restaurant.abc.core.domain.service.BasicService;
import restaurant.abc.core.domain.type.ServiceType;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AdditionalServiceTest {

    private OfferedService basicService;
    private OfferedService liveMusicService;
    private OfferedService tableDecorationService;
    private AddedService service;

    @Before
    public void setUp() {
        basicService = new OfferedService.Builder()
                .setName("Basic Dining Service")
                .setDescription("Standard dining experience")
                .setServiceType(ServiceType.dine_in)
                .setPrice(2000)
                .markAsAdditional()
                .build();

        liveMusicService = new OfferedService.Builder()
                .setName("Live Music")
                .setDescription("Enjoy live music")
                .setServiceType(ServiceType.additional)
                .setPrice(1500)
                .markAsAdditional()
                .build();

        tableDecorationService = new OfferedService.Builder()
                .setName("Table Decoration")
                .setDescription("Beautiful table decorations")
                .setServiceType(ServiceType.additional)
                .setPrice(500)
                .markAsAdditional()
                .build();
        
        service = new BasicService(basicService);
    }

    @Test
    public void testBasicService() {
        assertEquals("Standard dining experience", service.getDescription());
        assertEquals(2000, service.getPrice(), 0);
    }

    @Test
    public void testServiceWithLiveMusic() {
        // Add live music
        service = new AdditionalServiceDecorator(service, liveMusicService);

        assertEquals("Standard dining experience, with Live Music", service.getDescription());
        assertEquals(3500, service.getPrice(), 0);  // 2000 (basic) + 1500 (live music)
    }

    @Test
    public void testServiceWithTableDecoration() {
        // Add live music
        service = new AdditionalServiceDecorator(service, liveMusicService);
        // Add table decoration
        service = new AdditionalServiceDecorator(service, tableDecorationService);

        assertEquals("Standard dining experience, with Live Music, with Table Decoration", service.getDescription());
        assertEquals(4000, service.getPrice(), 0);  // 2000 (basic) + 1500 (live music) + 500 (table decoration)
    }
}
