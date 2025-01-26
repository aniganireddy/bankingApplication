package com.example.Banking_App.controller;

import com.example.Banking_App.dto.AccountDto;
import com.example.Banking_App.service.bankServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/accounts")
public class BankAccountController {
    @Autowired
    bankServiceImpl bankService;




    @PostMapping("/create")
    public ResponseEntity<AccountDto> createAccount(@RequestBody AccountDto bankDto){
      AccountDto bankDto1= bankService.createbankAccount(bankDto);
      return new ResponseEntity<>(bankDto1, HttpStatus.CREATED);
    }


    @PutMapping ("/deposit/{partId}")
    public ResponseEntity<AccountDto> depositAmount(@PathVariable String partId,
                                                @RequestBody Map<String, Double> request)
    {

        double accountBalance= request.get("accountBalance");
        if (!request.containsKey("accountBalance") || request.get("accountBalance") == null) {
            return ResponseEntity.badRequest().body(null); // Handle invalid input
        }
        AccountDto accountDto = bankService.addAccountbalance(partId,accountBalance);
         return ResponseEntity.ok(accountDto);

    }
     @GetMapping("/{partId}")
    public ResponseEntity<Optional<AccountDto>> getAccounts(@PathVariable String partId){
       Optional<AccountDto> accountDto= bankService.getAccounts(partId);
       return ResponseEntity.ok(accountDto);
    }

    @PutMapping ("/withdrawal/{partId}")
    public ResponseEntity<AccountDto> withDrawalAmount(@PathVariable String partId,
                                                    @RequestBody Map<String, Double> request)
    {

        double accountBalance= request.get("accountBalance");
        AccountDto accountDto = bankService.withDrawalAmount(partId,accountBalance);
        return ResponseEntity.ok(accountDto);

    }
    @GetMapping("/all-accounts")
    public ResponseEntity<List<AccountDto>> getAllAccountss(){
        List<AccountDto> accountDto= bankService.getAllAccounts();
        return new ResponseEntity<>(accountDto, HttpStatus.OK);
    }

    @PutMapping("/update/{partId}")
    public  ResponseEntity<AccountDto> updateAccount(@PathVariable String partId,@RequestBody AccountDto accountDto){
        accountDto.setPartId(partId);
       AccountDto updated = bankService.updateAccountDetails(accountDto);
       return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{partId}")
    public ResponseEntity <String> deleteCustomerAccount(@PathVariable String partId){
        bankService.deleteAccount(partId);
        return new ResponseEntity<>("deleted succesfully",HttpStatus.OK);
    }


}
