package service;

import DAO.IAccountDAO;
import DAO.ImplementAccountDAO;
import DTO.InputDTO;
import DTO.OutputDTO;
import core.exceptions.*;
import core.mapper.Mapper;
import model.Account;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class ImplementAccountService implements IAccountService{
    private final IAccountDAO accountDAO;

    public ImplementAccountService(IAccountDAO accountDAO){
        this.accountDAO = accountDAO;       //injection of dependency
    }

    @Override
    public Account authenticate(String username, String password) throws AccountNotFoundException {
        return accountDAO.getByUsername(username)
                .filter(account -> account.getPassword().equals(password))
                .orElseThrow(()->new AccountNotFoundException("Invalid username or password"));
    }

    public boolean createNewAccount(InputDTO dto) throws DuplicateIbanException, DuplicateUsernameException, NegativeAmountException {
        try {
            Account account = Mapper.mapToModelEntity(dto);

            if (accountDAO.getByIban(account.getIban()).isPresent()){
                throw new DuplicateIbanException("The provided IBAN" + account.getIban()+ "belongs to an existing account");
            }

            if (accountDAO.getByUsername(account.getUsername()).isPresent()){
                throw new DuplicateUsernameException("The provided username" + account.getUsername() + "belongs to an existing account");
            }

            if (dto.getBalance().compareTo(BigDecimal.ZERO) < 0){
                throw new NegativeAmountException("Amount cannot be negative");
            }

            accountDAO.saveOrUpdate(account);
            return true;
        }catch (DuplicateIbanException e){
            System.err.printf("%s.IBAN is unique for every account \n %s",LocalDateTime.now(),e);
            throw e;
        }catch (DuplicateUsernameException e){
            System.err.printf("%s.Username is unique for every account \n %s",LocalDateTime.now(),e);
            throw e;
        }catch (NegativeAmountException e){
            System.err.printf("%s.The amount provided (%f) is negative. \n %s" , LocalDateTime.now(),dto.getBalance(),e);
            throw e;
        }
    }

    @Override
    public boolean updateAccount(InputDTO dto) throws AccountNotFoundException {
        try {
            Account account = accountDAO.getByIban(dto.getIban())
                    .orElseThrow(() -> new AccountNotFoundException("Account with iban" + dto.getIban() + "not found"));

            account.setEmail(dto.getEmail());
            account.setUsername(dto.getUsername());
            return true;
        }catch (AccountNotFoundException e){
            System.err.printf("%s.The account with iban %s was not found. \n %s", LocalDateTime.now(),dto.getIban(),e);
            throw e;
        }
    }

    @Override
    public boolean removeAccount(String iban) throws AccountNotFoundException {
        try {
            Account account = accountDAO.getByIban(iban)
                    .orElseThrow(() -> new AccountNotFoundException("Account with iban" + iban + "not found"));

            accountDAO.remove(iban);
            return true;

        } catch  (AccountNotFoundException e){
            System.err.printf("%s.The account with iban %s was not found. \n %s", LocalDateTime.now(),iban,e);
            throw e;
        }
    }

    @Override
    public void deposit(String iban, BigDecimal amount) throws NegativeAmountException, AccountNotFoundException {
        try{
            Account account = accountDAO.getByIban(iban)
                    .orElseThrow(() -> new AccountNotFoundException("Account with iban" + iban + "not found"));

            if (amount.compareTo(BigDecimal.ZERO) < 0){
                throw new NegativeAmountException("Amount cannot be negative");
            }

            account.setBalance(account.getBalance().add(amount));
            accountDAO.saveOrUpdate(account);
        }catch (AccountNotFoundException e) {
            System.err.printf("%s.The account with iban %s was not found. \n %s", LocalDateTime.now(), iban, e);
            throw e;
        }catch (NegativeAmountException e){
            System.err.printf("%s.The amount provided (%f) is negative. \n %s" , LocalDateTime.now(),amount,e);
            throw e;
        }

    }

    @Override
    public void withdraw(String iban, BigDecimal amount) throws NegativeAmountException, AccountNotFoundException, InsufficientBalanceException {
        try{
            Account account = accountDAO.getByIban(iban)
                    .orElseThrow(() -> new AccountNotFoundException("Account with iban" + iban + "not found"));

            if (amount.compareTo(BigDecimal.ZERO) < 0){
                throw new NegativeAmountException("Amount cannot be negative");
            }

            if (amount.compareTo(account.getBalance()) > 0){
                throw new InsufficientBalanceException("Your balance is not sufficient for this withdrawal");
            }

            account.setBalance(account.getBalance().subtract(amount));
            accountDAO.saveOrUpdate(account);
        }catch (AccountNotFoundException e) {
            System.err.printf("%s.The account with iban %s was not found. \n %s", LocalDateTime.now(), iban, e);
            throw e;
        }catch (NegativeAmountException e){
            System.err.printf("%s.The amount provided (%f) is negative. \n %s" , LocalDateTime.now(),amount,e);
            throw e;
        }catch (InsufficientBalanceException e){
            System.err.printf("%s.The amount provided (%f) is greater than the balance. \n %s",LocalDateTime.now(),amount,e);
        }
    }

    @Override
    public OutputDTO getAccountByIban(String iban) throws AccountNotFoundException {
        try {
            Account account = accountDAO.getByIban(iban)
                    .orElseThrow(() -> new AccountNotFoundException("Account with iban" + iban + "not found"));

            return new OutputDTO(account.getIban(), account.getBalance(), account.getFirstname(), account.getLastname(),account.getEmail(),account.getUsername());
        }catch (AccountNotFoundException e){
            System.err.printf("%s.The account with iban %s was not found. \n %s", LocalDateTime.now(), iban, e);
            throw e;
        }
    }

    @Override
    public List<OutputDTO> getAccountsByName(String firstname, String lastname)throws AccountNotFoundException {
            List<Account> accounts = accountDAO.getByName(firstname,lastname);

        try {
            if (accounts.isEmpty()) {
                throw new AccountNotFoundException("The provided name: (" + lastname + firstname + ") does not own any accounts");
            }

            return new ArrayList<>(accounts.stream().map((account) -> new OutputDTO(account.getIban(), account.getBalance(), account.getFirstname(), account.getLastname(), account.getEmail(), account.getUsername())).collect(Collectors.toList()));
        }catch (AccountNotFoundException e){
            System.err.printf("%s.The account with the name [%s + %s] was not found. \n %s", LocalDateTime.now(), lastname,firstname, e);
            throw e;
        }
    }
}
