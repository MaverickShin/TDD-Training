package ch01;

public class PasswordStrengthMeter {

    public PasswordStrength meter(String s) {
        // return PasswordStrength.STRONG; // 첫번째 테스트용
        // return PasswordStrength.NORMAL; // 두번째 테스트용

        // 네 번째, 다섯 번재 코드를 통과하기 위한 코드 (NULL, "" 체크)
        if(s == null || s.isEmpty()) return PasswordStrength.INVALID;

        // 첫번째, 두번째 코드를 동시 통과하기 위한 코드
        /*if(s.length() < 8 ) {
            return PasswordStrength.NORMAL;
        }*/

        /*
        // 첫번째, 두번째, 일곱번째 코드를 통과하기 위한 코드
        boolean lengthCheck = s.length() >= 8;
        // 세 번째 코드를 통과시키기 위한 코드 : 각 문자를 비교해서 0~9 사이의 값을 갖는 문자가 없으면 NORMAL을 리턴하도록 한다.
        boolean containNum = isContainNum(s);
        // 대문자 검증
        boolean containUpper = isUpperCase(s);
        */

        /*
        // 일곱 번재 코드 검증 : 길이가 8자 이상이지만 나머지를 충족하지 않는 경우
        if (lengthCheck && !containNum && !containUpper) {
            return PasswordStrength.WEAK;
        }

        // 여덟 번째 테스트 : 숫자 포함 조건만 충족하는 경우
        if (!lengthCheck && containNum && !containUpper) {
            return PasswordStrength.WEAK;
        }

        // 아홉 번째 테스트 : 대문자 포함 조건만 충족하는 경우
        if (!lengthCheck && !containNum && containNum) {
            return PasswordStrength.WEAK;
        }

        // 나머진 충족하지만 길이가 8글자가 아닌 경우
        if (!lengthCheck) {
            return PasswordStrength.NORMAL;
        }
        // 문자에 0~9 사이의 값을 갖는 문자가 없으면 NORMAL
        if (!containNum || !containUpper) return PasswordStrength.NORMAL;
        */


        // 1-1 리팩토링 : 길이, 숫자, 대문자 포함여부를 이미 true/false 형태로 알아낼 수 있기 때문에
        //               3개 중 몇개를 통과했는지 카운트 할 수 있다.
        int metCounts = getMetCriteriaCounts(s);

        // 1-2 리팩토링 : 조건을 복잡하게 할 필요 없이 처음 검증에서 count 1이하 이면 WEAK 라고 알아 낼 수 있고
        //               count 1이하 조건을 지나쳤다면 이미 조건을 2개 통과 했다는 뜻이기에 아래처럼 어떤 조건을 불충족 했는지만 알면 된다.

        // 검증이 하나만 통과한 경우
        /*if(metCounts <= 1) return PasswordStrength.WEAK;
        // 비밀번호 길이만 8자 이상인 경우
        if(!lengthCheck) return PasswordStrength.NORMAL;
        // 숫자만 포함한 경우
        if(!containNum) return PasswordStrength.NORMAL;
        // 대문자만 포함한 경우
        if(!containUpper) return PasswordStrength.NORMAL;*/

        // 2차 리펙토링 : 1-2 리팩토링 과정은 사실 필요없다. 왜냐하면 이미 조건이 2개 통과된 상태에서
        //              마지막 3번째에 어떤 조건에서 불충족 하는지에 대한 검증인데,
        //              1-2에서 별도로 확인할 필요없이 1-1에서 이미 알 수 있기 때문이다.
        if(metCounts <= 1) return PasswordStrength.WEAK;
        if(metCounts == 2) return PasswordStrength.NORMAL;

        return PasswordStrength.STRONG;
    }

    // 숫자포함 여부 메소드
    private boolean isContainNum(String s) {
        for (char ch : s.toCharArray()) {
            // 문자에 0~9 사이의 값을 갖는 문자가 있는지 검증
            if (ch >= '0' && ch <= '9') {
                return true;
            }
        }
        return false;
    }

    // 대문자 포함여부 메소드
    private boolean isUpperCase(String s) {
        for (char ch : s.toCharArray()) {
            // 문자 중에 하나라도 대문자를 포함하고 있으면 true 반환
            if (Character.isUpperCase(ch)) {
                return true;
            }
        }
        return false;
    }

    // 조건 충족 갯수 구하는 메소드
    private int getMetCriteriaCounts(String s) {
        int metCounts = 0;
        if(s.length() >= 8) metCounts++;
        if(isContainNum(s)) metCounts++;
        if(isUpperCase(s)) metCounts++;
        return metCounts;
    }
}
