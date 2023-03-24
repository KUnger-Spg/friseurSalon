package at.spengergasse.friseursalon.domain;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
//@AllArgsConstructor //no need because of the constructor created below.

@MappedSuperclass
public abstract class AbstractPerson extends AbstractPersistable<Long> {

    @NotNull
    @Version
    private Integer version;

    @NotBlank
//    @Email  //Email can be the username
              //when it is active, it looks for an email as username
    @Column(length = 255)
    private String userName;

    @NotNull
    @Column(length = 32)
    private String password;

    @NotNull
    @Column(length = 32)
    private String firstName;

    @NotNull
    @Column(length = 64)
    private String lastName;

    public AbstractPerson(String userName, String password, String firstName, String lastName) {
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
