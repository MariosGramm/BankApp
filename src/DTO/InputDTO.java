package DTO;

import java.math.BigDecimal;

public class InputDTO {
    private String iban;
    private BigDecimal balance;
    private String firstname;
    private String lastname;

    public InputDTO() {

    }

    public InputDTO(String iban, BigDecimal balance, String firstName, String lastname) {
        this.iban = iban;
        this.balance = balance;
        this.firstname = firstName;
        this.lastname = lastname;
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

    @Override
    public String toString() {
        return "InputDTO{" +
                "iban='" + iban + '\'' +
                ", balance=" + balance +
                ", firstName='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }
}