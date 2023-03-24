package at.spengergasse.friseursalon.persistence.converter;

import at.spengergasse.friseursalon.domain.Gender;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GenderConverterTest {

    private GenderConverter genderConverter = new GenderConverter();

    @Test
    void ensureCorrectHandlingofNullValues(){
        //expect
        assertThat(genderConverter.convertToDatabaseColumn(null)).isNull();
        assertThat(genderConverter.convertToEntityAttribute(null)).isNull();
    }

    @Disabled
    @Test
    void ensureCorrectHandlingofDataQualityProblems(){
        //given
        String dbValue = "X";

        //when
        var iaEx =
                assertThrows(IllegalArgumentException.class,
                        () -> genderConverter.convertToEntityAttribute("X"));

        //then
        assertThat(iaEx).hasMessageContaining("%s is not a valid".formatted(dbValue));
    }

    @ParameterizedTest
    @MethodSource
    void ensureCorrectHandlingofGivenValues(Gender javaValue, String dbValue) {
        //expect
        assertThat(genderConverter.convertToDatabaseColumn(javaValue)).isEqualTo(dbValue);
        assertThat(genderConverter.convertToEntityAttribute(dbValue)).isEqualTo(javaValue);
    }

    static Stream<Arguments> ensureCorrectHandlingofGivenValues(){
        return Stream.of(
                Arguments.of(Gender.MALE, "M"),
                Arguments.of(Gender.FEMALE, "F"),
                Arguments.of(Gender.DIVERS, "D")
        );
    }
}
