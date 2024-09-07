package restaurant.abc.core.domain.service;

public abstract class ServiceDecorator implements AddedService {
    protected AddedService decoratedService;

    public ServiceDecorator(AddedService decoratedService) {
        this.decoratedService = decoratedService;
    }

    @Override
    public String getDescription() {
        return decoratedService.getDescription();
    }

    @Override
    public double getPrice() {
        return decoratedService.getPrice();
    }
}