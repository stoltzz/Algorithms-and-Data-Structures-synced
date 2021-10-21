package hjelpeklasser;

import static org.junit.jupiter.api.Assertions.*;

class TabellTest {

    @org.junit.jupiter.api.Test
    void nestMaks4() {

        int[] a = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        Tabell.nestMaks4(a);

        int[] exp = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        assertArrayEquals(exp, a);
    }
}