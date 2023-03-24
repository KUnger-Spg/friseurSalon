package at.spengergasse.friseursalon.service.commands;

import java.math.BigDecimal;
import java.util.Currency;

//public record CreateServiceCommand (String serviceName, BigDecimal servicePreis, Currency serviceCurrency) {  }

public record CreateServiceCommand (String serviceName) {  }
