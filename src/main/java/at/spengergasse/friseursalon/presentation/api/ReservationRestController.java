package at.spengergasse.friseursalon.presentation.api;

import at.spengergasse.friseursalon.domain.Customer;
import at.spengergasse.friseursalon.domain.Reservation;
import at.spengergasse.friseursalon.persistence.CustomerRepository;
import at.spengergasse.friseursalon.persistence.ReservationRepository;
import at.spengergasse.friseursalon.service.commands.CreateReservationCommand;
import at.spengergasse.friseursalon.service.ReservationService;
import at.spengergasse.friseursalon.service.dtos.CustomerDto;
import at.spengergasse.friseursalon.service.dtos.ReservationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequiredArgsConstructor

@RestController
// @RequestMapping( "/api/reservations")  //ApiConstants altinda API = "/api" diye eslestirdi.
// public class ApiConstants {public static final String API = "/api";}
@RequestMapping( ApiConstants.API+"/reservations")

public class ReservationRestController {

    private final ReservationService reservationService;
    private final ReservationRepository reservationRepository;
    private final CustomerRepository customerRepository;

    /*
    @GetMapping({ "", "/"})
    public HttpEntity<List<ReservationDto>> getAllReservations() {
        return ResponseEntity.ok(reservationService
                .getAllReservations()
                .stream()
                .map(ReservationDto::new)
                .toList());
    }
*/
        @GetMapping({ "", "/"})  //?expand=customer; customer.address
        public HttpEntity<CollectionModel<ReservationDto>> getAllReservations() {

        List<ReservationDto> dtoList = reservationService.getAllReservations().stream().map(this::toDto).toList();
        if (dtoList.isEmpty()) return ResponseEntity.noContent().build();
        CollectionModel<ReservationDto> reservationDtos = CollectionModel.of(dtoList).add(createCollectionLink());

        return ResponseEntity.ok(reservationDtos);
        }

        @PostMapping( { "", "/"} )
    public HttpEntity<ReservationDto> createReservation(@Valid @RequestBody CreateReservationCommand cmd) {

        Reservation reservation = reservationService.createReservation(cmd);
        ReservationDto dto = toDto(reservation);
        Link _self = dto.getLink(IanaLinkRelations.SELF).get();

//        URI location = linkTo(methodOn(ReservationRestController.class).getReservation(reservation.getKey())).withSelfRel().toUri();

        return ResponseEntity.created(_self.toUri()).body(dto);
   }

   /*
    @PostMapping( { "", "/"} )
    public HttpEntity<ReservationDto> createReservation(@RequestBody CreateReservationCommand createReservationCommand) {

        return Optional.ofNullable(reservationService.createReservation(createReservationCommand.reservationName(),
                        createReservationCommand.customerKey()))
                .map(ReservationDto::new)
                .map(dto -> ResponseEntity.created(createReservationUri(dto)).body(dto))
                .orElse(ResponseEntity.noContent().build());
    }
*/

    @GetMapping( "/{key}")
    public HttpEntity<ReservationDto> getReservation(@PathVariable String key) {
//        return reservationService.findReservationByReservationName(key)
        return reservationService.findByKey(key)
                .map(this::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping( "/{key}")
    public HttpEntity<ReservationDto> updateReservationName(@PathVariable String key, @RequestParam String reservationName) {
        return reservationService.updateReservationName(key, reservationName)
                .map(this::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

protected ReservationDto toDto (Reservation reservation) {
        var _self = linkTo(methodOn(ReservationRestController.class).getReservation(reservation.getKey()));
        return new ReservationDto(reservation)
        .add(_self.withSelfRel())
        .add(_self.slash("customer").withRel("customer-nested"))
                .add(_self.slash("product").withRel("product-nested"))
                .add(_self.slash("service").withRel("service-nested"))
                .add(_self.slash("barber").withRel("barber-nested"))
//                .add(linkTo(methodOn(ProductRestController.class).getProduct(reservation.getProduct().getKey())).withRel("product"))
//                .add(linkTo(methodOn(ServiceRestController.class).getService(reservation.getService().getKey())).withRel("service"))
//                .add(linkTo(methodOn(BarberRestController.class).getBarber(reservation.getBarber().getKey())).withRel("barber"))
        .add(linkTo(methodOn(CustomerRestController.class).getCustomer(reservation.getCustomer().getKey())).withRel("customer"))
        .add(createCollectionLink());
        }

private Link createCollectionLink() {
        return linkTo(methodOn(ReservationRestController.class).getAllReservations()).withRel(IanaLinkRelations.COLLECTION);
        }

    /*
    private URI createReservationUri(ReservationDto dto) {
        try {
            String key = URLEncoder.encode(dto.reservationName(), StandardCharsets.UTF_8);
            return new URI("/api/reservations/%s".formatted(key));
        } catch (URISyntaxException uriSyntaxEx) {
            throw new RuntimeException(uriSyntaxEx);
        }
    }
*/

}
