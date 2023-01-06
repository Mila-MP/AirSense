package GetData;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class GetAdviceTest {

    @Test
    void print1() throws IOException {
        GetAdvice advice = new GetAdvice(1);
        assertEquals("Enjoy your usual outdoor activities.",
                advice.print());
    }

    @Test
    void print2() throws IOException {
        GetAdvice advice = new GetAdvice(2);
        assertEquals("Enjoy your usual outdoor activities.",
                advice.print());
    }

    @Test
    void print3() throws IOException {
        GetAdvice advice = new GetAdvice(3);
        assertEquals("Enjoy your usual outdoor activities.",
                advice.print());
    }

    @Test
    void print4() throws IOException {
        GetAdvice advice = new GetAdvice(4);
        assertEquals("Adults and children with lung problems, and adults with" +
                        " heart problems, who experience symptoms, should consider" +
                        " reducing strenuous physical activity, particularly outdoors.",
                advice.print());
    }

    @Test
    void print5() throws IOException {
        GetAdvice advice = new GetAdvice(5);
        assertEquals("Adults and children with lung problems, and adults with" +
                        " heart problems, who experience symptoms, should consider" +
                        " reducing strenuous physical activity, particularly outdoors.",
                advice.print());
    }

    @Test
    void print6() throws IOException {
        GetAdvice advice = new GetAdvice(6);
        assertEquals("Adults and children with lung problems, and adults with" +
                        " heart problems, who experience symptoms, should consider" +
                        " reducing strenuous physical activity, particularly outdoors.",
                advice.print());
    }

    @Test
    void print7() throws IOException {
        GetAdvice advice = new GetAdvice(7);
        assertEquals("Adults and children with lung problems, and adults with" +
                        " heart problems, should reduce strenuous physical exertion," +
                        " particularly outdoors, and particularly if they experience symptoms." +
                        " People with asthma may find they need to use their reliever inhaler more often." +
                        " Older people should also reduce physical exertion.",
                advice.print());
    }

    @Test
    void print8() throws IOException {
        GetAdvice advice = new GetAdvice(8);
        assertEquals("Adults and children with lung problems, and adults with" +
                        " heart problems, should reduce strenuous physical exertion," +
                        " particularly outdoors, and particularly if they experience symptoms." +
                        " People with asthma may find they need to use their reliever inhaler more often." +
                        " Older people should also reduce physical exertion.",
                advice.print());
    }

    @Test
    void print9() throws IOException {
        GetAdvice advice = new GetAdvice(9);
        assertEquals("Adults and children with lung problems, and adults with" +
                        " heart problems, should reduce strenuous physical exertion," +
                        " particularly outdoors, and particularly if they experience symptoms." +
                        " People with asthma may find they need to use their reliever inhaler more often." +
                        " Older people should also reduce physical exertion.",
                advice.print());
    }

    @Test
    void print10() throws IOException {
        GetAdvice advice = new GetAdvice(10);
        assertEquals("Adults and children with lung problems, adults with heart" +
                        " problems, and older people, should avoid strenuous physical activity." +
                        " People with asthma may find they need to use their reliever inhaler more often.",
                advice.print());
    }
}