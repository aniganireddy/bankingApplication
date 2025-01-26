package com.example.Banking_App.service;

import com.example.Banking_App.dto.AccountDto;
import com.example.Banking_App.exception.AccountNotExistsException;
import com.example.Banking_App.model.AccountModel;
import com.example.Banking_App.repository.BankRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        savedbank.setPartId(AccountModel.generatePartyId());
        String prefix = "123";
        String suffix = "500";
        String AccountNumber = prefix+savedbank.getPartId()+suffix;
        savedbank.setBankAccountNumber(AccountNumber);
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

    @Override
    public AccountDto withDrawalAmount(String partId, double accoutbalance) {
        AccountModel accountModel = bankRepository.findByPartId(partId).orElseThrow(() -> new AccountNotExistsException("Account doesn't exists for this customer"));
        double remainingBalance ;
        remainingBalance=accountModel.getAccountBalance() - accoutbalance;
        accountModel.setAccountBalance(remainingBalance);
        AccountModel accountModel2 = bankRepository.save(accountModel);
        AccountDto accountDto=modelMapper.map(accountModel2,AccountDto.class);
        return accountDto;
    }

    @Override
    public List<AccountDto> getAllAccounts() {
       List<AccountModel> accountModels= bankRepository.findAll();
        return accountModels.stream()
                .map(accountModel -> modelMapper.map(accountModel, AccountDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public AccountDto updateAccountDetails(AccountDto accountDto) {
        Optional<AccountModel> existing= bankRepository.findByPartId(accountDto.getPartId());
        if(!existing.isPresent()){
            throw new AccountNotExistsException("Account Not Found Exception");
        }
        existing.get().setBankAccountNumber(accountDto.getBankAccountNumber());
        existing.get().setAccountBalance(accountDto.getAccountBalance());
        AccountModel updated=bankRepository.save(existing.get());
        return modelMapper.map(updated,AccountDto.class);
    }

    @Override
    public String  deleteAccount(String partId) {
        Optional<AccountModel> existing= bankRepository.findByPartId(partId);
        if(!existing.isPresent()){
            throw new AccountNotExistsException("Account Not Found Exception");
        }
        bankRepository.deleteByPartId(partId);
        return null;
    }


}
