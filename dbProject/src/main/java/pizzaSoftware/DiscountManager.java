package pizzaSoftware;

import java.time.LocalDate;
import java.util.List;

public class DiscountManager {
    private static final String DISCOUNT_CODE = "DISCOUNT10";
    private static final String BIRTHDAY_CODE = "HAPPYBIRTHDAY";

    private int pizzaOrderCount;//counts the # of pizzas a customer orderd from the last discount they got
    private Order order;

    public DiscountManager(Order order) {
        this.order = order;
    }

     // Method to check if a customer gets a 10% discount after 10 pizzas
     public double applyLoyaltyDiscount(Customer customer, double totalPrice) {
        if (customer.getPizzaOrderCount() >= 10) {
            System.out.println("Congratulations! You receive a 10% loyalty discount.");
            totalPrice *= 0.90; // Apply 10% discount
            customer.resetPizzaOrderCount();//the customer has again the # of pizzas reset to 0
        }
        return totalPrice;
    }


     //Method to check if it's the customer's birthday and apply free pizza and drink
    public double applyBirthdayOffer(Customer customer, double totalPrice, List <OrderItem> orderItems, MenuService menuService) {
        if (isBirthday(customer.getBirthdate())) {
            System.out.println("Happy Birthday! You get a free pizza and drink.");
            // Adjust totalPrice to account for the free pizza and drink
            totalPrice -= getFreePizzaAndDrinkPrice(orderItems, totalPrice, menuService);
        }
        return totalPrice;
    }

    // Helper method to check if today is the customer's birthday
    private boolean isBirthday(LocalDate birthDate) {
        LocalDate today = LocalDate.now();
        return birthDate.getDayOfMonth() == today.getDayOfMonth() && birthDate.getMonth() == today.getMonth();
    }

    // Assume this method calculates the price of a free pizza and drink
   private double getFreePizzaAndDrinkPrice(List <OrderItem> orderItems, double totalPrice, MenuService menuService) {
        // Adjust the price of the first pizza to 0
        for (OrderItem item : orderItems) {
            if (item.getPizzaId() != -100000) {
                Pizza freePizza = menuService.getPizzaById(item.getPizzaId());//i get all the details about each chosen pizza
                double pizzaPrice = freePizza.getPrice(); // Get the price of the first pizza
                totalPrice = totalPrice - pizzaPrice;
                break; // Only the first pizza should be free
            }
        }

        boolean drinkFound = false;
           // Check if there's a drink in the order
           for (OrderItem item : orderItems) {
            if (item.getDrinkId() != -100000) {
                drinkFound = true;
                Drink freeDrink = menuService.getDrinkById(item.getPizzaId());//i get all the details about drink
                double drinkPrice = freeDrink.getPrice(); // Get the price of the first pizza
                totalPrice = totalPrice - drinkPrice;
                break;
            }
        }

        // If no drink is found, add a Coke with price 0
        if (!drinkFound) {
            OrderItem freeCoke = new OrderItem();
            freeCoke.setDrinkId(1);
            orderItems.add(freeCoke);
            System.out.println("No drink found, adding a free drink to the order.");
        }

        return totalPrice;

       
    }
    

}
