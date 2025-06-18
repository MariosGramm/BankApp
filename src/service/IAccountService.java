package service;

import DTO.InputDTO;
import DTO.OutputDTO;
import core.exceptions.AccountNotFoundException;
import core.exceptions.DuplicateIbanException;
import core.exceptions.InsufficientBalanceException;
import core.exceptions.NegativeAmountException;
import model.Account;

import java.math.BigDecimal;
import java.util.List;

public interface IAccountService {
    boolean createNewAccount(InputDTO dto) throws DuplicateIbanException;
    boolean updateAccount(InputDTO dto) throws AccountNotFoundException;
    boolean removeAccount(String iban) throws AccountNotFoundException;
    void deposit(String iban, BigDecimal amount) throws NegativeAmountException, AccountNotFoundException;
    void withdraw(String iban , BigDecimal amount) throws NegativeAmountException, AccountNotFoundException , InsufficientBalanceException;
    OutputDTO getAccountByIban(String iban) throws AccountNotFoundException;
    List<OutputDTO> getAccountsByName(String firstName,String lastName) throws AccountNotFoundException;
}
