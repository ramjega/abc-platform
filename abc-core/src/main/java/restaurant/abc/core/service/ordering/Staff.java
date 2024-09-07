package restaurant.abc.core.service.ordering;

public class Staff implements Observer {
    private String name;

    public Staff(String name) {
        this.name = name;
    }

    @Override
    public void update(String orderStatus) {
        System.out.println("Staff member " + name + ", the order status is now " + orderStatus);
    }
}
