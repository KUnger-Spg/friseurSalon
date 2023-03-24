package at.spengergasse.friseursalon.service.dtos;

import at.spengergasse.friseursalon.domain.Address;

public record AddressDto(String streetNumber, String zipCodeCity, CountryDto country) {
    public AddressDto(Address address) {
        this(address.getStreetNumber(),
                "%s %s".formatted(address.getZipCode(),address.getCity()),
                new CountryDto(address.getCountry()));
    }
}
