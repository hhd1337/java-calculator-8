package calculator;

import java.util.regex.Pattern;

/**
 * 입력 문자열이 유효한 입력인지 완벽히 검증한다.
 */
public final class InputValidator {
    private InputValidator() {
    }

    // 외부에서 직접 호출하는 진입점
    public static void validateOrThrow(String input) {
        // 입력이 null, 빈문자열이면 검증완료
        if (input == null || input.isEmpty()) {
            return;
        }

        //테스트용 코드. 입력 정규화 (리터럴 "\n"과 실제 개행입력 모두 처리 가능하도록)
        input = input.replace("\\n", "\n");

        // 커스텀구분자 형식 검증
        if (input.startsWith("//")) {
            validateCustomForm(input);
            return;
        }
        // 기본 구분자 형식 검증
        validateBasicForm(input);
    }

    // 커스텀 구분자 형식 검증
    private static void validateCustomForm(String input) {
        // 최소 4글자 (예 : '//;\n')
        if (input.length() < 4) {
            throw new IllegalArgumentException("구분자는 한 글자이며, 형식은 \"//{구분자}\\n{숫자...}\" 여야 합니다.");
        }
        // 전체 입력 문자열에서 공백(스페이스, 탭 등) 금지
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c == '\n') {
                continue; // 커스텀 구분자 형식상 허용 (리터럴 '\n'은 이미 실제 개행으로 정규화됨)
            }
            if (Character.isWhitespace(c)) {
                throw new IllegalArgumentException("문자열에 공백을 포함할 수 없습니다.");
            }
        }
        // 구분자는 한글자만 허용, '\n'의 위치는 인덱스 3이어야 함.
        if (input.charAt(3) != '\n') {
            throw new IllegalArgumentException("구분자는 한글자만 허용되고, 커스텀 구분자 형식에 맞아야 합니다.");
        }
        // 커스텀구분자 추출, 구분자는 숫자나 공백이나 탭이면 안됨.
        if (Character.isDigit(input.charAt(2)) || Character.isWhitespace(input.charAt(2))) {
            throw new IllegalArgumentException("커스텀 구분자는 숫자나 공백, 탭이면 안됩니다.");
        }

        // 커스텀 구분자와 숫자 본문 추출
        ParsedParts parsedParts = CustomSeparatorParser.parse(input);
        String customSep = parsedParts.customSeparator(); // 커스텀 구분자
        String numbersPart = parsedParts.numbersPart(); // 숫자 본문

        String regex = "[,:]|" + Pattern.quote(customSep);
        String[] tokens = numbersPart.split(regex, -1); // 맨뒤 구분자도 잡아내기 위해 -1 설정

        // '\n' 이후 빈 본문이면 안됨. 숫자가 나와야 함.
        if (numbersPart.isEmpty()) {
            throw new IllegalArgumentException("\\n 이후에 문자열(숫자와 기본구분자 조합)이 있어야 합니다.");
        }

        // 구분자 집합
        String[] seps = {customSep, ",", ":"};

        // 맨앞,맨뒤에 구분자 금지
        for (String s : seps) {
            if (numbersPart.startsWith(s) || numbersPart.endsWith(s)) {
                throw new IllegalArgumentException("구분자는 숫자 사이에만 올 수 있습니다.");
            }
        }

        // 연속 구분자 금지
        for (int i = 0; i < numbersPart.length() - 1; i++) {
            for (String a : seps) {
                for (String b : seps) {
                    if (numbersPart.startsWith(a, i) && numbersPart.startsWith(b, i + a.length())) {
                        throw new IllegalArgumentException("구분자는 연속으로 올 수 없습니다.");
                    }
                }
            }
        }

        // 숫자외 문자, 음수, 0 금지
        for (String token : tokens) {
            // 구분자만 있거나 연속된 경우 -> 빈 문자열 토큰이 생김
            if (token.isEmpty()) {
                throw new IllegalArgumentException("구분자는 숫자 사이에만 올 수 있습니다.");
            }

            int number;
            try {
                number = Integer.parseInt(token);
            } catch (NumberFormatException e) {
                // 숫자 자리에 문자가 들어간 경우
                throw new IllegalArgumentException("숫자 형식이 올바르지 않습니다."); // IllegalArgumentException로 종료시킴
            }

            // 음수와 0 금지
            if (number <= 0) {
                throw new IllegalArgumentException("숫자는 양수만 입력해야 합니다.");
            }
        }

    }

    // 기본 구분자 형식 검증(쉼표, 콜론)
    private static void validateBasicForm(String input) {
        // 공백(스페이스, 탭 등) 금지
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (Character.isWhitespace(c)) {
                throw new IllegalArgumentException("문자열에 공백을 포함할 수 없습니다.");
            }
        }
        // 허용 문자: 숫자, 콤마, 콜론만
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (!Character.isDigit(c) && c != ',' && c != ':' && c != '-') { // '-' 문자는 이후에 별도 처리
                throw new IllegalArgumentException("숫자와 기본 구분자(, :)만 사용할 수 있습니다. 이외의 문자입력은 커스텀구분자로 등록해야합니다.");
            }
        }
        // 맨앞, 맨뒤 구분자 금지
        char first = input.charAt(0);
        char last = input.charAt(input.length() - 1);
        if (first == ',' || first == ':' || last == ',' || last == ':') {
            throw new IllegalArgumentException("구분자는 숫자 사이에만 올 수 있습니다.");
        }
        // 연속 구분자 사용 금지
        for (int i = 0; i < input.length() - 1; i++) {
            char a = input.charAt(i);
            char b = input.charAt(i + 1);
            if ((a == ',' || a == ':') && (b == ',' || b == ':')) {
                throw new IllegalArgumentException("구분자가 연속으로 올 수 없습니다.");
            }
        }
        // 숫자외 문자, 음수, 0 금지
        String[] tokens = input.split(",|:", -1); // 맨뒤 구분자도 잡아내기 위해 -1 설정

        for (String token : tokens) {
            // 구분자만 있거나 연속된 경우 -> 빈 문자열 토큰이 생김
            if (token.isEmpty()) {
                throw new IllegalArgumentException("구분자는 숫자 사이에만 올 수 있습니다.");
            }

            int number;
            try {
                number = Integer.parseInt(token);
            } catch (NumberFormatException e) {
                // 숫자 자리에 문자가 들어간 경우
                throw new IllegalArgumentException("숫자 형식이 올바르지 않습니다."); // IllegalArgumentException로 종료시킴
            }

            // 음수와 0 금지
            if (number <= 0) {
                throw new IllegalArgumentException("숫자는 양수만 입력해야 합니다.");
            }
        }
    }

}
