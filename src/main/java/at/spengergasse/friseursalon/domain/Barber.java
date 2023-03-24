package at.spengergasse.friseursalon.domain;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@AllArgsConstructor   //barber ve customer icin cift user_name Repo Test`de olabilsin diye bunu sildi.

@Entity
@Table(name="barbers")  //Database Table creation

public class Barber extends AbstractPerson{

    @NotNull
    @Column(length = 32)
    private String nickName;

    @Embedded  //die Attributes von PhoneNumber.java zusätzlich gekriegt.
    @AttributeOverrides({
            @AttributeOverride(name ="countryCode", column = @Column(name = "mobilePhoneNumber_country_code")),
            @AttributeOverride(name = "areaCode", column = @Column(name = "mobilePhoneNumber_area_code")),
            @AttributeOverride(name = "serialNumber", column = @Column(name = "mobilePhoneNumber_serial_number", length=16)),
    })
    private PhoneNumber mobilePhoneNumber;

    @Embedded  //die Attributes von Email.java zusätzlich gekriegt.
    @AttributeOverrides({
            @AttributeOverride(name ="address", column = @Column(name = "email_addess", length=128)),
    })
    private Email email;

    @Builder
    public Barber(String userName, String password, String firstName, String lastName, String nickName,
                  PhoneNumber mobilePhoneNumber, Email email) {
        super(userName, password, firstName, lastName);
        this.nickName = nickName;
        this.mobilePhoneNumber = mobilePhoneNumber;
        this.email = email;
    }

}
