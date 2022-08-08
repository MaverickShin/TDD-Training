package ch02;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.YearMonth;

import static org.junit.jupiter.api.Assertions.*;

// 테스트 작성 순서 연습
public class ExpiryDateCalculatorTest {

    // 서비스의 규칙
    // - 서비스를 사용하려면 매달 1만원을 선불로 남부한다. 납부일 기준으로 한 달 뒤가 서비스 만료일이 된다.
    // - 2개월 이상 요금을 납부할 수 있다.
    // - 10만 원을 납부하면 서비스를 1년 제공한다.

    // 쉬원 것부터 테스트 : 구현하기 쉬운 것부터 먼저 테스트 -> 예외 상황을 먼저 테스트
    @Test
    void 만원_납부하면_한달_뒤가_만료일이_됨() {
        /*LocalDate billingDate = LocalDate.of(2022, 8, 7);
        int payAmount = 10_000;

        ExpiryDateCalculator cal = new ExpiryDateCalculator();
        LocalDate expiryDate = cal.calculateExpiryDate(billingDate, payAmount);

        assertEquals(LocalDate.of(2022,9,7), expiryDate);

        LocalDate billingDate2 = LocalDate.of(2022, 9, 7);
        int payAmount2 = 10_000;

        ExpiryDateCalculator cal2 = new ExpiryDateCalculator();
        LocalDate expiryDate2 = cal2.calculateExpiryDate(billingDate2, payAmount2);

        assertEquals(LocalDate.of(2022,10,7), expiryDate2);*/

        assertPayExpiryDate(
                PayData.builder()
                        .billingDate(LocalDate.of(2022, 8, 7))
                        .payAmount(10_000)
                        .build(),
                LocalDate.of(2022, 9, 7)
        );
    }

    // 코드 정리 : 중복제거
    /*@Test
    void 만원_납부하면_한달_뒤가_만료일이_됨2() {
        assertExpiryDate(LocalDate.of(2022,8,7),
                10_000, LocalDate.of(2022,9,7));
        assertExpiryDate(LocalDate.of(2022,9,10),
                10_000, LocalDate.of(2022,10,10));

    }*/

    // 다음 결제일 비교 메소드
    /*private void assertExpiryDate(LocalDate billingDate, int payAmount, LocalDate expectedExpiryDate) {
        ExpiryDateCalculator cal =  new ExpiryDateCalculator();
        // 배포대상 메소드에서 반환되는 다음결제일을 구함 ('결제일', '결제금액') 전달
        LocalDate realExpiryDate = cal.calculateExpiryDate(billingDate, payAmount);
        // 예상 다음 결제일과 메소드에서 반환된 다음결제일 비교
        assertEquals(expectedExpiryDate, realExpiryDate);
    }*/

    // 예외 상황 처리
    // - 납부일이 2022-01-31이고 납부액이 1만 원이면 만료일은 2022-02-28
    // - 납부일이 2022-05-31이고 납부액이 1만 원이면 만료일은 2022-06-30
    // - 남부일이 2020-01-31이고 납부액이 1만 원이면 만료일은 2020-02-29
    @Test
    void 납부일과_한달_뒤_일자가_같지_않음() {
        /*assertExpiryDate(
                LocalDate.of(2022,1,31), 10_000,
                LocalDate.of(2022,2,28));
        assertExpiryDate(
                LocalDate.of(2022,5,31), 10_000,
                LocalDate.of(2022,6,30));
        assertExpiryDate(
                LocalDate.of(2020,1,31), 10_000,
                LocalDate.of(2020,2,29));*/
        assertPayExpiryDate(
                PayData.builder()
                        .billingDate(LocalDate.of(2022,1,31))
                        .payAmount(10_000)
                        .build(),
                LocalDate.of(2022,2,28)
        );
        assertPayExpiryDate(
                PayData.builder()
                        .billingDate(LocalDate.of(2022,5,31))
                        .payAmount(10_000)
                        .build(),
                LocalDate.of(2022,6,30)
        );
        assertPayExpiryDate(
                PayData.builder()
                        .billingDate(LocalDate.of(2020,1,31))
                        .payAmount(10_000)
                        .build(),
                LocalDate.of(2020,2,29)
        );
    }

    // 리팩토링 대비 메소드
    private void assertPayExpiryDate(PayData payData, LocalDate expectedExpiryDate) {
        ExpiryDateCalculator cal = new ExpiryDateCalculator();
        LocalDate realExpiryDate = cal.paycalculateExpiryDate(payData);
        assertEquals(expectedExpiryDate, realExpiryDate);
    }

    // 첫 납부일과 만료일의 일자가 같지 않을 때 1만 원 납부하면 첫 납부일 기준으로 다음 만료일 정함
    @Test
    void 첫_납부일과_만료일_일자가_다를때_만원_납부(){
        PayData payData = PayData.builder()
                .firstBillingDate(LocalDate.of(2022, 1, 31))
                .billingDate(LocalDate.of(2022, 2, 28))
                .payAmount(10_000)
                .build();

        assertPayExpiryDate(payData, LocalDate.of(2022,3,31));

        PayData payData2 = PayData.builder()
                .firstBillingDate(LocalDate.of(2022, 1, 29))
                .billingDate(LocalDate.of(2022, 2, 28))
                .payAmount(10_000)
                .build();

        assertPayExpiryDate(payData2, LocalDate.of(2022,3,29));

        PayData payData3 = PayData.builder()
                .firstBillingDate(LocalDate.of(2022, 5, 31))
                .billingDate(LocalDate.of(2022, 6, 30))
                .payAmount(10_000)
                .build();

        assertPayExpiryDate(payData3, LocalDate.of(2022,7,31));
    }


    // 다시 예외 상황 처리
    // - 2만 원을 지불하면 만료일이 두 달 뒤가 된다.
    // - 3만 원을 지불하면 만료일이 세 달 뒤가 된다.
    @Test
    void 이만원_이상_납부하면_비례해서_만료일_계산() {
        assertPayExpiryDate(
                PayData.builder()
                        .billingDate(LocalDate.of(2022, 3, 1))
                        .payAmount(20_000)
                        .build(),
                LocalDate.of(2022,5,1)
        );

        assertPayExpiryDate(
                PayData.builder()
                        .billingDate(LocalDate.of(2022, 3, 1))
                        .payAmount(30_000)
                        .build(),
                LocalDate.of(2022,6,1)
        );
    }

    // 첫 납부일과 납부일의 일자가 다를 때 2만원 이상 납부
    @Test
    void 첫_납부일과_만료일_일자가_다를때_이만원_이상_납부() {
        assertPayExpiryDate(
                PayData.builder()
                        .firstBillingDate(LocalDate.of(2022, 1, 31))
                        .billingDate(LocalDate.of(2022, 2, 28))
                        .payAmount(20_000)
                        .build()
                , LocalDate.of(2022, 4, 30)
        );

        assertPayExpiryDate(
                PayData.builder()
                        .firstBillingDate(LocalDate.of(2022, 1, 31))
                        .billingDate(LocalDate.of(2022, 2, 28))
                        .payAmount(30_000)
                        .build()
                , LocalDate.of(2022, 5, 31)
        );
    }

    // 10개월 요금을 납부하면 1년 제공
    @Test
    void 십만원을_납부하면_1년_제공() {
        assertPayExpiryDate(
                PayData.builder()
                        .billingDate(LocalDate.of(2022, 1, 28))
                        .payAmount(100_000)
                        .build()
                , LocalDate.of(2023, 1, 28)
        );
    }

    //

}
