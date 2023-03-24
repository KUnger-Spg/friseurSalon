package at.spengergasse.friseursalon.service.forms;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor

public class AddReservationForm {

    @NotBlank
    @Size(min=2, max=64)
    private String reservationName;


    @NotNull
    String customerKey;
}
