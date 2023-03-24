package at.spengergasse.friseursalon.persistence.converter;

import at.spengergasse.friseursalon.domain.EmailType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Optional;
import java.util.function.Function;

@Converter(autoApply = true)

public class EmailTypeConverter extends AbstractEnumToStringConverter<EmailType> {

    protected EmailTypeConverter() {

        super((o) -> switch (o) {
            case BUSINESS -> "B";
            case PRIVATE -> "P";
        }, (v) -> switch (v) {
            case "B" -> EmailType.BUSINESS;
            case "P" -> EmailType.PRIVATE;
            //    default -> null;
            default -> throw new IllegalArgumentException("Data Quality Problem in DB: %s is not a vlaid orientation value!".formatted(v));
        });
    }
}
