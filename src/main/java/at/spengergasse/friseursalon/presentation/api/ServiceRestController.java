package at.spengergasse.friseursalon.presentation.api;


import at.spengergasse.friseursalon.domain.Service;
import at.spengergasse.friseursalon.service.ServiceService;
import at.spengergasse.friseursalon.service.dtos.ServiceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor

@RestController
//@RequestMapping( "/api/services")
@RequestMapping( ApiConstants.API+"/services")

public class ServiceRestController {

    private final ServiceService serviceService;


    @GetMapping({ "", "/"})
    public HttpEntity<List<ServiceDto>> getAllServices() {
        return ResponseEntity.ok(serviceService.getAllServices()
                .stream()
                .map(ServiceDto::new)
                .toList()) ;
    }

/*
    @GetMapping("/{key}")
    public HttpEntity<ServiceDto> getService(@PathVariable String key) {
        return serviceService.getService(key)
                .map(ServiceDto::new)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
 */


    @GetMapping("/")
    public List<Service> getService(@PathVariable String serviceName) {
        return serviceService.getService(serviceName);
//                .map(ServiceDto::new)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());

    }

/*
    @PostMapping( { "", "/"} )
    public HttpEntity<ServiceDto> createService(@RequestBody CreateServiceCommand createServiceCommand) {

        return Optional.ofNullable(serviceService.createService(createServiceCommand.serviceName()))
//                        createServiceCommand.reservationKey()))
                .map(ServiceDto::new)
                .map(dto -> ResponseEntity.created(createServiceUri(dto)).body(dto))
                .orElse(ResponseEntity.noContent().build());

    private URI createServiceUri (ServiceDto dto){
            try {
                String key = URLEncoder.encode(dto.serviceName(), StandardCharsets.UTF_8);
                return new URI("/api/services/%s".formatted(key));
            } catch (URISyntaxException uriSyntaxEx) {
                throw new RuntimeException(uriSyntaxEx);
            }
        }
    }
*/

}
