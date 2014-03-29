package com.djmedia.maifang51.test;

import android.test.InstrumentationTestCase;

/**
 * Created by rd on 14-3-16.
 */
public class SearchApartmentFragmentTest extends InstrumentationTestCase {
    /**
     * All test methods MUST start with the “test-” prefix or
     * Android Studio will not detect them as tests and
     * you will get all kinds of weird errors and nothing will work.
     */

    public void testPass() throws Exception {
        final int expected = 1;
        final int reality = 1;
        assertEquals(expected, reality);
    }

    public void testFailed() throws Exception {
        final int expected = 1;
        final int reality = 1;
        assertEquals(expected, reality);
    }
}
