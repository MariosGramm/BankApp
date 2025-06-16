package service;

import DTO.InputDTO;
import exceptions.AccountNotFoundException;
import exceptions.InsufficientBalanceException;
import exceptions.NegativeAmountException;
import model.Account;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface IAccountService {
    String createNewAccount(InputDTO dto);
    String updateAccount(InputDTO dto) throws AccountNotFoundException;
    String removeAccount(InputDTO dto) throws NegativeAmountException;
    void deposit(String iban, BigDecimal amount) throws NegativeAmountException, AccountNotFoundException;
    void withdraw(String iban , BigDecimal amount) throws NegativeAmountException, AccountNotFoundException , InsufficientBalanceException;
    BigDecimal getBalance(String iban) throws AccountNotFoundException;
    List<Account> getAccounts();

}
