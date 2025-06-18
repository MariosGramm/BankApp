package DAO;

import model.Account;

import java.util.List;
import java.util.Optional;

public interface IAccountDAO {

    void saveOrUpdate(Account account);
    void remove(String iban);
    Optional<Account> getByIban(String iban);
    List<Account> getAccounts();
    List<Account> getByName(String firstname,String lastname);
}
