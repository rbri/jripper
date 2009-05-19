/*
* Copyright 2004-2009 by rbri
*
* This source is distributed under the terms of the GNU PUBLIC LICENSE version 3
* http://www.gnu.org/licenses/gpl.html
*/

package com.googlepages.dronten.jripper.util;



/**
 * Some StringUtil's
 */
public class StringUtil {
	
	private static final String[] PLAIN_ASCII = new String[] {
		    "A", "a", "E", "e", "I", "i", "O", "o", "U", "u", // grave
			"A", "a", "E", "e", "I", "i", "O", "o", "U", "u", "Y", "y", // acute
			"A", "a", "E", "e", "I", "i", "O", "o", "U", "u", "Y", "y", // circumflex
			"A", "a", "O", "o", "N", "n", // tilde
			"Ae", "ae", "E", "e", "I", "i", "Oe", "oe", "Ue", "ue", "Y", "y", // umlaut
			"A", "a", // ring
			"C", "c", // cedilla
			"O", "o", "U", "u" // double acute
		}
	;

	private static final String UNICODE = 
		      "\u00C0\u00E0\u00C8\u00E8\u00CC\u00EC\u00D2\u00F2\u00D9\u00F9"
			+ "\u00C1\u00E1\u00C9\u00E9\u00CD\u00ED\u00D3\u00F3\u00DA\u00FA\u00DD\u00FD"
			+ "\u00C2\u00E2\u00CA\u00EA\u00CE\u00EE\u00D4\u00F4\u00DB\u00FB\u0176\u0177"
			+ "\u00C3\u00E3\u00D5\u00F5\u00D1\u00F1"
			+ "\u00C4\u00E4\u00CB\u00EB\u00CF\u00EF\u00D6\u00F6\u00DC\u00FC\u0178\u00FF"
			+ "\u00C5\u00E5" + "\u00C7\u00E7" + "\u0150\u0151\u0170\u0171";

	// remove accentued from a string and replace with ascii equivalent
	public static String convertNonAscii(String tmpString) {
		if (tmpString == null) {
			return null;
		}
		StringBuilder tmpResult = new StringBuilder();
		int n = tmpString.length();
		for (int i = 0; i < n; i++) {
			char tmpChar = tmpString.charAt(i);
			int pos = UNICODE.indexOf(tmpChar);
			if (pos > -1) {
				tmpResult.append(PLAIN_ASCII[pos]);
			} else {
				tmpResult.append(tmpChar);
			}
		}
		return tmpResult.toString();
	}
}
