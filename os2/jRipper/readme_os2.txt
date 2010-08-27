
!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
!!!! USE AT YOUR OWN RISK !!!!
!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

THE SOFTWARE  IS  PROVIDED  "AS IS"  WITHOUT WARRANTY OF ANY KIND,
EITHER EXPRESS OR IMPLIED,  INCLUDING,  BUT NOT LIMITED TO IMPLIED
WARRANTIES   OF  MERCHANTABILITY  AND  FITNESS  FOR  A  PARTICULAR
PURPOSE.  THE ENTIRE RISK AS TO THE QUALITY AND PERFORMANCE OF THE
PROGRAM  IS  WITH  YOU.


Description
===========
This is adoption of jRipper (http://dronten.googlepages.com/jripper)
for OS/2 or eCS.
Use at your own risk.



Requirements:
=============
- OS/2 - eCS
- GC JDK 1.4.1 or Innotek JDK 1.4.2


Installation instructions:
  1. Adjust the path to your jdk in file jRipper.cmd
  2. Start jRipper.cmd from a command window.
  3. Adjust the paths in the setup dialog on page 'Program Paths'
  4. Adjust the CD Device in the setup dialog
     on page 'CD Reader Settings'
  5. Define the path to the output folder in
     the setup dialog on page 'General Options'
  6. Start encoding...

  If there is a problem, send a report to rbri@rbri.de
  If it works, send a report to rbri@rbri.de



History
=======

Version 1.02.1 (2010-08-26)

  Resync release with jRipper 1.02

  Changed:
   * resync to jRipper 1.02
   * automatic refresh of the log output panel if open
   * small improvements of the command file 
   * JGoodies looks updated to version 2.3.1

  Fixed:
   * some internal warnings fixed
   * some small things with the installer fixed



Version 1.0.1 (2009-05-21)

  This is my first bugfix release (because dronten has no time and/or motivation
  to work on jRipper at the moment)

  Changed:
   * title includes version number

  Fixed:
   * some more special characters are replaces by more saver ones during the generation of the file names
   * wrong naming for two setup pages
   * parsing for available cd drives was a bit to stupid
   * some internal warnings fixed



2009-04-05
 * improved command file to support GC JDK options to
   hide the started command windows
 * icon update with real background and border



2009-03-09
 * updated to jRipper 1.0
 * the original no longer contains the looks lib
    - this distributions contains again the latest version of this lib
 * the sources are no longer patched; the startup script is improved to
   support L&F switching
 * improved startup script
 * icon has now a transparent background
 * a WarpIn installer is now available



2008-08-07
 * updated to the latest version available
 * the original no longer contains the looks lib
    - this distributions contains again the latest version of this lib
    - the source is patched to make use of the lib if available



2008-03-01
 * first version



ToDo
====
  fix the bugs



    rbri
