package calculator;

import camp.nextstep.edu.missionutils.Console;

public class Application {
    public static void main(String[] args) {
        // 사용자 입력
        System.out.println("덧셈할 문자열을 입력해 주세요.");
        final String input = Console.readLine();

        // 입력 정규화 (리터럴 "\n"과 실제 개행입력 모두 처리 가능하도록)
        String normalizedInput = input.replace("\\n", "\n");

        try {
            // 사용자가 입력한 문자열 유효성 검증
            InputValidator.validateOrThrow(normalizedInput);

            // 유효한 문자열 계산
            int result = StringCalculator.calculate(normalizedInput);

            // 결과 출력
            System.out.println("결과 : " + result);
        } catch (IllegalArgumentException e) {
            // 사용자 입력오류를 처리하여 안내 메시지 출력 후 애플리케이션을 정상 종료
            System.out.println("잘못된 입력입니다: " + e.getMessage());
        } finally {
            // 입력 리소스 정리
            Console.close();
        }

    }
}
