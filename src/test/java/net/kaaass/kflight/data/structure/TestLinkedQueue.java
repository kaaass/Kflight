package net.kaaass.kflight.data.structure;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.*;

@SpringBootTest
public class TestLinkedQueue {

    @Test
    public void testPushPop() {
        var Q = new LinkedQueue<Integer>();

        assertEquals(0, Q.size());
        assertNull(Q.popFront());

        Q.push(1);
        Q.push(2);
        Q.popFront();
        Q.push(3);

        assertEquals(2, Q.size());
        assertEquals(Integer.valueOf(2), Q.popFront());
        assertEquals(1, Q.size());
        assertEquals(Integer.valueOf(3), Q.popFront());
        assertEquals(0, Q.size());

        Q.popFront();
        Q.popFront();

        assertEquals(0, Q.size());
    }

    @Test
    public void testFrontBack() {
        var Q = new LinkedQueue<Integer>();

        Q.push(1);
        Q.push(2);
        Q.push(3);

        assertEquals(Integer.valueOf(1), Q.front());
        assertEquals(Integer.valueOf(3), Q.back());

        Q.popFront();
        Q.popFront();

        assertEquals(Integer.valueOf(3), Q.front());
        assertEquals(Integer.valueOf(3), Q.back());

        Q.popFront();

        assertNull(Q.front());
        assertNull(Q.back());
    }
}
