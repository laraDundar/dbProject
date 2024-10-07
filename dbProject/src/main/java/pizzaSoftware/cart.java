package pizzaSoftware;
import java.util.ArrayList;
import java.util.List;

public class cart {

    private static cart instance;
    public List<Pizza> pizzasInCart;
    public List<Drink> drinksInCart;
    public List<Dessert> dessertsInCart;

    public cart() {
        pizzasInCart = new ArrayList<>();
        drinksInCart = new ArrayList<>();
        dessertsInCart = new ArrayList<>();
    }

    public static cart getInstance() {
        if (instance == null) {
            instance = new cart();
        }
        return instance;
    }

    public void addPizza(Pizza selectedPizza) {
        pizzasInCart.add(selectedPizza);
    }
    public void addDrink(Drink selectedDrink) {
        drinksInCart.add(selectedDrink);
    }
    public void addDessert(Dessert selectedDessert) {
        dessertsInCart.add(selectedDessert);
    }
    public List<Pizza> getPizzasInCart() {
        return pizzasInCart;
    }

    public List<Drink> getDrinksInCart() {
        return drinksInCart;
    }

    public List<Dessert> getDessertsInCart() {
        return dessertsInCart;
    }
}
