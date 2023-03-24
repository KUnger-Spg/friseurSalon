package at.spengergasse.friseursalon.domain;

import lombok.*;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder

@Entity
@Table(name="countries")
public class Country extends AbstractPersistable<Long> {

    @NotNull
    @Version
    private Integer version;

    @NotBlank
    @Column(length = 64)
    private String name;

    @NotBlank
    @Column(length = 2)
    private String iso2Code;

}
