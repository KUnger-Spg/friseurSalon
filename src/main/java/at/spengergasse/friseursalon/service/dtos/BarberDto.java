package at.spengergasse.friseursalon.service.dtos;

import at.spengergasse.friseursalon.domain.Barber;

public record BarberDto(String nickName,
                        String firstName,
                        String lastName) {

    public BarberDto(Barber barber){
        this(barber.getNickName(),
                barber.getFirstName(),
                barber.getLastName());
    }

}
