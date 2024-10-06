package pizzaSoftware;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDate;

//this is ok 
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
    private String zipCode;
    private int numberOfPizzasAfterDiscount;

    public Customer (){

    }    
    //* Customer constructor with all the necessar info */
    public Customer (String email, String username, String firstName, String lastName, String gender, LocalDate birthdate, String phoneNumber, String address, String zipCode){
        this.firstName = firstName;
        this.lastName = lastName;
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


    //all only getters and seeters for customer's attributes

    public void setPasswordHash (String passwordHash){
        this.passwordHash = passwordHash;
    }

    public String getPasswordHash(){
        return passwordHash;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public LocalDate getBirthdate(){
        return birthdate;
    }

    public void setBirthdate (LocalDate birthdate){
        this.birthdate = birthdate;
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
