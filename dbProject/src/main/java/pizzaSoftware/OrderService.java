package pizzaSoftware;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.sql.Statement;
import java.sql.Time;

public class OrderService {

    private MenuService menuService;
    private DiscountManager discountManager;

    public OrderService() {
        menuService = new MenuService();
    }

    public Order placeOrder(Customer customer, List<Integer> pizzaIds, List<Integer> drinkIds, List<Integer> dessertIds) {
        //make sure that an order includes at leat a pizza
        if (pizzaIds.isEmpty()) {
            throw new IllegalArgumentException("An order must include at least one pizza.");
        }
        //create the order
        Order order = new Order();
        order.setCustomerId(customer.getCustomerId());
        order.setOrderTimestamp(new Timestamp(System.currentTimeMillis()));
        order.setStatus("Processing");
        //order.setDeliveryId(deliveryId);

        
        List<OrderItem> orderItems = new ArrayList<>();//the items in the order
        double totalPrice = 0.0;//the price of the order
        int pizzaCount = 0;//counter for the number of pizzas ordered

        for (Integer pizzaId : pizzaIds) {
            Pizza pizza = menuService.getPizzaById(pizzaId);//i get all the details about each chosen pizza
            if (pizza != null) {
               // Check if the pizza is already in the order
            boolean itemFound = false;
            for (OrderItem item : orderItems) {
                if (item.getPizzaId() == pizza.getPizzaId()) {
                    item.setQuantity(item.getQuantity() + 1); // Increment quantity
                    itemFound = true;
                    break;
                }
            }
            // If not found, add new OrderItem
            if (!itemFound) {
                OrderItem orderItem = new OrderItem();
                orderItem.setPizzaId(pizza.getPizzaId());
                orderItem.setQuantity(1); // Start with quantity 1
                orderItems.add(orderItem);
            }
            totalPrice += pizza.getPrice(); // Add price to total
            pizzaCount++;//increment pizza count
            }
        }

        for (Integer drinkId : drinkIds) {
            Drink drink = menuService.getDrinkById(drinkId);
            if (drink != null) {
                // Check if the drink is already in the order
            boolean itemFound = false;
            for (OrderItem item : orderItems) {
                if (item.getDrinkId() == drink.getDrinkId()) {
                    item.setQuantity(item.getQuantity() + 1); // Increment quantity
                    itemFound = true;
                    break;
                }
            }
            // If not found, add new OrderItem
            if (!itemFound) {
                OrderItem orderItem = new OrderItem();
                orderItem.setDrinkId(drink.getDrinkId());
                orderItem.setQuantity(1); // Start with quantity 1
                orderItems.add(orderItem);
            }
            totalPrice += drink.getPrice(); // Add price to total
            }
        }

        for (Integer dessertId : dessertIds) {
            Dessert dessert = menuService.getDessertById(dessertId);
            if (dessert != null) {
                // Check if the dessert is already in the order
            boolean itemFound = false;
            for (OrderItem item : orderItems) {
                if (item.getDessertId() == dessert.getDessertId()) {
                    item.setQuantity(item.getQuantity() + 1); // Increment quantity
                    itemFound = true;
                    break;
                }
            }
            // If not found, add new OrderItem
            if (!itemFound) {
                OrderItem orderItem = new OrderItem();
                orderItem.setDessertId(dessert.getDessertId());
                orderItem.setQuantity(1); // Start with quantity 1
                orderItems.add(orderItem);
            }
            totalPrice += dessert.getPrice(); // Add price to total
            }
        }

        // Increment the pizza order count in the DiscountManager
        //discountManager.incrementPizzaOrderCount(pizzaCount); // Pass the pizzaCount to update

        // Apply discounts using DiscountManager
        //double finalPrice = discountManager.applyDiscounts(customer, totalPrice);
        order.setPrice(totalPrice);
        //order.setPriceDiscounted(finalPrice);
        order.setOrderItems(orderItems);
        System.out.println("The order items are:" + order.getOrderItems());
        discountManager = new DiscountManager(order);
        double finalPrice = discountManager.applyLoyaltyDiscount(customer, totalPrice);
        finalPrice = discountManager.applyBirthdayOffer(customer, totalPrice, orderItems, menuService);
        order.setPriceDiscounted(finalPrice);
        System.out.println("The discounted price 1 :" + order.getPriceDiscounted());

        for (OrderItem item : orderItems) {
            System.out.println( ", Pizza ID: " + item.getPizzaId() + 
                               ", Drink ID: " + item.getDrinkId() + ", Dessert ID: " + item.getDessertId() +
                               ", Quantity: " + item.getQuantity());
        }
       
        // Save order to the database (this requires implementation of the database logic)
        saveOrderToDatabase(order, orderItems);
        System.out.println(order.getOrderId());
        System.out.println(order.getCustomerId());
        System.out.println(order.getPrice());

        String orderZipCode = customer.getZipCode();
        System.out.println("!!!CUSTOMER ZIPCODE ORDER: " + customer.getZipCode());

         //Send confirmation to the customer (simple print for now)
        System.out.println("Order Confirmation: ");
        System.out.println("Customer: " + customer.getName());
        System.out.println("Order Total: " + totalPrice);
        System.out.println("Discounted total " + finalPrice);
        // Estimate the preparation time based on the number of pizzas
        int estimatedPreparationTime = estimatePreparationTime(pizzaCount);
        System.out.println("ESTIMATED PREPARATION TIME : " + estimatePreparationTime(pizzaCount));
        order.setEstimatedPreparationTime(estimatedPreparationTime);

        DeliveryService deliveryService = new DeliveryService(orderZipCode);
        deliveryService.placeDelivery(order);
        //System.out.println("Delivery time: " + deliveryService.estimateDeliveryTime());
    
        // Print the estimated preparation time
        System.out.println("Estimated Preparation Time: " + estimatedPreparationTime + " minutes");
            return order;
    }
    public int estimatePreparationTime(int pizzaCount) {
        int baseTime = 5; // Base time for cancellation window
        int timePerPizza = 5; // Time added per pizza
        
        // Calculate total preparation time
        int totalPreparationTime = baseTime + (pizzaCount * timePerPizza);
        
        return totalPreparationTime;
    }

   public void saveOrderToDatabase(Order order, List<OrderItem> orderItems) {
    String orderQuery = "INSERT INTO Orders (customer_id, order_timestamp, status, price, price_discounted) VALUES (?, ?, ?, ?, ?)";
    
    try (Connection conn = menuService.getdbConnector().connect(); 
         PreparedStatement orderStmt = conn.prepareStatement(orderQuery, Statement.RETURN_GENERATED_KEYS)) {
        
        // Set parameters for the Orders table
        orderStmt.setInt(1, order.getCustomerId()); // Assuming you have a method to get customer ID
        orderStmt.setTimestamp(2, order.getOrderTimestamp()); // Assuming your order class has this method
        orderStmt.setString(3, order.getStatus()); // Assuming you have a status for the order
        //orderStmt.setInt(4, order.estimatedPreparationTime(order.getCustomerId())); // Assuming you have a method for this
        orderStmt.setDouble(4, order.getPrice()); // Assuming getPrice() returns BigDecimal
        orderStmt.setDouble(5, order.getPriceDiscounted()); // Assuming getPriceDiscounted() returns BigDecimal
        
        // Execute the insert and retrieve the generated order ID
        orderStmt.executeUpdate();
        
        try (ResultSet generatedKeys = orderStmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                int orderId = generatedKeys.getInt(1); // Get the generated order ID
                order.setOrderId(orderId);
                saveOrderItems(conn, orderId, orderItems); // Save associated order items
            } else {
                throw new SQLException("Inserting order failed, no ID obtained.");
            }
        }
        
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

private void saveOrderItems(Connection conn, int orderId, List<OrderItem> orderItems) throws SQLException {
    String itemQuery = "INSERT INTO order_items (order_id, pizza_id, drink_id, dessert_id, quantity) VALUES (?, ?, ?, ?, ?)";
    
    try (PreparedStatement itemStmt = conn.prepareStatement(itemQuery, Statement.RETURN_GENERATED_KEYS)) {
        for (OrderItem item : orderItems) {
            // Ensure the item has a quantity greater than 0
            if (item.getQuantity() > 0) {
                itemStmt.setInt(1, orderId); // Set the order ID
                itemStmt.setInt(2, item.getPizzaId()); // Set the pizza ID
                itemStmt.setInt(3, item.getDrinkId()); // Set the drink ID
                itemStmt.setInt(4, item.getDessertId()); // Set the dessert ID
                itemStmt.setInt(5, item.getQuantity()); // Set the quantity

                // Execute the insert for this item
                itemStmt.executeUpdate();

                // Retrieve generated orderItemId (if needed)
                try (ResultSet generatedKeys = itemStmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int orderItemId = generatedKeys.getInt(1); // Get the generated orderItem ID
                        item.setOrderItemId(orderItemId);
                        item.setOrderItemId(orderItemId); // Assuming you have a method to set the ID in OrderItem
                    }
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace(); // Handle the exception accordingly
    }
    
}

// New method for cancelling an order
public boolean cancelOrder(int orderId) {
    try (Connection conn = menuService.getdbConnector().connect()) {
        // Fetch the order to check the order timestamp
        String orderQuery = "SELECT order_timestamp FROM orders WHERE order_id = ?";
        try (PreparedStatement orderStmt = conn.prepareStatement(orderQuery)) {
            orderStmt.setInt(1, orderId);
            ResultSet rs = orderStmt.executeQuery();
            if (rs.next()) {
                Timestamp orderTimestamp = rs.getTimestamp("order_timestamp");
                LocalDateTime orderDateTime = orderTimestamp.toLocalDateTime();
                LocalDateTime currentDateTime = LocalDateTime.now();

                // Check if the order is within the 5-minute cancellation window
                if (java.time.Duration.between(orderDateTime, currentDateTime).toMinutes() <= 5) {
                    // Proceed to cancel the order
                    String cancelQuery = "UPDATE orders SET status = ? WHERE order_id = ?";
                    try (PreparedStatement cancelStmt = conn.prepareStatement(cancelQuery)) {
                        cancelStmt.setString(1, "Cancelled");
                        cancelStmt.setInt(2, orderId);
                        int rowsUpdated = cancelStmt.executeUpdate();
                        return rowsUpdated > 0; // Return true if the order was cancelled
                    }
                } else {
                    System.out.println("Cancellation period has expired for order ID: " + orderId);
                    return false; // Cancellation period has expired
                }
            } else {
                System.out.println("Order not found for order ID: " + orderId);
                return false; // Order not found
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        return false; // Return false on SQL error
    }
}

// Method to schedule order status updates
    /*public void processOrder(Order order, DeliveryPerson deliveryPerson) {
        System.out.println("Order placed: " + order.getOrderId());
        updateOrderStatus(order, OrderStatus.PLACED);

        // Schedule the order to start cooking after COOKING_DELAY minutes
        scheduler.schedule(() -> {
            updateOrderStatus(order, OrderStatus.IN_PROGRESS);
        }, COOKING_DELAY, TimeUnit.MINUTES);

        // Schedule the order to be ready for delivery after preparation time
        scheduler.schedule(() -> {
            updateOrderStatus(order, OrderStatus.READY_FOR_DELIVERY);

            // Check if delivery person is available and start delivery
            if (deliveryPerson.isAvailable()) {
                updateOrderStatus(order, OrderStatus.DELIVERY_STARTED);
                
                // Schedule the delivery to be completed after delivery time
                scheduler.schedule(() -> {
                    updateOrderStatus(order, OrderStatus.DELIVERED);
                    System.out.println("Order delivered: " + order.getOrderId());
                }, DELIVERY_TIME, TimeUnit.MINUTES);
            }
        }, COOKING_DELAY + PREPARATION_TIME, TimeUnit.MINUTES);
    }

    // Method to update order status
    public void updateOrderStatus(Order order, OrderStatus newStatus) {
        order.setStatus(newStatus);
        System.out.println("Order " + order.getOrderId() + " status changed to: " + newStatus);
    }

    // Shutdown scheduler (optional, call this when the application ends)
    public void shutdownScheduler() {
        scheduler.shutdown();
    }
}*/


}
