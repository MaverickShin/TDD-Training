package ch02;

import java.time.LocalDate;
import java.time.YearMonth;

public class ExpiryDateCalculator {

    // 납부액이 1만원 일 때 고정 메소드
    public LocalDate calculateExpiryDate(LocalDate date, int pay) {
        // return LocalDate.of(2022,9,7);
        return date.plusMonths(1);
    }


    /**
     * 1. billingDate와 paymentAmount를 갖는 PayData 클래스를 만든다.
     * 2. PayData를 사용하는 새로운 calculateExpiryDate() 메서드를 추가한다.
     * 3. 기존 calculateExpiryData() 메서드가 새로 만든 calculateExpiryDate() 메서드를
     *    호출하도록 변경한다. (테스트에서 실행해서 통과 확인)
     * 4. 새로 만든 calculateExpiryDate() 메서드를 사용하는 assertExpiryDate9) 메서드를 테스트 클래스에 새로 추가한다.
     * 5. 테스트 클래스의 기존 assertExpiryDate() 메서드가 사용하는 assertExpiryDate() 메서드를 호출하도록 변경
     * 6. 테스트의 기존 assertExpiryDate() 메소드를 인라인 처리하여 각 테스트 코드가 새로 만든 assertExpiryDate9) 메서드를 호출하도록 변경
     * 7. 더는 사용되니 않는 기존 calculateExpriryDate() 메서드를 삭제한다.
     */

    // 납부액에 따라 다음 결제일이 변경되는 메소드
    public LocalDate paycalculateExpiryDate(PayData payData) {

        // 전달 받은 금액에서 만원을 나눈 수를 다음 만료월 수로 사용한다.
        int addedMonths = payData.getPayAmount() / 10_000;

        if (payData.getFirstBillingDate() != null) {
            // 첫 납부일과 만료일의 일자가 같지 않을 때 n만 원 납부하면 첫 납부일 기준으로 다음 만료일 정하기 위한 조건

            // 다음 청구일(첫 납부일 이상)로 부터 만료일을 구한다.
            LocalDate candidateExp = payData.getBillingDate().plusMonths(addedMonths);

            // 첫 납부일의 일자와 만료일의 일자를 비교한다.
            if (payData.getFirstBillingDate().getDayOfMonth() != candidateExp.getDayOfMonth()) {

                // 첫 납부일의 일자가 만료월의 마지막 일자보다 클 때
                if(YearMonth.from(candidateExp).lengthOfMonth() <
                        payData.getFirstBillingDate().getDayOfMonth()) {
                    // 만료월의 마지막 일자를 반환
                    return candidateExp.withDayOfMonth(YearMonth.from(candidateExp).lengthOfMonth());
                }

                // 그 외 첫 납부일 일자와 만료일의 일자가 다를 때
                // withDayOfMonth() 메소드를 통해 첫 납부일의 일자를 넘겨주어 해당 월(만료월)의 일자로 반환한다.
                // 예) 첫 납부일이 1-10일이고 다음 청구일(2월)로 부터 만료월이 3월이면 3-10일을 반환한다.
                return candidateExp.withDayOfMonth(payData.getFirstBillingDate().getDayOfMonth());
            }
        }
        return payData.getBillingDate().plusMonths(addedMonths);
    }

}
