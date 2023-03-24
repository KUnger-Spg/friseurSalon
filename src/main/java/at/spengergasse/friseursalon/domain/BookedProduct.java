package at.spengergasse.friseursalon.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder

@Embeddable
public class BookedProduct {

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_bp_reservation_id"))
//    @Column(name = "reservation_bp")  //@ManyToOne`da @Column olmuyor
    private Reservation reservation;

    @Column(length = 16)
    private Integer productAmount;

    @FutureOrPresent
    @Column(name = "product_ts")
    private LocalDateTime productTS;  //should be booking date and time at future
//    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

}
