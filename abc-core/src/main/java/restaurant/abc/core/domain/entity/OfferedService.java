package restaurant.abc.core.domain.entity;

import lombok.*;
import restaurant.abc.core.domain.type.ServiceType;

import javax.persistence.*;

@Data
@Entity
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Table(indexes = {
        @Index(columnList = "name"),
})
public class OfferedService extends ResourceEntity {

    @Id
    @GeneratedValue(generator = "service_id_generator")
    protected Long id;

    private String name;

    private String description;

    private Double price;

    private Boolean additionalService = false;

    private ServiceType serviceType;

    // Private constructor to prevent direct object creation
    private OfferedService(Builder builder) {
        this.name = builder.name;
        this.serviceType = builder.serviceType;
        this.description = builder.description;
        this.additionalService = builder.additionalService;
        this.price = builder.price;
    }

    // Builder Class
    public static class Builder {
        private String name;
        private ServiceType serviceType;
        private String description;
        private boolean additionalService;
        private double price;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setServiceType(ServiceType serviceType) {
            this.serviceType = serviceType;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder markAsAdditional() {
            this.additionalService = true;
            return this;
        }

        public Builder setPrice(double price) {
            this.price = price;
            return this;
        }

        // Builds the final OfferedService object
        public OfferedService build() {
            return new OfferedService(this);
        }
    }
}
