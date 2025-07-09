package DTO;

import java.math.BigDecimal;

public class OutputDTO {
    private String iban;
    private BigDecimal balance;
    private String firstname;
    private String lastName;
    private String email;
    private String username;


    public OutputDTO() {

    }

    public OutputDTO(String iban, BigDecimal balance, String firstname, String lastName, String email, String username) {
        this.iban = iban;
        this.balance = balance;
        this.firstname = firstname;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }



    @Override
    public String toString() {
        return "Account found:" + "\n" +
                "iban: " + iban + "\n"+
                "balance: " + balance + "\n" +
                "firstname: " + firstname + "\n" +
                "lastName: " + lastName + "\n" +
                "email: " + email + "\n" +
                "username: " + username ;
    }
}
