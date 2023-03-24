package at.spengergasse.friseursalon.domain;


import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Optional;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
// @Builder

@Entity  //@Entity yapinca Attributes hata veriyor cünkü onlar standard type degil, onlar Object type
@Table(name="customers")  //Database Table creation

public class Customer extends AbstractPerson implements KeyHolder{

    public static final int KEY_LENGTH = 4;

    @NotNull
    @Column(length = KEY_LENGTH, unique = true)
    private String key;

    @Embedded  //die vier Attributes von Address.java zusätzlich gekriegt.
    @AttributeOverrides({
            @AttributeOverride(name ="streetNumber", column = @Column(name = "billingAddress_street_number", length=64)),
            @AttributeOverride(name = "zipCode", column = @Column(name = "billingAddress_zip_code", length=16)),
            @AttributeOverride(name = "city", column = @Column(name = "billingAddress_city", length=64)),
    })
    @AssociationOverrides({   //Country Tabelle ile Beziehung. Country ist hier Fremdeschlüssel
            @AssociationOverride(name = "country",
                    joinColumns = {@JoinColumn(name = "billingAddress_country_id")},
                    foreignKey = @ForeignKey(name = "billingAddress_country_id"))
    })
    private Address billingAddress;

    @Embedded  //die Attributes von PhoneNumber.java zusätzlich gekriegt.
    @AttributeOverrides({
            @AttributeOverride(name ="countryCode", column = @Column(name = "mobilePhoneNumber_country_code")),
            @AttributeOverride(name = "areaCode", column = @Column(name = "mobilePhoneNumber_area_code")),
            @AttributeOverride(name = "serialNumber", column = @Column(name = "mobilePhoneNumber_serial_number", length=16)),
    })
    private PhoneNumber mobilePhoneNumber;

    @Embedded  //die Attributes von Email.java zusätzlich gekriegt.
    @AttributeOverrides({
            @AttributeOverride(name ="address", column = @Column(name = "email_address", length=128)),
    })
    private Email email;

    @Column(length = 1)  //memory`de yer kaplamasin diye 1 yaptik.
    private Gender gender;

    @Builder
    public Customer(String key, String userName, String password, String firstName, String lastName, Address billingAddress,
                    PhoneNumber mobilePhoneNumber, Email email, Gender gender) {
        super(userName, password, firstName, lastName);
        this.key = key;
        this.billingAddress = billingAddress;
        this.mobilePhoneNumber = mobilePhoneNumber;
        this.email = email;
        this.gender = gender;
    }

    public String getDisplayName() {
        return "%s %s".formatted(getFirstName(), getLastName().toUpperCase());
    }
}
