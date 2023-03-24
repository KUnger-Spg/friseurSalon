package at.spengergasse.friseursalon.persistence;

import at.spengergasse.friseursalon.TestFixtures;
import at.spengergasse.friseursalon.domain.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.Locale;
import java.util.Set;

import static at.spengergasse.friseursalon.TestFixtures.createReservation;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assumptions.assumeThat;

@Slf4j
@DataJpaTest  //remove this, when you use class with AbstractTest
@Import(TestcontainersPostgreSQLTestConfiguration.class)  //when you use this one, you can keep original class

//class AlbumRepositoryTest extends AbstractBaseDataJpaTest{

class ReservationRepositoryTest {

    @Autowired
    private ReservationRepository reservationRepository;

    @BeforeEach
    void setup() {
        assumeThat(reservationRepository).isNotNull();
    }

    @Test
    void ensureSaveAndRereadOfReservationWorksCorrectly() {

        //given
        Country aut = Country.builder().name("Österreich").iso2Code("AT").build();

        Address oahg45 = Address.builder()
                .streetNumber("Obere Amtshausgasse 45")
                .zipCode("1050")
                .city("Wien")
                .country(aut)
                .build();

        PhoneNumber mobilePhoneNumber = PhoneNumber.builder()
                .countryCode(43)
                .areaCode(660)
                .serialNumber("66082237")
                .build();

        Email sbt_spg_at = Email.builder().address("bue22177@spg.at").type(EmailType.BUSINESS).build();

        Customer sbt = Customer.builder()  //Customer.java`da @Builder girilmesi gerekiyor.
                .key("1976")
                .userName("sbt")
                .firstName("Selcuk")
                .lastName("Büyüktanir")
                .password("Leo_890")
                .billingAddress(oahg45)
                .mobilePhoneNumber(mobilePhoneNumber)
                .email(sbt_spg_at)
                .gender(Gender.MALE)
                .build();

        Barber ali = Barber.builder()
                .userName("ali")  //bu Attributes iki sefer var. Hem burda hem Customer`da.
                .firstName("Ali") //@AllArgsConstructor Barber.java`dan sildi
                .lastName("Alidoust")
                .password("ali_1050")
                .nickName("ali")
                .build();

        BookedProduct bp1 = BookedProduct.builder()
                .productAmount(1)
                .productTS(LocalDateTime.of(2022,12,12,12,30))  //future DateTime??
        //        .dateFormat("12/11/2022 12:30:00")
                .build();

        Product p1 = Product.builder()
                .key("urfa")
                .productName("Schampoo")
                .productPreis(BigDecimal.valueOf(12.50))
                .productCurrency(Currency.getInstance(Locale.GERMANY))
//                .bookedProduct(bp1)
                .build();

        BookedService bs1 = BookedService.builder()
                .serviceAmount(1)
                .serviceTS(LocalDateTime.of(2022,12,12,12,30))  //future DateTime??
        //        .dateFormat("12/11/2022 12:30:00")
                .barber(ali)
                .build();

        Service s1 = Service.builder()
                .key("amir")
                .serviceName("Haarschnitt")
                .servicePreis(BigDecimal.valueOf(18.00))
                .serviceCurrency(Currency.getInstance(Locale.GERMANY))
//                .bookedService(bs1)  //will be removed
                .serviceDuration(Duration.ofMinutes(30))
                .build();

        Reservation reservation1 = Reservation.builder()
                .key("selci")
                .reservationName("SelciHaarschnitt")
                .creationTS(LocalDateTime.now())
                .customer(sbt)
//                .product(p1)
//                .service(s1)
//                .bookedProduct(bp1)
//                .bookedService(bs1)
                .build();

        //when
        var saved1 = reservationRepository.save(reservation1);
//        var saved2 = reservationRepository.saveAndFlush(reservation2);

        //then
        assertThat(saved1).isSameAs(reservation1);
        assertThat(saved1.getId()).isNotNull();

//        assertThat(saved2).isSameAs(reservation2);
//        assertThat(saved2.getId()).isNotNull();

    }

    @Disabled  //ignore test
    @Test
    void ensureReadingReservationInfoDTOWorksCorrectly() {

        Email amir_spg_at = Email.builder().address("sia22813@spg.at").type(EmailType.BUSINESS).build();

        PhoneNumber amirPhoneNumber = PhoneNumber.builder()
                .countryCode(43)
                .areaCode(660)
                .serialNumber("1234567")
                .build();

        Address spg20 = Address.builder()
                .streetNumber("Spengergasse 20")
                .zipCode("1050")
                .city("Aachen")
                .country(TestFixtures.germany)  //austria gives detached entity failure
                .build();

        //given /arrange
        Customer amir = Customer.builder()
                .key("2023")
                .userName("amir")  //sia22813@spg.at
                .password("Leo_890")
                .firstName("Amir")
                .lastName("Siasi")
                .billingAddress(spg20)
                .mobilePhoneNumber(amirPhoneNumber)
                .email(amir_spg_at)
                .gender(Gender.MALE)
                .build();

        Reservation reservation2 = Reservation.builder()
                .key("abcd5")
                .reservationName("SBT Haarschnitt")
//                .reservationName("AmirHaarschnitt")
                .creationTS(LocalDateTime.now())
//                .customer(TestFixtures.amir)  //gives detached entity country failure
                .customer(amir)
//                .product(p1)
//                .service(s1)
//                .bookedProduct(bp1)
//                .bookedService(bs1)
                .build();

    //    Reservation reservation3 = createReservation("AmirHaarschnitt");

    //    reservationRepository.saveAndFlush(reservation3);
        reservationRepository.save(reservation2);

        //when /act
        var reservationInfo = reservationRepository.findAllByReservationNameLike("%Haarschnitt%");

        //then /assert
        assertThat(reservationInfo).isNotNull().isNotEmpty();
        assertThat(reservationInfo.get(0).reservationName()).isEqualTo("SBT Haarschnitt");
//        assertThat(reservationInfo.get(1).reservationName()).isEqualTo("AmirHaarschnitt");

    }

}
