package at.spengergasse.friseursalon.service.dtos;

import at.spengergasse.friseursalon.domain.Reservation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
@Relation(collectionRelation = "reservations", itemRelation = "reservation")
public class ReservationDto extends RepresentationModel<ReservationDto> {
    private final String reservationName;
    private final LocalDateTime createdAt;
    private final String customer;
    private final CustomerDto customerDto;

//    private final ProductDto productDto;
//    private final ServiceDto serviceDto;


    public ReservationDto(Reservation reservation) {
        this(reservation.getReservationName(), reservation.getCreationTS(),
                reservation.getCustomer().getDisplayName(), new CustomerDto(reservation.getCustomer()));

    }
}

