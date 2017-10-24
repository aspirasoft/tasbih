package pk.aspirasoft.tasbih.data;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CounterManagerTest {

    @Test
    public void stringConversionTest() throws Exception {
        CounterManager manager = CounterManager.getInstance(null);

        // Create two counters
        Counter counter1 = new Counter("Test 1", "This is a test counter.");
        counter1.setValue(100);

        Counter counter2 = new Counter("Test 2", "This is second test counter.");
        counter2.setValue(200);

        // Add two counters
        manager.add(counter1);
        manager.add(counter2);
        assertEquals(manager.size(), 2);

        // Save instance with two counters
        String savedInstance = manager.toString();
        System.out.print(savedInstance);

        // Add third counters
        manager.add(new Counter("Test 3", "This is another test."));
        assertEquals(manager.size(), 3);

        // Restore saved instance
        manager.fromString(savedInstance);
        assertEquals(manager.size(), 2);

        // Check contents of both counters
        assertEquals(manager.get(0).getValue(), 100);
        assertEquals(manager.get(1).getValue(), 200);
    }
}