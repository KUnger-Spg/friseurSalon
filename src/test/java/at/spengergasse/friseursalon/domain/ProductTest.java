package at.spengergasse.friseursalon.domain;

import org.junit.jupiter.api.Test;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.node.DecimalNode;

import java.text.DecimalFormat;
import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void ensureGetProductCountForWorksProperly() {

        //given
        Product product = new Product();
        Predicate<Reservation> productReservationsWithSchampoo = (p) -> p.getReservationName() == "Schampoo";

        //when
        Long count = product.getReservationCountFor(productReservationsWithSchampoo);

        //then
        assertThat(count).isEqualTo(0);
    }

}