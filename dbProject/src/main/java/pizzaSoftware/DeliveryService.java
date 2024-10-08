package pizzaSoftware;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DeliveryService {
    private List<Order> orders; 
    private List<DeliveryPerson> deliveryPeople; 
    private List<DeliveryBatches> deliveryBatches; 
    private String zipCode;
    private dbConnector dbConnector;

    int deliveryPersonId;
                String name;
                boolean availabilityStatus;
                int distance;

    public DeliveryService(String zipCode) {
        this.orders = new ArrayList<>();
        this.deliveryPeople = new ArrayList<>();
        this.deliveryBatches = new ArrayList<>();
        this.zipCode = zipCode;
        this.dbConnector = new dbConnector();
        loadDeliveryPeople(); // Load delivery people on initialization
        loadDeliveryPeopleFullData();
    }

    public void placeDelivery(Order order) {
        createDelivery(order);
        DeliveryBatches batch = scheduleDelivery(order);
        if (batch.getOrders().size() >= 3) {
            processDelivery(batch);
        }
    }

    private void createDelivery(Order order) {
        Delivery delivery = new Delivery(zipCode, order);
        saveDeliveryToDatabase(delivery, order);
    }

    private void saveDeliveryToDatabase(Delivery delivery, Order order) {
        String sql = "INSERT INTO deliveries (order_id, start_time, status) VALUES (?, ?, ?)";
        try (Connection conn = dbConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, order.getOrderId());
            pstmt.setTimestamp(2, delivery.getStartTime());
            pstmt.setString(3, delivery.getStatus());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Failed to save delivery: " + e.getMessage());
        }
    }

    private DeliveryBatches scheduleDelivery(Order order) {
        DeliveryBatches batch = findOrCreateDeliveryBatch(order);
        batch.addOrder(order);
        return batch;
    }

    private DeliveryBatches findOrCreateDeliveryBatch(Order order) {

        // Debug statement to check delivery people list
    System.out.println("Total delivery people: " + deliveryPeople.size());
    deliveryPeople.forEach(person -> {
        System.out.println("Delivery Person: " + person.getName() + ", Available: " + person.isAvailable());
    });
        DeliveryBatches batch = findDeliveryBatchFromDB(zipCode);
        if (batch == null) {
            System.out.println("No batch found in the DB");
            batch = new DeliveryBatches(zipCode);
            DeliveryPerson deliveryPerson = findAvailableDeliveryPerson();
            //System.out.println("DELIVERY PEORSON !!!: " + deliveryPerson.getName());
            if (deliveryPerson != null) {
                System.out.println("entered if");
                System.out.println("DELIVERY PERSON NAME: " + deliveryPerson.getName());
                batch.setDeliveryPerson(deliveryPerson);
                deliveryBatches.add(batch);
                saveDeliveryBatchToDatabase(batch);
            } else {
                System.out.println("No available delivery person for zip code: " + zipCode);
            }
        }
        return batch;
    }

    private void saveDeliveryBatchToDatabase(DeliveryBatches batch) {
        String sql = "INSERT INTO delivery_batches (postal_code, delivery_person_id, start_time) VALUES (?, ?, ?)";
        try (Connection conn = dbConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, batch.getPostalCode());
            pstmt.setInt(2, batch.getDeliveryPerson().getDeliveryPersonId());
            pstmt.setTimestamp(3, batch.getStartTime());
            pstmt.executeUpdate();

            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                batch.setBatchId(generatedKeys.getInt(1));
            }

            saveOrdersInBatch(batch);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Failed to save delivery batch: " + e.getMessage());
        }
    }

    private void saveOrdersInBatch(DeliveryBatches batch) {
        String sql = "UPDATE orders SET batch_id = ? WHERE order_id = ?";
        try (Connection conn = dbConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (Order order : batch.getOrders()) {
                pstmt.setInt(1, batch.getBatchId());
                pstmt.setInt(2, order.getOrderId());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Failed to save orders in batch: " + e.getMessage());
        }
    }

    private DeliveryBatches findDeliveryBatchFromDB(String zipCode) {
        String sql = "SELECT * FROM delivery_batches WHERE postal_code = ? AND start_time >= NOW() - INTERVAL 3 MINUTE";
        try (Connection conn = dbConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, zipCode);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                DeliveryBatches batch = new DeliveryBatches(rs.getString("postal_code"));
                batch.setBatchId(rs.getInt("batch_id"));
                DeliveryPerson deliveryPerson = findDeliveryPersonById(rs.getInt("delivery_person_id"));

                batch.setDeliveryPerson(deliveryPerson);
                //batch.setOrders(findOrdersByBatchId(batch.getBatchId()));
                return batch;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Failed to find delivery batch: " + e.getMessage());
        }
        return null;
    }

    private DeliveryPerson findAvailableDeliveryPerson() {
        System.out.println("Searching for available delivery person for zip code: " + zipCode);
    
    return deliveryPeople.stream()
        .filter(person -> {
            boolean available = person.isAvailable();
            System.out.println(person.getDeliveryPersonId());
            DeliveryArea area = person.getDeliveryArea(person.getDeliveryPersonId(), dbConnector, person.getAreaId());
            System.out.println("AREA ID: " + area.getDistance());
            boolean zipCodeMatch = area != null && area.getZipCode().equals(zipCode);
           
            
            System.out.println("Delivery Person: " + person.getName() + ", Available: " + available + ", Zip Code Match: " + zipCodeMatch);
            
            return available && zipCodeMatch;
        })
        .findFirst()
        .orElse(null);
        
        /*return deliveryPeople.stream()
            .filter(person -> person.isAvailable() && person.getDeliveryArea(person.getDeliveryPersonId()).getZipCode().contains(zipCode))
            .findFirst()
            .orElse(null);*/
    }

    private void loadDeliveryPeople() {
        dbConnector connector = new dbConnector(); // Create an instance of dbConnector
        deliveryPeople = connector.fetchDeliveryPeople(); // Get the list of delivery people
        System.out.println("Total delivery people: " + deliveryPeople.size());
        deliveryPeople.forEach(person -> {
            System.out.println("Delivery Person: " + person.getName() + ", Available: " + person.isAvailable());
        });
    }

    // Method to load delivery people and their associated delivery areas from the database
    private void loadDeliveryPeopleFullData() {
        try (Connection connection = dbConnector.connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT dp.delivery_person_id, dp.name, dp.availability_status, da.postal_code, da.distance FROM delivery_people dp JOIN delivery_areas da ON dp.area_id = da.area_id")) {
            
            while (resultSet.next()) {
                int deliveryPersonId = resultSet.getInt("delivery_person_id");
                String name = resultSet.getString("name");
                boolean availabilityStatus = resultSet.getBoolean("availability_status");
                String zipCode = resultSet.getString("postal_code");
                int distance = resultSet.getInt("distance");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private DeliveryPerson findDeliveryPersonById(int deliveryPersonId) {
        String sql = "SELECT * FROM delivery_people WHERE delivery_person_id = ?";
        try (Connection conn = dbConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, deliveryPersonId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new DeliveryPerson(
                    deliveryPersonId,
                    rs.getString("name"),
                    rs.getBoolean("availability_status"),
                    findDeliveryAreaById(rs.getInt("area_id")).getAreaId()
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Failed to find delivery person: " + e.getMessage());
        }
        return null;
    }

    private DeliveryArea findDeliveryAreaById(int deliveryAreaId) {
        String sql = "SELECT * FROM delivery_areas WHERE delivery_area_id = ?";
        try (Connection conn = dbConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, deliveryAreaId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new DeliveryArea(deliveryAreaId, rs.getString("postal_code"), rs.getInt("distance"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Failed to find delivery area: " + e.getMessage());
        }
        return null;
    }

    private List<Order> findOrdersByBatchId(int batchId) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE batch_id = ?";
        try (Connection conn = dbConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, batchId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                orders.add(new Order(
                    rs.getInt("order_id"),
                    rs.getInt("customer_id"),
                    rs.getTimestamp("order_timestamp"),
                    rs.getString("status"),
                    rs.getDouble("price"),
                    rs.getDouble("price_discounted")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Failed to find orders by batch ID: " + e.getMessage());
        }
        return orders;
    }

    private void processDelivery(DeliveryBatches batch) {
        DeliveryPerson availableDeliveryPerson = findAvailableDeliveryPerson();
        if (availableDeliveryPerson != null) {
            batch.setDeliveryPerson(availableDeliveryPerson);
            availableDeliveryPerson.setUnavailable(30); 
            // Proceed with further delivery processing logic
        }
    }

    public void addDeliveryPerson(DeliveryPerson deliveryPerson) {
        deliveryPeople.add(deliveryPerson);
    }

    public void clearDeliveryPeople() {
        deliveryPeople.clear();
    }

    public int estimateDeliveryTime(int distance) {
        int baseTime = 10; 
        return baseTime + (distance * 5); 
    }
}
