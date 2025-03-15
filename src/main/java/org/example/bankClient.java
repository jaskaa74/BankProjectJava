package org.example;

import java.io.Serializable;
import java.util.*;

public class bankClient implements Serializable {
    private String username;
    private String password;
    private double balance;
    private double wallet;

    public bankClient(String username, String password, double balance, double wallet) {
        this.username = username;
        this.password = password;
        this.balance = balance;
        this.wallet = wallet;
    }

    public bankClient(String username, String password) {
        this(username, password, 0, 100);
    }

    public String getUsername() {
        return username;
    }

    public boolean checkPassword(String inputPassword) {
        return this.password.equals(inputPassword);
    }

    public void printBalance() {
        System.out.println(balance);
    }

    public void printWallet() {
        System.out.println(wallet);
    }

    public void printStatus() {
        System.out.println("Stato utente:\nNome utente: " + username + "\nConto: " + balance);
    }

    public void deposit(double moneyToDeposit) {
        if (balance < 0) {
            balance += moneyToDeposit;
        }
        if (wallet < moneyToDeposit) {
            System.out.println("Solo " + wallet + " euro sono stati depositati");
            moneyToDeposit = wallet;
            wallet = 0;
        } else {
            wallet -= moneyToDeposit;
        }
        balance += moneyToDeposit;
    }

    public void withdraw(double moneyToTake) {
        if (balance <= 0) {
            System.out.println("Conto vuoto");
            return;
        }
        if (moneyToTake > balance) {
            System.out.println("Soldi prelevati: " + balance + " euro");
            moneyToTake = balance;
            balance = 0;
        }
        wallet += moneyToTake;
        balance -= moneyToTake;
    }

    public List<Double> shortInvestiment(List<Double> finalResults) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Scelta:\n1-Investimento di basso rischio e basso guadagno\n2-Investimento di medio rischio e medio guadagno\n3-Investimento di alto rischio e alto guadagno");
        int choice = scanner.nextInt();

        double[] winnings = {0.7, 0.8, 0.9, 1.0, 1.1, 1.1, 1.1, 1.1, 1.2, 1.2};
        Random rand = new Random();
        double moneyToInvest;

        System.out.print("soldi disponibili: ");
        printBalance();
        System.out.println("Quanto vuoi investire?");
        moneyToInvest = scanner.nextDouble();

        if (moneyToInvest > balance) {
            System.out.println((moneyToInvest - balance) + " euro non sono stati investiti");
            moneyToInvest = balance;
            balance = 0;
        } else {
            balance -= moneyToInvest;
        }

        for (int duration = 0; duration < 12; duration++) {
            int controlInvestiment = rand.nextInt(10);
            moneyToInvest *= winnings[controlInvestiment];
            finalResults.add(moneyToInvest);
        }
        return finalResults;
    }

    public List<Double> mediumInvestiment(List<Double> finalResults) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Investimento a medio termine (4 anni)");

        double[] winnings = {0.6, 0.7, 0.8, 0.9, 1.0, 1.2, 1.2, 1.3, 1.3, 1.5};
        Random rand = new Random();
        double moneyToInvest;

        System.out.print("Soldi disponibili: ");
        printBalance();
        System.out.println("Quanto vuoi investire?");
        moneyToInvest = scanner.nextDouble();

        if (moneyToInvest > balance) {
            System.out.println((moneyToInvest - balance) + " euro non sono stati investiti");
            moneyToInvest = balance;
            balance = 0;
        } else {
            balance -= moneyToInvest;
        }

        for (int duration = 0; duration < 4; duration++) { // 4 anni
            int controlInvestiment = rand.nextInt(10);
            moneyToInvest *= winnings[controlInvestiment];
            finalResults.add(moneyToInvest);
        }
        return finalResults;
    }

    public List<Double> longInvestiment(List<Double> finalResults) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Investimento a lungo termine (10 anni)");

        double[] winnings = {0.5, 0.6, 0.7, 0.8, 1.0, 1.2, 1.5, 1.7, 2.0, 2.5};
        Random rand = new Random();
        double moneyToInvest;

        System.out.print("Soldi disponibili: ");
        printBalance();
        System.out.println("Quanto vuoi investire?");
        moneyToInvest = scanner.nextDouble();

        if (moneyToInvest > balance) {
            System.out.println((moneyToInvest - balance) + " euro non sono stati investiti");
            moneyToInvest = balance;
            balance = 0;
        } else {
            balance -= moneyToInvest;
        }

        for (int duration = 0; duration < 10; duration++) { // 10 anni
            int controlInvestiment = rand.nextInt(10);
            moneyToInvest *= winnings[controlInvestiment];
            finalResults.add(moneyToInvest);
        }
        return finalResults;
    }


    public List<Double> invest() {
        Scanner scanner = new Scanner(System.in);
        List<Double> finalResults = new ArrayList<>();
        System.out.println("1 - Investimento breve durata (12 mesi)\n2 - Investimento media durata (4 anni)\n3 - Investimento lunga durata (10 anni)");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                shortInvestiment(finalResults);
                break;
            case 2:
                mediumInvestiment(finalResults);
                break;
            case 3:
                longInvestiment(finalResults);
                break;
            default:
                System.out.println("Scelta inesistente");
                invest();
                break;
        }
        return finalResults;
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
}
