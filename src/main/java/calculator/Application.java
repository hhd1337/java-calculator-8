package calculator;

import camp.nextstep.edu.missionutils.Console;

public class Application {
    public static void main(String[] args) {
        // 사용자 입력
        System.out.println("덧셈할 문자열을 입력해 주세요.");
        final String input = Console.readLine();

        // 입력 정규화 (리터럴 "\n"을 실제 개행으로 정규화)
        String normalizedInput = input.replace("\\n", "\n");

        try {
            //TODO: InputValidator에 사용자가 입력한 문자열 유효성 검증 위임

            // StringCalculator에 유효한 문자열 전달, 결과 반환받음
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
