/*
* Copyright 2004-2010 by dronten@gmail.com
*
* This source is distributed under the terms of the GNU PUBLIC LICENSE version 3
* http://www.gnu.org/licenses/gpl.html
*/


package com.googlepages.dronten.jripper;

import com.googlepages.dronten.jripper.gui.MainWindow;
import com.googlepages.dronten.jripper.util.Log;
import com.googlepages.dronten.jripper.util.Pref;
import com.googlepages.dronten.jripper.util.ThreadCollector;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.Properties;


/**
 * This is the application object.<br>
 * And also the where the applications starts from.
 */
public class JRipper extends JFrame {
    private static final long   serialVersionUID = 666L;
    private ThreadCollector     aThreads = new ThreadCollector();
    private static JRipper      JRIPPER;
    private MainWindow          aMainWindow = null;
    private Cursor              aDefaultCursor = null;
    private Cursor              aWaitCursor = null;


    /**
     * Create gui and try to restore size and position from last time.
     */
    public JRipper() {
        super(Constants.APP_NAME);
        JRIPPER = this;

        setTitle(null);
        Log.get().setLogLevel(Constants.LOG_LEVEL);
        new Pref(Constants.APP_NAME);
        aMainWindow = new MainWindow();
        aThreads.start();
        aDefaultCursor = new Cursor(Cursor.DEFAULT_CURSOR);
        aWaitCursor = new Cursor(Cursor.WAIT_CURSOR);

        if (Pref.getPref(Constants.CODEPAGE, "").length() > 0) {
            setCodePage(Pref.getPref(Constants.CODEPAGE, ""));
        }
        else {
            Log.get().addTime(1, "Current codepage:" + System.getProperty("file.encoding"));
        }

        setContentPane(aMainWindow);
        restoreWindow();
        aMainWindow.getMenuPanel().enableUI();
        setIcon("com/googlepages/dronten/jripper/resource/jRipper.icon.png");

        /**
         *
         */
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                saveWindow();
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    /**
     * Return application object.
     *
     * @return Application object
     */
    public static JRipper get() {
        return JRIPPER;
    }


    /**
     * Get thread collector
     *
     * @return The thread collector thread
     */
    public ThreadCollector getThreadCollector() {
        return aThreads;
    }


    /**
     * Return main panel.
     *
     * @return Main pamel object
     */
    public MainWindow getWin() {
        return aMainWindow;
    }


    /**
     * Add main panel and restore size.
     */
    public void restoreWindow() {
        double      w = Pref.getPref("w", 800.0);
        double      h = Pref.getPref("h", 600.0);
        double      x = Pref.getPref("x", 50.0);
        double      y = Pref.getPref("y", 50.0);
        Dimension   screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        if (x > screenSize.getWidth()) {
            x = 0;
        }

        if (y > screenSize.getHeight()) {
            y = 0;
        }

        pack();
        setSize((int) w, (int) h);
        setLocation((int) x, (int) y);
    }


    /**
     * Quit application.
     * Save last used path and screen position.
     */
    public void saveWindow() {
        aThreads.IS_RUNNING = false;

        try {
            Dimension size = getSize();
            Point pos = getLocation();

            Pref.setPref("w", Double.toString(size.getWidth()));
            Pref.setPref("h", Double.toString(size.getHeight()));
            Pref.setPref("x", Double.toString(pos.getX()));
            Pref.setPref("y", Double.toString(pos.getY()));
        }
        catch (Exception e) {
        }

        setVisible(false);
        dispose();
        System.exit(0);
    }


    /**
     * Set new codepage.<br>
     * It's the "file.encoding" system property.<br>
     * To do other encoding changes you must do it manually.<br>
     *
     * @param newCodePage New codepage
     */
    public void setCodePage(String newCodePage) {
        if (newCodePage.length() > 0 || Pref.getPref(Constants.CODEPAGE, "").length() > 0) {
            Pref.setPref(Constants.CODEPAGE, newCodePage);
            Log.get().addTime(1, "Old codepage:" + System.getProperty("file.encoding"));
            Properties pi = System.getProperties();
            pi.put("file.encoding", newCodePage);
            System.setProperties(pi);
            Log.get().addTime(1, "New codepage:" + System.getProperty("file.encoding"));
        }
    }


    /**
     * @param iconName Resource name of icon
     */
    public void setIcon(String iconName) {
        try {
            URL pathShell;
            ClassLoader cl = JRipper.class.getClassLoader();
            pathShell = cl.getResource(iconName);

            Image icon = Toolkit.getDefaultToolkit().getImage(pathShell);
            setIconImage(icon);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Set normal cursor
     */
    public void setNormalCursor() {
        aMainWindow.setCursor(aDefaultCursor);
    }


    /**
     * Set application title text.
     *
     * @param additionalText Extra text after app name
     */
    public void setTitle(String additionalText) {
    	StringBuilder tmpTitle = new StringBuilder(Constants.APP_NAME);
    	tmpTitle.append(" ");
    	tmpTitle.append(Constants.APP_VERSION); 
        if (additionalText != null) {
            tmpTitle.append(" ");
            tmpTitle.append(additionalText); 
        }
    	super.setTitle(tmpTitle.toString());
    }


    /**
     * Set wait cursor
     */
    public void setWaitCursor() {
        aMainWindow.setCursor(aWaitCursor);
    }


    /**
     * Show error message.
     *
     * @param resName - Resource key
     */
    public void showError(String resName) {
        JOptionPane.showMessageDialog(this, resName, Constants.APP_NAME, JOptionPane.ERROR_MESSAGE);
    }


    /**
     * Show error message.
     *
     * @param resName      - Resource key
     * @param extraMessage - Resource key
     */
    public void showHtmlError(String resName, String extraMessage) {
        JOptionPane.showMessageDialog(this, "<html>" + resName + "<br>" + extraMessage + "</html>", Constants.APP_NAME, JOptionPane.ERROR_MESSAGE);
    }


    /**
     * Show information message.
     *
     * @param resName - Resource key
     */
    public void showInfo(String resName) {
        JOptionPane.showMessageDialog(this, resName, Constants.APP_NAME, JOptionPane.INFORMATION_MESSAGE);
    }


    /**
     * Start app.<br>
     * It accepts look and feel class name.
     *
     * @param args Command arguments
     */
    public static void main(String[] args) {
        boolean failed = true;

        if (args.length > 0) {
            try {
                UIManager.setLookAndFeel(args[0]);
                failed = false;
            }
            catch (Exception e) {
                if (Constants.DEBUG) e.printStackTrace();
            }
        }

        if (failed) {
            try {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
                failed = false;
            }
            catch (Exception e) {
                if (Constants.DEBUG) e.printStackTrace();
            }
        }

        if (failed) {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            }
            catch (Exception e) {
                if (Constants.DEBUG) e.printStackTrace();
            }
        }

        try {
            final JRipper ripper = new JRipper();

            javax.swing.SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    ripper.setVisible(true);
                }
            });
        }
        catch (Exception e) {
            System.out.println("Exception caught in main");
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace());
            Log.get().addTime(1, "Exception caught in main: " + e.getMessage());
            Log.get().addTime(1, "Stacktrace: " + e.getStackTrace());
            System.exit(1);
        }
    }
}
