package ru.ittrans.zp.client.lib;

import ru.ittrans.zp.client.Connect;
import ru.ittrans.zp.client.DicManager;
import ru.ittrans.zp.client.def.*;
import ru.ittrans.zp.io.ZpClient;
import ru.ittrans.zp.io.ZpClientRole;
import ru.ittrans.zp.io.ZpRole;
import ru.ittrans.zp.io.ZpShop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 16.06.11
 * Time: 18:07
 * To change this template use File | Settings | File Templates.
 */
public class ClientEditDialog extends DefDialog<ZpClient>
        implements ButtonSaveInterface
{
    public static String[] COL_NAMES_CLIENT =
            new String[]{"ФИО", "Логин", "Пароль", "Все права", "Базовый"};

    public static final String[] MES_CLIENT_ROLE =
            {"Роль", "Действует"};

    private JLabel lbName = new JLabel(COL_NAMES_CLIENT[0]);
    private DefTextField edtName = new DefTextField();

    private JLabel lbLogin = new JLabel(COL_NAMES_CLIENT[1]);
    private DefTextField edtLogin = new DefTextField();

    private JLabel lbPassword = new JLabel(COL_NAMES_CLIENT[2]);
    private DefTextField edtPassword = new DefTextField();

    private DefCheckBox cbxAdmin = new DefCheckBox(COL_NAMES_CLIENT[3], false);

    private JLabel lbShop = new JLabel(COL_NAMES_CLIENT[4]);
    private DefComboBox<ZpShop> cbShop = new DefComboBox<ZpShop>();

    private DefTable tblClientRole = new DefTable(new TmClientRole(), false);
    private JScrollPane spClientRole = new JScrollPane(tblClientRole);

    private ButtonSavePanel bsp = new ButtonSavePanel(this);

    public ClientEditDialog(JFrame f)
    {
        super(f, DefCO.MSG_CLIENT);
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

        pnlMain.add(pnlMan, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));
        pnlMain.add(bsp, new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS0, 0, 0));

        ///////////////////////////////////////////////////////////////////////////

        pnlMan.add(lbName, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));
        pnlMan.add(edtName, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));

        pnlMan.add(lbLogin, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));
        pnlMan.add(edtLogin, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));

        pnlMan.add(lbPassword, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));
        pnlMan.add(edtPassword, new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));

        pnlMan.add(lbShop, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));
        pnlMan.add(cbShop, new GridBagConstraints(1, 3, 1, 1, 1.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));

        pnlMan.add(cbxAdmin, new GridBagConstraints(0, 4, 2, 1, 1.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));

        pnlMan.add(spClientRole, new GridBagConstraints(0, 5, 2, 1, 1.0, 1.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));

        cbxAdmin.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent actionEvent)
            {
                cbxAdminPressed();
            }
        });

        setSize(500, 500);

        setLocationRelativeTo(getOwner());
    }

    public void showClient(ZpClient client)
    {
        setObject(client);

        DefComboBox.cbAddItem(cbShop, DicManager.getParentShopMap(), true, false);

        edtName.setText(client.getName());
        edtLogin.setText(client.getLogin());
        edtPassword.setText(client.getPassword());
        cbShop.setSelectedItem(DicManager.getParentShopMap().get(client.getShopId()));
        cbxAdmin.setValue(client.getAdmin());
        setClientRole(client);

        cbxAdminPressed();

        DefCO.requestFocus(edtName);
        setVisible(true);
    }

    private void cbxAdminPressed()
    {
        boolean isSelected = cbxAdmin.isSelected();
        if (isSelected)
            setClientRole(null);
        tblClientRole.setMyEditable(!isSelected);
    }

    private void setClientRole(ZpClient man)
    {
        List<ZpClientRole> tblMrList = new ArrayList<ZpClientRole>();
        List<ZpClientRole> mrList = man == null ? null : man.getMrList();
        for (ZpRole r : DicManager.getRoleMap().values())
        {
            ZpClientRole tblMr = new ZpClientRole();
            tblMr.setRoleId(r.getId());
            if (mrList != null && !mrList.isEmpty())
                for (ZpClientRole mr : mrList)
                    if (tblMr.getRoleId().equals(mr.getRoleId()))
                    {
                        tblMr.setClientId(mr.getClientId());
                        break;
                    }
            tblMrList.add(tblMr);
        }
        ((TmClientRole) tblClientRole.getModel()).setData(tblMrList);
    }

    protected boolean isNextEnabled()
    {
        if (edtName.getText().trim().length() == 0)
        {
            DefCO.inputMessage(this, lbName.getText());
            return false;
        }

        if (edtLogin.getText().trim().length() == 0)
        {
            DefCO.inputMessage(this, lbLogin.getText());
            return false;
        }

        if (edtPassword.getText().trim().length() == 0)
        {
            DefCO.inputMessage(this, lbPassword.getText());
            return false;
        }

        return true;
    }

    private ZpClient getZpClient()
    {
        ZpClient client = getObject();

        client.setName(edtName.getText());
        client.setLogin(edtLogin.getText());
        client.setPassword(edtPassword.getText());
        client.setShopId(cbShop.getSelectedId());
        client.setAdmin(cbxAdmin.getValue());

        List<ZpClientRole> mrList = ((TmClientRole) tblClientRole.getModel()).getList();

        List<ZpClientRole> resList = new ArrayList<ZpClientRole>();

        if (mrList != null)
            for (ZpClientRole mr : mrList)
                if (mr.getClientId() != null)
                    resList.add(mr);

        client.setMrList(resList);

        return client;
    }

    public void btnSave_actionPerformed(ActionEvent e)
    {
        if (isNextEnabled())
            try
            {
                ZpClient client = getZpClient();
                Connect.getZpRemote().mergeClient(client);
                super.btnSave_actionPerformed(e);
            } catch (Exception ex)
            {
                DefCO.showError(this, ex);
            }
    }
}

