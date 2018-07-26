package ru.ittrans.zp.client.lib;

import ru.ittrans.zp.client.Connect;
import ru.ittrans.zp.client.MainFrame;
import ru.ittrans.zp.client.def.*;
import ru.ittrans.zp.io.ZpShop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;


/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 10.05.11
 * Time: 17:50
 * To change this template use File | Settings | File Templates.
 */
public class ShopDialog extends DefDialog
        implements ButtonEditInterface
{
    private ShopEditDialog shopEditDialog = null;

    private DefTable tblShop = new DefTable(new TmShop(), false);

    private JScrollPane spShop = new JScrollPane(tblShop);

    private ButtonEditPanel bep = new ButtonEditPanel(this);

    public ShopDialog(MainFrame mf)
    {
        super(mf, DefCO.MSG_SHOP);
        shopEditDialog = new ShopEditDialog(mf);
        jbInit();
    }

    public void jbInit()
    {
        super.jbInit();

        JPanel pnlMain = getPanel();
        pnlMain.setLayout(new GridBagLayout());

        pnlMain.add(spShop, new GridBagConstraints(0, 0, 1, 1,
                1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(DefCO.INS, DefCO.INS, 0, DefCO.INS), 0, 0));
        pnlMain.add(bep, new GridBagConstraints(0, 1, 1, 1,
                0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                DefCO.INSETS0, 0, 0));

        EmailTableCellRenderer dtcr = new EmailTableCellRenderer();
        tblShop.addMouseListener(dtcr);
        tblShop.addMouseMotionListener(dtcr);

        DefCO.setMaxWindowsSize(this);
        setLocationRelativeTo(getOwner());
    }

    public void btnInsert_actionPerformed(ActionEvent e)
    {
        ZpShop shop = new ZpShop();
        shop.setParentId(ZpShop.BASE_PYATEROCHKA);
        shopEditDialog.showShop(shop);
        if (shopEditDialog.modalResult)
            getShops();
    }

    public void btnUpdate_actionPerformed(ActionEvent e)
    {
        if (tblShop.isSelected())
        {
            ZpShop shop = (ZpShop) tblShop.getSelObj();
            shopEditDialog.showShop(shop);
            if (shopEditDialog.modalResult)
                getShops();
        } else DefCO.showSelected(this);
    }

    public void btnDelete_actionPerformed(ActionEvent e)
    {
        if (tblShop.isSelected())
        {
            try
            {
                ZpShop shop = (ZpShop) tblShop.getSelObj();
                if (DefCO.isDel())
                {
                    Connect.getZpRemote().removeEntity(shop);
                    getShops();
                }
            } catch (Exception ex)
            {
                DefCO.showError(this, ex);
            }
        } else DefCO.showSelected(this);
    }

    public void getShops()
    {
        try
        {
            List<ZpShop> pList = Connect.getZpRemote().getShop();
            ((TmShop) tblShop.getModel()).setData(pList);
        } catch (Exception ex)
        {
            DefCO.showError(this, ex);
        }
    }

}
