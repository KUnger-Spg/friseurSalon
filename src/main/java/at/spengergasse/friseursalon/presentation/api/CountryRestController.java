package at.spengergasse.friseursalon.presentation.api;

import at.spengergasse.friseursalon.service.CountryService;
import at.spengergasse.friseursalon.service.dtos.CountryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RequiredArgsConstructor

@RestController
//@RequestMapping( "/api/countries")
@RequestMapping( ApiConstants.API+"/countries")

public class CountryRestController {

        private final CountryService countryService;

        @GetMapping({ "", "/"})
        public HttpEntity<List<CountryDto>> getAllCountries() {
            return ResponseEntity.ok(countryService.getAllCountries()
                    .stream()
                    .map(CountryDto::new)
                    .toList()) ;
        }
}
