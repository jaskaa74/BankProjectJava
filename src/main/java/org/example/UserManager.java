package org.example;

import java.io.*;
import java.util.*;

public class UserManager {
    private static final String FILE_PATH = "users.txt";
    private Map<String, bankClient> users;

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
        saveUsers();
        System.out.println("Registrazione avvenuta con successo!");
    }

    public bankClient loginUser(String username, String password) {
        bankClient user = users.get(username);
        if (user != null && user.checkPassword(password)) {
            System.out.println("Login effettuato con successo!");
            return user;
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
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Map.Entry<String, bankClient> entry : users.entrySet()) {
                bankClient user = entry.getValue();
                writer.write(user.getUsername() + "," + user.getPassword() + "," + user.getBalance() + "," + user.getWallet());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveUserData() {
        saveUsers();
    }

    private void loadUsers() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String username = parts[0];
                    String password = parts[1];
                    double balance = Double.parseDouble(parts[2]);
                    double wallet = Double.parseDouble(parts[3]);
                    users.put(username, new bankClient(username, password, balance, wallet));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

