package ru.ittrans.zp.client.lib;

import ru.ittrans.zp.client.DicManager;
import ru.ittrans.zp.client.def.*;
import ru.ittrans.zp.io.ZpOwner;
import ru.ittrans.zp.io.ZpOwnerMan;

import javax.swing.*;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 17.06.11
 * Time: 13:54
 * To change this template use File | Settings | File Templates.
 */
public class OwnerManEditDialog extends DefDialog<ZpOwnerMan>
        implements ButtonSaveInterface
{
    public static String[] COL_NAMES_OWNER_MAN =
            new String[]{DefCO.MSG_OWNER_MAN, DefCO.MSG_OWNER, "Действует с", "Действует по"};

    private JLabel lbName = new JLabel(COL_NAMES_OWNER_MAN[0]);
    private DefTextField edtName = new DefTextField();

    private JLabel lbOwner = new JLabel(COL_NAMES_OWNER_MAN[1]);
    private DefComboBox<ZpOwner> cbOwner = new DefComboBox<ZpOwner>();

    private JLabel lbSd = new JLabel(COL_NAMES_OWNER_MAN[2]);
    private DefDatePicker dpSd = new DefDatePicker();

    private JLabel lbFd = new JLabel(COL_NAMES_OWNER_MAN[3]);
    private DefDatePicker dpFd = new DefDatePicker();

    private ButtonSavePanel bsp = new ButtonSavePanel(this);

    public OwnerManEditDialog(DefDialog f)
    {
        super(f, DefCO.MSG_OWNER_MAN);
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

        pnlMan.add(lbOwner, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));
        pnlMan.add(cbOwner, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));

        pnlMan.add(lbSd, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));
        pnlMan.add(dpSd, new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE, DefCO.INSETS, 0, 0));

        pnlMan.add(lbFd, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));
        pnlMan.add(dpFd, new GridBagConstraints(1, 3, 1, 1, 1.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE, DefCO.INSETS, 0, 0));

        setSize(370, 230);
        setLocationRelativeTo(getOwner());
    }

    public void showOwnerMan(ZpOwnerMan ownerMan)
    {
        setObject(ownerMan);

        edtName.setText(ownerMan.getName());
        DefComboBox.cbAddItem(cbOwner, DicManager.getOwnerMap(), false);

        cbOwner.setSelectedItem(DicManager.getOwnerMap().get(ownerMan.getOwnerId()));
        dpSd.setDate(ownerMan.getSd());
        dpFd.setDate(ownerMan.getFd());

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

    public ZpOwnerMan getOwnerMan()
    {
        ZpOwnerMan ownerMan = getObject();

        ownerMan.setName(edtName.getText());
        ownerMan.setOwnerId(cbOwner.getSelectedId());
        ownerMan.setSd(dpSd.getDate());
        ownerMan.setFd(dpFd.getDate());

        return ownerMan;
    }

//    public void btnSave_actionPerformed(ActionEvent e)
//    {
//        if (isNextEnabled())
//            try
//            {
//                ZpOwnerMan ownerMan = getOwnerMan();
//                Connect.getZpRemote().mergeEntity(ownerMan);
//                super.btnSave_actionPerformed(e);
//            } catch (Exception ex)
//            {
//                DefCO.showError(this, ex);
//            }
//    }
}


