package at.spengergasse.friseursalon.domain;

import org.junit.jupiter.api.Test;

import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ServiceTest {

    @Test
    void ensureGetServiceCountForWorksProperly() {

        //given
        Service service = new Service();
        Predicate<Reservation> serviceReservationsWithHaarschnitt = (p) -> p.getReservationName() == "Haarschnitt";

        //when
        Long count = service.getReservationCountFor(serviceReservationsWithHaarschnitt);

        //then
        assertThat(count).isEqualTo(0);
    }


}