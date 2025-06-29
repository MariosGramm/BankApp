package validation;

import DTO.InputDTO;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Validator {

    private Validator() {
        // Utility class - private constructor to prevent instantiation
    }

    public static Map<String, String> validate(InputDTO dto) {
        Map<String, String> errors = new HashMap<>();

        validateIban(dto.getIban(), errors);
        validateBalance(dto.getBalance(), errors);
        validateFirstname(dto.getFirstname(), errors);
        validateLastname(dto.getLastname(), errors);
        validateEmail(dto.getEmail(),errors);
        validateUsername(dto.getUsername(),errors);
        validatePassword(dto.getPassword(), errors);

        return errors;
    }

    private static void validateIban(String iban, Map<String, String> errors) {
        if (iban == null || iban.isEmpty()) {
            errors.put("iban", "Iban is required");
        } else if (!iban.trim().matches("^GR\\d{5}$")) {
            errors.put("iban", "Iban must start with 'GR' followed by 5 digits");
        }
    }

    private static void validateBalance(BigDecimal balance, Map<String, String> errors) {
        if (balance == null) {
            errors.put("balance", "Balance is required");
        } else if (balance.compareTo(BigDecimal.ZERO) < 0) {
            errors.put("balance", "Balance cannot be a negative amount");
        }
    }

    private static void validateFirstname(String firstname, Map<String, String> errors) {
        if (firstname == null) {
            errors.put("firstname", "Firstname is required");
        } else if (!firstname.matches("^[A-Za-z]{2,10}$")) {
            errors.put("firstname", "Firstname must be 2–10 alphabetic characters");
        }
    }

    private static void validateLastname(String lastname, Map<String, String> errors) {
        if (lastname == null) {
            errors.put("lastname", "Lastname is required");
        } else if (!lastname.matches("^[A-Za-z]{4,15}$")) {
            errors.put("lastname", "Lastname must be 4–15 alphabetic characters");
        }
    }

    private static void validateEmail(String email,Map<String,String> errors) {
        if (email == null) {
            errors.put("email","Email is required");
        }else if (!email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")){
            errors.put("email","Invalid email format");
        }
    }

    private static void validateUsername(String username,Map<String,String> errors) {
        if (username == null) {
            errors.put("username","Username is required");
        }else if (!username.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{1,8}$\n")){
            errors.put("username", "Invalid username");
        }
    }

    private static void validatePassword(String password,Map<String,String> errors) {
        if (password == null) {
            errors.put("password","Password is required");
        }else if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-\\[\\]{};':\"\\\\|,.<>/?]).{4,12}$")) {
            errors.put("password", "Invalid username");
        }
))
    }
}
