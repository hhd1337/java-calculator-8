package calculator.parser;

/**
 * 커스텀 구분자가 포함된 입력 문자열을 파싱한 결과를 담는 불변 레코드.
 */
public record ParsedParts(
        String numbersPart,     // 숫자 부분 문자열
        String customSeparator  // 커스텀 구분자 문자
) {
}
