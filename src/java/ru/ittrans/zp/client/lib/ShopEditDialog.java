package ru.ittrans.zp.client.lib;

import ru.ittrans.zp.client.Connect;
import ru.ittrans.zp.client.DicManager;
import ru.ittrans.zp.client.def.*;
import ru.ittrans.zp.io.ZpClient;
import ru.ittrans.zp.io.ZpShop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.ParseException;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 10.05.11
 * Time: 17:50
 * To change this template use File | Settings | File Templates.
 */
public class ShopEditDialog extends DefDialog<ZpShop>
        implements ButtonSaveInterface
{
    private ShopPanel pnlShop = new ShopPanel(true);

    private ButtonSavePanel bsp = new ButtonSavePanel(this);

    public ShopEditDialog(JFrame f)
    {
        super(f, DefCO.MSG_SHOP);
        jbInit();
    }

    protected void jbInit()
    {
        super.jbInit();

        JPanel pnlMain = getPanel();
        pnlMain.setLayout(new GridBagLayout());

        pnlMain.add(pnlShop, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));
        pnlMain.add(bsp, new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));

        setSize(600, 450);
        setLocationRelativeTo(getOwner());
    }

    public void showShop(ZpShop shop)
    {
        pnlShop.showShop(shop);
        setVisible(true);
    }

    private ZpShop getZpShop()
    {
        return pnlShop.getZpShop();
    }

    public void btnSave_actionPerformed(ActionEvent e)
    {
        if (pnlShop.isNextEnabled())
            try
            {
                ZpShop shop = getZpShop();
                String str = Connect.getZpRemote().mergeShop(shop);
                if (str != null)
                {
                    DefCO.showInf(this, str);
                } else
                    super.btnSave_actionPerformed(e);
            } catch (Exception ex)
            {
                DefCO.showError(this, ex);
            }
    }
}


