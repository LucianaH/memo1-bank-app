package com.aninfo.model;

import javax.persistence.*;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long cbu;
    private String transactionType;
    private Double sum;

    public Transaction(Double sum, String type, Long cbu) {
        this.sum = sum;
        this.transactionType = type;
        this.cbu = cbu;
    }

    public Transaction() {
    }

    public Long getId() {
        return id;
    }

    public Long getCbu() {
        return cbu;
    }

    public Double getSum() {
        return sum;
    }

    public String getTransactionType() {
        return transactionType;
    }
}
