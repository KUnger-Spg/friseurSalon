package at.spengergasse.friseursalon.persistence;

import at.spengergasse.friseursalon.TestFixtures;
import at.spengergasse.friseursalon.TestFixturesUnger;
import at.spengergasse.friseursalon.domain.Country;
import at.spengergasse.friseursalon.domain.Customer;
import at.spengergasse.friseursalon.domain.Product;
import at.spengergasse.friseursalon.domain.Reservation;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

import static at.spengergasse.friseursalon.TestFixtures.*;
import static at.spengergasse.friseursalon.TestFixturesUnger.germany;
import static at.spengergasse.friseursalon.TestFixturesUnger.selci;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assumptions.assumeThat;


@Slf4j
@DataJpaTest  //remove this, when you use class with AbstractTest
@Import(TestcontainersPostgreSQLTestConfiguration.class)  //when you use this one, you can keep original class

//class AlbumRepositoryTest extends AbstractBaseDataJpaTest{

class ProductRepositoryTestUnger {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomerRepository customerRepository;

    private Product p1;
    private Product p2;
    private Product p3;

    private Product product;

    private Reservation reservation;
    private Reservation rp1;
    private Reservation rp2;
    private Reservation rp3;
    private Reservation rp4;

    @BeforeEach
    void setup() { assumeThat(productRepository).isNotNull();

        Country germany = germany();
        Customer selci = selci(germany);

        rp1 = TestFixturesUnger.createReservation1("rp1", selci);
        rp2 = TestFixturesUnger.createReservation2("rp2", selci);
        rp3 = TestFixturesUnger.createReservation3("rp3", selci);

        product = Product.builder()
                .key("ssbt")
                .productName("shampoo")
                .productPreis(BigDecimal.valueOf(14.90))
                .productCurrency(Currency.getInstance(Locale.GERMANY))
                .creationTS(LocalDateTime.now())
                .build()
//                .addReservationsBP(LocalDateTime.of(2022,12,12,12,30), createReservationBP("shampooSelci"));
                .addReservations(LocalDateTime.of(2022,12,12,12,30), rp1, rp2, rp3);

        var saved = productRepository.saveAndFlush(product);

// then / assert

        assertThat(saved).isSameAs(product);
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getVersion()).isNotNull();
        List.of(rp1, rp2, rp3).forEach(reservation -> {
            assertThat(reservation.getId()).isNotNull();
            assertThat(reservation.getVersion()).isNotNull();
        });
        assertThat(selci.getId()).isNotNull();
        assertThat(selci.getVersion()).isNotNull();
        assertThat(germany.getId()).isNotNull();

    }


    @Test
    void ensureSaveOfProductWithInsertedReservationsWorksCorrectly() {

        //given
        Customer selci = customerRepository.findByUserName("sbt@spengergasse.at").orElseThrow();
        rp4 = TestFixturesUnger.createReservation("rp4", selci);
//        rp4 = TestFixtures.createReservation("rp4");

        //expect
        product.insertReservation(LocalDateTime.now(), rp4);
        productRepository.saveAndFlush(product);
    }


//        @Disabled
    @Test
    void ensureRetrievalOfProductInfoDTOWorksCorrectly() {

        //given
        Customer selci = customerRepository.findByUserName("sbt@spengergasse.at").orElseThrow();
        rp4 = TestFixturesUnger.createReservation("rp4", selci);

        product.insertReservation(LocalDateTime.now(), rp4);
        productRepository.saveAndFlush(product);

        //when /act
        var productInfo = productRepository.findAllByProductNameLike("%shampo%");

        //then /assert
        assertThat(productInfo).isNotNull().isNotEmpty();
        assertThat(productInfo.get(0).productName()).isEqualTo("shampoo");

    }

//    @Disabled
    @Test
    void ensureSaveOfProductWithAddedReservationsWorksCorrectly() {

        //given
        Customer selci = customerRepository.findByUserName("sbt@spengergasse.at").orElseThrow();
        rp4 = TestFixturesUnger.createReservation("rp4", selci);

        reservation = Reservation.builder()
                .key("55555")
                .reservationName("SelciHaarschnitt")
                .creationTS(LocalDateTime.now())
                .customer(selci)
                .build();

//        product.addReservation(LocalDateTime.now(), rp4);
        product.addReservation(LocalDateTime.now(), reservation);
        productRepository.saveAndFlush(product);

    }
}

