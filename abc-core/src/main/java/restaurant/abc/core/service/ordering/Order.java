package restaurant.abc.core.service.ordering;

import java.util.ArrayList;
import java.util.List;

public class Order implements Subject {
    private List<Observer> observers;
    private String orderStatus;

    public Order() {
        this.observers = new ArrayList<>();
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(orderStatus);
        }
    }

    // Change order status and notify observers
    public void setOrderStatus(String status) {
        this.orderStatus = status;
        notifyObservers();
    }

    public String getOrderStatus() {
        return orderStatus;
    }
}
