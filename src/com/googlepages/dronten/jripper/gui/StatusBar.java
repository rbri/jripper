/*
* Copyright 2004-2010 by dronten@gmail.com
*
* This source is distributed under the terms of the GNU PUBLIC LICENSE version 3
* http://www.gnu.org/licenses/gpl.html
*/

package com.googlepages.dronten.jripper.gui;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.util.Calendar;


/**
 * Status bar object.
 */
public class StatusBar extends JPanel {
    private static final long   serialVersionUID = 666L;
    public static int           FLASH_TIME = 500;
    public static int           FLASH_TIMES = 6;
    public static Color         FLASH_COLOR = Color.LIGHT_GRAY;
    public static Color         FLASH_ERROR_COLOR = Color.RED;
    public static Color         FLASH_NOTIFY_COLOR = Color.YELLOW;
    public static boolean       FLASH_TURNED_ON = true;

    private JLabel              aMessage = null;
    private Boolean             aFlashRun = false;

    private static final int    MESSAGE = 0;
    private static final int    NOTIFY = 1;
    private static final int    ERROR = 2;


    /**
     * Create statusbar.
     */
    public StatusBar() {
        super();
        aMessage = new JLabel();

        setPreferredSize(new Dimension(Short.MAX_VALUE, 20));
        setLayout(ComponentFactory.createBorderLayout(5, 0));
        setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));

        add(aMessage, BorderLayout.CENTER);
    }


    /**
     * Set status bar text.
     *
     * @param message       Either resource key or raw text
     * @param type          MESAGE, NOTIFY or ERROR
     * @param additional    Additional messages
     */
    private void setMessage(String message, int type, String[] additional) {
        for (String msg : additional) {
            message += " " + msg;
        }

        if (type == MESSAGE) {
            aMessage.setText(message);
        }
        else if (type == NOTIFY) {
            aMessage.setText(message);

            if (FLASH_TURNED_ON) {
                FLASH_COLOR = FLASH_NOTIFY_COLOR;
                new FlashGordon().start();
            }
        }
        else if (type == ERROR) {
            aMessage.setText(message);
            Toolkit.getDefaultToolkit().beep();

            if (FLASH_TURNED_ON) {
                FLASH_COLOR = FLASH_ERROR_COLOR;
                new FlashGordon().start();
            }
        }

    }


    /**
     * Set status message.
     *
     * @param message       Message text
     * @param additional    Aditional messages
     */
    public void setMessage(String message, String... additional) {
        setMessage(message, MESSAGE, additional);
    }


    /**
     * Set error message.
     *
     * @param message       Message text
     * @param additional    Additional messages
     */
    public void setErrorMessage(String message, String... additional) {
        setMessage(message, ERROR, additional);
    }


    /**
     * Set notify message.
     *
     * @param message       Message text
     * @param additional    Additional messages
     */
    public void setNotifyMessage(String message, String... additional) {
        setMessage(message, NOTIFY, additional);
    }


    /**
     * A thread that redraws background of status panel in alternative colors for some time.
     */
    class FlashGordon extends Thread {
        /**
         *
         */
        @Override public void run() {
            synchronized (aFlashRun) {
                if (aFlashRun) {
                    return;
                }
                aFlashRun = true;
            }

            long start = Calendar.getInstance().getTimeInMillis();
            Color bg = getBackground();

            while (true) {
                setBackground(FLASH_COLOR);

                try {
                    Thread.sleep(FLASH_TIME);
                }
                catch (InterruptedException e) {
                }

                setBackground(bg);

                try {
                    Thread.sleep(FLASH_TIME);
                }
                catch (InterruptedException e) {
                }

                if ((Calendar.getInstance().getTimeInMillis() - start) > (FLASH_TIME * FLASH_TIMES)) {
                    break;
                }
            }

            synchronized (aFlashRun) {
                aFlashRun = false;
            }
        }
    }
}
