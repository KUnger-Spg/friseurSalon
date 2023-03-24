package at.spengergasse.friseursalon.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Min;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder

@Embeddable
public class PhoneNumber {

    @Min(1)
    private Integer countryCode;
    @Min(1)
    private Integer areaCode;
    @Column(length = 16)
    private String serialNumber;

}
