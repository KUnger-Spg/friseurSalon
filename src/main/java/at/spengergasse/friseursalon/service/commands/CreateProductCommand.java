package at.spengergasse.friseursalon.service.commands;

import java.math.BigDecimal;
import java.util.Currency;

//public record CreateProductCommand(String productName, BigDecimal productPreis, Currency productCurrency, String reservationKey) { }

    public record CreateProductCommand(String productName, String reservationKey) {

}
