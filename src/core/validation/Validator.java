package core.validation;

import DTO.InputDTO;

import java.util.HashMap;
import java.util.Map;

public class Validator {

    private Validator(){    //utility class

    }

    public static Map<String,String> validate(InputDTO dto){
        Map<String,String> errors = new HashMap<>();

        if (dto.getIban() == null){
            errors.put("iban","Iban is required");
        }else if (dto.getIban().trim().length() != 5){
            errors.put("iban","Iban's length must be 5 characters");

    }
}
