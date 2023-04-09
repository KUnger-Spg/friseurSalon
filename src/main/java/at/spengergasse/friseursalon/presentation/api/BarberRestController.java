package at.spengergasse.friseursalon.presentation.api;

import at.spengergasse.friseursalon.service.BarberService;
import at.spengergasse.friseursalon.service.CustomerService;
import at.spengergasse.friseursalon.service.commands.CreateBarberCommand;
import at.spengergasse.friseursalon.service.commands.CreateCustomerCommand;
import at.spengergasse.friseursalon.service.dtos.BarberDto;
import at.spengergasse.friseursalon.service.dtos.CustomerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor

@RestController
//@RequestMapping( "/api/customers")
@RequestMapping( ApiConstants.API+"/barbers")

public class BarberRestController {

    private final BarberService barberService;

    @GetMapping({ "", "/"})
    public HttpEntity<List<BarberDto>> getAllBarbers() {
        return ResponseEntity.ok(barberService
                .getAllBarbers()
                .stream()
                .map(BarberDto::new)
                .toList());
    }

    @PostMapping( { "", "/"} )
    public HttpEntity<BarberDto> createBarber(@RequestBody CreateBarberCommand createBarberCommand) {

        return Optional.ofNullable(barberService.createBarber(
                        createBarberCommand.nickName(),
                        createBarberCommand.firstName(),
                        createBarberCommand.lastName()))
                .map(BarberDto::new)
                .map(dto -> ResponseEntity.created(createBarberUri((BarberDto) dto)).body(dto))
                .orElse(ResponseEntity.noContent().build());
    }

    private URI createBarberUri(BarberDto dto) {
        try {
            String key = URLEncoder.encode(dto.nickName(), StandardCharsets.UTF_8);
            return new URI("/api/barbers/%s".formatted(key));
        } catch (URISyntaxException uriSyntaxEx) {
            throw new RuntimeException(uriSyntaxEx);
        }
    }

}


