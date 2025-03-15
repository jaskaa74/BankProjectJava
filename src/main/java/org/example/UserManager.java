package org.example;

import java.io.*;
import java.util.*;

public class UserManager {
    private static final String FILE_PATH = "users.dat";
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
        users.put(username, new bankClient(username, password));
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

    private void saveUsers() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            out.writeObject(users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadUsers() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            users = (Map<String, bankClient>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            users = new HashMap<>();
        }
    }
}

