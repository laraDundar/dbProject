package pizzaSoftware;

import java.time.LocalDate;

public class DiscountManager {
    private static final String DISCOUNT_CODE = "DISCOUNT10";
    private static final String BIRTHDAY_CODE = "HAPPYBIRTHDAY";

    private int pizzaOrderCount;//counts the # of pizzas a customer orderd from the last discount they got
    private boolean usedDiscountCode;
    private boolean usedBirthdayOffer;

    public DiscountManager() {
        this.pizzaOrderCount = 0;
        this.usedDiscountCode = false;
        this.usedBirthdayOffer = false;
    }

    /**
     * calculates the price of an order after applying a discount
     */
    public double applyDiscounts(Customer customer, double totalPrice) {
        double discount = 0.0;

        // Check for birthday offer
        if (isBirthdayOfferApplicable(customer) && !usedBirthdayOffer) {
            // Apply discount for one pizza and one drink only
            discount +=20;//just for test
            //discount += pizza.getPrice() + drinkPrice;
            usedBirthdayOffer = true; // Mark the birthday offer as used
        }

        // Check for pizza order count discount
        if (pizzaOrderCount >= 10 && !usedDiscountCode) {
            discount += totalPrice * 0.10; // 10% discount on the entire order
        }

        // Apply discount code if applicable
        if (customer.getDiscountCode() != null && customer.getDiscountCode().equals(DISCOUNT_CODE) && !usedDiscountCode) {
            discount += totalPrice * 0.10; // Additional 10% discount
            usedDiscountCode = true; // Mark the code as used
            resetPizzaOrderCount();//reset after using the discount code
        }

        // Calculate final price
        return totalPrice - discount;
    }

    public void incrementPizzaOrderCount() {
        pizzaOrderCount++;
    }

    public void incrementPizzaOrderCount(int pizzaCount){
        pizzaOrderCount += pizzaCount;
    }

    /**
     * resets the number of ordered pizzas to 0,(after using the discount)
     */
     public void resetPizzaOrderCount() {
        pizzaOrderCount = 0;
    }

    /**
     * checks if the birthday doscount is still available (a month from the birthday)
     * @param customer
     * @return
     */
    private boolean isBirthdayOfferApplicable(Customer customer) {
        LocalDate today = LocalDate.now();
        LocalDate birthday = customer.getBirthdate();
        return birthday.getDayOfMonth() == today.getDayOfMonth() && 
               birthday.getMonth() == today.getMonth() &&
               isWithinOneMonthOfBirthday(today, birthday);
    }

    /**
     * checks if between today and the birtday was less than a month
     * @param today
     * @param birthday
     * @return
     */
    private boolean isWithinOneMonthOfBirthday(LocalDate today, LocalDate birthday) {
        LocalDate oneMonthAfterBirthday = birthday.plusMonths(1);
        return today.isBefore(oneMonthAfterBirthday) && today.isAfter(birthday.minusDays(1));
    }
}
