package com.example.Banking_App.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
private String id ;
private String partId;
private String bankAccountNumber;
private double accountBalance;
}
