package at.spengergasse.friseursalon;

import at.spengergasse.friseursalon.domain.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.Locale;

public class TestFixturesUnger {

    public static Country austria = Country.builder().name("Österreich").iso2Code("AT").build();
    public static Country germany() { return  //with ()
            Country.builder().name("Deutschland").iso2Code("DE").build();}
    public static Country tuerkei = Country.builder().name("Tuerkei").iso2Code("TR").build();
    public static Country schweiz = Country.builder().name("Schweiz").iso2Code("CH").build();


    public static Address spengergasse20 = spengergasse(20);  //asagida ortak static Address Methode var
    public static Address spengergasse21 = spengergasse(21);  //asagida ortak static Address Methode var
    public static Address Istiklal = Istiklal(22);  //numarayi customer icinde yazinca buna gerek kalmadi.

    public static PhoneNumber sbtMobilePhoneNumber = PhoneNumber.builder().countryCode(43).areaCode(660).serialNumber("66082237").build();
    public static PhoneNumber aliMobilePhoneNumber = PhoneNumber.builder().countryCode(43).areaCode(660).serialNumber("12345678").build();

    public static Email sbtEmail = createSbtEmail("sbt");  //asagida ortak static Email Methode var
    public static Email aliEmail = createAliEmail("ali");  //asagida ortak static Email Methode var

    public static Customer selci (Country country) {
        return Customer.builder()
                .key("1973")
                .userName("sbt@spengergasse.at")
                .password("Leo_89042")
                .firstName("Selci")
                .lastName("BTanir")
                .billingAddress(Kircheichstr(34, country))
                .mobilePhoneNumber(sbtMobilePhoneNumber)
//                .emailAddresses(Set.of(EmailAddress.builder().address("uk@spg.at").type(EmailType.BUSINESS).build()))
                .build();
//                .build().addEmails(ukBusinessEmailAddress, weaverBusinessEmailAddress);
    }

    public static Customer sbt = Customer.builder()
            .key("uk69")
            .userName("sbt@spengergasse.at")
            .password("Leo_89042")
            .firstName("Selcuk")
            .lastName("Büyüktanir")
            .billingAddress(spengergasse20)
            .mobilePhoneNumber(sbtMobilePhoneNumber)
            .email(sbtEmail)
            .gender(Gender.MALE)
            .build();

    public static Customer amir = Customer.builder()
            .key("sbt5")
            .userName("sbt@spengergasse.at")
            .password("Leo_89042")
            .firstName("Amir")
            .lastName("Siasi")
            .billingAddress(spengergasse21)
            .mobilePhoneNumber(sbtMobilePhoneNumber)
            .email(sbtEmail)
            .gender(Gender.MALE)
            .build();

    public static Customer alia = Customer.builder()
            .key("leo5")
            .userName("sbt@spengergasse.at")
            .password("Leo_89042")
            .firstName("Ali")
            .lastName("Alidoust")
            .billingAddress(Istiklal(22))  //direk buradan adrese gidiyor.
            .mobilePhoneNumber(sbtMobilePhoneNumber)
            .email(sbtEmail)
            .gender(Gender.MALE)
            .build();

    public static Barber ali = Barber.builder()
            .userName("ali@spengergasse.at")
            .password("Leo_89042")
            .firstName("Ali")
            .lastName("Alidoust")
            .nickName("ali")
            .mobilePhoneNumber(aliMobilePhoneNumber)
            .email(aliEmail)
            .build();


    public static Product shampoo = createProduct("shampoo");
    public static Product cream = createProduct("cream");

    public static Product createProduct(String productName){  //more bookings
        return Product.builder()
                .key("8904")
                .productName(productName)
                .productPreis(BigDecimal.valueOf(14.40))
                .productCurrency(Currency.getInstance(Locale.GERMANY))
                .creationTS(LocalDateTime.now())
                .build()
//                .addReservationsBP(LocalDateTime.of(2022,12,12,12,30), createReservationBP("shampooSelci"));
                .addReservations(LocalDateTime.of(2022,12,12,12,30), shampooSelci, shampooSBT);
    }

    BookedProduct bpSelci = BookedProduct.builder()  //one booking
            .productAmount(1)
            .productTS(LocalDateTime.of(2022,12,12,12,30))  //future DateTime??
//            .reservationBP(createReservationBP("shampooSelci"))
            .reservation(shampooSelci)
            .build();

    BookedProduct bpSBT = BookedProduct.builder()  //one booking
            .productAmount(1)
            .productTS(LocalDateTime.of(2022,12,12,12,30))  //future DateTime??
//            .reservationBP(createReservationBP("shampooSBT"))
            .reservation(shampooSBT)
            .build();

//    public static Service createService(String serviceName, Double servicePreis, Currency serviceCurrency, Duration serviceDuration){
//        return createService(serviceName, servicePreis, serviceCurrency, serviceDuration); }

    public static Service haarschnitt = createService("haarschnitt");

    public static Service createService(String serviceName){   //more bookings
        return Service.builder()
                .key("4444")
                .serviceName(serviceName)
                .servicePreis(BigDecimal.valueOf(14.40))
                .serviceCurrency(Currency.getInstance(Locale.GERMANY))
                .serviceDuration(Duration.ofMinutes(30))
                .creationTS(LocalDateTime.now())
                .build()
                .addReservations(LocalDateTime.of(2022,12,12,12,30), haarschnittSelci);
    }

    BookedService bsSelci = BookedService.builder()  //one booking
            .serviceAmount(1)
            .serviceTS(LocalDateTime.of(2022,12,12,12,30))  //future DateTime??
            .barber(ali)
            .reservation(createReservation("haarschnittSelci"))
            .build();

//    public static Reservation createReservation(String reservationName, Customer customer, LocalDateTime creationTS) {
//        return createReservation(reservationName, customer, creationTS);  }


    public static Reservation shampooSelci = createReservation("shampooSelci");
    public static Reservation shampooSBT = createReservation("shampooSBT");

/*
    public static Reservation shampooSelci = Reservation.builder()
            .reservationName("shampooSelci")
            .creationTS(LocalDateTime.now())
            .customer(sbt)
            .build();

    public static Reservation shampooSBT = Reservation.builder()
            .reservationName("shampooSBT")
            .creationTS(LocalDateTime.now())
            .customer(sbt)
            .build();
*/

    public static Reservation createReservation(String reservationName) {
        return Reservation.builder()
                .key("22222")
                .reservationName(reservationName)
                .creationTS(LocalDateTime.now())
                .customer(Customer.builder().build())  //fixed customer ismi girince detached entity hatasi veriyordu.
                .build();
    }

    public static Reservation createReservation(String reservationName, Customer customer) {
        return Reservation.builder()
                .key("99999")
                .reservationName(reservationName)
                .creationTS(LocalDateTime.now())
                .customer(customer)  //fixed customer ismi girince detached entity hatasi veriyordu.
                .build();
    }

    public static Reservation createReservation1(String reservationName, Customer customer) {
        return Reservation.builder()
                .key("88888")
                .reservationName(reservationName)
                .creationTS(LocalDateTime.now())
                .customer(customer)  //fixed customer ismi girince detached entity hatasi veriyordu.
                .build();
    }

    public static Reservation createReservation2(String reservationName, Customer customer) {
        return Reservation.builder()
                .key("77777")
                .reservationName(reservationName)
                .creationTS(LocalDateTime.now())
                .customer(customer)  //fixed customer ismi girince detached entity hatasi veriyordu.
                .build();
    }

    public static Reservation createReservation3(String reservationName, Customer customer) {
        return Reservation.builder()
                .key("66666")
                .reservationName(reservationName)
                .creationTS(LocalDateTime.now())
                .customer(customer)  //fixed customer ismi girince detached entity hatasi veriyordu.
                .build();
    }

    public static Reservation haarschnittSelci = createReservation("haarschnittSelci");

    /*
    public static Reservation createReservation(String reservationName) {
        return Reservation.builder()
                .reservationName(reservationName)
                .creationTS(LocalDateTime.now())
                .customer(sbt)
                .build();
    }
*/

    private static Email createSbtEmail(String username) {
        return Email.builder().address("%s@spg.at".formatted(username)).type(EmailType.BUSINESS).build() ;
    }

    private static Email createAliEmail(String username) {
        return Email.builder().address("%s@spg.at".formatted(username)).type(EmailType.BUSINESS).build() ;
    }

    public static Address spengergasse(Integer number) {
        return Address.builder()
                .streetNumber("Spengergasse %d".formatted(number))
                .zipCode("1050")
                .city("Wien")
                .country(austria)
                .build();
    }

    public static Address Istiklal(Integer number) {
        return Address.builder()
                .streetNumber("Istiklal %d".formatted(number))
                .zipCode("34")
                .city("Istanbul")
                .country(tuerkei)  //detached entity hatasi verdigi icin yeni bir ülke tanimladim.
                .build();
    }

    public static Address Kircheichstr(Integer number, Country country) {
        return Address.builder()
                .streetNumber("Kircheichstr %d".formatted(number))
                .zipCode("52134")
                .city("Herzogenrath")
                .country(country)  //detached entity hatasi verdigi icin yeni bir ülke tanimladim.
                .build();
    }

}
