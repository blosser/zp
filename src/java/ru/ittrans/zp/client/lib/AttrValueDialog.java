package ru.ittrans.zp.client.lib;

import ru.ittrans.zp.client.Connect;
import ru.ittrans.zp.client.MainFrame;
import ru.ittrans.zp.client.def.*;
import ru.ittrans.zp.io.ZpAttrValue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 10.05.11
 * Time: 18:49
 * To change this template use File | Settings | File Templates.
 */
public class AttrValueDialog extends DefDialog
        implements ButtonEditInterface
{
    private AttrValueEditDialog attrValueEditDialog = null;

    private DefTable tblAttrValue = new DefTable(new TmAttrValue(), false);

    private JScrollPane spAttrValue = new JScrollPane(tblAttrValue);

    private ButtonEditPanel bep = new ButtonEditPanel(this);

    public AttrValueDialog(MainFrame mf)
    {
        super(mf, DefCO.MSG_ATTR_VALUE);
        attrValueEditDialog = new AttrValueEditDialog(mf);
        jbInit();
    }

    public void jbInit()
    {
        super.jbInit();

        JPanel pnlMain = getPanel();
        pnlMain.setLayout(new GridBagLayout());

        pnlMain.add(spAttrValue, new GridBagConstraints(0, 0, 1, 1,
                1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(DefCO.INS, DefCO.INS, 0, DefCO.INS), 0, 0));
        pnlMain.add(bep, new GridBagConstraints(0, 1, 1, 1,
                0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                DefCO.INSETS0, 0, 0));

        setSize(600, 400);
        setLocationRelativeTo(getOwner());
    }

    public void btnInsert_actionPerformed(ActionEvent e)
    {
        ZpAttrValue av = new ZpAttrValue();
        attrValueEditDialog.showAttrValue(av);
        if (attrValueEditDialog.modalResult)
            getAttrValue();
    }

    public void btnUpdate_actionPerformed(ActionEvent e)
    {
        if (tblAttrValue.isSelected())
        {
            ZpAttrValue av = (ZpAttrValue) tblAttrValue.getSelObj();
            attrValueEditDialog.showAttrValue(av);
            if (attrValueEditDialog.modalResult)
                getAttrValue();
        } else DefCO.showSelected(this);
    }

    public void btnDelete_actionPerformed(ActionEvent e)
    {
        if (tblAttrValue.isSelected())
        {
            try
            {
                ZpAttrValue av = (ZpAttrValue) tblAttrValue.getSelObj();
                if (DefCO.isDel())
                {
                    Connect.getZpRemote().removeEntity(av);
                    getAttrValue();
                }
            } catch (Exception ex)
            {
                DefCO.showError(this, ex);
            }
        } else DefCO.showSelected(this);
    }

    public void getAttrValue()
    {
        try
        {
            List<ZpAttrValue> avList = Connect.getZpRemote().getAttrValue(null);
            ((TmAttrValue) tblAttrValue.getModel()).setData(avList);
        } catch (Exception ex)
        {
            DefCO.showError(this, ex);
        }
    }
}
