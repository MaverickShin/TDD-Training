package ch01;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// 암호 검사기 테스트 클래스
public class PasswordStrengthMeterTest {

    private PasswordStrengthMeter meter = new PasswordStrengthMeter();

    // 코드 가독성을 높히기 위한 메소드 생성
    private void assertStrength(String password, PasswordStrength passwordStrength) {
        PasswordStrength reulst = meter.meter(password);
        assertEquals(passwordStrength, reulst);
    }

    @Test
    void Strong_TEST() {

        // 첫 번째 테스트 : 모든 규칙을 충족하는 경우
//        PasswordStrength result = meter.meter("ab12!@AB");
//        assertEquals(PasswordStrength.STRONG, result);
        // 코드 가독성 높히기
        assertStrength("ab12!@AB", PasswordStrength.STRONG);


        // STRONG과 result2가 같은 값인지 비교
//        PasswordStrength result2 = meter.meter("abc1!Add");
//        assertEquals(PasswordStrength.STRONG, result2);
        // 코드 가독성 높히기
        assertStrength("abc1!Add", PasswordStrength.STRONG);
    }

    @Test
    void Normal_TEST() {

        // 두 번째 테스트 : 길이만 8글자 미만이고 나머지 조건은 충족하는 경우
//        PasswordStrength result = meter.meter("ab12!@A");
//        assertEquals(PasswordStrength.NORMAL, result);

        // 코드 가독성 높히기
        assertStrength("ab12!@A", PasswordStrength.NORMAL);
    }

    @Test
    void Normal_TEST2() {

        // 세 번재 테스트 : 숫자를 포함하지 않고 나머지 조건은 충족하는 경우

//        PasswordStrength result = meter.meter("ab!@ABqwer");
//        assertEquals(PasswordStrength.NORMAL, result);

        // 코드 가독성 높히기
        assertStrength("ab!@ABqwer", PasswordStrength.NORMAL);
    }

    @Test
    void Null_TEST() {
        // 네 번째 테스트 : NULL 체크
        assertStrength(null, PasswordStrength.INVALID);
    }

    @Test
    void Empty_TEST() {
        // 다섯 번째 테스트 : "" 체크
        assertStrength("", PasswordStrength.INVALID);
    }

    @Test
    void Uppercase_TEST() {
        // 여섯 번재 테스트 : 대문자를 포함하지 않고 나머지 조건을 충족하는 경우
        assertStrength("ab12!@df", PasswordStrength.NORMAL);
    }

    @Test
    void Length_TEST() {
        // 일곱 번째 테스트 : 길이가 8글자 이상인 조건만 충족하는 경우
        assertStrength("abcdefghi", PasswordStrength.WEAK);
    }

    @Test
    void Number_TEST() {
        // 여덟 번재 테스트 : 숫자 포함 조건만 충족하는 경우
        assertStrength("12345", PasswordStrength.WEAK);
    }

    @Test
    void only_Upper_TEST() {
        // 아홉 번째 테스트 : 대문자 포함 조건만 충족하는 경우
        assertStrength("ABZEE", PasswordStrength.WEAK);
    }

    @Test
    void not_match_TEST() {
        // 열번째 테스트 : 아무 조건도 충족하지 않은 경우
        assertStrength("abc", PasswordStrength.WEAK);
    }

}
