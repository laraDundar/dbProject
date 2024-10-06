package pizzaSoftware;
import java.util.ArrayList;
import java.util.List;

public class cart {

    public static List<Pizza> pizzasInCart;
    public static List<Drink> drinksInCart;
    public static List<Dessert> dessertsInCart;

    public cart() {
        pizzasInCart = new ArrayList<>();
        drinksInCart = new ArrayList<>();
        dessertsInCart = new ArrayList<>();
    }

    public static void addPizza(Pizza selectedPizza) {
        pizzasInCart.add(selectedPizza);
    }
    public static void addDrink(Drink selectedDrink) {
        drinksInCart.add(selectedDrink);
    }
    public static void addDessert(Dessert selectedDessert) {
        dessertsInCart.add(selectedDessert);
    }
}
