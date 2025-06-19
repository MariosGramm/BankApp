import DAO.IAccountDAO;
import DAO.ImplementAccountDAO;
import DTO.InputDTO;
import service.IAccountService;
import service.ImplementAccountService;

import java.math.BigDecimal;
import java.util.Scanner;

public class Main {
    private static final IAccountDAO dao = new ImplementAccountDAO();
    private static final IAccountService service = new ImplementAccountService(dao);
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        String option;
        String iban;
        String firstname;
        String lastname;

        while (true) {
            printMenu();
            option = scanner.nextLine();

            try{
                switch (option){
                    case "1":
                        System.out.println("Please insert your iban");
                        iban = scanner.nextLine();
                        System.out.println("Please insert your first name");
                        firstname = scanner.nextLine();
                        System.out.println("Please insert your last name");
                        lastname = scanner.nextLine();
                        System.out.println("Please insert your balance");
                        BigDecimal balance = new BigDecimal(scanner.nextLine());
                        InputDTO dto = new InputDTO(iban,balance,firstname,lastname);
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
}
