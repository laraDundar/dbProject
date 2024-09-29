import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDate;

public class Customer {
    private int customerId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String address;
    private String gender;
    private String username;
    private LocalDate birthdate;
    private String passwordHash;

    private int pizzaOrderCount;//number of ordered pizzas
    
    public Customer (String email, String username, String firstName, String lastName, String gender, LocalDate birthdate, String phoneNumber, String address){
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.birthdate = birthdate;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.pizzaOrderCount = 0;
        this.email = email;
        this.username = username;
    }

    /**
     * check if today it is the customer's birthday
     * @return boolean 
     */
    public boolean isBirthday(){
        LocalDate today = LocalDate.now();
        return today.getDayOfMonth() == birthdate.getDayOfMonth() && today.getMonth() == birthdate.getMonth();
    }

    public void setPasswordHash (String passwordHash){
        this.passwordHash = passwordHash;
    }

    public String getPasswordHash(){
        return passwordHash;
    }

    public String getUsername(){
        return username;
    }


    public int getCustomerId(){
        return customerId;
    }

    public void setCustomerId(int customerId){
        this.customerId = customerId;
    }

    public String getFirstName(){
        return firstName;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName; 
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
