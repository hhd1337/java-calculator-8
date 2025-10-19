package calculator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import calculator.validation.InputValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class InputValidatorTest {

    @Test
    @DisplayName("빈 문자열은 예외 없이 통과된다")
    void emptyInput_passesValidation() {
        assertDoesNotThrow(() -> InputValidator.validateOrThrow(""));
    }

    @Test
    @DisplayName("음수 입력시 예외 발생")
    void negativeNumber_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> InputValidator.validateOrThrow("-1,2,3"));
    }

    @Test
    @DisplayName("공백이 포함되면 예외 발생")
    void spaceIncluded_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> InputValidator.validateOrThrow("1, 2,3"));
    }

    @Test
    @DisplayName("연속 구분자 사용시 예외 발생")
    void continuousSeparator_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> InputValidator.validateOrThrow("1,,2"));
    }

    @Test
    @DisplayName("커스텀 구분자 형식이 올바르면 통과")
    void customSeparator_validFormat_passes() {
        assertDoesNotThrow(() -> InputValidator.validateOrThrow("//;\\n1;2;3"));
    }

    @Test
    @DisplayName("커스텀 구분자 뒤 본문이 비어있으면 예외 발생")
    void customSeparator_noNumbers_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> InputValidator.validateOrThrow("//;\\n"));
    }
}
