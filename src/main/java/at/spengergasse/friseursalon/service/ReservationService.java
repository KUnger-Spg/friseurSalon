package at.spengergasse.friseursalon.service;

import at.spengergasse.friseursalon.domain.Reservation;
import at.spengergasse.friseursalon.foundation.Base58;
import at.spengergasse.friseursalon.foundation.DateTimeFactory;
import at.spengergasse.friseursalon.persistence.CustomerRepository;
import at.spengergasse.friseursalon.persistence.ReservationRepository;
import at.spengergasse.friseursalon.service.commands.CreateReservationCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)

public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final CustomerRepository customerRepository;
    private final DateTimeFactory dateTimeFactory;
    private final Base58 keyGen;

    @Transactional(readOnly = false)  //default false
    public Reservation createReservation(CreateReservationCommand cmd) {
        return createReservation(cmd.reservationName(),
                cmd.customerKey());
    }
    @Transactional(readOnly = false)  //default false
    public Reservation createReservation(String reservationName, String customerKey) {

        try {
            return customerRepository.findByKey(customerKey)
                    .map(customer -> Reservation.builder()
                            .key(keyGen.randomString(Reservation.KEY_LENGTH))
                            .reservationName(reservationName)
//                            .name(name)
//                        .creationTS(LocalDateTime.now())  //bunu begenmiyor.
                            .creationTS(dateTimeFactory.now())
                            .customer(customer)
                            .build())
                    .map(reservationRepository::save)
                    .orElseThrow();
        } catch (PersistenceException sqlEx) {
            throw ServiceException.forEntity(Reservation.class.getSimpleName(), sqlEx);
        }
    }

    public List<Reservation> getAllReservations() {

        return reservationRepository.findAll();
    }

    public Optional<Reservation> findReservationByReservationName(String reservationName) {
        return reservationRepository.findByReservationName(reservationName);
    }

    public Optional<Reservation> findByKey(String key) {

        return reservationRepository.findByKey(key);
    }

    /*
    public Optional<Reservation> updateReservationName(String reservationName, String newReservationName) {
    return reservationRepository.findByReservationName(reservationName)
            .map(r -> {
                r.setReservationName(newReservationName);
                return reservationRepository.save(r);
            });
    }
*/

    @Transactional(readOnly = false)
    public Optional<Reservation> updateReservationName(String key, String newReservationName) {
        return reservationRepository.findByKey(key)
                .map(r -> {
                    r.setReservationName(newReservationName);
//                    return reservationRepository.save(r);  //save ist nicht Muss, nicht Notwendig
                    return r;
                });
    }

    @Transactional(readOnly = false)
    public void deleteByKey(String key) {

        reservationRepository.deleteByKey(key);
    }

    @Transactional(readOnly = false)
    public void updateReservation(String reservationKey, String reservationName, String customerKey) {
        reservationRepository.findByKey(reservationKey).ifPresent(r -> {
            r.setReservationName(reservationName);
            r.setCustomer(customerRepository.getReferenceByKey(customerKey));
        });
    }
}
