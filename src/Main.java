import DAO.IAccountDAO;
import DAO.ImplementAccountDAO;
import DTO.InputDTO;
import core.exceptions.AccountNotFoundException;
import core.exceptions.DuplicateIbanException;
import core.exceptions.DuplicateUsernameException;
import core.exceptions.NegativeAmountException;
import model.Account;
import service.IAccountService;
import service.ImplementAccountService;
import validation.Validator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static final IAccountDAO dao = new ImplementAccountDAO();
    private static final IAccountService service = new ImplementAccountService(dao);
    private static final Scanner scanner = new Scanner(System.in);
    private static String choice;
    private static String iban;
    private static String firstname;
    private static String lastname;
    private static String email;
    private static String username;
    private static String password;

    public static void main(String[] args) {

        while (true) {
            printMenu();
            choice = scanner.nextLine();

            try{
                switch (choice){
                    case "1":
                        createNewAccount();
                        break;
                    case "2":

                }
            }

        }
    }

    public static void printMenu(){
        System.out.println("\n=== Welcome to the banking app ===\n");
        System.out.println("1.Create new account");
        System.out.println("2.Update existing account");
        System.out.println("3.Delete an account");
        System.out.println("4.Deposit");
        System.out.println("5.Withdraw");
        System.out.println("6.Search account by IBAN");
        System.out.println("7.Search account(or accounts) by Name");
        System.out.println("8.Exit");
        System.out.println("Please insert an option: ");
    }

    public static void createNewAccount() throws DuplicateIbanException, DuplicateUsernameException , NegativeAmountException {
        System.out.println("Please insert your iban");
        iban = scanner.nextLine();
        System.out.println("Please insert your first name");
        firstname = scanner.nextLine();
        System.out.println("Please insert your last name");
        lastname = scanner.nextLine();
        System.out.println("Please insert your balance");
        BigDecimal balance = new BigDecimal(scanner.nextLine());
        System.out.println("Please insert your email");
        email = scanner.nextLine();
        System.out.println("Please insert your username");
        System.out.println("Username must be 1–8 characters long, include at least one lowercase letter, one uppercase letter, and one digit. Only letters and numbers allowed.");
        username = scanner.nextLine();
        System.out.println("Please insert your password");
        System.out.println("Password must be 4-12 characters long, include at least one lowercase letter, one uppercase letter, one digit and one special character.");
        password = scanner.nextLine();
        InputDTO dto = new InputDTO(iban,balance,firstname,lastname,email,username,password);

        Map<String,String> errors;
        errors = Validator.validate(dto);

        if (!errors.isEmpty()){
            errors.forEach((k,v)-> System.out.println(v));
            System.out.printf("%s Account was not created.Please try again", LocalDateTime.now());
            return ;
        }

        service.createNewAccount(dto);
        System.out.println("Account was successfully created");
    }

    public static void updateAccount() throws AccountNotFoundException {
        System.out.println("Enter your username");
        username = scanner.nextLine();
        System.out.println("Enter your password");
        password = scanner.nextLine();

        Account account;
        try {
            account = service.authenticate(username,password);
        } catch (AccountNotFoundException e) {
            throw new AccountNotFoundException("Invalid credentials");
        }

        System.out.println("Which field would you like to update? (email, username, password, all):");
        String choice = scanner.nextLine().toLowerCase();

        switch (choice){
            case "email":
                System.out.println("Please enter new email");
                account.setEmail(scanner.nextLine());
                break;
            case "username":
                System.out.println("Please enter new username");
                account.setUsername(scanner.nextLine());
                break;
            case "password":
                String newPassword;
                String confirmPassword;

                while (true) {
                    System.out.println("Please enter new password");
                    newPassword = scanner.nextLine();
                    System.out.println("Please confirm new password");
                    confirmPassword = scanner.nextLine();

                    if (!newPassword.equals(confirmPassword)){
                        System.out.println("Passwords don't match.Please try again");
                    }else{
                        break;
                    }
                }

                account.setPassword(newPassword);
                break;
            case "all":
                System.out.println("Please enter new email");
                account.setEmail(scanner.nextLine());
                System.out.println("Please enter new username");
                account.setUsername(scanner.nextLine());

                String password1;
                String password2;

                while (true) {
                    System.out.println("Please enter new password");
                    password1 = scanner.nextLine();
                    System.out.println("Please confirm new password");
                    password2 = scanner.nextLine();

                    if (!password1.equals(password2)){
                        System.out.println("Passwords don't match.Please try again");
                    }else{
                        break;
                    }
                }

                account.setPassword(password1);
                break;
            default:
                System.out.println("Invalid choice");
                return;
        }

        InputDTO dto = new InputDTO(
                account.getIban(),
                account.getBalance(),
                account.getFirstname(),
                account.getLastname(),
                account.getEmail(),
                account.getEmail(),
                account.getPassword()
        );

        Map<String, String> errors = Validator.validate(dto);
        if (!errors.isEmpty()) {
            errors.forEach((k, v) -> System.out.println(v));
            System.out.println("Update failed. Please correct the above errors and try again.");
            return;
        }

        service.updateAccount(dto);
        System.out.println("Account updated successfully!");
    }
}
