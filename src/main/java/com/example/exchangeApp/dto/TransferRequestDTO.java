package com.example.exchangeApp.dto;

public record TransferRequestDTO(String emailDestination, Double montant, String deviseDestination, String paymentOption) {
    
}

