package at.spengergasse.friseursalon.persistence;

import at.spengergasse.friseursalon.domain.Barber;
import at.spengergasse.friseursalon.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BarberRepository extends JpaRepository<Barber, Long> {
}
