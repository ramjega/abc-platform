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

    private double price;

    private ServiceType serviceType;

    // Private constructor to prevent direct object creation
    private OfferedService(Builder builder) {
        this.name = builder.name;
        this.serviceType = builder.serviceType;
        this.description = builder.description;
    }

    // Builder Class
    public static class Builder {
        private String name;
        private ServiceType serviceType;
        private String description;

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

        // Builds the final OfferedService object
        public OfferedService build() {
            return new OfferedService(this);
        }
    }
}
