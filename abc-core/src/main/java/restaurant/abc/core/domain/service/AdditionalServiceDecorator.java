package restaurant.abc.core.domain.service;

import restaurant.abc.core.domain.entity.OfferedService;

public class AdditionalServiceDecorator extends ServiceDecorator {
    private OfferedService additionalService;

    public AdditionalServiceDecorator(AddedService decoratedService, OfferedService additionalService) {
        super(decoratedService);
        this.additionalService = additionalService;
    }

    @Override
    public String getDescription() {
        return decoratedService.getDescription() + ", with " + additionalService.getName();
    }

    @Override
    public double getPrice() {
        return decoratedService.getPrice() + additionalService.getPrice();
    }
}
