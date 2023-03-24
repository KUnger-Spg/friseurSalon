package at.spengergasse.friseursalon.service.dtos;

import at.spengergasse.friseursalon.domain.Customer;
import at.spengergasse.friseursalon.domain.Service;

import java.math.BigDecimal;
import java.util.Currency;

//public record ServiceDto(String serviceName, BigDecimal servicePreis, Currency serviceCurrency) {

public record ServiceDto(String serviceName) {

        public ServiceDto(Service service){
            this(service.getServiceName());
//                    service.getServicePreis(),
//                    service.getServiceCurrency());
        }
}
