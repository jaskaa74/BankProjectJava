package org.example;
import java.util.*;

public class App
{
    public static void waitForSendKey() {
        System.out.println("\nPremi Invio per tornare al menu principale.");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        bankClient firstClient = new bankClient("Giovanni");
        int choice = 0;
        List<Double> investimentsV = new ArrayList<>();

        while (choice != 7) {
            System.out.println("\nCosa vuoi fare?:");
            System.out.println("1 - Deposito");
            System.out.println("2 - Prelievo");
            System.out.println("3 - Investimento");
            System.out.println("4 - Avanza nel tempo");
            System.out.println("5 - Visualizza lo stato del tuo conto");
            System.out.println("6 - Visualizza lo stato del tuo portafoglio");
            System.out.println("7 - Chiudi applicazione\n");

            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Inserisci la somma da depositare");
                    System.out.println("ATTENZIONE: SE LA CIFRA È SUPERIORE A QUELLA DEL PORTAFOGLIO LA DIFFERENZA NON VERRÀ DEPOSITATA");
                    System.out.println("SE IL CONTO È IN NEGATIVO VERRÀ UTILIZZATA PER RISANARE IL DEBITO");
                    double depositSum = scanner.nextDouble();
                    firstClient.deposit(depositSum);
                    break;
                case 2:
                    System.out.println("Inserisci la somma da prelevare");
                    System.out.println("ATTENZIONE: SE IL CONTO È IN NEGATIVO NON SARÀ POSSIBILE PRELEVARE");
                    double takeSum = scanner.nextDouble();
                    firstClient.takeMoney(takeSum);
                    break;
                case 3:
                    investimentsV = firstClient.invest();
                    break;
                case 4:
                    System.out.println("Di quanti mesi vuoi avanzare?");
                    int JumpMonths = scanner.nextInt();
                    firstClient.JumpMoths(investimentsV, JumpMonths);
                    break;
                case 5:
                    firstClient.printStatus();
                    break;
                case 6:
                    firstClient.printWallet();
                    break;
                case 7:
                    scanner.close();
                    return;
                default:
                    break;
            }
        }
        scanner.close();
    }
}
