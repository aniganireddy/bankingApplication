package com.example.Banking_App.service;

import com.example.Banking_App.dto.AccountDto;
import com.example.Banking_App.exception.AccountNotExistsException;
import com.example.Banking_App.model.AccountModel;
import com.example.Banking_App.repository.BankRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class bankServiceImpl implements bankService {

    @Autowired
    BankRepository bankRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public AccountDto createbankAccount(AccountDto bankDto) {
        // converting bank dto to bank model
        AccountModel savedbank = modelMapper.map(bankDto, AccountModel.class);
        savedbank.setPartId(AccountModel.generatePartyId()); // corrected method call
        AccountModel savedUser = bankRepository.save(savedbank);

        // converting bank model to bank dto
        AccountDto savedbanks = modelMapper.map(savedUser, AccountDto.class);
        return savedbanks;
    }

    @Override
    public AccountDto addAccountbalance(String partId, double accountBalance) {
        AccountModel accountModel = bankRepository.findByPartId(partId).orElseThrow(() -> new AccountNotExistsException("Account doesn't exists for this customer"));
        double total = accountModel.getAccountBalance() + accountBalance;
        accountModel.setAccountBalance(total);
        AccountModel accountModel2 = bankRepository.save(accountModel);
       AccountDto accountDto= modelMapper.map(accountModel2, AccountDto.class);
       return accountDto;


    }

    @Override
    public Optional<AccountDto> getAccounts(String partId) {
        Optional<AccountModel> accountModel=bankRepository.findByPartId(partId);
        AccountDto accountDto1=modelMapper.map(accountModel, AccountDto.class);
        return Optional.ofNullable(accountDto1);
    }


}
