package pizzaSoftware;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

//this is ok 
public class Customer {
    private int customerId;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private String gender;
    private String username;
    private LocalDate birthdate;
    private String passwordHash;
    private String zipCode;
    private int numberOfPizzasAfterDiscount;

    public Customer (){

    }    
    //* Customer constructor with all the necessar info */
    public Customer (String email, String username, String name, String gender, LocalDate birthdate, String phoneNumber, String address, String zipCode){
        this.name = name;
        this.gender = gender;
        this.birthdate = birthdate;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.email = email;
        this.username = username;
        this.zipCode = zipCode;
        this.numberOfPizzasAfterDiscount = 0;
    }

   //this is just for test
    public Customer (String username, String passward){
        this.username = username;
        this.passwordHash = passward;
    }

    // Method to retrieve customerId from the database based on username
    public void retrieveCustomerId(dbConnector dbConnection) {
        String query = "SELECT customer_id FROM Customers WHERE username = ?";
        
        try (Connection connection = dbConnection.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {
             
            statement.setString(1, username); // Set the username parameter
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    this.customerId = resultSet.getInt("customer_id"); // Retrieve the customer_id
                } else {
                    System.out.println("No customer found with the given username.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

     // Method to retrieve customer details from the database
     public void retrieveCustomerDetails(dbConnector dbConnection) {
        String query = "SELECT * FROM Customers WHERE customer_id = ?";

        try (Connection connection = dbConnection.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {
             
            statement.setInt(1, customerId); // Set the customerId parameter
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    this.username = resultSet.getString("username");
                    this.name = resultSet.getString("name");
                    this.email = resultSet.getString("email");
                    this.phoneNumber = resultSet.getString("phone_number");
                    this.address = resultSet.getString("address");
                    this.zipCode = resultSet.getString("zip_code");
                    this.gender = resultSet.getString("gender");
                    this.birthdate = resultSet.getObject("birthdate", LocalDate.class);
                } else {
                    System.out.println("No customer found with the given ID.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to update customer details in the database
    public void updateCustomerDetails(dbConnector dbConnection) {
        String query = "UPDATE Customers SET name = ?, email = ?, phone_number = ?, address = ?, zip_code = ?, gender = ?, birthdate = ? WHERE customer_id = ?";

        try (Connection connection = dbConnection.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {
             
            statement.setString(1, name); // Assuming firstName is the same as name
            //statement.setString(2, name); // You might want to change this to a separate lastName if you have it
            statement.setString(2, email);
            statement.setString(3, phoneNumber);
            statement.setString(4, address);
            statement.setString(5, zipCode);
            statement.setString(6, gender);
            statement.setObject(7, birthdate);
            statement.setInt(8, customerId); // Set the customerId parameter

            statement.executeUpdate(); // Execute the update

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void retrieveCustomerByUsername(dbConnector dbConnection, String username) {
        String query = "SELECT * FROM Customers WHERE username = ?";
        
        try (Connection connection = dbConnection.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {
             
            statement.setString(1, username); // Set the username parameter
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // Retrieve all necessary fields
                    this.customerId = resultSet.getInt("customer_id");
                    this.username = resultSet.getString("username");
                    this.name = resultSet.getString("name");
                    this.email = resultSet.getString("email");
                    this.phoneNumber = resultSet.getString("phone_number");
                    this.address = resultSet.getString("address");
                    this.zipCode = resultSet.getString("zip_code");
                    this.gender = resultSet.getString("gender");
                    this.birthdate = resultSet.getObject("birthdate", LocalDate.class);
                } else {
                    System.out.println("No customer found with the given username.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // In your controller where you set the customer data
    public void setCustomerData(Customer customer) {
    SessionManager session = SessionManager.getInstance();
    session.setLoggedInCustomer(customer); // Assuming this method exists.

    // If you need to set specific fields.
    session.getLoggedInCustomer().setName(customer.getName());
    session.getLoggedInCustomer().setGender(customer.getGender());
    session.getLoggedInCustomer().setAddress(customer.getAddress());
    session.getLoggedInCustomer().setEmail(customer.getEmail());
    session.getLoggedInCustomer().setBirthdate(customer.getBirthdate());
    session.getLoggedInCustomer().setPhoneNumber(customer.getPhoneNumber());
    session.getLoggedInCustomer().setUsername(customer.getUsername());
    session.getLoggedInCustomer().setZipCode(customer.getZipCode());
}

    //all only getters and seeters for customer's attributes

    public void setPasswordHash (String passwordHash){
        this.passwordHash = passwordHash;
    }

    public String getPasswordHash(){
        return passwordHash;
    }

    public String getGender(){
        return gender;
    }

    public void setGender(String gender, dbConnector dbConnection) {
        this.gender = gender;
        updateCustomerDetails(dbConnection);
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }
    public void setUsername(String username, dbConnector dbConnection) {
        this.username = username;
        updateCustomerDetails(dbConnection);
    }

    public LocalDate getBirthdate(){
        return birthdate;
    }

    public void setBirthdate (LocalDate birthdate){
        this.birthdate = birthdate;
    }
    public void setBirthdate(LocalDate birthdate, dbConnector dbConnection) {
        this.birthdate = birthdate;
        updateCustomerDetails(dbConnection);
    }

    public int getPizzaOrderCount (){
        return numberOfPizzasAfterDiscount;
    }

    public void resetPizzaOrderCount(){
        numberOfPizzasAfterDiscount = 0;
    }

    public void setPizzaOrderCount(int numberOfPizzasAfterDiscount){
        this.numberOfPizzasAfterDiscount = numberOfPizzasAfterDiscount;
    }


    public int getCustomerId(){
        return customerId;
    }

    public void setCustomerId(int customerId){
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setName(String name, dbConnector dbConnection) {
        this.name = name;
        updateCustomerDetails(dbConnection);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setEmail(String email, dbConnector dbConnection) {
        this.email = email;
        updateCustomerDetails(dbConnection);
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber, dbConnector dbConnection) {
        this.phoneNumber = phoneNumber;
        updateCustomerDetails(dbConnection);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    } 

    public void setAddress(String address, dbConnector dbConnection) {
        this.address = address;
        updateCustomerDetails(dbConnection);
    }

    public String getZipCode(){
        return zipCode;
    }

    public void setZipCode(String zipCode, dbConnector dbConnection) {
        this.zipCode = zipCode;
        updateCustomerDetails(dbConnection);
    }

    public void setGender(String gender) {
        this.gender = gender;
        // No database update here
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

}
