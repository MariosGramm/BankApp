import DAO.IAccountDAO;
import DAO.ImplementAccountDAO;
import DTO.InputDTO;
import DTO.OutputDTO;
import core.exceptions.*;
import model.Account;
import service.IAccountService;
import service.ImplementAccountService;
import validation.Validator;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static final IAccountDAO dao = new ImplementAccountDAO();
    private static final IAccountService service = new ImplementAccountService(dao);
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        while (true) {
            printMenu();
            String choice = scanner.nextLine();

            switch (choice){
                case "1":
                    createNewAccount();
                    break;
                case "2":
                    updateAccount();
                    break;
                case "3":
                    deleteAccount();
                    break;
                case "4":
                    deposit();
                    break;
                case "5":
                    withdraw();
                    break;
                case "6":
                    OutputDTO foundAccount = ibanSearch();
                    System.out.println(foundAccount);
                    break;
                case "7":
                    List<OutputDTO> accounts = nameSearch();
                    accounts.forEach(System.out::println);
                    break;
                case "8":
                    appExit();
                    break;
                default:
                    System.out.println("Invalid choice , please try again");
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

    public static void createNewAccount() {
        while (true) {

            System.out.println("Please insert your iban");
            System.out.println("Iban must start with 'GR' followed by 5 digits");
            String iban = scanner.nextLine().trim();

            System.out.println("Please insert your first name");
            String firstname = scanner.nextLine().trim();

            System.out.println("Please insert your last name");
            String lastname = scanner.nextLine().trim();

            BigDecimal balance;
            while (true) {
                try {
                    System.out.println("Please insert your balance:");
                    balance = new BigDecimal(scanner.nextLine().trim());
                    break;
                } catch (NumberFormatException | NegativeAmountException e) {
                    System.out.println("Invalid number format. Please try again.");
                    if (askToReturnToMenu()) return;
                }
            }

            System.out.println("Please insert your email");
            String email = scanner.nextLine().trim();

            System.out.println("Please insert your username");
            System.out.println("Username must be 3–15 characters long, include at least one lowercase letter, one uppercase letter, and one digit. Only letters and numbers allowed.");
            String username = scanner.nextLine().trim();

            System.out.println("Please insert your password");
            System.out.println("Password must be 4-15 characters long, include at least one lowercase letter, one uppercase letter, one digit and one special character.");
            String password = scanner.nextLine().trim();


            InputDTO dto = new InputDTO(iban, balance, firstname, lastname, email, username, password);

            Map<String, String> errors = Validator.validate(dto);

            if (!errors.isEmpty()) {
                errors.forEach((k, v) -> System.out.println(v));
                System.out.println("Account was not created. Please try again");
                if (askToReturnToMenu()) return;
            }

            try {
                service.createNewAccount(dto);
                System.out.println("Account was successfully created");
            } catch (DuplicateIbanException e) {
                System.out.println("Error: The IBAN you entered is already in use. Please try again with a different IBAN.");
            } catch (DuplicateUsernameException e) {
                System.out.println("Error: The username you entered is already taken. Please try again with a different username.");
            } catch (NegativeAmountException e) {
                System.out.println("Error: The balance cannot be negative. Please enter a positive amount.");
            }
            if (askToReturnToMenu()) return;

        }
    }


    public static void updateAccount() throws AccountNotFoundException {

        Account account = accountLogin();
        System.out.printf("Welcome, %s %s",account.getFirstname(),account.getLastname());

        System.out.println("Which field would you like to update? (email, username, password, all):");
        String choice = scanner.nextLine().toLowerCase();

        switch (choice){
            case "email":
                System.out.println("Please enter new email");
                account.setEmail(scanner.nextLine().trim());
                break;
            case "username":
                System.out.println("Please enter new username");
                account.setUsername(scanner.nextLine().trim());
                break;
            case "password":
                String newPassword;
                String confirmPassword;

                while (true) {
                    System.out.println("Please enter new password");
                    newPassword = scanner.nextLine().trim();
                    System.out.println("Please confirm new password");
                    confirmPassword = scanner.nextLine().trim();

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
                account.setEmail(scanner.nextLine().trim());
                System.out.println("Please enter new username");
                account.setUsername(scanner.nextLine().trim());

                String password1;
                String password2;

                while (true) {
                    System.out.println("Please enter new password");
                    password1 = scanner.nextLine().trim();
                    System.out.println("Please confirm new password");
                    password2 = scanner.nextLine().trim();

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
                account.getUsername(),
                account.getEmail(),
                account.getPassword()
        );

        Map<String, String> errors = Validator.validate(dto);
        if (!errors.isEmpty()) {
            errors.forEach((k, v) -> System.out.println(v));
            System.out.println("Update failed. Please correct the above errors and try again.");
            return;
        }

        try {
            service.updateAccount(dto);
            System.out.println("Account updated successfully!");
        }catch (AccountNotFoundException e){
            System.out.println("Account was not found");
        }
    }

    public static void deleteAccount() throws AccountNotFoundException{
        Account account = accountLogin();
        System.out.printf("Welcome, %s %s",account.getFirstname(),account.getLastname());

        while(true) {
            System.out.println("Are you sure you want to permanently delete your account? (Y for yes , N for no");
            String choice = scanner.nextLine();
            if (choice.equals("Y") || choice.equals("y")){
                System.out.println("Please re-enter your password");
                String password = scanner.nextLine();
                if (password.equals(account.getPassword())){
                    service.removeAccount(account.getIban());
                    System.out.println("Successfully deleted account");
                    break;
                }else {
                    System.out.println("Invalid password , please try again");
                }
            }else if (choice.equals("N") || choice.equals("n")){
                break;
            }else {
                System.out.println("Invalid choice. Please try again");
            }
        }
    }

    public static void deposit() throws AccountNotFoundException{
        Account account = accountLogin();
        System.out.printf("Welcome, %s %s",account.getFirstname(),account.getLastname());

        while (true) {
            System.out.println("Please insert the amount you want to deposit");
            try {
                BigDecimal amount = new BigDecimal(scanner.nextLine());
                service.deposit(account.getIban(), amount);
                break;
            } catch (NegativeAmountException e) {
                System.out.println("The amount you entered is negative.Please try again");
            } catch (NumberFormatException e){
                System.out.println("The amount you entered is not valid.Please try again");
            }
        }
    }

    public static void withdraw() throws AccountNotFoundException{
        Account account = accountLogin();
        System.out.printf("Welcome, %s %s",account.getFirstname(),account.getLastname());

        while (true) {
            System.out.println("Please insert the amount you want to withdraw");
            try {
                BigDecimal amount = new BigDecimal(scanner.nextLine());
                service.withdraw(account.getIban(), amount);
                break;
            }catch (NegativeAmountException e) {
                System.out.println("The amount you entered is negative.Please try again");
            }catch (NumberFormatException e){
                System.out.println("The amount you entered is not valid.Please try again");
            }catch (InsufficientBalanceException e) {
                System.out.println("Your balance is not sufficient for this withdrawal");
            }
        }
    }

    public static OutputDTO ibanSearch() throws AccountNotFoundException{
        System.out.println("Please insert Iban");
        String iban = scanner.nextLine();

        return service.getAccountByIban(iban);
    }

    public static List<OutputDTO> nameSearch() throws AccountNotFoundException{
        System.out.println("Please insert firstname");
        String firstname = scanner.nextLine();
        System.out.println("Please insert lastname");
        String lastname = scanner.nextLine();

        return service.getAccountsByName(firstname,lastname);
    }

    public static void appExit() {
        scanner.close();
        System.out.println("Exiting app...");
    }



    private static Account accountLogin() throws AccountNotFoundException{
        System.out.println("Enter your username");
        String username = scanner.nextLine();
        System.out.println("Enter your password");
        String password = scanner.nextLine();

        return service.authenticate(username,password);
    }

    private static boolean askToReturnToMenu() {
        while (true) {
            System.out.println("\nDo you want to try again or return to main menu?");
            System.out.println("1. Try again");
            System.out.println("2. Return to menu");
            String input = scanner.nextLine().trim();
            if (input.equals("1")) return false;
            else if (input.equals("2")) return true;
            else System.out.println("Invalid choice. Please enter 1 or 2.");
        }
    }
}
