package at.spengergasse.friseursalon.presentation.api;

import at.spengergasse.friseursalon.service.CustomerService;
import at.spengergasse.friseursalon.service.commands.CreateCustomerCommand;
import at.spengergasse.friseursalon.service.dtos.CustomerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@RequiredArgsConstructor

@RestController
//@RequestMapping( "/api/customers")
@RequestMapping( ApiConstants.API+"/customers")

public class CustomerRestController {

    private final CustomerService customerService;

/*
   @PostMapping( { "", "/"} )
    public HttpEntity<CustomerDto> createCustomer(@Valid @RequestBody CreateCustomerCommand cmd) {

        Customer customer = customerService.createCustomer(cmd);
        URI location = linkTo(methodOn(CustomerRestController.class).getCustomer(customer.getKey())).withSelfRel().toUri();
        CustomerDto dto = new CustomerDto(customer);
        return ResponseEntity.created(location).body(dto);

   }
*/

    @PostMapping( { "", "/"} )
    public HttpEntity<CustomerDto> createCustomer(@RequestBody CreateCustomerCommand createCustomerCommand) {

        return Optional.ofNullable(customerService.createCustomer(createCustomerCommand.userName(),
                        createCustomerCommand.password(),
                        createCustomerCommand.firstName(),
                        createCustomerCommand.lastName(),
                        createCustomerCommand.billingAddressStreetNumber(),
                        createCustomerCommand.billingAddressZipCode(),
                        createCustomerCommand.billingAddressCity(),
                        createCustomerCommand.billingAddressCountryIso2Code()))
                .map(CustomerDto::new)
                .map(dto -> ResponseEntity.created(createCustomerUri(dto)).body(dto))
                .orElse(ResponseEntity.noContent().build());
    }

    @GetMapping("/{key}")
    public HttpEntity<CustomerDto> getCustomer(@PathVariable String key) {
        return customerService.getCustomer(key)
                .map(CustomerDto::new)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    private URI createCustomerUri(CustomerDto dto) {
        try {
            String key = URLEncoder.encode(dto.userName(), StandardCharsets.UTF_8);
            return new URI("/api/customers/%s".formatted(key));
        } catch (URISyntaxException uriSyntaxEx) {
            throw new RuntimeException(uriSyntaxEx);
        }
    }
}
