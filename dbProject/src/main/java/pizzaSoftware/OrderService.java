package pizzaSoftware;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
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
        discountManager = new DiscountManager();
    }

    public Order placeOrder(Customer customer, List<Integer> pizzaIds, List<Integer> drinkIds, List<Integer> dessertIds, String discountCode) {
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
       
       /*  //Get DeliveryPerson from Delivery via delivery_id
        Delivery delivery = Delivery.getDeliveryId(deliveryId);
        if (delivery != null) {
            int deliveryPersonId = delivery.getDeliveryPersonId(); // Fetch delivery_person_id

        // Get DeliveryPersonArea using delivery_person_id
        DeliveryPersonArea deliveryPersonArea = DeliveryPersonArea.getByDeliveryPersonId(deliveryPersonId);
        if (deliveryPersonArea != null) {
            int areaId = deliveryPersonArea.getAreaId(); // Fetch area_id

        // Get DeliveryArea using area_id
        DeliveryArea deliveryArea = DeliveryArea.getDeliveryAreaById(areaId);
        if (deliveryArea != null) {
            int estimatedTime = estimateDeliveryTime(pizzaCount, deliveryArea.getDistance()); // Estimate time based on distance
            order.setEstimatedDeliveryTime(estimatedTime); // Set the estimated time on the order
        } else {
            System.out.println("Delivery area not found for areaId: " + areaId);
        }
    } else {
        System.out.println("Delivery person area not found for deliveryPersonId: " + deliveryPersonId);
    }
} else {
    System.out.println("Delivery not found for deliveryId: " + deliveryId);
}

        int estimatedTime = estimateDeliveryTime(pizzaCount, deliveryArea.getDistance());
        order.setEstimatedDeliveryTime(estimatedTime);
        
        // Save order to the database (this requires implementation of the database logic)
        saveOrderToDatabase(order);
*/
         //Send confirmation to the customer (simple print for now)
        System.out.println("Order Confirmation: ");
        System.out.println("Customer: " + customer.getFirstName() + " " + customer.getLastName());
        System.out.println("Order Total: " + totalPrice);
        System.out.println("Estimated Delivery Time: 30 minutes");
        return order;
    }

   public void saveOrderToDatabase(Order order) {
    String orderQuery = "INSERT INTO Orders (customer_id, order_timestamp, status, estimated_delivery_time, price, price_discounted) VALUES (?, ?, ?, ?, ?, ?)";
    
    try (Connection conn = menuService.getdbConnector().connect(); 
         PreparedStatement orderStmt = conn.prepareStatement(orderQuery, Statement.RETURN_GENERATED_KEYS)) {
        
        // Set parameters for the Orders table
        orderStmt.setInt(1, order.getCustomerId()); // Assuming you have a method to get customer ID
        orderStmt.setTimestamp(2, order.getOrderTimestamp()); // Assuming your order class has this method
        orderStmt.setString(3, order.getStatus()); // Assuming you have a status for the order
        orderStmt.setInt(4, order.getEstimatedDeliveryTime()); // Assuming you have a method for this
        orderStmt.setDouble(5, order.getPrice()); // Assuming getPrice() returns BigDecimal
        orderStmt.setDouble(6, order.getPriceDiscounted()); // Assuming getPriceDiscounted() returns BigDecimal
        
        // Execute the insert and retrieve the generated order ID
        orderStmt.executeUpdate();
        
        try (ResultSet generatedKeys = orderStmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                int orderId = generatedKeys.getInt(1); // Get the generated order ID
                saveOrderItems(conn, orderId, order.getOrderItems()); // Save associated order items
            } else {
                throw new SQLException("Inserting order failed, no ID obtained.");
            }
        }
        
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

private void saveOrderItems(Connection conn, int orderId, List<OrderItem> orderItems) throws SQLException {
    String itemQuery = "INSERT INTO Order_Items (order_id, pizza_id, drink_id, dessert_id, quantity) VALUES (?, ?, ?, ?, ?)";
    
    try (PreparedStatement itemStmt = conn.prepareStatement(itemQuery)) {
        for (OrderItem item : orderItems) {
            itemStmt.setInt(1, orderId);
            itemStmt.setInt(2, item.getPizzaId()); // Assuming you have methods to get IDs
            itemStmt.setInt(3, item.getDrinkId());
            itemStmt.setInt(4, item.getDessertId());
            itemStmt.setInt(5, item.getQuantity());
            itemStmt.addBatch(); // Add to batch
        }
        itemStmt.executeBatch(); // Execute all inserts in one go
    }
}
/* 
// Get Delivery Area based on the delivery person ID
private DeliveryArea getDeliveryAreaByDeliveryPersonId(int deliveryPersonId) {
    for (DeliveryPersonArea dpa : deliveryPersonAreas) {
        if (dpa.getDeliveryPersonId() == deliveryPersonId) {
            // Found the delivery person area, now find the delivery area
            int areaId = dpa.getAreaId();
            for (DeliveryArea da : deliveryAreas) {
                if (da.getAreaId() == areaId) {
                    return da; // Return the corresponding DeliveryArea
                }
            }
        }
    }
    return null; // Return null if not found
}

public int estimateDeliveryTime(int deliveryPersonId, int numberOfPizzas) {
    DeliveryArea deliveryArea = getDeliveryAreaByDeliveryPersonId(deliveryPersonId);
    if (deliveryArea == null) {
        throw new IllegalArgumentException("No delivery area found for delivery person ID: " + deliveryPersonId);
    }

    int distance = deliveryArea.getDistance();
    
    // Base delivery time in minutes
    int baseTime = 20; // Base time (you can adjust this)
    int pizzaTime = 7; // Time added for each pizza in minutes

    // Calculate estimated time based on distance and number of pizzas
    int estimatedTime = baseTime + (pizzaTime * numberOfPizzas);

    // Add additional time based on distance
    switch (distance) {
        case 1: // Close
            estimatedTime += 0; // No extra time for close
            break;
        case 2: // Far
            estimatedTime += 10; // Add 10 minutes for far
            break;
        case 3: // Very far
            estimatedTime += 20; // Add 20 minutes for very far
            break;
        default:
            throw new IllegalArgumentException("Invalid distance value: " + distance);
    }
    
    return estimatedTime;
}
*/

}
