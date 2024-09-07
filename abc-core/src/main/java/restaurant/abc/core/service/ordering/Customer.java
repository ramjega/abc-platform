package restaurant.abc.core.service.ordering;

public class Customer implements Observer {
    private String name;

    public Customer(String name) {
        this.name = name;
    }

    @Override
    public void update(String orderStatus) {
        System.out.println("Dear " + name + ", your order is now " + orderStatus);
    }
}
