package calculator;

/**
 * 문자열 덧셈 계산 전체의 흐름을 관리하는 클래스. 전제: 입력값의 유효성 검사는 InputValidator에서 완료된 상태다.
 */
public final class StringCalculator {

    // 유틸리티 클래스. 인스턴스화 차단.
    private StringCalculator() {
    }

    public static int calculate(String input) {
        // null,빈 문자열이면 0 반환
        if (input == null || input.isEmpty()) {
            return 0;
        }
        // 커스텀구분자 포함 문자열의 경우
        if (input.startsWith("//")) {
            // 구분자와 숫자부분 분리
            final ParsedParts parsedParts = CustomSeparatorParser.parse(input);
            // 분리결과를 NumberAdder에 전달하여 합 계산을 위임
            return NumberAdder.add(parsedParts.numbersPart(), parsedParts.customSeparator());
        }
        // 기본 구분자만 포함된 문자열은 곧바로 NumberAdder에 합 계산 위임
        return NumberAdder.add(input);
    }

}
