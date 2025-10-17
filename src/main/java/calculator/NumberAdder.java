package calculator;

public final class NumberAdder {
    private NumberAdder() {
    }

    // 기본 구분자(, :) 처리
    public static int add(String input) {
        // 쉼표(,)나 콜론(:)을 기준으로 문자열을 분리
        String[] tokens = input.split("[,:]");

        int sum = 0;
        for (String token : tokens) {
            int number = Integer.parseInt(token); // 문자열을 정수로 변환
            sum += number;
        }

        return sum;
    }

    // 커스텀 구분자 처리
    public static int add(String numbersPart, String customSeparator) {
        // TODO: 커스텀 구분자 기준 분리 및 합계 계산 구현
        throw new UnsupportedOperationException("NumberAdder.add(String,String) 아직 구현되지 않음.");
    }
}

