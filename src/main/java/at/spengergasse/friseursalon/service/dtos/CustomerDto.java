package at.spengergasse.friseursalon.service.dtos;

import at.spengergasse.friseursalon.domain.Customer;

public record CustomerDto(String userName, String password, String firstName, String lastName, AddressDto billingAddress) {

    public CustomerDto(Customer customer){
        this(customer.getUserName(),
                customer.getPassword(),
                customer.getFirstName(),
                customer.getLastName(),
                new AddressDto(customer.getBillingAddress()));
    }
}
