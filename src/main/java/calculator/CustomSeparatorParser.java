package calculator;

public final class CustomSeparatorParser {
    private CustomSeparatorParser() {
    }

    public static ParsedParts parse(String input) {
        // "//" 뒤의 세 번째 문자가 커스텀 구분자 (인덱스 2)
        final String customSeparator = String.valueOf(input.charAt(2));

        // 개행문자('\n') 다음 인덱스(4번)부터 숫자 부분이 시작됨
        final String numbersPart = input.substring(4);

        // 파싱 결과를 ParsedParts로 감싸서 반환
        return new ParsedParts(numbersPart, customSeparator);
    }
}
