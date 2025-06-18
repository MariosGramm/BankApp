package DTO;

import java.math.BigDecimal;

public class OutputDTO {
    private String iban;
    private BigDecimal balance;
    private String firstname;
    private String lastName;

    public OutputDTO() {

    }

    public OutputDTO(String iban, BigDecimal balance, String firstname, String lastName) {
        this.iban = iban;
        this.balance = balance;
        this.firstname = firstname;
        this.lastName = lastName;
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

    @Override
    public String toString() {
        return "OutputDTO{" +
                "iban='" + iban + '\'' +
                ", balance=" + balance +
                ", firstName='" + firstname + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
