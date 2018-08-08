package pk.aspirasoft.tasbih.data;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CounterTest {

    @Test
    public void stringConversionTest() throws Exception {
        Counter counter1 = new Counter("Test", "This is a test counter.");
        Counter counter2 = new Counter(counter1.toString());

        assertEquals(counter2.getName(), "Test");
        assertEquals(counter2.getDescription(), "This is a test counter.");
        assertEquals(counter2.getValue(), 0);
    }

    @Test
    public void negativeValueTest() throws ArithmeticException {
        Counter counter = new Counter("Test", "This is a test counter.");

        try {
            counter.setValue(-100);
        } catch (ArithmeticException ignored) {

        } finally {
            assertEquals(counter.getValue(), 0);
        }

        counter.decrement();
        assertEquals(counter.getValue(), 0);
    }
}