package org.example;

import java.io.Serializable;
import java.util.Date;

public class Transaction implements Serializable {
    String type;
    double amount;
    Date date;
    String description;

    public Transaction(String type, double amount, String description) {
        this.type = type;
        this.amount = amount;
        this.date = new Date();
        this.description = description;
    }

    @Override
    public String toString() {
        return "Data: " + date.toString() + "\nTipo: " + type + "\nImporto: " + amount + "€\nDescrizione: " + description + "\n";
    }
}
