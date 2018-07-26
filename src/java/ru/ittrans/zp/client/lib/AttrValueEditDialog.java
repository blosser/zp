package ru.ittrans.zp.client.lib;

import ru.ittrans.zp.client.Connect;
import ru.ittrans.zp.client.DicManager;
import ru.ittrans.zp.client.def.*;
import ru.ittrans.zp.io.ZpAttr;
import ru.ittrans.zp.io.ZpAttrValue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 10.05.11
 * Time: 18:51
 * To change this template use File | Settings | File Templates.
 */
public class AttrValueEditDialog extends DefDialog<ZpAttrValue>
        implements ButtonSaveInterface
{
    public static String[] COL_NAMES_ATTR_VALIE =
            new String[]{"Атрибут", "Действует с", "Действует по", "Значение"};

    private JLabel lbAttr = new JLabel(COL_NAMES_ATTR_VALIE[0]);
    private DefComboBox<ZpAttr> cbAttr = new DefComboBox<ZpAttr>();

    private JLabel lbSd = new JLabel(COL_NAMES_ATTR_VALIE[1]);
    private DefDatePicker dpSd = new DefDatePicker();

    private JLabel lbFd = new JLabel(COL_NAMES_ATTR_VALIE[2]);
    private DefDatePicker dpFd = new DefDatePicker();

    private JLabel lbValue = new JLabel(COL_NAMES_ATTR_VALIE[3]);
    private DefNumberTextField edtValue = new DefNumberTextField(10);

    private ButtonSavePanel bsp = new ButtonSavePanel(this);

    public AttrValueEditDialog(JFrame f)
    {
        super(f, DefCO.MSG_ATTR_VALUE);
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

        pnlMan.add(lbAttr, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));
        pnlMan.add(cbAttr, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));

        pnlMan.add(lbSd, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));
        pnlMan.add(dpSd, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE, DefCO.INSETS, 0, 0));

        pnlMan.add(lbFd, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));
        pnlMan.add(dpFd, new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE, DefCO.INSETS, 0, 0));

        pnlMan.add(lbValue, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));
        pnlMan.add(edtValue, new GridBagConstraints(1, 3, 1, 1, 1.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));

        setSize(470, 330);
        setLocationRelativeTo(getOwner());
    }

    public void showAttrValue(ZpAttrValue attrValue)
    {
        setObject(attrValue);

        DefComboBox.cbAddItem(cbAttr, DicManager.getAttrMap(), false);

        cbAttr.setSelectedItem(DicManager.getAttrMap().get(attrValue.getAttrId()));
        dpSd.setDate(attrValue.getSd());
        dpFd.setDate(attrValue.getFd());
        edtValue.setValue(attrValue.getValue());

        DefCO.requestFocus(cbAttr);
        setVisible(true);
    }

    protected boolean isNextEnabled()
    {
        if (dpSd.getDate() == null)
        {
            DefCO.inputMessage(this, lbSd.getText());
            return false;
        }

        if (dpFd.getDate() == null)
        {
            DefCO.inputMessage(this, lbFd.getText());
            return false;
        }

        if (edtValue.getValue() == null)
        {
            DefCO.inputMessage(this, lbValue.getText());
            return false;
        }

        return true;
    }

    private ZpAttrValue getZpAttrValue()
    {
        ZpAttrValue av = getObject();

        av.setAttrId(cbAttr.getSelectedId());
        av.setSd(dpSd.getDate());
        av.setFd(dpFd.getDate());
        av.setValue(edtValue.getValue());

        return av;
    }

    public void btnSave_actionPerformed(ActionEvent e)
    {
        if (isNextEnabled())
            try
            {
                ZpAttrValue av = getZpAttrValue();
                Connect.getZpRemote().mergeEntity(av);
                super.btnSave_actionPerformed(e);
            } catch (Exception ex)
            {
                DefCO.showError(this, ex);
            }
    }
}

