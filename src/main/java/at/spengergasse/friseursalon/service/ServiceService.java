package at.spengergasse.friseursalon.service;

//import at.spengergasse.friseursalon.domain.Service;
import at.spengergasse.friseursalon.domain.Product;
import at.spengergasse.friseursalon.domain.Reservation;
import at.spengergasse.friseursalon.foundation.Base58;
import at.spengergasse.friseursalon.foundation.DateTimeFactory;
import at.spengergasse.friseursalon.persistence.ServiceRepository;
import at.spengergasse.friseursalon.service.commands.CreateServiceCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor

@Service
@Transactional(readOnly = true)

public class ServiceService {

    private final ServiceRepository serviceRepository;

    private final DateTimeFactory dateTimeFactory;
    private final Base58 keyGen;

    public List<at.spengergasse.friseursalon.domain.Service> getAllServices() {
        return serviceRepository.findAll();
    }

    @Transactional
    public at.spengergasse.friseursalon.domain.Service createService(CreateServiceCommand cmd) {
        return createService(
                cmd.serviceName());
//                cmd.productPreis(),
//                cmd.productCurrency(),
    }

    @Transactional
//    public Product createService(String serviceName, BigDecimal servicePreis, Currency serviceCurrency, String reservationKey) {
    public at.spengergasse.friseursalon.domain.Service createService(String serviceName) {

        return at.spengergasse.friseursalon.domain.Service.builder()
                .key(keyGen.randomString(at.spengergasse.friseursalon.domain.Service.KEY_LENGTH))
                .serviceName(serviceName)
                //                       .productPreis(productPreis)
                //                       .productCurrency(productCurrency)
                .creationTS(dateTimeFactory.now())
//                        .reservations(BookedProduct)
                .build()
                .addReservations(LocalDateTime.of(2022, 12, 12, 12, 30),
                        Reservation.builder()
                                .reservationName("SBTtest")
                                .build());

    }


    public List<at.spengergasse.friseursalon.domain.Service> getService(String serviceName) {
        return serviceRepository.findAll();
    }


    public Optional<at.spengergasse.friseursalon.domain.Service> findByKey(String key) {
        return serviceRepository.findByKey(key);
    }

}

