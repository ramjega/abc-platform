package restaurant.abc.core.domain.menu;

public interface MenuComponent {
     void add(MenuComponent menuComponent) ;

     void remove(MenuComponent menuComponent) ;

     MenuComponent getChild(int i) ;

     String getName() ;

     String getDescription() ;

     double getPrice() ;

     boolean isVegetarian() ;

     void print() ;
}





