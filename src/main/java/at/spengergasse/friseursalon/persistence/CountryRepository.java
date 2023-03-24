package at.spengergasse.friseursalon.persistence;

import at.spengergasse.friseursalon.domain.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country, Long> {

    Optional<Country> findByIso2Code(String billingAddressCountryIso2Code);
}
