package DTO;

import java.math.BigDecimal;

public class OutputDTO {
    private String iban;
    private BigDecimal balance;
    private String fullName;

    public OutputDTO(){

    }

    public OutputDTO(String iban, BigDecimal balance, String fullName) {
        this.iban = iban;
        this.balance = balance;
        this.fullName = fullName;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public String toString() {
        return "OutputDTO{" +
                "iban='" + iban + '\'' +
                ", balance=" + balance +
                ", fullName='" + fullName + '\'' +
                '}';
    }
}
