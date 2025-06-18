package service;

import DTO.InputDTO;
import core.exceptions.AccountNotFoundException;
import core.exceptions.InsufficientBalanceException;
import core.exceptions.NegativeAmountException;

import java.math.BigDecimal;

public interface IAccountService {
    boolean createNewAccount(InputDTO dto);
    boolean updateAccount(String iban) throws AccountNotFoundException;
    boolean removeAccount(String iban) throws NegativeAmountException;
    void deposit(String iban, BigDecimal amount) throws NegativeAmountException, AccountNotFoundException;
    void withdraw(String iban , BigDecimal amount) throws NegativeAmountException, AccountNotFoundException , InsufficientBalanceException;
    BigDecimal getBalance(String iban) throws AccountNotFoundException;
}
