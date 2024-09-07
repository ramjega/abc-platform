package restaurant.abc.core.domain.service;

import restaurant.abc.core.domain.entity.OfferedService;

public class BasicService implements AddedService {
    private OfferedService basicService;

    public BasicService(OfferedService basicService) {
        this.basicService = basicService;
    }

    @Override
    public String getDescription() {
        return basicService.getDescription();
    }

    @Override
    public double getPrice() {
        return basicService.getPrice();
    }
}