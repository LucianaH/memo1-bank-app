package com.aninfo.service;

import com.aninfo.exceptions.InvalidCbuException;
import com.aninfo.exceptions.InvalidTransactionTypeException;
import com.aninfo.model.Account;
import com.aninfo.model.Transaction;
import com.aninfo.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountService accountService;

    public void deleteById(Long id) {
        transactionRepository.deleteById(id);
    }

    public Optional<Transaction> findById(Long id) {
        return transactionRepository.findById(id);
    }

    public Transaction createTransaction(Long cbu, Double sum, String transactionType) {

        Optional<Account> account = accountService.findById(cbu);
        if (!account.isPresent()) {
            throw new InvalidCbuException("Account with requested cbu not found");
        }

        if (transactionType.equals("deposito")) {
            accountService.deposit(cbu, sum);
        } else if (transactionType.equals("extraccion")) {
            accountService.withdraw(cbu, sum);
        } else throw new InvalidTransactionTypeException("Transaction should be 'deposito' or 'extraccion'");

        Transaction transaction = new Transaction(sum, transactionType, cbu);
        return transactionRepository.save(transaction);
    }

    public List<Transaction> getTransactions(Long cbu) {
        List<Transaction> transactions = transactionRepository.findAll();

        return transactions.stream().filter(transaction-> transaction.getCbu() == cbu).collect(Collectors.toList());
    }
}
