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

    public Account(){

    }

    public Account(String iban, BigDecimal balance, String firstName, String lastname) {
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
