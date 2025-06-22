package DTO;

import java.math.BigDecimal;

public class InputDTO {
    private String iban;
    private BigDecimal balance;
    private String firstname;
    private String lastname;
    private String email;

    public InputDTO() {

    }

    public InputDTO(String iban, BigDecimal balance, String firstname, String lastname, String email) {
        this.iban = iban;
        this.balance = balance;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
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

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "InputDTO{" +
                "iban='" + iban + '\'' +
                ", balance=" + balance +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}