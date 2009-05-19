//
//
// Copyright 2009 Ronald Brill
// All rights reserved.
//

package com.googlepages.dronten.jripper.util;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * @author rbri
 */
public class StringUtilTest  extends TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }

    public static Test suite() {
        return new TestSuite(StringUtilTest.class);
    }

    public void test() {
    	String tmpInput = "È,É,Ê,Ë,Û,Ù,Ï,Î,À,Â,Ä,Ô,Ö,è,é,ê,ë,û,ù,ü,ï,î,à,â,ô,ç";
    	assertEquals("E,E,E,E,U,U,I,I,A,A,Ae,O,Oe,e,e,e,e,u,u,ue,i,i,a,a,o,c", StringUtil.convertNonAscii(tmpInput));
    }
}
