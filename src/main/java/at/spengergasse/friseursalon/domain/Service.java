package at.spengergasse.friseursalon.domain;

import lombok.*;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder

@Entity
@Table(name="services")  //Database Table creation

public class Service extends AbstractPersistable<Long> implements KeyHolder{

    public static final int KEY_LENGTH = 4;

    @NotNull
    @Column(length = KEY_LENGTH, unique = true)
    private String key;

    @NotNull
    @Version
    private Integer version;

    @NotNull
    @Size(min=3, max=64)
//    @Pattern(regexp = "[A-Z][A-Za-z0-9 -]{1,63}")
    @Pattern(regexp = "[A-Za-z]{1,63}")  //service names has small letters. Therefore I used this regexp
    @Column(name="service_name", length=64)
    private String serviceName;

    @NotNull
    @PastOrPresent
    @Column(name = "creation_ts")
    private LocalDateTime creationTS;

    @NotNull
    @Column(length = 32)
    private BigDecimal servicePreis;  //BigDecimal.valueOf(12.50)

    @NotNull
    @Column(length = 16)
    private Currency serviceCurrency;

    @NotNull
    @Column(length = 16)
    private Duration serviceDuration;

    /*
    @Embedded  //die Attributes von BookedService.java zusätzlich gekriegt.
    @AttributeOverrides({
            @AttributeOverride(name ="serviceAmount", column = @Column(name = "bookedService_serviceAmount", length=16)),
            @AttributeOverride(name = "serviceTS", column = @Column(name = "bookedService_serviceTS")),
    //        @AttributeOverride(name = "serviceBarber", column = @Column(name = "bookedService_serviceBarber", length=32)),
    })
    private BookedService bookedService;
     */

    @Builder.Default
    @ElementCollection
    @CollectionTable(name = "service_reservations",  //collection olmayacak dedi.
            joinColumns = @JoinColumn(name = "service_id", foreignKey = @ForeignKey(name= "fk_service_reservations_2_service")))
    private List<BookedService> reservations = new ArrayList<>();  //List yerine ArrayList`de olabilir

    public Service addReservations(LocalDateTime serviceTS, Reservation... reservations){
        Arrays.stream(reservations).forEach(reservation -> addReservation(serviceTS, reservation));
        return this;
    }

    public Service addReservation(LocalDateTime serviceTS, Reservation reservation){
        Objects.requireNonNull(serviceTS, "Service Reservation timestamp must not be null!");
//        Objects.requireNonNull(reservation, "Service Reservation must not be null!");  //??

        BookedService bookedService = createBookedService(serviceTS, reservation);
        reservations.add(bookedService);
        return this;
    }

    private BookedService createBookedService(LocalDateTime serviceTS, Reservation reservation) {
        var bookedService = BookedService.builder()
                .reservation(reservation)
                .serviceTS(serviceTS)
                .build();

        return bookedService;
    }

    public Service insertReservation(LocalDateTime serviceTS, Reservation reservation) {
        Objects.requireNonNull(serviceTS, "Service Reservation timestamp must not be null!");
//        Objects.requireNonNull(reservation, "Service Reservation must not be null!");  //??

        BookedService bookedService = createBookedService(serviceTS, reservation);
        reservations.add(bookedService);
        return this;
    }

    public List<Reservation> getReservations(){
        // List<Photo> getPhotos(){
        return reservations.stream()
                .map(BookedService::getReservation)
                .collect(Collectors.toUnmodifiableList());
    }

    public List<BookedService> getBookedServices() {
//        return Collections.unmodifiableList(photos);
        return reservations;
    }

    public Integer getReservationCount() {
        return reservations.size();  //??size yapilan service Reservation sayisi.
    }

    public Long getReservationCountFor(Predicate<Reservation> condition) {

        return reservations.stream()
                .filter(p -> condition.test(p.getReservation()))
                .count();
    }

    /*
    @Builder.Default
    @ElementCollection
    @CollectionTable(name = "service_reservations",
            joinColumns = @JoinColumn(name = "service_id", foreignKey = @ForeignKey(name= "fk_service_reservations_2_service")))
    private List<BookedService> reservationsBS = new ArrayList<>();  //List yerine ArrayList`de olabilir

    public Service addReservationsBS(LocalDateTime serviceTS, Reservation... reservationsBS){
        Arrays.stream(reservationsBS).forEach(reservationBS -> addReservationBS(serviceTS, reservationBS));
        return this;
    }
        public Service addReservationBS(LocalDateTime serviceTS, Reservation reservationBS){
        Objects.requireNonNull(serviceTS, "Service Reservation timestamp must not be null!");
        Objects.requireNonNull(reservationBS, "Service Reservation must not be null!");  //??

        BookedService bookedService = createBookedService(serviceTS, reservationBS);
        reservationsBS.add(bookedService);
        return this;
    }

    private BookedService createBookedService(LocalDateTime serviceTS, Reservation reservationBS) {
        var bookedService = BookedService.builder()
                .reservationBS(reservationBS)
                .serviceTS(serviceTS)
                .build();

        return bookedService;
    }

    public Service insertReservationBS(LocalDateTime serviceTS, Reservation reservationBS) {
        Objects.requireNonNull(serviceTS, "Service Reservation timestamp must not be null!");
        Objects.requireNonNull(reservationBS, "Service Reservation must not be null!");  //??

        BookedService bookedService = createBookedService(serviceTS, reservationBS);
        reservationsBS.add(bookedService);
        return this;
    }

    public List<Reservation> getReservationsBS(){
        // List<Photo> getPhotos(){
        return reservationsBS.stream()
                .map(BookedService::getReservationBS)
                .collect(Collectors.toUnmodifiableList());
    }

    List<BookedService> getBookedServices() {
//        return Collections.unmodifiableList(photos);
        return reservationsBS;
    }

     */

}
