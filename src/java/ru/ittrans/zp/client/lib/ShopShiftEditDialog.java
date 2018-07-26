package ru.ittrans.zp.client.lib;

import ru.ittrans.zp.client.Connect;
import ru.ittrans.zp.client.DicManager;
import ru.ittrans.zp.client.def.*;
import ru.ittrans.zp.io.ZpShop;
import ru.ittrans.zp.io.ZpShopShift;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 15.12.11
 * Time: 13:36
 * To change this template use File | Settings | File Templates.
 */
public class ShopShiftEditDialog extends DefDialog<ZpShopShift>
        implements ButtonSaveInterface
{
    public static String[] COL_NAMES_SHOP_SHIFT =
            new String[]{"Магазин", "Кол-во смен"};

    private JLabel lbShop = new JLabel(COL_NAMES_SHOP_SHIFT[0]);
    private DefComboBox<ZpShop> cbShop = new DefComboBox<ZpShop>();

    private JLabel lbValue = new JLabel(COL_NAMES_SHOP_SHIFT[1]);
    private DefNumberTextField edtValue = new DefNumberTextField(10);

    private ButtonSavePanel bsp = new ButtonSavePanel(this);

    public ShopShiftEditDialog(JFrame f)
    {
        super(f, DefCO.MSG_SHOP_SHIFT);
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

        pnlMan.add(lbShop, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));
        pnlMan.add(cbShop, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));

        pnlMan.add(lbValue, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));
        pnlMan.add(edtValue, new GridBagConstraints(1, 3, 1, 1, 1.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));

        setSize(270, 130);
        setLocationRelativeTo(getOwner());
    }

    public void showShopShift(ZpShopShift shopShift)
    {
        setObject(shopShift);

        DefComboBox.cbAddItem(cbShop, DicManager.getShopMap(), false);

        cbShop.setSelectedItem(DicManager.getShopMap().get(shopShift.getShopId()));
        edtValue.setValue(shopShift.getValue());

        DefCO.requestFocus(cbShop);
        setVisible(true);
    }

    protected boolean isNextEnabled()
    {
        if (cbShop.getSelectedId() == null)
        {
            DefCO.inputMessage(this, lbShop.getText());
            return false;
        }

        if (edtValue.getValue() == null)
        {
            DefCO.inputMessage(this, lbValue.getText());
            return false;
        }

        return true;
    }

    private ZpShopShift getShopShift()
    {
        ZpShopShift shopShift = getObject();

        shopShift.setShopId(cbShop.getSelectedId());
        shopShift.setValue(edtValue.getValue());

        return shopShift;
    }

    public void btnSave_actionPerformed(ActionEvent e)
    {
        if (isNextEnabled())
            try
            {
                ZpShopShift shopShift = getShopShift();
                Connect.getZpRemote().mergeEntity(shopShift);
                super.btnSave_actionPerformed(e);
            } catch (Exception ex)
            {
                DefCO.showError(this, ex);
            }
    }
}


