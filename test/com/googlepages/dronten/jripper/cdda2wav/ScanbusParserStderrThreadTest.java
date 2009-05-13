//
//
// Copyright 2009 Ronald Brill
// All rights reserved.
//

package com.googlepages.dronten.jripper.cdda2wav;

import java.util.Vector;

import com.googlepages.dronten.jripper.util.Log;
import com.googlepages.dronten.jripper.util.Progress;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * @author rbri
 */
public class ScanbusParserStderrThreadTest  extends TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }

    public static Test suite() {
        return new TestSuite(ScanbusParserStderrThreadTest.class);
    }

    public void testParseNothing() throws Exception {
    	ScanbusParserStderrThread tmpParser = new ScanbusParserStderrThread(new Log(0), new Progress());

    	tmpParser.data("scsibus0:");
    	
    	Vector<String> tmpDevices = tmpParser.getDevices();
        
    	assertEquals(1, tmpDevices.size());
    	assertEquals("--- Probed Devices ---", tmpDevices.get(0));
    }


    public void testParseOne() throws Exception {
    	ScanbusParserStderrThread tmpParser = new ScanbusParserStderrThread(new Log(0), new Progress());

    	tmpParser.data("scsibus0:");
    	tmpParser.data("        0,0,0     0) '_NEC    ' 'DVD_RW ND-3540A ' '1.01' Removable CD-ROM");
    	
    	Vector<String> tmpDevices = tmpParser.getDevices();
        
    	assertEquals(2, tmpDevices.size());
    	assertEquals("--- Probed Devices ---", tmpDevices.get(0));
    	assertEquals("0,0,0 - '_NEC    ' 'DVD_RW ND-3540A ' '1.01' Removable CD-ROM", tmpDevices.get(1));
    }


    public void testParseTwo() throws Exception {
    	ScanbusParserStderrThread tmpParser = new ScanbusParserStderrThread(new Log(0), new Progress());

    	tmpParser.data("scsibus0:");
    	tmpParser.data("        0,0,0     0) '_NEC    ' 'DVD_RW ND-3540A ' '1.01' Removable CD-ROM");
    	tmpParser.data("scsibus1:");
    	tmpParser.data("        1,0,0   100) 'COMPAQ  ' 'CD-ROM CRD-8484B' '1.04' Removable CD-ROM");
    	tmpParser.data("        1,1,0   101) *");
		tmpParser.data("        1,2,0   102) *");
		tmpParser.data("        1,3,0   103) *");
		tmpParser.data("        1,4,0   104) *");
		tmpParser.data("        1,5,0   105) *");
		tmpParser.data("        1,6,0   106) *");
		tmpParser.data("        1,7,0   107) *");    	
    	Vector<String> tmpDevices = tmpParser.getDevices();
        
    	assertEquals(3, tmpDevices.size());
    	assertEquals("--- Probed Devices ---", tmpDevices.get(0));
    	assertEquals("0,0,0 - '_NEC    ' 'DVD_RW ND-3540A ' '1.01' Removable CD-ROM", tmpDevices.get(1));
    	assertEquals("1,0,0 - 'COMPAQ  ' 'CD-ROM CRD-8484B' '1.04' Removable CD-ROM", tmpDevices.get(2));
    }


    public void testParseOS2() throws Exception {
    	ScanbusParserStderrThread tmpParser = new ScanbusParserStderrThread(new Log(0), new Progress());

    	tmpParser.data("scsibus0:");
    	tmpParser.data("\t0,0,0\t  0) '_NEC    ' 'DVD_RW ND-3540A ' '1.01' Removable CD-ROM");
		tmpParser.data("\t0,1,0\t  1) *");    	
		tmpParser.data("\t0,2,0\t  2) *");    	
		tmpParser.data("\t0,3,0\t  3) *");    	
		tmpParser.data("\t0,4,0\t  4) *");    	
		tmpParser.data("\t0,5,0\t  5) *");    	
		tmpParser.data("\t0,6,0\t  6) *");    	
		tmpParser.data("\t0,7,0\t  7) *");    	
    	Vector<String> tmpDevices = tmpParser.getDevices();
        
    	assertEquals(2, tmpDevices.size());
    	assertEquals("--- Probed Devices ---", tmpDevices.get(0));
    	assertEquals("0,0,0 - '_NEC    ' 'DVD_RW ND-3540A ' '1.01' Removable CD-ROM", tmpDevices.get(1));
    }
}
