package at.spengergasse.friseursalon.persistence.converter;

import javax.persistence.AttributeConverter;
import java.util.Optional;
import java.util.function.Function;

public class AbstractEnumToStringConverter <E extends Enum> implements AttributeConverter <E, String> {

    private final Function<E, String> toDbValue;

    private final Function<String, E> fromDbValue;

    protected AbstractEnumToStringConverter(Function<E, String> toDbValue, Function<String, E> fromDbValue) {
        this.toDbValue = toDbValue;
        this.fromDbValue = fromDbValue;
    }

    @Override
//    public String convertToDatabaseColumn(E e) { return null; }
    public final String convertToDatabaseColumn(E javaValue) {
        return Optional.ofNullable(javaValue).map(toDbValue).orElse(null);
    }

    @Override
//    public E convertToEntityAttribute(String s) { return null; }
    public final E convertToEntityAttribute(String dbValue) {
        return Optional.ofNullable(dbValue).map(fromDbValue).orElse(null);
    }

}
