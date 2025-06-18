package service;

import DAO.IAccountDAO;
import DTO.InputDTO;
import core.exceptions.AccountNotFoundException;
import core.mapper.Mapper;
import model.Account;




public class ImplementAccountService implements IAccountService{
    private final IAccountDAO accountDAO;

    public ImplementAccountService(IAccountDAO accountDAO){
        this.accountDAO = accountDAO;       //injection of dependency
    }

    public boolean createNewAccount(InputDTO dto){
        Account account = Mapper.mapToModelEntity(dto);
        accountDAO.saveOrUpdate(account);
        return true;
    }

        


}
