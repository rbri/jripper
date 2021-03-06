//
//
// Copyright 20089 Ronald Brill
// All rights reserved.
//

package com.googlepages.dronten.jripper.util;

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

        TestSuite suite = new TestSuite("All cdda2wav tests");

        suite.addTest(StringUtilTest.suite());

        return suite;
    }
}
