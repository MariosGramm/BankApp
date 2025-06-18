package DAO;

import model.Account;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ImplementAccountDAO implements IAccountDAO {
    private final List<Account> accounts = new ArrayList<>();

    @Override
    public void saveOrUpdate(Account account) {
        if (accounts.contains(account)) {
            accounts.set(accounts.indexOf(account),account);
        }
        accounts.add(account);
    }

    @Override
    public void remove(String iban) {
        accounts.removeIf(account -> account.getIban().equals(iban));
    }

    @Override
    public Optional<Account> getByIban(String iban) {
        return accounts.stream()
                .filter(account -> account.getIban().equals(iban))
                .findFirst();
    }

    @Override
    public List<Account> getAccounts() {
        return new ArrayList<>(accounts);
    }

    @Override
    public List<Account> getByName(String firstname,String lastname) {
        return accounts.stream()
                .filter(account -> (account.getFirstname().equals(firstname) && account.getLastname().equals(lastname)))
                .collect(Collectors.toList());
    }
}


