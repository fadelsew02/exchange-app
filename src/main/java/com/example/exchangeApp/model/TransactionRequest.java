package com.example.exchangeApp.model;

import lombok.Data;

@Data

public class TransactionRequest {
    private String userSourceEmail;
    private String userDestinationEmail;
    private Double transactionAmount;
    private String deviseSource;
    private String deviseDestination;
}




