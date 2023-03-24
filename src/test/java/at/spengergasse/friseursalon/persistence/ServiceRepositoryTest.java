package at.spengergasse.friseursalon.persistence;

import at.spengergasse.friseursalon.TestFixtures;
import at.spengergasse.friseursalon.TestFixturesUnger;
import at.spengergasse.friseursalon.domain.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.Locale;

import static at.spengergasse.friseursalon.TestFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assumptions.assumeThat;


@Slf4j
@DataJpaTest  //remove this, when you use class with AbstractTest
//@Import(TestcontainersPostgreSQLTestConfiguration.class)  //when you use this one, you can keep original class

//class AlbumRepositoryTest extends AbstractBaseDataJpaTest{

class ServiceRepositoryTest {

    @Autowired
    private ServiceRepository serviceRepository;
    private Service service;

    private Reservation rs1;

    private Reservation rs2;

    private Reservation rs3;

    @BeforeEach
    void setup() { assumeThat(serviceRepository).isNotNull(); }

    /*
    void setup() {
        assumeThat(productRepository).isNotNull();

        Reservation r1 = createReservationBP("shampooSelcuk1");
        Reservation r2 = createReservationBP("shampooSelcuk2");

        product = Product.builder()
                .productName("shampoo")
                .productPreis(15.10)
                .productCurrency(Currency.getInstance(Locale.GERMANY))
                .creationTS(LocalDateTime.now())
                .build()
//                .addReservationsBP(LocalDateTime.of(2022,12,12,12,30), createReservationBP("shampooSelci"));
                .addReservationsBP(LocalDateTime.of(2022,12,12,12,30), r1, r2);

        var saved3 = productRepository.save(r1);

        assertThat(saved3).isSameAs(r1);
        assertThat(saved3.getId()).isNotNull();
        assertThat(saved3.getVersion()).isNotNull();
        List.of(r1,r2).forEach(reservation -> {
            assertThat(reservation.getId()).isNotNull();
            assertThat(reservation.getVersion()).isNotNull();
        });

        assertThat(sbt.getId()).isNotNull();
        assertThat(sbt.getVersion()).isNotNull();
        assertThat(austria.getId()).isNotNull();
    }
*/

    @Test
    void ensureSaveOfServiceWithMultipleReservationsWorksCorrectly() {

        Reservation rs1 = createReservation("haarschnittSelcuk1");
        Reservation rs2 = createReservation("haarschnittSelcuk2");

        service = Service.builder()
                .serviceName("haarschnitt")
                .servicePreis(BigDecimal.valueOf(19.90))
                .serviceCurrency(Currency.getInstance(Locale.GERMANY))
                .serviceDuration(Duration.ofMinutes(30))
                .creationTS(LocalDateTime.now())
                .build()
//                .addReservationsBP(LocalDateTime.of(2022,12,12,12,30), createReservationBP("shampooSelci"));
                .addReservations(LocalDateTime.of(2022,12,12,12,30), rs1, rs2);

        /*
        var saved3 = productRepository.save(product);

        assertThat(saved3).isSameAs(product);
        assertThat(saved3.getId()).isNotNull();
        assertThat(saved3.getVersion()).isNotNull();
        List.of(r1,r2).forEach(reservationBP -> {
            assertThat(reservationBP.getId()).isNotNull();
            assertThat(reservationBP.getVersion()).isNotNull();
        });

        assertThat(sbt.getId()).isNotNull();
        assertThat(sbt.getVersion()).isNotNull();
        assertThat(austria.getId()).isNotNull();
*/

        service.insertReservation(LocalDateTime.now(),rs1);  //productTS
        service.insertReservation(LocalDateTime.now(),rs2);  //productTS
        serviceRepository.save(service);

    }

//    @Disabled
    @Test
    void ensureSaveAndRereadOfServiceWorksCorrectly() {

        Service haarschnitt = Service.builder()
                .serviceName("haarschnitt")
                .servicePreis(BigDecimal.valueOf(19.90))
                .serviceCurrency(Currency.getInstance(Locale.GERMANY))
                .serviceDuration(Duration.ofMinutes(30))
                .creationTS(LocalDateTime.now())
                .build()
//                .addReservationBP(LocalDateTime.of(2022,12,12,12,30), TestFixtures.shampooSelci);
                .addReservations(LocalDateTime.of(2022, 12, 12, 12, 30), haarschnittSelci);

        BookedService bsSelci = BookedService.builder()  //one booking
                .serviceAmount(1)
                .barber(ali)
                .serviceTS(LocalDateTime.of(2022, 12, 12, 12, 30))  //future DateTime??
                .reservation(haarschnittSelci)
                .build();

        BookedService bsSBT = BookedService.builder()  //one booking
                .serviceAmount(1)
                .barber(ali)
                .serviceTS(LocalDateTime.of(2022, 12, 12, 12, 30))  //future DateTime??
                .reservation(haarschnittSelci)
                .build();

        //insert sonradan ekledim
        haarschnitt.insertReservation(LocalDateTime.now(), haarschnittSelci);  //productTS
//        shampoo.insertReservationBP(LocalDateTime.now(), shampooSBT);
        serviceRepository.save(haarschnitt);
//        productRepository.save(shampoo);

/*
//        var saved = productRepository.saveAndFlush(TestFixtures.createReservationBP("shampooSBT"));
        var saved1 = serviceRepository.saveAndFlush(TestFixtures.haarschnitt);  //buradaki tekil
//        var saved2 = productRepository.saveAndFlush(TestFixtures.shampoo);  //buradaki tekil

//        assertThat(saved1).isSameAs(shampoo);  //assert Problem. burada product-shampoo girili ama yukarda reservation
        assertThat(saved1).isSameAs(TestFixtures.haarschnitt);
        assertThat(saved1.getId()).isNotNull();
//        assertThat(saved2).isSameAs(shampoo);
//        assertThat(saved2).isSameAs(TestFixtures.shampoo);
//        assertThat(saved2.getId()).isNotNull();

 */
    }

    //    @Disabled
    @Test
    void ensureRetrievalOfServiceInfoDTOWorksCorrectly() {

        Reservation rs3 = Reservation.builder()
                .key("prst")
                .reservationName("forSBT")
                .creationTS(LocalDateTime.now())
                .customer(TestFixturesUnger.selci(TestFixturesUnger.germany()))  //detached entity passed to persist hatasi veriyordu. yeni bir customer tanimladik
                //: at.spengergasse.friseursalon.domain.Customer   //detached entity passed to persist hatasi veriyordu. yeni bir country tanimladik
                //yani customer kullandin, tekrar kullanma diyor.
                .build();

//        Reservation rp1 = createReservation("shampooSelcuk1");
//        Reservation rp2 = createReservation("shampooSelcuk2");

        Service service3 = Service.builder()
                .key("pfst")
                .serviceName("haarschnitt")
                .servicePreis(BigDecimal.valueOf(14.90))
                .serviceCurrency(Currency.getInstance(Locale.GERMANY))
                .serviceDuration(Duration.ofMinutes(30))
                .creationTS(LocalDateTime.now())
                .build()
//                .addReservationsBP(LocalDateTime.of(2022,12,12,12,30), createReservationBP("shampooSelci"));
                .addReservations(LocalDateTime.of(2022, 12, 12, 12, 30), rs3);

        /*
        var saved3 = productRepository.save(product);

        assertThat(saved3).isSameAs(product);
        assertThat(saved3.getId()).isNotNull();
        assertThat(saved3.getVersion()).isNotNull();
        List.of(r1,r2).forEach(reservationBP -> {
            assertThat(reservationBP.getId()).isNotNull();
            assertThat(reservationBP.getVersion()).isNotNull();
        });

        assertThat(sbt.getId()).isNotNull();
        assertThat(sbt.getVersion()).isNotNull();
        assertThat(austria.getId()).isNotNull();
*/

        service3.insertReservation(LocalDateTime.now(),rs3);  //productTS
        serviceRepository.save(service3);

        //when /act
        var serviceInfo = serviceRepository.findAllByServiceNameLike("%haar%");

        //then /assert
        assertThat(serviceInfo).isNotNull().isNotEmpty();
        assertThat(serviceInfo.get(0).serviceName()).isEqualTo("haarschnitt");

    }

}

