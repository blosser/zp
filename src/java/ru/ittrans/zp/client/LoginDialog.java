package ru.ittrans.zp.client;

import ru.ittrans.zp.client.def.ButtonSavePanel;
import ru.ittrans.zp.client.def.DefCO;
import ru.ittrans.zp.client.def.DefDialog;
import ru.ittrans.zp.client.def.DefTextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 21.10.2010
 * Time: 13:57:03
 * To change this template use File | Settings | File Templates.
 */
public class LoginDialog extends DefDialog
{
    private JLabel lbLogin = new JLabel("Ћогин:");
    private DefTextField edtLogin = new DefTextField();
    private JLabel lbPassword = new JLabel("ѕароль:");
    private JPasswordField edtPassword = new JPasswordField();

    private ButtonSavePanel bsp =
            new ButtonSavePanel(this,
                    ButtonSavePanel.BSP_TYPE.BSP_ENTER);

    private static final int LOGIN_WIDTH = 260;
    private static final int LOGIN_HEIGHT = 160;

    public LoginDialog()
    {
        super("¬ход в программу");
        try
        {
            jbInit();
            String username = System.getProperty("user.name");
            if (username.contains("blohin"))
            {
                edtLogin.setText("admin");
                edtPassword.setText("1945");
            }
        } catch (Exception ex)
        {
            DefCO.showError(this, ex);
        }
    }

    public void jbInit()
    {
        super.jbInit();

        setSize(LOGIN_WIDTH, LOGIN_HEIGHT);

        JPanel pnlLogin = new JPanel(new GridBagLayout());
        pnlLogin.setBorder(BorderFactory.createTitledBorder(""));
        pnlLogin.add(lbLogin, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));
        pnlLogin.add(edtLogin, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));
        pnlLogin.add(lbPassword, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));
        pnlLogin.add(edtPassword, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));

        JPanel pnlMain = getPanel();
        pnlMain.setLayout(new GridBagLayout());
        pnlMain.add(pnlLogin, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));
        pnlMain.add(bsp, new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS0, 0, 0));

        addWindowListener(new java.awt.event.WindowAdapter()
        {
            public void windowClosing(final WindowEvent ev)
            {
                myWindowClosing(ev);
            }
        });

        bsp.setDefaultButton();

        //иначе не работает requestFocus
        Rectangle bounds = getBounds();
        pack();
        setBounds(bounds);

        DefCO.requestFocus(edtLogin);

        setResizable(false);

        setLocationRelativeTo(getOwner());
    }


    public void btnSave_actionPerformed(ActionEvent e)
    {
        try
        {
            Connect.tprInit();
            String sLogin = edtLogin.getText();
            String sPassword = new String(edtPassword.getPassword());
            if (Connect.isLogin(this, sLogin, sPassword))
            {
                setVisible(false);
                DicManager.initStatusDic();
                MainFrame mf = new MainFrame();
                //mf.changeSelPanel();
                mf.setExtendedState(mf.getExtendedState() | JFrame.MAXIMIZED_BOTH);
                mf.setVisible(true);
                //mf.checkPass();
            }
        } catch (Exception ex)
        {
            DefCO.showError(this, ex);
        }
    }

    public void btnCancel_actionPerformed(ActionEvent e)
    {
        myWindowClosing(null);
    }

    final void myWindowClosing(final WindowEvent e)
    {
        DefCO.debugInfo("all ok windowClosed");
        dispose();
        System.exit(0);
    }
}
