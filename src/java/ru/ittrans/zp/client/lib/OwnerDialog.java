package ru.ittrans.zp.client.lib;

import ru.ittrans.zp.client.Connect;
import ru.ittrans.zp.client.MainFrame;
import ru.ittrans.zp.client.def.*;
import ru.ittrans.zp.io.ZpOwner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 17.06.11
 * Time: 12:25
 * To change this template use File | Settings | File Templates.
 */
public class OwnerDialog extends DefDialog
        implements ButtonEditInterface
{
    private OwnerEditDialog ownerEditDialog = null;

    private DefTable tblOwner = new DefTable(new TmOwner(), false);

    private JScrollPane spOwner = new JScrollPane(tblOwner);

    private ButtonEditPanel bep = new ButtonEditPanel(this);

    public OwnerDialog(MainFrame mf)
    {
        super(mf, DefCO.MSG_OWNER);
        ownerEditDialog = new OwnerEditDialog(mf);
        jbInit();
    }

    public void jbInit()
    {
        super.jbInit();

        JPanel pnlMain = getPanel();
        pnlMain.setLayout(new GridBagLayout());

        pnlMain.add(spOwner, new GridBagConstraints(0, 0, 1, 1,
                1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(DefCO.INS, DefCO.INS, 0, DefCO.INS), 0, 0));
        pnlMain.add(bep, new GridBagConstraints(0, 1, 1, 1,
                0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                DefCO.INSETS0, 0, 0));

        DefCO.setMaxWindowsSize(this);
        setLocationRelativeTo(getOwner());
    }

    public void btnInsert_actionPerformed(ActionEvent e)
    {
        ZpOwner owner = new ZpOwner();
        ownerEditDialog.showOwner(owner);
        if (ownerEditDialog.modalResult)
            getOwners();
    }

    public void btnUpdate_actionPerformed(ActionEvent e)
    {
        if (tblOwner.isSelected())
        {
            ZpOwner owner = (ZpOwner) tblOwner.getSelObj();
            ownerEditDialog.showOwner(owner);
            if (ownerEditDialog.modalResult)
                getOwners();
        } else DefCO.showSelected(this);
    }

    public void btnDelete_actionPerformed(ActionEvent e)
    {
        if (tblOwner.isSelected())
        {
            try
            {
                ZpOwner owner = (ZpOwner) tblOwner.getSelObj();
                if (DefCO.isDel())
                {
                    Connect.getZpRemote().removeEntity(owner);
                    getOwners();
                }
            } catch (Exception ex)
            {
                DefCO.showError(this, ex);
            }
        } else DefCO.showSelected(this);
    }

    public void getOwners()
    {
        try
        {
            List<ZpOwner> cList = Connect.getZpRemote().getOwner();
            ((TmOwner) tblOwner.getModel()).setData(cList);
        } catch (Exception ex)
        {
            DefCO.showError(this, ex);
        }
    }
}


