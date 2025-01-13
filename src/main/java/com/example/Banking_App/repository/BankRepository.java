package com.example.Banking_App.repository;

import com.example.Banking_App.model.AccountModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface BankRepository extends MongoRepository<AccountModel,String> {
    Optional<AccountModel> findByPartId(String partId);
}
