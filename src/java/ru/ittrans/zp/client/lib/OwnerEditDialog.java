package ru.ittrans.zp.client.lib;

import ru.ittrans.zp.client.Connect;
import ru.ittrans.zp.client.def.*;
import ru.ittrans.zp.io.ZpOwner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 17.06.11
 * Time: 12:25
 * To change this template use File | Settings | File Templates.
 */
public class OwnerEditDialog extends DefDialog<ZpOwner>
        implements ButtonSaveInterface
{
    public static String[] COL_NAMES_OWNER =
            new String[]{DefCO.MSG_OWNER};

    private JLabel lbName = new JLabel(COL_NAMES_OWNER[0]);
    private DefTextField edtName = new DefTextField();

    private ButtonSavePanel bsp = new ButtonSavePanel(this);

    public OwnerEditDialog(JFrame f)
    {
        super(f, COL_NAMES_OWNER[0]);
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

        setSize(400, 120);
        setLocationRelativeTo(getOwner());
    }

    public void showOwner(ZpOwner owner)
    {
        setObject(owner);

        edtName.setText(owner.getName());

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

    private ZpOwner getZpOwner()
    {
        ZpOwner owner = getObject();

        owner.setName(edtName.getText());

        return owner;
    }

    public void btnSave_actionPerformed(ActionEvent e)
    {
        if (isNextEnabled())
            try
            {
                ZpOwner owner = getZpOwner();
                Connect.getZpRemote().mergeEntity(owner);
                super.btnSave_actionPerformed(e);
            } catch (Exception ex)
            {
                DefCO.showError(this, ex);
            }
    }
}


