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
        bankClient currentUser = null; // Utente loggato
        List<Double> investimentsV = new ArrayList<>();

        // Autenticazione utente
        while (currentUser == null) {
            System.out.println("\nBenvenuto! Seleziona un'opzione:");
            System.out.println("1 - Registrati");
            System.out.println("2 - Accedi");
            System.out.println("3 - Esci");

            int authChoice = scanner.nextInt();
            scanner.nextLine(); // Pulisce il buffer

            if (authChoice == 1) {
                System.out.print("Inserisci username: ");
                String username = scanner.nextLine();
                System.out.print("Inserisci password: ");
                String password = scanner.nextLine();
                userManager.registerUser(username, password);
            } else if (authChoice == 2) {
                System.out.print("Inserisci username: ");
                String username = scanner.nextLine();
                System.out.print("Inserisci password: ");
                String password = scanner.nextLine();
                currentUser = userManager.loginUser(username, password);
            } else if (authChoice == 3) {
                System.out.println("Uscita...");
                scanner.close();
                return;
            } else {
                System.out.println("Scelta non valida.");
            }
        }

        int choice = 0;
        while (choice != 7) {
            System.out.println("\nCosa vuoi fare?:");
            System.out.println("1 - Deposito");
            System.out.println("2 - Prelievo");
            System.out.println("3 - Investimento");
            System.out.println("4 - Avanza nel tempo");
            System.out.println("5 - Visualizza lo stato del tuo conto");
            System.out.println("6 - Visualizza lo stato del tuo portafoglio");
            System.out.println("7 - Esci");

            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Inserisci la somma da depositare:");
                    double depositSum = scanner.nextDouble();
                    currentUser.deposit(depositSum);
                    break;
                case 2:
                    System.out.println("Inserisci la somma da prelevare:");
                    double takeSum = scanner.nextDouble();
                    currentUser.withdraw(takeSum);
                    break;
                case 3:
                    investimentsV = currentUser.invest();
                    break;
                case 4:
                    System.out.println("Di quanti mesi vuoi avanzare?");
                    int JumpMonths = scanner.nextInt();
                    currentUser.JumpMoths(investimentsV, JumpMonths);
                    break;
                case 5:
                    currentUser.printStatus();
                    break;
                case 6:
                    currentUser.printWallet();
                    break;
                case 7:
                    System.out.println("Uscita...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Scelta non valida.");
            }
            waitForSendKey();
        }
        scanner.close();
    }
}

