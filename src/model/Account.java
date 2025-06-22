package model;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * A model class representing a bank account.
 * Contains the account's IBAN and balance.
 */
public class Account {
    private String iban;
    private BigDecimal balance;
    private String firstname;
    private String lastname;
    private String email;

    public Account(){

    }

    public Account(String iban, BigDecimal balance, String firstname, String lastname, String email) {
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
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(iban, account.iban);
    }

    @Override
    public int hashCode() {
        return Objects.hash(iban);
    }
}
