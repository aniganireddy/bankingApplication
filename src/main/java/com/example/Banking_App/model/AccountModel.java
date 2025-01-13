package com.example.Banking_App.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Random;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "bank-accounts")
public class AccountModel {


    @Id
    private String id;
    private String partId;
    private String bankAccountNumber;
    private double accountBalance;


    public static String generatePartyId() {
        Random random = new Random();
        int partyId = 100000000 + random.nextInt(900000000); // Generates a 9-digit number
        return String.valueOf(partyId);
    }
}
