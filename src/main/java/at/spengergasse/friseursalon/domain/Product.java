package at.spengergasse.friseursalon.domain;

import lombok.*;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder

@Entity
@Table(name="products")  //Database Table creation

public class Product extends AbstractPersistable<Long> implements KeyHolder{

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
    @Pattern(regexp = "[A-Za-z]{1,63}")  //shampoo name has only small letters. Therefore I used this regexp
    @Column(name="product_name", length=64)
    private String productName;

    @NotNull
    @Column(length = 32)
    private BigDecimal productPreis;  //BigDecimal.valueOf(12.50)

    @NotNull
    @Column(length = 16)
    private Currency productCurrency;

    @NotNull
    @PastOrPresent
    @Column(name = "creation_ts")
    private LocalDateTime creationTS;

    /*
    @Embedded  //die Attributes von BookedProduct.java zusätzlich gekriegt.
    @AttributeOverrides({
            @AttributeOverride(name ="productAmount", column = @Column(name = "bookedProduct_productAmount", length=16)),
            @AttributeOverride(name = "productTS", column = @Column(name = "bookedProduct_product_ts")),
    })
    */

    //    private BookedProduct bookedProduct;

    @Builder.Default
    @ElementCollection
    @CollectionTable(name = "product_reservations",  //collection olmayacak dedi.
            joinColumns = @JoinColumn(name = "product_id", foreignKey = @ForeignKey(name= "fk_product_reservations_2_product")))
    private List<BookedProduct> reservations = new ArrayList<>();  //List yerine ArrayList`de olabilir

    public Product addReservations(LocalDateTime productTS, Reservation... reservations){
        Arrays.stream(reservations).forEach(reservation -> addReservation(productTS, reservation));
        return this;
    }

    public Product addReservation(LocalDateTime productTS, Reservation reservation){
        Objects.requireNonNull(productTS, "Product Reservation timestamp must not be null!");
//        Objects.requireNonNull(reservation, "Product Reservation must not be null!");  //??

        BookedProduct bookedProduct = createBookedProduct(productTS, reservation);
        reservations.add(bookedProduct);
        return this;
    }

    private BookedProduct createBookedProduct(LocalDateTime productTS, Reservation reservation) {
        var bookedProduct = BookedProduct.builder()
                .reservation(reservation)
                .productTS(productTS)
                .build();

        return bookedProduct;
    }

    public Product insertReservation(LocalDateTime productTS, Reservation reservation) {
        Objects.requireNonNull(productTS, "Product Reservation timestamp must not be null!");
//        Objects.requireNonNull(reservation, "Product Reservation must not be null!");  //??

        BookedProduct bookedProduct = createBookedProduct(productTS, reservation);
        reservations.add(bookedProduct);
        return this;
    }

    public List<Reservation> getReservations(){
        // List<Photo> getPhotos(){
        return reservations.stream()
                .map(BookedProduct::getReservation)
                .collect(Collectors.toUnmodifiableList());
    }

    public List<BookedProduct> getBookedProducts() {
//        return Collections.unmodifiableList(photos);
        return reservations;
    }

    public Integer getReservationCount() {

        return reservations.size();  //??size yapilan product Reservation sayisi
    }

    public Long getReservationCountFor(Predicate<Reservation> condition) {

        return reservations.stream()
                .filter(p -> condition.test(p.getReservation()))
                .count();
    }

    /*
    @Builder.Default
    @ElementCollection
    @CollectionTable(name = "product_reservations",
            joinColumns = @JoinColumn(name = "product_id", foreignKey = @ForeignKey(name= "fk_product_reservations_2_product")))
    private List<BookedProduct> reservationsBP = new ArrayList<>();  //List yerine ArrayList`de olabilir

    public Product addReservationsBP(LocalDateTime productTS, Reservation... reservationsBP){
        Arrays.stream(reservationsBP).forEach(reservationBP -> addReservationBP(productTS, reservationBP));
        return this;
    }

    public Product addReservationBP(LocalDateTime productTS, Reservation reservationBP){
        Objects.requireNonNull(productTS, "Product Reservation timestamp must not be null!");
//        Objects.requireNonNull(reservationBP, "Product Reservation must not be null!");  //??

        BookedProduct bookedProduct = createBookedProduct(productTS, reservationBP);
        reservationsBP.add(bookedProduct);
        return this;
    }

    private BookedProduct createBookedProduct(LocalDateTime productTS, Reservation reservationBP) {
        var bookedProduct = BookedProduct.builder()
                .reservationBP(reservationBP)
                .productTS(productTS)
                .build();

        return bookedProduct;
    }

    public Product insertReservationBP(LocalDateTime productTS, Reservation reservationBP) {
        Objects.requireNonNull(productTS, "Product Reservation timestamp must not be null!");
//        Objects.requireNonNull(reservationBP, "Product Reservation must not be null!");  //??

        BookedProduct bookedProduct = createBookedProduct(productTS, reservationBP);
        reservationsBP.add(bookedProduct);
        return this;
    }

    public List<Reservation> getReservationsBP(){
        // List<Photo> getPhotos(){
        return reservationsBP.stream()
                .map(BookedProduct::getReservationBP)
                .collect(Collectors.toUnmodifiableList());
    }

    List<BookedProduct> getBookedProducts() {
//        return Collections.unmodifiableList(photos);
        return reservationsBP;
    }
*/

}
