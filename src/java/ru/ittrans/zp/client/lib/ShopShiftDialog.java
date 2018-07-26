package ru.ittrans.zp.client.lib;

import ru.ittrans.zp.client.Connect;
import ru.ittrans.zp.client.MainFrame;
import ru.ittrans.zp.client.def.*;
import ru.ittrans.zp.io.ZpShopShift;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 15.12.11
 * Time: 13:36
 * To change this template use File | Settings | File Templates.
 */
public class ShopShiftDialog extends DefDialog
        implements ButtonEditInterface
{
    private ShopShiftEditDialog shopShiftEditDialog = null;

    private JLabel lbPeriod = new JLabel("Период");
    private DefComboBox<String> cbPeriod = new DefComboBox<String>();

    private DefTable tblShopShift = new DefTable(new TmShopShift(), false);

    private JScrollPane spShopShift = new JScrollPane(tblShopShift);

    private ButtonEditPanel bep = new ButtonEditPanel(this);

    public ShopShiftDialog(MainFrame mf)
    {
        super(mf, DefCO.MSG_SHOP_SHIFT);
        shopShiftEditDialog = new ShopShiftEditDialog(mf);
        jbInit();
    }

    public void jbInit()
    {
        super.jbInit();

        JPanel pnlMain = getPanel();
        pnlMain.setLayout(new GridBagLayout());

        pnlMain.add(lbPeriod, new GridBagConstraints(0, 0, 1, 1,
                0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                DefCO.INSETS, 0, 0));
        pnlMain.add(cbPeriod, new GridBagConstraints(1, 0, 1, 1,
                0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
                DefCO.INSETS, 0, 0));
        pnlMain.add(spShopShift, new GridBagConstraints(0, 1, 2, 1,
                1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(DefCO.INS, DefCO.INS, 0, DefCO.INS), 0, 0));
        pnlMain.add(bep, new GridBagConstraints(0, 2, 2, 1,
                0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                DefCO.INSETS0, 0, 0));

        cbPeriod.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                getShopShift();
            }
        });

        setSize(500, 300);
        setLocationRelativeTo(getOwner());
    }

    public void setData(int periodIndex)
    {
        DefCO.setPeriodData(cbPeriod, periodIndex);
    }

    public void btnInsert_actionPerformed(ActionEvent e)
    {
        ZpShopShift shopShift = new ZpShopShift();
        Date period = DefCO.getCbPeriod(cbPeriod);
        shopShift.setPeriod(period);
        shopShiftEditDialog.showShopShift(shopShift);
        if (shopShiftEditDialog.modalResult)
            getShopShift();
    }

    public void btnUpdate_actionPerformed(ActionEvent e)
    {
        if (tblShopShift.isSelected())
        {
            ZpShopShift shopShift = (ZpShopShift) tblShopShift.getSelObj();
            shopShiftEditDialog.showShopShift(shopShift);
            if (shopShiftEditDialog.modalResult)
            {
                getShopShift();
            }
        } else DefCO.showSelected(this);
    }

    public void btnDelete_actionPerformed(ActionEvent e)
    {
        if (tblShopShift.isSelected())
        {
            try
            {
                ZpShopShift shopShift = (ZpShopShift) tblShopShift.getSelObj();
                if (DefCO.isDel())
                {
                    Connect.getZpRemote().removeEntity(shopShift);
                    getShopShift();
                }
            } catch (Exception ex)
            {
                DefCO.showError(this, ex);
            }
        } else DefCO.showSelected(this);
    }

    public void getShopShift()
    {
        try
        {
            Date period = DefCO.getCbPeriod(cbPeriod);
            List<ZpShopShift> shopShiftList = Connect.getZpRemote().getShopShift(period);
            ((TmShopShift) tblShopShift.getModel()).setData(shopShiftList);
        } catch (Exception ex)
        {
            DefCO.showError(this, ex);
        }
    }
}

