package ru.ittrans.zp.client.report;

import ru.ittrans.zp.client.Connect;
import ru.ittrans.zp.client.def.ButtonSaveInterface;
import ru.ittrans.zp.client.def.ButtonSavePanel;
import ru.ittrans.zp.client.def.DefCO;
import ru.ittrans.zp.client.def.DefDialog;
import ru.ittrans.zp.io.ZpClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 21.03.11
 * Time: 15:44
 * To change this template use File | Settings | File Templates.
 */
public class ChangePassDialog extends DefDialog<ZpClient>
        implements ButtonSaveInterface
{
    public static String CHANGE_PASS = "Смена пароля";

    public static String PASS_TXT = "<html>Пароль должен состоять минимум из " +
            DefCO.MIN_LENGHT_PASS + " символов.<br>От сложности пароля (длины и набора символов) зависит Ваша безопасность.</html>";

    private JLabel lbName0 = new JLabel(PASS_TXT);

    public static final String[] MES_CHANGE_PASS =
            {"Старый пароль", "Новый пароль", "Подтверждение"};

    private JLabel lbName1 = new JLabel(MES_CHANGE_PASS[0]);
    private JPasswordField edtName1 = new JPasswordField();
    private JLabel lbName2 = new JLabel(MES_CHANGE_PASS[1]);
    private JPasswordField edtName2 = new JPasswordField();
    private JLabel lbName3 = new JLabel(MES_CHANGE_PASS[2]);
    private JPasswordField edtName3 = new JPasswordField();

    private ButtonSavePanel bsp = new ButtonSavePanel(this);

    public ChangePassDialog(JFrame f)
    {
        super(f, CHANGE_PASS);
        jbInit();
    }

    protected void jbInit()
    {
        super.jbInit();

        JPanel pnlMain = getPanel();
        pnlMain.setLayout(new GridBagLayout());

        JPanel pnlMan = new JPanel(new GridBagLayout());

        pnlMan.setBorder(BorderFactory.createTitledBorder(""));

        ///////////////////////////////////////////////////////////////////////////

        pnlMain.add(lbName0, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));
        pnlMain.add(pnlMan, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));
        pnlMain.add(bsp, new GridBagConstraints(0, 2, 1, 1, 1.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS0, 0, 0));

        ///////////////////////////////////////////////////////////////////////////

        pnlMan.add(lbName1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));
        pnlMan.add(edtName1, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));

        pnlMan.add(lbName2, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));
        pnlMan.add(edtName2, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));

        pnlMan.add(lbName3, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));
        pnlMan.add(edtName3, new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));

        setSize(550, 250);
    }

    public void showMan(ZpClient client)
    {
        setObject(client);

        setTitle(CHANGE_PASS + " - " + DefCO.getClientText(client));
        edtName1.setText("");
        edtName2.setText("");
        edtName3.setText("");

        setLocationRelativeTo(getOwner());

        DefCO.requestFocus(edtName1);
        setVisible(true);
    }

    protected boolean isNextEnabled()
    {
        if (String.valueOf(edtName1.getPassword()).length() == 0)
        {
            DefCO.inputMessage(this, lbName1.getText());
            return false;
        }

        if (String.valueOf(edtName2.getPassword()).length() == 0)
        {
            DefCO.inputMessage(this, lbName2.getText());
            return false;
        }

        if (String.valueOf(edtName3.getPassword()).length() == 0)
        {
            DefCO.inputMessage(this, lbName3.getText());
            return false;
        }

        int min_length = DefCO.MIN_LENGHT_PASS;
        if (String.valueOf(edtName2.getPassword()).length() < min_length)
        {
            DefCO.showInf(this, lbName2.getText() + " должен состоять минимум из " +
                    min_length + " символов");
            return false;
        }

        if (!Connect.getClient().getPassword().equals(String.valueOf(edtName1.getPassword())))
        {
            DefCO.showInf(this, lbName1.getText() + " введен не верно");
            return false;
        }

        if (!String.valueOf(edtName2.getPassword()).equals(String.valueOf(edtName3.getPassword())))
        {
            DefCO.showInf(this, lbName2.getText() + " не совпадает с подтвержденным");
            return false;
        }

        return true;
    }

    private ZpClient getTpMan()
    {
        ZpClient man = getObject();

        man.setPassword(String.valueOf(edtName2.getPassword()));

        return man;
    }

    public void btnSave_actionPerformed(ActionEvent e)
    {
        if (isNextEnabled())
            try
            {
                ZpClient man = getTpMan();

                Connect.getZpRemote().mergeEntity(man);
                DefCO.showInf(this, "Пароль успешно изменен");
                setVisible(false);
            } catch (Exception ex)
            {
                DefCO.showError(this, ex);
            }
    }
}
