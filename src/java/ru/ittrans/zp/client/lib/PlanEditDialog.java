package ru.ittrans.zp.client.lib;

import ru.ittrans.zp.client.Connect;
import ru.ittrans.zp.client.DicManager;
import ru.ittrans.zp.client.MainFrame;
import ru.ittrans.zp.client.def.*;
import ru.ittrans.zp.io.ZpMan;
import ru.ittrans.zp.io.ZpManValue;
import ru.ittrans.zp.io.ZpShop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 11.07.12
 * Time: 13:02
 * To change this template use File | Settings | File Templates.
 */
public class PlanEditDialog extends DefDialog<ZpManValue>
        implements ButtonSaveInterface
{
    public static String[] COL_NAMES_SHOP =
            new String[]{"ФИО", "Дата*", "Код магазина*", "Метро", "Примечание", "Подтвержден"};

    private ManDialog manDialog = null;

    private JLabel lbName = new JLabel(COL_NAMES_SHOP[0]);
    private DefTextField edtName = new DefTextField();
    private DefButton btnDot = new DefButton("...");
    private DefButton btnClear = new DefButton("X");

    private JLabel lbSd = new JLabel(COL_NAMES_SHOP[1]);
    private DefDatePicker dpSd = new DefDatePicker();

    private JLabel lbShop = new JLabel(COL_NAMES_SHOP[2]);
    private DefComboBox<ZpShop> cbShop = new DefComboBox<ZpShop>();

    private DefCheckBox cbxActive = new DefCheckBox(COL_NAMES_SHOP[5], false);

    private JLabel lbCnt = new JLabel("Кол-во персонала");
    private DefNumberTextField edtCnt = new DefNumberTextField();

    private ButtonSavePanel bsp = new ButtonSavePanel(this);

    private Long manId = null;

    public PlanEditDialog(MainFrame mf)
    {
        super(mf, DefCO.MSG_PlAN);
        manDialog = new ManDialog(mf, true);
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

        edtName.setMyEditable(false);
        JPanel pnlName = new JPanel(new GridBagLayout());

        pnlName.add(edtName, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));
        pnlName.add(btnDot, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE, DefCO.INSETS, 0, 0));
        pnlName.add(btnClear, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE, DefCO.INSETS, 0, 0));

        pnlMan.add(lbName, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));
        pnlMan.add(pnlName, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS0, 0, 0));

        pnlMan.add(lbSd, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));
        pnlMan.add(dpSd, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE, DefCO.INSETS, 0, 0));

        pnlMan.add(lbShop, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));
        pnlMan.add(cbShop, new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));

        pnlMan.add(lbCnt, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));
        pnlMan.add(edtCnt, new GridBagConstraints(1, 3, 1, 1, 1.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));

        pnlMan.add(cbxActive, new GridBagConstraints(0, 4, 2, 1, 1.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.BOTH, DefCO.INSETS0, 0, 0));

        btnDot.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent actionEvent)
            {
                btnDotPressed();
            }
        });

        btnClear.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent actionEvent)
            {
                btnClearPressed();
            }
        });

        setSize(600, 250);
        setLocationRelativeTo(getOwner());
    }

    private void btnDotPressed()
    {
        ZpManValue mv = getObject();
        manDialog.setMan(mv.getMan());
        manDialog.setVisible(true);
        if (manDialog.modalResult)
        {
            ZpMan man = manDialog.getMan();
            manId = man.getId();
            edtName.setText(man.toString());
            mv.setMan(man);
        }
    }

    private void btnClearPressed()
    {
        manId = null;
        edtName.setText("");
    }

    public void showPlan(ZpManValue mv)
    {
        setObject(mv);

        manId = mv.getMan() == null ? null : mv.getMan().getId();

        DefComboBox.cbAddItem(cbShop, DicManager.getShopMap(), false);
        edtCnt.setValue(ZpMan.ONE);

        lbCnt.setVisible(mv.getId() == null);
        edtCnt.setVisible(mv.getId() == null);

        edtName.setText(mv.getMan() == null ? "" : mv.getMan().toString());
        dpSd.setDate(mv.getSd());
        cbShop.setSelectedItem(DicManager.getShopMap().get(mv.getShopId()));

        cbxActive.setValue(mv.getActive());

        DefCO.requestFocus(dpSd);
        setVisible(true);
    }

    protected boolean isNextEnabled()
    {
//        if (manId == null && cbxActive.isSelected())
//        {
//            DefCO.inputMessage(this, lbName.getText());
//            return false;
//        }

        if (dpSd.getDate() == null)
        {
            DefCO.inputMessage(this, lbSd.getText());
            return false;
        }

        if (cbShop.getSelectedId() == null)
        {
            DefCO.inputMessage(this, lbShop.getText());
            return false;
        }

        if (edtCnt.getValue() == null)
        {
            DefCO.inputMessage(this, lbCnt.getText());
            return false;
        }

        return true;
    }

    private ZpManValue getZpManValue()
    {
        ZpManValue mv = getObject();

        mv.setManId(manId);
        mv.setSd(dpSd.getDate());
        mv.setShopId(cbShop.getSelectedId());
        mv.setSessionId(Connect.getSessionId());
        mv.setValue(ZpMan.ONE);
        mv.setActive(cbxActive.isSelected() ? ZpMan.ONE : ZpMan.TWO);

        return mv;
    }

    public void btnSave_actionPerformed(ActionEvent e)
    {
        if (isNextEnabled())
            try
            {
                ZpManValue mv = getZpManValue();
                int cnt = edtCnt.getValue().intValue();
                for (int i = 0; i < cnt; i++)
                    Connect.getZpRemote().mergeEntity(mv);
                super.btnSave_actionPerformed(e);
            } catch (Exception ex)
            {
                DefCO.showError(this, ex);
            }
    }
}
