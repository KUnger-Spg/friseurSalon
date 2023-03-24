package at.spengergasse.friseursalon.service.dtos;

import at.spengergasse.friseursalon.domain.Country;

public record CountryDto(
        String name,
        String iso2Code
) {
    public CountryDto(Country country) {

        this(country.getName(), country.getIso2Code());
    }
}
