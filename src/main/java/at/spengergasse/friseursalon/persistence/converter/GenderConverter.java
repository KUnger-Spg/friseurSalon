package at.spengergasse.friseursalon.persistence.converter;

import at.spengergasse.friseursalon.domain.Gender;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class GenderConverter extends AbstractEnumToStringConverter<Gender> {

    public GenderConverter() {

        super((o) -> switch (o) {
            case MALE -> "M";
            case FEMALE -> "F";
            case DIVERS -> "D";
        }, (v) -> switch (v) {
            case "M" -> Gender.MALE;
            case "F" -> Gender.FEMALE;
            case "D" -> Gender.DIVERS;
            //    default -> null;
            default -> throw new IllegalArgumentException("Data Quality Problem in DB: %s is not a vlaid orientation value!".formatted(v));
        });
    }
}
