REM -----------------------------
REM Make jRipper ready for java 1.4
REM with Retrotranslator
REM
REM RBRi 2006, 2008, 2010
REM -----------------------------

SET JAVA_HOME=%JAVA_HOME_GC_14%

SET TRANS_CLASSPATH=%JAVA_HOME%\jre\lib\rt.jar;
SET TRANS_CLASSPATH=%TRANS_CLASSPATH%%JAVA_HOME%\jre\lib\jce.jar;
SET TRANS_CLASSPATH=%TRANS_CLASSPATH%%JAVA_HOME%\jre\lib\jsse.jar;
SET TRANS_CLASSPATH=%TRANS_CLASSPATH%%RETROTRANS%\retrotranslator-runtime-1.2.9.jar;
SET TRANS_CLASSPATH=%TRANS_CLASSPATH%%RETROTRANS%\backport-util-concurrent-3.1.jar;
SET TRANS_CLASSPATH=%TRANS_CLASSPATH%.\helper;


SET TRANS_BACKPORT=
SET TRANS_BACKPORT=%TRANS_BACKPORT%java.awt.Component.setPreferredSize:de.rbri.jdk14.ComponentUtil.setPreferredSize;
SET TRANS_BACKPORT=%TRANS_BACKPORT%java.awt.Component.setMaximumSize:de.rbri.jdk14.ComponentUtil.setMaximumSize;



SET TRANS_EMBED=net.sf.retrotranslator

%JAVA_HOME%\jre\bin\java.exe -jar %RETROTRANS%\retrotranslator-transformer-1.2.9.jar -srcjar .\jripper_1_02_1.jar -destjar .\jripper\jripper_os2.jar -target 1.4 -classpath %TRANS_CLASSPATH% -backport %TRANS_BACKPORT% -embed %TRANS_EMBED% -verify -smart

%JAVA_HOME%\bin\jar uvf .\jripper\jripper_os2.jar -C .\helper de\rbri\jdk14\ComponentUtil.class


