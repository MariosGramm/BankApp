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
import java.util.*;

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
                    if (foundAccount != null) {
                        System.out.println(foundAccount);
                    }
                    break;
                case "7":
                    List<OutputDTO> accounts = nameSearch();
                    if (!accounts.isEmpty()) {
                        accounts.forEach(System.out::println);
                    }
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
        System.out.println("Please insert an option(1-8): ");
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
                } catch (NumberFormatException e) {
                    System.out.println("Invalid number format. Please try again.");
                    if (askToReturnToMenu()) return;
                }
            }

            System.out.println("Please insert your email");
            String email = scanner.nextLine().trim();

            System.out.println("Please insert your username");
            System.out.println("Username must be 3-15 characters long, include at least one lowercase letter, one uppercase letter, and one digit. Only letters and numbers allowed.");
            String username = scanner.nextLine().trim();

            System.out.println("Please insert your password");
            System.out.println("Password must be 4-15 characters long, include at least one lowercase letter, one uppercase letter, one digit and one special character.");
            String password = scanner.nextLine().trim();


            InputDTO dto = new InputDTO(iban, balance, firstname, lastname, email, username, password);

            Map<String, String> errors = Validator.validate(dto);

            if (!errors.isEmpty()) {
                errors.forEach((k, v) -> System.out.println(v));
                System.out.println("Account was not created. Please try again");
                if (askToReturnToMenu()) {
                    break;
                }else {
                    continue;
                }
            }

            try {
                service.createNewAccount(dto);
                System.out.println("Account was successfully created");
                break;
            } catch (DuplicateIbanException e) {
                System.out.println("Error: The IBAN you entered is already in use. Please try again with a different IBAN.");
                if (askToReturnToMenu()) return;
            } catch (DuplicateUsernameException e) {
                System.out.println("Error: The username you entered is already taken. Please try again with a different username.");
                if (askToReturnToMenu()) return;
            } catch (NegativeAmountException e) {
                System.out.println("Error: The balance cannot be negative. Please enter a positive amount.");
                if (askToReturnToMenu()) return;
            }

        }
    }


    public static void updateAccount() {
        while (true) {
            Account account = accountLogin();
            if (account == null) return;

            System.out.printf("Welcome, %s %s%n", account.getFirstname(), account.getLastname());

            System.out.println("Which field would you like to update? (email, username, password, all):");
            String choice = scanner.nextLine().trim().toLowerCase();

            InputDTO dto = new InputDTO(
                    account.getIban(),
                    account.getBalance(),
                    account.getFirstname(),
                    account.getLastname(),
                    account.getUsername(),
                    account.getEmail(),
                    account.getPassword()
            );

            boolean updateSuccessful ;

            try {
                switch (choice) {
                    case "email":
                        updateSuccessful = updateEmail(dto);
                        break;
                    case "username":
                        updateSuccessful = updateUsername(dto);
                        break;
                    case "password":
                        updateSuccessful = updatePassword(dto);
                        break;
                    case "all":
                        updateSuccessful = updateEmail(dto)
                                && updateUsername(dto)
                                && updatePassword(dto);
                        break;
                    default:
                        System.out.println("Invalid choice.");
                        continue;
                }

                if (updateSuccessful) {
                    service.updateAccount(dto);
                    System.out.println("Account updated successfully!");
                    break;
                }
            } catch (AccountNotFoundException e) {
                System.out.println("Account was not found.");
                if (askToReturnToMenu()) return;
            } catch (DuplicateUsernameException e) {
                System.out.println("The username is already taken.");
                if (askToReturnToMenu()) return;
            }


        }
    }


    public static void deleteAccount() {
        Account account = accountLogin();
        if (account == null) return;

        System.out.printf("Welcome, %s %s\n", account.getFirstname(), account.getLastname());

        while (true) {
            System.out.println("Are you sure you want to permanently delete your account? (Y for yes , N for no):");
            String choice = scanner.nextLine().trim().toLowerCase();

            if (choice.equals("y")) {
                System.out.println("Please re-enter your password:");
                String password = scanner.nextLine();
                if (password.equals(account.getPassword())) {
                    try {
                        service.removeAccount(account.getIban());
                        System.out.println("Successfully deleted account.");
                    } catch (AccountNotFoundException e) {
                        System.out.println("Error: Account could not be found. Please try again.");
                        if (askToReturnToMenu()) return;
                    }
                    break;
                } else {
                    System.out.println("Invalid password. Please try again.");
                }
            } else if (choice.equals("n")) {
                break;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
            if (askToReturnToMenu()) {
                break;
            }
        }


    }


    public static void deposit() {
        Account account = accountLogin();
        if (account == null) return;
        System.out.printf("Welcome, %s %s",account.getFirstname(),account.getLastname());

        while (true) {
            System.out.println(". Please insert the amount you want to deposit");
            try {
                BigDecimal amount = new BigDecimal(scanner.nextLine());
                service.deposit(account.getIban(), amount);
                System.out.println("Deposit successful! New balance: " + account.getBalance());
                break;
            } catch (NegativeAmountException e) {
                System.out.println("The amount you entered is negative.Please try again");
            } catch (NumberFormatException e){
                System.out.println("The amount you entered is not valid.Please try again");
            }catch (AccountNotFoundException e) {
                System.out.println("Error: Account could not be found. Please try again.");
            }
            if (askToReturnToMenu()){
                break;
            }
        }
    }

    public static void withdraw(){
        Account account = accountLogin();
        if (account == null) return;
        System.out.printf("Welcome, %s %s",account.getFirstname(),account.getLastname());

        while (true) {
            System.out.println(". Please insert the amount you want to withdraw");
            try {
                BigDecimal amount = new BigDecimal(scanner.nextLine());
                service.withdraw(account.getIban(), amount);
                System.out.println("Withdrawal successful! New balance: " + account.getBalance());
                break;
            }catch (NegativeAmountException e) {
                System.out.println("The amount you entered is negative.Please try again");
            }catch (NumberFormatException e){
                System.out.println("The amount you entered is not valid.Please try again");
            }catch (InsufficientBalanceException e) {
                System.out.println("Your balance is not sufficient for this withdrawal");
            }catch (AccountNotFoundException e) {
                System.out.println("Error: Account could not be found. Please try again.");
            }
            if (askToReturnToMenu()){
                break;
            }
        }
    }

    public static OutputDTO ibanSearch(){
        while(true) {
            System.out.println("Please insert Iban");
            String iban = scanner.nextLine();
            try {
                return service.getAccountByIban(iban);
            } catch (AccountNotFoundException e) {
                System.out.println("Error: Account could not be found. Please try again.");
            }
            if (askToReturnToMenu()) {
                return null;
            }
        }
    }

    public static List<OutputDTO> nameSearch(){
        while(true){
            System.out.println("Please insert firstname");
            String firstname = scanner.nextLine();
            System.out.println("Please insert lastname");
            String lastname = scanner.nextLine();
            try {
                return service.getAccountsByName(firstname, lastname);
            } catch (AccountNotFoundException e) {
                System.out.println("Error: Account could not be found. Please try again.");
            }
            if (askToReturnToMenu()) {
                return Collections.emptyList();
            }
        }
    }

    public static void appExit() {
        scanner.close();
        System.out.println("Exiting app...");
        System.exit(1);
    }



    private static Account accountLogin() {
        while (true) {
            try {
                System.out.println("Please login");
                System.out.println("Enter your username:");
                String username = scanner.nextLine();
                System.out.println("Enter your password:");
                String password = scanner.nextLine();

                return service.authenticate(username, password);
            } catch (AccountNotFoundException e) {
                System.out.println("Account not found. Please try again.");

                if (askToReturnToMenu()) return null;
            }
        }
    }


    private static boolean askToReturnToMenu() {
        while (true) {
            System.out.println("\nDo you want to try again or return to main menu?");
            System.out.println("1. Try again");
            System.out.println("2. Return to menu");
            System.out.println("Please enter 1 or 2");
            String input = scanner.nextLine().trim();
            if (input.equals("1")) return false;
            else if (input.equals("2")) return true;
            else System.out.println("Invalid choice. Please enter 1 or 2.");
        }
    }

    private static boolean updateEmail(InputDTO dto) {
        while (true) {
            System.out.println("Please enter new email:");
            String newEmail = scanner.nextLine().trim();
            Map<String, String> errors = new HashMap<>();
            Validator.validateEmail(newEmail, errors);

            if (errors.isEmpty()) {
                dto.setEmail(newEmail);
                return true;
            }
            System.out.println(errors.get("email"));
            System.out.println("Please try again (or type 'exit' to cancel):");
            if (scanner.nextLine().trim().equalsIgnoreCase("exit")) {
                return false;
            }
        }
    }

    private static boolean updateUsername(InputDTO dto) {
        while (true) {
            System.out.println("Please enter new username:");
            String newUsername = scanner.nextLine().trim();
            Map<String, String> errors = new HashMap<>();
            Validator.validateUsername(newUsername, errors);

            if (errors.isEmpty()) {
                dto.setUsername(newUsername);
                return true;
            }
            System.out.println(errors.get("username"));
            System.out.println("Please try again (or type 'exit' to cancel):");
            if (scanner.nextLine().trim().equalsIgnoreCase("exit")) {
                return false;
            }
        }
    }

    private static boolean updatePassword(InputDTO dto) {
        while (true) {
            System.out.println("Please enter new password:");
            String newPassword = scanner.nextLine().trim();
            System.out.println("Please confirm new password:");
            String confirmPassword = scanner.nextLine().trim();

            if (!newPassword.equals(confirmPassword)) {
                System.out.println("Passwords don't match. Please try again (or type 'exit' to cancel):");
                if (scanner.nextLine().trim().equalsIgnoreCase("exit")) {
                    return false;
                }
                continue;
            }

            Map<String, String> errors = new HashMap<>();
            Validator.validatePassword(newPassword, errors);

            if (errors.isEmpty()) {
                dto.setPassword(newPassword);
                return true;
            }
            System.out.println(errors.get("password"));
            System.out.println("Please try again (or type 'exit' to cancel):");
            if (scanner.nextLine().trim().equalsIgnoreCase("exit")) {
                return false;
            }
        }
    }
}
