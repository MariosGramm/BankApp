package core.mapper;

import DTO.InputDTO;
import DTO.OutputDTO;
import model.Account;

public class Mapper {

    private Mapper() {

    }

    public static Account mapToModelEntity(InputDTO dto){
        return new Account(dto.getIban(),dto.getBalance(), dto.getFirstname(), dto.getLastname());
    }

    public static OutputDTO mapToOutputDTO(Account account){
        return new OutputDTO(account.getIban(),account.getBalance(), account.getFirstname(), account.getLastname());
    }
}
