@ECHO OFF
REM ----------------
REM jRipper
REM
REM RBRi 2008..2010
REM ----------------

SETLOCAL

REM ---------------------------------------------------------
REM overwrite your system JAVA_HOME if you like
REM ---------------------------------------------------------
rem SET JAVA_HOME=D:\progs\java141
rem SET JAVA_HOME=D:\progs\java142


REM -------------------------
REM select your Look&Feel
REM -------------------------
SET LAF=com.jgoodies.looks.plastic.PlasticLookAndFeel
SET LAF=com.jgoodies.looks.plastic.Plastic3DLookAndFeel
rem SET LAF=com.jgoodies.looks.plastic.PlasticXPLookAndFeel


REM ---------------------------------------------------------
REM make more memory available if needed
REM ---------------------------------------------------------
set JAVA_OPT=
rem JAVA_OPT=%JAVA_OPT% "-Xmx512m"

REM ---------------------------------------------------------
REM VM Options for GoldenCode JDK
REM    noerasebackground is needed to avoid flicker of some
REM    dialogs
REM
REM    it is possible to hide the command windows
REM ---------------------------------------------------------
SET JAVA_OPT=%JAVA_OPT% "-Dsun.awt.noerasebackground=true"
rem SET JAVA_OPT=%JAVA_OPT% "-XprocessLaunchingOptions=20"


REM ---------------------------------------------------------
REM use java instead of javaw to see error messages
REM on the console
REM ---------------------------------------------------------
SET JAVA_EXE=javaw.exe
rem SET JAVA_EXE=java.exe


IF "%JAVA_HOME%" == "" goto MSG_NOHOME
IF NOT EXIST %JAVA_HOME%\jre\bin\%JAVA_EXE% GOTO MSG_NOJAVA
%JAVA_HOME%\jre\bin\%JAVA_EXE% %JAVA_OPT% -cp .\jripper_os2.jar;.\looks-2.3.1.jar; com/googlepages/dronten/jripper/JRipper %LAF%

GOTO END

:MSG_NOHOME
ECHO **************************************************
ECHO *                                                *
ECHO * Please set the environment variable JAVA_HOME  *
ECHO *                                                *
ECHO * You can set it for your system via config.sys  *
ECHO * or set it in the file jripper.cmd              *
ECHO *                                                *
ECHO **************************************************
PAUSE
GOTO END

:MSG_NOJAVA
ECHO **************************************************
ECHO *                                                *
ECHO * Your JAVA_HOME/JAVA_EXE are invalid            *
ECHO * File not found:                                *
ECHO *   '%JAVA_HOME%\jre\bin\%JAVA_EXE%'
ECHO *                                                *
ECHO **************************************************
PAUSE
GOTO END

:END
ENDLOCAL

