package at.spengergasse.friseursalon.domain;

import lombok.*;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;


@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor  //asagida Construction tanimladigin zaman buna gerek yok.
@Builder

//@ToString(of = {"id", "version", "name", "creationTS"})
@ToString(callSuper = true, of = {"version", "name", "creationTS"})

@Entity
@Table(name="reservations")  //Database Table creation

public class Reservation extends AbstractPersistable<Long> implements KeyHolder{

    public static final int KEY_LENGTH = 5;

    @NotNull
    @Column(length = KEY_LENGTH, unique = true)
    private String key;

    @NotNull
    @Version
    private Integer version;

    @NotBlank
//    @Column(length = 64, unique = true)  //unique olursa degistiremem.
    @Column(length = 64)
    private String reservationName;

    @NotNull
    @PastOrPresent
    @Column(name = "creation_ts")  //column name change
    private LocalDateTime creationTS;

//    @ManyToOne  //many reservations by one customer
//    @JoinColumn(name = "customer_id")
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_reservation_customer"))
    private Customer customer;

    /*
//    @ManyToOne  //many reservations for one product
//    @JoinColumn(name = "product_id")
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(foreignKey = @ForeignKey(name = "reservation_product_id"))
    private Product product;

//    @ManyToOne  //many reservations for one service
//    @JoinColumn(name = "service_id")
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(foreignKey = @ForeignKey(name = "reservation_service_id"))
    private Service service;

    private BookedProduct bookedProduct;
    private BookedService bookedService;
*/


    public Reservation(String reservationName, LocalDateTime creationTS, Customer customer, Product product,
                       Service service, BookedProduct bookedProduct, BookedService bookedService) {
        this.reservationName = reservationName;
        this.creationTS = creationTS;
        this.customer = customer;
//        this.product = product;
//        this.service = service;
//        this.bookedProduct = bookedProduct;
//        this.bookedService = bookedService;
    }

}
