package ch01;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class NoteHignLightTest {

    @Test
    void highlight() {

        assertThat(highlight("note")).isEqualTo("{note}");
        assertThat(highlight("1 note")).isEqualTo("1 {note}");
    }

    private String highlight(String str) {
        int idx = str.indexOf("note");

        // note가 포함되지 않으면 걍 반환
        if(idx == -1) {
            return str;
        }

        // idx 1을 뺀 값이 0보다 크거나 같을 때
        if(idx - 1 >= 0){
            char pre = str.charAt(idx - 1);

            if (pre == 'y' || pre == '1') {
                System.out.println("test : " + pre);
                return str;
            }
        }

        return str.replace("note","{note}");
    }
}
