package at.spengergasse.friseursalon.domain;

import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder

@Embeddable
public class Address {

    @Column(length = 64)
    private String streetNumber;
    @Column(length = 16)
    private String zipCode;
    @Column(length = 64)
    private String city;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Country country;

}
