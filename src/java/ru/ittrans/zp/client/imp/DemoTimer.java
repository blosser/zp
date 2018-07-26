package ru.ittrans.zp.client.imp;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 29.10.12
 * Time: 17:59
 * To change this template use File | Settings | File Templates.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DemoTimer
{
    private static final String[] LABEL_STRINGS =
            {
                    "Sunday", "Monday", "Tuesday", "Wednesday",
                    "Thursday", "Friday", "Saturday"
            };
    private static final Dimension APP_SIZE = new Dimension(300, 100);
    protected static final int TIMER_DELAY = 600;
    private JPanel mainPanel = new JPanel();
    private JLabel label = new JLabel("Watch this space", SwingConstants.CENTER);

    public DemoTimer()
    {
        final JButton startButton = new JButton("Start");
        JPanel btnPanel = new JPanel();
        btnPanel.add(startButton);
        startButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                startButton.setEnabled(false);
                new Timer(TIMER_DELAY, new ActionListener()
                {
                    int count = 0;

                    public void actionPerformed(ActionEvent e)
                    {
                        if (count < LABEL_STRINGS.length)
                        {
                            label.setText(LABEL_STRINGS[count]);
                            count++;
                        } else
                        {
                            startButton.setEnabled(true);
                            ((Timer) e.getSource()).stop();
                        }
                    }
                }).start();
            }
        });


        mainPanel.setPreferredSize(APP_SIZE);
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(label, BorderLayout.NORTH);
        mainPanel.add(btnPanel, BorderLayout.SOUTH);
    }

    public JComponent getComponent()
    {
        return mainPanel;
    }

    private static void createAndShowUI()
    {
        JFrame frame = new JFrame("DemoTimer");
        frame.getContentPane().add(new DemoTimer().getComponent());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args)
    {
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                createAndShowUI();
            }
        });
    }
}
