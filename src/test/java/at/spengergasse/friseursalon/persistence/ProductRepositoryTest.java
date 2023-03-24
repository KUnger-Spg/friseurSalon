package at.spengergasse.friseursalon.persistence;

import at.spengergasse.friseursalon.TestFixtures;
import at.spengergasse.friseursalon.domain.BookedProduct;
import at.spengergasse.friseursalon.domain.Customer;
import at.spengergasse.friseursalon.domain.Product;
import at.spengergasse.friseursalon.domain.Reservation;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

import static at.spengergasse.friseursalon.TestFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assumptions.assumeThat;


@Slf4j
@DataJpaTest  //remove this, when you use class with AbstractTest
//@Import(TestcontainersPostgreSQLTestConfiguration.class)  //when you use this one, you can keep original class

//class AlbumRepositoryTest extends AbstractBaseDataJpaTest{

class ProductRepositoryTest {

    @Autowired  //Autowired kapatinca Unit Testleri ignore etti.
    private ProductRepository productRepository;
    private Product p1;
    private Product p2;
    private Product p3;

    private Reservation rp1;
    private Reservation rp2;
    private Reservation rp3;

    @BeforeEach
    void setup() { assumeThat(productRepository).isNotNull(); }

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


    //    @Disabled
    @Test
    void ensureRetrievalOfProductInfoDTOWorksCorrectly() {

        Reservation rp3 = Reservation.builder()
                .key("jkmn")
                .reservationName("forAli")
                .creationTS(LocalDateTime.now())
                .customer(alia)  //detached entity passed to persist hatasi veriyordu. yeni bir customer tanimladik
                //: at.spengergasse.friseursalon.domain.Customer
                //yani customer kullandin, tekrar kullanma diyor.
                .build();

//        Reservation rp1 = createReservation("shampooSelcuk1");
//        Reservation rp2 = createReservation("shampooSelcuk2");

        p3 = Product.builder()
                .key("efgh")
                .productName("shampoo")
                .productPreis(BigDecimal.valueOf(14.90))
                .productCurrency(Currency.getInstance(Locale.GERMANY))
                .creationTS(LocalDateTime.now())
                .build()
//                .addReservationsBP(LocalDateTime.of(2022,12,12,12,30), createReservationBP("shampooSelci"));
                .addReservations(LocalDateTime.of(2022,12,12,12,30), rp3);

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

        p3.insertReservation(LocalDateTime.now(),rp3);  //productTS
        productRepository.save(p3);

        //when /act
        var productInfo = productRepository.findAllByProductNameLike("%shampo%");

        //then /assert
        assertThat(productInfo).isNotNull().isNotEmpty();
        assertThat(productInfo.get(0).productName()).isEqualTo("shampoo");

    }

//    @Disabled
    @Test
    void ensureSaveOfProductWithMultipleReservationsWorksCorrectly() {

        Reservation rp1 = Reservation.builder()
                .reservationName("forSelcuk")
                .creationTS(LocalDateTime.now())
                .customer(sbt)  //detached entity passed to persist hatasi veriyor.
                                   //: at.spengergasse.friseursalon.domain.Customer
                                   //yani customer kullandin, tekrar kullanma diyor.
                .build();

//        Reservation rp1 = createReservation("shampooSelcuk1");
//        Reservation rp2 = createReservation("shampooSelcuk2");

        p1 = Product.builder()
                .productName("shampoo")
                .productPreis(BigDecimal.valueOf(14.90))
                .productCurrency(Currency.getInstance(Locale.GERMANY))
                .creationTS(LocalDateTime.now())
                .build()
//                .addReservationsBP(LocalDateTime.of(2022,12,12,12,30), createReservationBP("shampooSelci"));
                .addReservations(LocalDateTime.of(2022,12,12,12,30), rp1);

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

        p1.insertReservation(LocalDateTime.now(),rp1);  //productTS
        productRepository.save(p1);

    }

//    @Disabled
    @Test
    void ensureSaveAndRereadOfProductWorksCorrectly() {

        Reservation rp2 = Reservation.builder()
                .reservationName("forAmir")
                .creationTS(LocalDateTime.now())
                .customer(amir)
                .build();

        p2 = Product.builder()
                .productName("shampoo2")
                .productPreis(BigDecimal.valueOf(14.90))
                .productCurrency(Currency.getInstance(Locale.GERMANY))
                .creationTS(LocalDateTime.now())
                .build()  //cogul kullanim durumunu cözmek gerek..
//                .addReservationBP(LocalDateTime.of(2022,12,12,12,30), TestFixtures.shampooSelci);
                .addReservations(LocalDateTime.of(2022, 12, 12, 12, 30), TestFixtures.shampooSelci, TestFixtures.shampooSBT);
//                .addReservations(LocalDateTime.of(2022, 12, 12, 12, 30), rp2);

        /*
        BookedProduct bpSelci = BookedProduct.builder()  //one booking
                .productAmount(1)
                .productTS(LocalDateTime.of(2022, 12, 12, 12, 30))  //future DateTime??
                .reservation(shampooSelci)
                .build();

        BookedProduct bpSBT = BookedProduct.builder()  //one booking
                .productAmount(1)
                .productTS(LocalDateTime.of(2022, 12, 12, 12, 30))  //future DateTime??
                .reservation(TestFixtures.shampooSBT)
                .build();
*/

        //insert sonradan ekledim
//        shampoo.insertReservation(LocalDateTime.now(), shampooSelci);  //productTS
//        shampoo.insertReservation(LocalDateTime.now(), shampooSBT);
        productRepository.save(p2);
//        productRepository.save(shampoo);

/*
//        var saved = productRepository.saveAndFlush(TestFixtures.createReservationBP("shampooSBT"));
        var saved1 = productRepository.saveAndFlush(shampoo);  //buradaki tekil
        var saved2 = productRepository.saveAndFlush(TestFixtures.shampoo);  //buradaki tekil

//        assertThat(saved1).isSameAs(shampoo);  //assert Problem. burada product-shampoo girili ama yukarda reservation
        assertThat(saved1).isSameAs(shampoo);
        assertThat(saved1.getId()).isNotNull();
//        assertThat(saved2).isSameAs(shampoo);
        assertThat(saved2).isSameAs(TestFixtures.shampoo);
        assertThat(saved2.getId()).isNotNull();

 */
    }
}

