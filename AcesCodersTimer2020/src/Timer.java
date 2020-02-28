
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.*;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author Nadun
 */
public class Timer extends JFrame implements MouseListener, MouseMotionListener, Runnable {

    private int currentX, currentY;

    // Stores the current minute and second.
    private int currentSecond, currentMinute, currentHours;

    // Variable to store the running state of thread.
    private boolean isRunning = false;

    // Thread to run the timer.
    private Thread t;

    // Stores the selected audio for the timer's timeout sound.
    //private AudioClip audio;

    private JMenuItem itemStart, itemExit;

    /**
     * Creates new form Timer
     */
    public Timer() {
        initComponents();

        itemStart = new JMenuItem("Start");
        itemStart.addActionListener(new ActionListener() {
            @Override
            @SuppressWarnings("empty-statement")
            public void actionPerformed(ActionEvent e) {
                if(itemStart.getText().equalsIgnoreCase("start")) {
                    startTimer();
                } else if(itemStart.getText().equalsIgnoreCase("stop")) {
                    stopTimer();
                }
            }
        });
        popupMenu.add(itemStart);

        itemExit = new JMenuItem("Exit");
        itemExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        popupMenu.add(itemExit);

        mainPanel.addMouseListener(this);
        mainPanel.addMouseMotionListener(this);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width, 220);

    }

    public static Font loadFont(int style, float size) {
        try {
            String fName = "fonts//KidsBoardGame-rqP9.ttf";
            InputStream is = Timer.class.getResourceAsStream(fName);
            Font font = Font.createFont(Font.TRUETYPE_FONT, is);
            return font.deriveFont(style, size);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return new Font("Tahoma", style, (int)size);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent ev) {
        currentX = ev.getX();
        currentY = ev.getY();
        if (ev.isPopupTrigger()) {
            popupMenu.show(ev.getComponent(), ev.getX(), ev.getY());
        }
    }

    @Override
    public void mouseReleased(MouseEvent ev) {
        this.setOpacity((float) 1.0);
        if (ev.isPopupTrigger()) {
            popupMenu.show(ev.getComponent(), ev.getX(), ev.getY());
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        this.setOpacity((float) 0.8);
        int x = e.getXOnScreen();
        int y = e.getYOnScreen();
        this.setLocation(x - currentX, y - currentY);
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    /**
     * Checks whether the number entered by the user in the field is valid or
     * not.
     */
    private boolean getMinutes() {
        try {
            String m = JOptionPane.showInputDialog(this, "Minutes ?", 30);
            this.currentMinute = Integer.parseInt(m);
            this.currentSecond = 0;
            this.currentHours = 0;

            if (currentMinute < 0) {
                throw new Exception();
            }

            if (currentMinute >= 60) {
                this.currentHours = currentMinute / 60;
                this.currentMinute = currentMinute % 60;
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid input, please enter an valid input", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    @SuppressWarnings("empty-statement")
    public void startTimer() {
        //audio.stop();
        itemStart.setText("Stop");
        while (!getMinutes());
        isRunning = true;
        t = new Thread(this);
        t.start();
    }

    /**
     * Method that helps to stop the thread.
     */
    private void stopTimer() {
        this.isRunning = false;
        itemStart.setText("Start");
        currentHours = 0;
        currentMinute = 0;
        currentSecond = 0;
    }

    /**
     * Thread that starts the countdown
     */
    @Override
    public void run() {

        String seconds = "00", minutes = "00", hours = "00";

        while (isRunning) {
            try {
                Thread.sleep(1000);

                if (this.currentSecond == 0 && this.currentMinute == 0 && this.currentHours >= 1) {
                    this.currentHours = this.currentHours - 1;
                    this.currentMinute = this.currentMinute - 1;
                    if (this.currentHours < 0) {
                        this.currentHours = 0;
                    }
                    if (this.currentMinute < 0) {
                        this.currentMinute = 0;
                    }
                    this.currentMinute = 59;
                    this.currentSecond = 59;
                } else if (this.currentSecond == 0 && this.currentMinute >= 1) {
                    this.currentMinute = this.currentMinute - 1;
                    if (this.currentMinute < 0) {
                        this.currentMinute = 0;
                    }
                    this.currentSecond = 59;
                } else {
                    this.currentSecond = this.currentSecond - 1;
                }

                if (this.currentHours <= 0 && this.currentMinute <= 0 && this.currentSecond <= 0) {
                    stopTimer();
                    //audio.play();
                    // play sound
                }

                if (this.currentHours <= 9) {
                    hours = "0" + this.currentHours;
                } else {
                    hours = this.currentHours + "";
                }
                if (this.currentSecond <= 9) {
                    seconds = "0" + this.currentSecond;
                } else {
                    seconds = this.currentSecond + "";
                }
                if (this.currentMinute <= 9) {
                    minutes = "0" + this.currentMinute;
                } else {
                    minutes = this.currentMinute + "";
                }

                // starts countdown
            } catch (InterruptedException ex) {
                Logger.getLogger(Timer.class.getName()).log(Level.SEVERE, null, ex);
            }

            String timeDisplay = hours + " : " + minutes + " : " + seconds;

            labelTiimeDisplay.setText(timeDisplay);

        }
        if (!isRunning) {
            minutes = "00";
            seconds = "00";
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        popupMenu = new javax.swing.JPopupMenu();
        mainPanel = new javax.swing.JPanel();
        labelTiimeDisplay = new javax.swing.JLabel();

        popupMenu.setBackground(new java.awt.Color(204, 204, 255));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        mainPanel.setBackground(new java.awt.Color(4, 25, 66));

        labelTiimeDisplay.setFont(new java.awt.Font("Calibri", 1, 140)); // NOI18N
        labelTiimeDisplay.setForeground(new java.awt.Color(204, 204, 255));
        labelTiimeDisplay.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelTiimeDisplay.setText("00 : 00 : 00");

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelTiimeDisplay, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1186, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelTiimeDisplay, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Timer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Timer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Timer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Timer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Timer().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel labelTiimeDisplay;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPopupMenu popupMenu;
    // End of variables declaration//GEN-END:variables

}
