package org.example;

import java.io.*;
import java.util.*;

public class UserManager {
    private static final String FILE_PATH = "src/main/resources/users.txt";
    private final Map<String, bankClient> users;

    public UserManager() {
        users = new HashMap<>();
        loadUsers();
    }

    public void registerUser(String username, String password) {
        if (users.containsKey(username)) {
            System.out.println("Errore: L'utente esiste già!");
            return;
        }
        users.put(username, new bankClient(username, password, 0.0, 100.0));
        System.out.println("Registrazione avvenuta con successo!");
    }

    public bankClient loginUser(String username, String password) {
        bankClient user = users.get(username);
        if (user != null && user.checkPassword(password)) {
            System.out.println("Login effettuato con successo!");
            return users.get(username);
        }
        System.out.println("Errore: Credenziali non valide.");
        return null;
    }

    public boolean deleteUser(String username, String password) {
        if (!users.containsKey(username)) {
            System.out.println("Errore: L'utente non esiste!");
            return false;
        }

        bankClient user = users.get(username);
        if (!user.checkPassword(password)) {
            System.out.println("Errore: La password non è corretta.");
            return false;
        }
        users.remove(username);
        saveUsers();
        System.out.println("Utente eliminato con successo.");
        return true;
    }

    private void saveUsers() {
        File file = new File(FILE_PATH);

        if(!file.exists()){
            try{
                System.out.println(file.createNewFile());
            }catch(Exception e){
                System.out.println("ERROR");
            }
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file.getPath(), false))) {
            for(Map.Entry<String, bankClient> user : users.entrySet()){
                String temp = "";
                bw.write(user.getValue().getUsername() + ";" + user.getValue().getPassword() + ";" + user.getValue().getBalance() + ";" + user.getValue().getWallet() + ";");
                for(Transaction transaction : user.getValue().transactionHistory){
                    temp = transaction.date.toString() + "," + transaction.type +  "," + Double.toString(transaction.amount) + "," + transaction.description;
                    bw.write(temp + ";");
                }
                bw.write("\n");
            }
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void saveUserData() {
        saveUsers();
    }

    private void loadUsers() {
        File file = new File(FILE_PATH);

        if(!file.exists()){
            System.out.println("ERROR");
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file.getPath()))) {
            String line = "";
            while((line = br.readLine()) != null){
                String[] lineParts = line.split(";");
                registerUser(lineParts[0], lineParts[1]);
                bankClient currentUser = loginUser(lineParts[0], lineParts[1]);
                currentUser.balance = Double.parseDouble(lineParts[2]);
                currentUser.wallet = Double.parseDouble(lineParts[3]);
                if(lineParts.length > 3){
                    for(int i = 4; i < lineParts.length ; i++){
                        String[] transactionParts = lineParts[i].split(",");
                        String tempType = transactionParts[1];
                        double amount = Double.parseDouble(transactionParts[2]);
                        String description = transactionParts[3];
                        String[] time = transactionParts[0].split(" ");
                        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
                        int month = 0;
                        for(int c = 0; c < months.length ; c++){
                            if(months[c].equals(time[1])){
                                month = c+1;
                                break;
                            }
                        }
                        Date tempDate = new Date();
                        tempDate.setMonth(month);
                        tempDate.setDate(Integer.parseInt(time[2]));
                        tempDate.setYear(Integer.parseInt(time[5]));
                        String[] tempTime = time[3].split(":");
                        tempDate.setHours(Integer.parseInt(tempTime[0]));
                        tempDate.setMinutes(Integer.parseInt(tempTime[1]));
                        tempDate.setSeconds(Integer.parseInt(tempTime[2]));
                        loginUser(lineParts[0], lineParts[1]).addTransaction(tempType, amount, description, tempDate);
                    }
                }
            }
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
