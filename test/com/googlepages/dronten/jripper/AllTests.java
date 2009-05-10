//
//
// Copyright 20089 Ronald Brill
// All rights reserved.
//

package com.googlepages.dronten.jripper;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author rbri
 */
public class AllTests {
    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }

    public static Test suite() {

        TestSuite suite = new TestSuite("All jRipper tests");

        suite.addTest(com.googlepages.dronten.jripper.cdda2wav.AllTests.suite());

        return suite;
    }
}
