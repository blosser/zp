package ru.ittrans.zp.client.lib;

import ru.ittrans.zp.client.Connect;
import ru.ittrans.zp.client.def.*;
import ru.ittrans.zp.io.ZpPaid;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 10.05.11
 * Time: 17:20
 * To change this template use File | Settings | File Templates.
 */
public class PaidEditDialog extends DefDialog<ZpPaid>
        implements ButtonSaveInterface
{
    public static String[] COL_NAMES_PAID =
            new String[]{"Ведомость", "Дата"};

    private JLabel lbName = new JLabel(COL_NAMES_PAID[0]);
    private DefTextField edtName = new DefTextField();

    private JLabel lbD = new JLabel(COL_NAMES_PAID[1]);
    private DefDatePicker dpD = new DefDatePicker();

    private ButtonSavePanel bsp = new ButtonSavePanel(this);

    public PaidEditDialog(JFrame f)
    {
        super(f, COL_NAMES_PAID[0]);
        jbInit();
    }

    protected void jbInit()
    {
        super.jbInit();

        JPanel pnlMain = getPanel();
        pnlMain.setLayout(new GridBagLayout());

        JPanel pnlMan = new JPanel(new GridBagLayout());

        ///////////////////////////////////////////////////////////////////////////

        pnlMain.add(pnlMan, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));
        pnlMain.add(bsp, new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS0, 0, 0));

        ///////////////////////////////////////////////////////////////////////////

        pnlMan.add(lbName, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));
        pnlMan.add(edtName, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));

        pnlMan.add(lbD, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));
        pnlMan.add(dpD, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE, DefCO.INSETS, 0, 0));

        setSize(400, 140);
        setLocationRelativeTo(getOwner());
    }

    public void showPaid(ZpPaid paid)
    {
        setObject(paid);

        edtName.setText(paid.getName());
        dpD.setDate(paid.getD());

        DefCO.requestFocus(edtName);
        setVisible(true);
    }

    protected boolean isNextEnabled()
    {
        if (edtName.getText().trim().length() == 0)
        {
            DefCO.inputMessage(this, lbName.getText());
            return false;
        }

        return true;
    }

    private ZpPaid getZpPaid()
    {
        ZpPaid paid = getObject();

        paid.setName(edtName.getText());
        paid.setD(dpD.getDate());

        return paid;
    }

    public void btnSave_actionPerformed(ActionEvent e)
    {
        if (isNextEnabled())
            try
            {
                ZpPaid paid = getZpPaid();
                Connect.getZpRemote().mergeEntity(paid);
                super.btnSave_actionPerformed(e);
            } catch (Exception ex)
            {
                DefCO.showError(this, ex);
            }
    }
}

