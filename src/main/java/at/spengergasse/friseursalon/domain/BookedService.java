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
public class BookedService {

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_bs_reservation_id"))
//    @Column(name = "reservation_bs")  //@ManyToOne`da @Column olmuyor
    private Reservation reservation;  //before reservationBS

    @Column(length = 16)
    private Integer serviceAmount;

    @FutureOrPresent
    @Column(name = "service_ts")
    private LocalDateTime serviceTS;  //should be booking date and time at future
//    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

//    @ManyToOne  //many Service von einem Barber
//    @JoinColumn(name = "service_barber_id")

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_barber_id"))
    private Barber barber;

}
