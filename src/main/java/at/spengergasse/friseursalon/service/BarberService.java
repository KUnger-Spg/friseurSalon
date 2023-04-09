package at.spengergasse.friseursalon.service;

import at.spengergasse.friseursalon.domain.Barber;
import at.spengergasse.friseursalon.persistence.BarberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor

@Service
@Transactional(readOnly = true)

public class BarberService {

    private final BarberRepository barberRepository;

    public List<Barber> getAllBarbers() {

        return barberRepository.findAll();
    }

    @Transactional
    public Barber createBarber(String nickName, String firstName, String lastName) {
        return Barber.builder()
                .nickName(nickName)
                .firstName(firstName)
                .lastName(lastName)
                .build();
    }

}
