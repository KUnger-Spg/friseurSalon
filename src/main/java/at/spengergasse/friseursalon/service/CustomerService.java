package at.spengergasse.friseursalon.service;

import at.spengergasse.friseursalon.domain.Address;
import at.spengergasse.friseursalon.domain.Customer;
import at.spengergasse.friseursalon.domain.Reservation;
import at.spengergasse.friseursalon.foundation.Base58;
import at.spengergasse.friseursalon.foundation.DateTimeFactory;
import at.spengergasse.friseursalon.persistence.CountryRepository;
import at.spengergasse.friseursalon.persistence.CustomerRepository;
import at.spengergasse.friseursalon.persistence.ReservationRepository;
import at.spengergasse.friseursalon.service.commands.CreateCustomerCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)

public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CountryRepository countryRepository;
    private final Base58 base58;

    @Transactional
    public Customer createCustomer(CreateCustomerCommand cmd) {
        return createCustomer(
                cmd.userName(),
                cmd.password(),
                cmd.firstName(),
                cmd.lastName(),
                cmd.billingAddressStreetNumber(),
                cmd.billingAddressZipCode(),
                cmd.billingAddressCity(),
                cmd.billingAddressCountryIso2Code());
    }

    @Transactional
    public Customer createCustomer(String userName, String password, String firstName, String lastName,
                                   String billingAddressStreetNumber, String billingAddressZipCode,
                                   String billingAddressCity, String billingAddressCountryIso2Code) {

        return countryRepository.findByIso2Code(billingAddressCountryIso2Code)
                .map(country -> Customer.builder()
                        .key(base58.randomString(Customer.KEY_LENGTH))
                        .userName(userName)
                        .password(password)
                        .firstName(firstName)
                        .lastName(lastName)
                        .billingAddress(Address.builder()
                                .streetNumber(billingAddressStreetNumber)
                                .zipCode(billingAddressZipCode)
                                .city(billingAddressCity)
                                .country(country)
                                .build())
                        .build())
                .map(customerRepository::save)
                .orElseThrow();

    }

    public List<Customer> getAllCustomers() {

        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomer(Long id) {

        return customerRepository.findById(id);
    }

    public Optional<Customer> getCustomer(String key) {

        return customerRepository.findByKey(key);

    }
}