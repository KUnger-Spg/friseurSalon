package at.spengergasse.friseursalon.persistence;

import at.spengergasse.friseursalon.domain.Reservation;
import at.spengergasse.friseursalon.persistence.projections.Projections;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long>, KeyHolderQueries<Reservation> {

    List<Reservation> findAllByCreationTSBetween(LocalDateTime startTS, LocalDateTime endTS);

    List<Projections.ReservationInfoDTO> findAllByReservationNameLike(String reservationName);

    Optional<Reservation> findByReservationName(String reservationName);
}
