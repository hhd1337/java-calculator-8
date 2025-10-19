package calculator.core;

import java.util.regex.Pattern;

public final class NumberAdder {
    private NumberAdder() {
    }

    // 기본 구분자(, :) 처리
    public static int add(String input) {
        // 쉼표(,)나 콜론(:)을 기준으로 문자열을 분리, 숫자만 배열에 담음
        String[] tokens = input.split("[,:]");

        int sum = 0;
        for (String token : tokens) {
            int number = Integer.parseInt(token); // 문자열을 정수로 변환
            sum += number;
        }

        return sum;
    }

    // 커스텀 구분자 처리 (기본 구분자와 함께 인식)
    public static int add(String numbersPart, String customSeparator) {
        // 기본 구분자(,:)와 커스텀구분자를 기준으로 문자열을 분리
        // 커스텀 구분자가 정규식용 특수문자가 아닌 문자 그대로 인식되도록 Pattern.quote()처리
        String regex = "[,:]|" + Pattern.quote(customSeparator);
        String[] tokens = numbersPart.split(regex);

        int sum = 0;
        for (String token : tokens) {
            int number = Integer.parseInt(token); // 문자열을 정수로 변환
            sum += number;
        }

        return sum;
    }
}

