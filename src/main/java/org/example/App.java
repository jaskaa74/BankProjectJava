package org.example;

import java.util.*;

public class App {
    public static void waitForSendKey() {
        System.out.println("\nPremi Invio per tornare al menu principale.");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserManager userManager = new UserManager();
        bankClient currentUser = null;

        while (true) {
            System.out.println("\n1 - Registrati");
            System.out.println("2 - Accedi");
            System.out.println("3 - Elimina account");
            System.out.println("4 - Esci");
            System.out.print("Scelta: ");
            int authChoice = scanner.nextInt();
            scanner.nextLine();

            if (authChoice == 4) {
                System.out.println("Uscita dal programma...");
                break;
            }

            System.out.print("Inserisci username: ");
            String username = scanner.nextLine();

            if (authChoice == 3) {
                System.out.print("Inserisci la tua password per confermare l'eliminazione: ");
                String password = scanner.nextLine();

                if (userManager.deleteUser(username, password)) {
                    System.out.println("Account eliminato con successo.");
                } else {
                    System.out.println("Password errata. Eliminazione annullata.");
                }
                continue;
            }

            System.out.print("Inserisci password: ");
            String password = scanner.nextLine();

            if (authChoice == 1) {
                userManager.registerUser(username, password);
            } else if (authChoice == 2) {
                currentUser = userManager.loginUser(username, password);
                if (currentUser != null) {
                    userMenu(scanner, userManager, currentUser);
                }
            }
        }
        scanner.close();
    }

    public static void userMenu(Scanner scanner, UserManager userManager, bankClient currentUser) {
        List<Double> investments = new ArrayList<>();
        int choice = 0;

        while (choice != 8) {
            System.out.println("\n1 - Deposito");
            System.out.println("2 - Prelievo");
            System.out.println("3 - Investimento");
            System.out.println("4 - Avanza nel tempo");
            System.out.println("5 - Stato conto");
            System.out.println("6 - Stato portafoglio");
            System.out.println("7 - Visualizza storico delle transazioni");
            System.out.println("8 - Logout");
            System.out.print("Scelta: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Inserisci la somma da depositare: ");
                    double depositSum = scanner.nextDouble();
                    currentUser.deposit(depositSum);
                    break;
                case 2:
                    System.out.print("Inserisci la somma da prelevare: ");
                    double takeSum = scanner.nextDouble();
                    currentUser.withdraw(takeSum);
                    break;
                case 3:
                    investments = currentUser.invest();
                    break;
                case 4:
                    System.out.print("Di quanti mesi vuoi avanzare? ");
                    int jumpMonths = scanner.nextInt();
                    currentUser.JumpMoths(investments, jumpMonths);
                    break;
                case 5:
                    currentUser.printStatus();
                    break;
                case 6:
                    currentUser.printWallet();
                    break;
                case 7:
                    currentUser.printTransactionHistory();
                    break;
                case 8:
                    System.out.println("Logout effettuato.");
                    userManager.saveUserData();
                    return;
                default:
                    System.out.println("Scelta non valida.");
            }
        }
    }
}