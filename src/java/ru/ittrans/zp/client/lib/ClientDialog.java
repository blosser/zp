package ru.ittrans.zp.client.lib;

import ru.ittrans.zp.client.Connect;
import ru.ittrans.zp.client.MainFrame;
import ru.ittrans.zp.client.def.*;
import ru.ittrans.zp.io.ZpClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 16.06.11
 * Time: 18:07
 * To change this template use File | Settings | File Templates.
 */
public class ClientDialog extends DefDialog
        implements ButtonEditInterface
{
    private ClientEditDialog clientEditDialog = null;

    private DefTable tblClient = new DefTable(new TmClient(), false);

    private JScrollPane spClient = new JScrollPane(tblClient);

    private ButtonEditPanel bep = new ButtonEditPanel(this);

    public ClientDialog(MainFrame mf)
    {
        super(mf, DefCO.MSG_CLIENT);
        clientEditDialog = new ClientEditDialog(mf);
        jbInit();
    }

    public void jbInit()
    {
        super.jbInit();

        JPanel pnlMain = getPanel();
        pnlMain.setLayout(new GridBagLayout());

        pnlMain.add(spClient, new GridBagConstraints(0, 0, 1, 1,
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
        ZpClient client = new ZpClient();
        clientEditDialog.showClient(client);
        if (clientEditDialog.modalResult)
            getClients();
    }

    public void btnUpdate_actionPerformed(ActionEvent e)
    {
        if (tblClient.isSelected())
        {
            ZpClient client = (ZpClient) tblClient.getSelObj();
            clientEditDialog.showClient(client);
            if (clientEditDialog.modalResult)
                getClients();
        } else DefCO.showSelected(this);
    }

    public void btnDelete_actionPerformed(ActionEvent e)
    {
        if (tblClient.isSelected())
        {
            try
            {
                ZpClient client = (ZpClient) tblClient.getSelObj();
                if (DefCO.isDel())
                {
                    Connect.getZpRemote().removeEntity(client);
                    getClients();
                }
            } catch (Exception ex)
            {
                DefCO.showError(this, ex);
            }
        } else DefCO.showSelected(this);
    }

    public void getClients()
    {
        try
        {
            List<ZpClient> cList = Connect.getZpRemote().getClient();
            ((TmClient) tblClient.getModel()).setData(cList);
        } catch (Exception ex)
        {
            DefCO.showError(this, ex);
        }
    }
}

