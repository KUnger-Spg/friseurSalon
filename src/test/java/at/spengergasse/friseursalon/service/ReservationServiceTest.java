package at.spengergasse.friseursalon.service;

import at.spengergasse.friseursalon.TestFixtures;
import at.spengergasse.friseursalon.domain.Country;
import at.spengergasse.friseursalon.domain.Customer;
import at.spengergasse.friseursalon.foundation.Base58;
import at.spengergasse.friseursalon.foundation.DateTimeFactory;
import at.spengergasse.friseursalon.persistence.CustomerRepository;
import at.spengergasse.friseursalon.persistence.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.PersistenceException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Optional;

import static at.spengergasse.friseursalon.TestFixtures.amir;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assumptions.assumeThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)

class ReservationServiceTest {

    private @Mock ReservationRepository reservationRepository;
    private @Mock CustomerRepository customerRepository;
    private @Mock DateTimeFactory dateTimeFactory;

    private ReservationService reservationService;

    @BeforeEach
    void setup() {
        assumeThat(reservationRepository).isNotNull();
        assumeThat(customerRepository).isNotNull();
        assumeThat(dateTimeFactory).isNotNull();
        reservationService = new ReservationService(reservationRepository, customerRepository, dateTimeFactory, new Base58());
    }

    @Test
    void ensureCreateReservationFromExistingCustomerWorksProperly() {

        //given
        String reservationName = "reservationName";
//        String name = "name";
        String customerKey = amir.getKey();
//        String customerKey = "8942";
        LocalDateTime cutOffTS = LocalDateTime.of(2022, Month.DECEMBER, 31, 23, 59, 59);
//        Country austria = TestFixtures.austria;
        Customer amir = TestFixtures.amir;

        //train mocks
        when(customerRepository.findByKey(customerKey)).thenReturn(Optional.of(amir));
        when(reservationRepository.save(any())).then(AdditionalAnswers.returnsFirstArg());
        when(dateTimeFactory.now())
                .thenReturn(cutOffTS)
                .thenReturn(cutOffTS.plusSeconds(2));

        //when
        var reservation = reservationService.createReservation(reservationName, customerKey);

        //then
        assertThat(reservation).isNotNull();
        assertThat(reservation.getReservationName()).isEqualTo(reservationName);
//        assertThat(reservation.getName()).isEqualTo(name);
        assertThat(reservation.getCreationTS()).isEqualTo(cutOffTS);
        assertThat(reservation.getCustomer()).isEqualTo(amir);

        verify(customerRepository).findByKey(customerKey);
        verifyNoMoreInteractions(customerRepository);
        verify(dateTimeFactory, times(1)).now();
        verifyNoMoreInteractions(dateTimeFactory);
        verify(reservationRepository).save(reservation);
        verifyNoMoreInteractions(reservationRepository);

    }

    @Test
    void ensureCreateReservationHandlesDBErrorsProperly() {

        //given
        String reservationName = "reservationName";
//        String name = "name";
        String customerKey = amir.getKey();
        LocalDateTime cutOffTS = LocalDateTime.of(2022, Month.DECEMBER, 31, 23, 59, 59);
//        Country austria = TestFixtures.austria;
        Customer amir = TestFixtures.amir;

        //train mocks
        when(customerRepository.findByKey(customerKey)).thenThrow(new PersistenceException("network timeout!"));

        //expect
        var svcEx = assertThrows(ServiceException.class,
                () -> reservationService.createReservation(reservationName, customerKey));

        assertThat(svcEx).isNotNull();

        verify(customerRepository).findByKey(customerKey);
        verifyNoMoreInteractions(customerRepository);
        verifyNoInteractions(dateTimeFactory);
        verifyNoInteractions(reservationRepository);

    }

}