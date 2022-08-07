package ch01;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// 덧셈 기능을 검증하기 위한 테스트 코드
public class CalculatorTest {

    @Test
    void plus() {
        int result = Calculator.plus(1, 2);
        assertEquals(3, result);
        assertEquals(5, Calculator.plus(4,1));
    }
}
