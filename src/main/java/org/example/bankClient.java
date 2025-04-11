package org.example;

import java.io.Serializable;
import java.util.*;

public class bankClient implements Serializable {
    String username;
    String password;
    double balance;
    double wallet;
    List<Transaction> transactionHistory;

    public bankClient(String username, String password, double balance, double wallet) {
        this.username = username;
        this.password = password;
        this.balance = balance;
        this.wallet = wallet;
        this.transactionHistory = new ArrayList<>();
    }

    public String getPassword() {
        return password;
    }

    public double getWallet() {
        return wallet;
    }

    public double getBalance() {
        return balance;
    }

    public String getUsername() {
        return username;
    }

    public boolean checkPassword(String inputPassword) {
        return this.password.equals(inputPassword);
    }

    public void addTransaction(String type, double amount, String description, Date date) {
        Transaction transaction = new Transaction(type, amount, description);
        transactionHistory.add(transaction);
    }

    public void deposit(double moneyToDeposit) {
        if (wallet < moneyToDeposit) {
            System.out.println("Solo " + wallet + " euro sono stati depositati");
            moneyToDeposit = wallet;
            wallet = 0;
        } else {
            wallet -= moneyToDeposit;
        }
        balance += moneyToDeposit;
        addTransaction("Deposito", moneyToDeposit, "Deposito di denaro", new Date());
    }

    public void withdraw(double moneyToTake) {
        if (balance <= 0) {
            System.out.println("Conto vuoto");
            return;
        }
        if (moneyToTake > balance) {
            System.out.println("Soldi prelevati: " + balance + " euro");
            moneyToTake = balance;
            balance = moneyToTake;
        }
        wallet += moneyToTake;
        balance -= moneyToTake;
        addTransaction("Prelievo", moneyToTake, "Prelievo di denaro", new Date());
    }

    public void printBalance() {
        System.out.println(balance);
    }

    public void printWallet() {
        System.out.println("Portafoglio: " + wallet);
    }

    public void printStatus() {
        System.out.println("\nNome utente: " + username + "\nConto: " + balance);
    }

    public List<Double> invest() {
        Scanner scanner = new Scanner(System.in);
        List<Double> finalResults = new ArrayList<>();
        System.out.println("1 - Investimento breve durata (12 mesi)\n2 - Investimento media durata (4 anni)\n3 - Investimento lunga durata (10 anni)");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                handleInvestment(
                        finalResults,
                        "Investimento Breve",
                        "Investimento a breve durata.",
                        12,
                        new double[][]{
                                {0.9, 0.95, 1.0, 1.05, 1.1, 1.1, 1.1, 1.1, 1.15, 1.15},
                                {0.8, 0.9, 0.95, 1.0, 1.05, 1.1, 1.1, 1.15, 1.2, 1.25},
                                {0.7, 0.8, 0.9, 1.0, 1.1, 1.2, 1.3, 1.4, 1.5, 1.6}
                        }
                );
                break;
            case 2:
                handleInvestment(
                        finalResults,
                        "Investimento Medio",
                        "Investimento a media durata.",
                        4,
                        new double[][]{
                                {0.8, 0.85, 0.9, 0.95, 1.0, 1.05, 1.05, 1.1, 1.1, 1.15},
                                {0.7, 0.8, 0.9, 0.95, 1.0, 1.1, 1.15, 1.2, 1.25, 1.3},
                                {0.6, 0.7, 0.8, 0.9, 1.0, 1.2, 1.3, 1.4, 1.5, 1.7}
                        }
                );
                break;
            case 3:
                handleInvestment(
                        finalResults,
                        "Investimento Lungo",
                        "Investimento a lunga durata.",
                        10,
                        new double[][]{
                                {0.8, 0.85, 0.9, 0.95, 1.0, 1.1, 1.15, 1.2, 1.25, 1.3},
                                {0.7, 0.8, 0.9, 1.0, 1.1, 1.2, 1.3, 1.4, 1.5, 1.7},
                                {0.5, 0.6, 0.7, 0.8, 1.0, 1.2, 1.5, 1.7, 2.0, 2.5}
                        }
                );
                break;
            default:
                System.out.println("Scelta inesistente");
                return invest();
        }

        return finalResults;
    }

    private void handleInvestment(List<Double> finalResults, String title, String description, int duration, double[][] riskProfiles) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(title);
        System.out.println("Scelta:\n1-Investimento di basso rischio e basso guadagno\n2-Investimento di medio rischio e medio guadagno\n3-Investimento di alto rischio e alto guadagno");
        int choice = scanner.nextInt();

        double[] winnings;
        if (choice >= 1 && choice <= 3) {
            winnings = riskProfiles[choice - 1];
        } else {
            System.out.println("Scelta non valida. Verrà selezionato l'investimento a medio rischio.");
            winnings = riskProfiles[1];
        }

        System.out.print("Soldi disponibili: ");
        printBalance();
        System.out.println("Quanto vuoi investire?");
        double moneyToInvest = scanner.nextDouble();

        addTransaction(title, moneyToInvest, description, new Date());

        if (moneyToInvest > balance) {
            System.out.println((moneyToInvest - balance) + " euro non sono stati investiti");
            moneyToInvest = balance;
            balance = 0;
        } else {
            balance -= moneyToInvest;
        }

        Random rand = new Random();
        for (int i = 0; i < duration; i++) {
            int index = rand.nextInt(10);
            moneyToInvest *= winnings[index];
            finalResults.add(moneyToInvest);
        }
    }


    public void JumpMoths(List<Double> investimentsV, int JumpM) {
        wallet += JumpM * 100;
        if (investimentsV.isEmpty()) {
            printStatus();
            return;
        }
        if (JumpM > investimentsV.size()) {
            balance += investimentsV.get(investimentsV.size() - 1);
        } else {
            balance += investimentsV.get(JumpM - 1);
        }
        printStatus();
    }

    public void printTransactionHistory() {
        if (transactionHistory.isEmpty()) {
            System.out.println("Nessuna transazione disponibile.");
            return;
        }
        System.out.println("Storico delle transazioni:");
        for (Transaction transaction : transactionHistory) {
            System.out.println(transaction);
        }
    }
}
