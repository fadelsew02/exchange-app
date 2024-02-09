package com.example.exchangeApp.dto;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor


public class TransactionInfoDTO {
    private Double transactionAmount;
    private LocalDateTime transactionDateTime;
    private String status;
    private String userEmail;
    private String transactionCurrency;

    public TransactionInfoDTO(Double transactionAmount, LocalDateTime transactionDateTime, String status, String userEmail, String transactionCurrency) {
        this.transactionAmount = transactionAmount;
        this.transactionDateTime = transactionDateTime;
        this.status = status;
        this.userEmail = userEmail;
        this.transactionCurrency = transactionCurrency;
    }

}
