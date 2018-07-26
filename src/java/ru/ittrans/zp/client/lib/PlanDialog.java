package ru.ittrans.zp.client.lib;

import ru.ittrans.zp.client.Connect;
import ru.ittrans.zp.client.DicManager;
import ru.ittrans.zp.client.MainFrame;
import ru.ittrans.zp.client.def.*;
import ru.ittrans.zp.io.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 11.07.12
 * Time: 12:55
 * To change this template use File | Settings | File Templates.
 */
public class PlanDialog extends DefDialog
        implements ButtonEditInterface
{
    private PlanEditDialog planEditDialog = null;

    public static final String[] PRIORITY_LIST = ManDialog.SECOND_JOB_LIST;

    private JLabel lbPeriod = new JLabel("Период");
    private DefComboBox<String> cbPeriod = new DefComboBox<String>();

    private DefComboBox<String> cbDayNum = new DefComboBox<String>();

    private JLabel lbMetro = new JLabel(ShopPanel.COL_NANES_SHOP[4]);
    private DefComboBox<ZpMetro> cbMetro = new DefComboBox<ZpMetro>();

    private JLabel lbPriority = new JLabel(ShopPanel.COL_NANES_SHOP[14]);
    private DefComboBox<String> cbPriority = new DefComboBox<String>();

    private JLabel lbActive = new JLabel(PlanEditDialog.COL_NAMES_SHOP[5]);
    private DefComboBox<String> cbActive = new DefComboBox<String>();

    private JLabel lbTerritClient = new JLabel(ShopPanel.COL_NANES_SHOP[8]);
    private DefComboBox<ZpClient> cbTerritClient = new DefComboBox<ZpClient>();

    public DefButton btnSearch = new DefButton("Поиск");

    private DefTable tblPlan = new DefTable(new TmPlan(), false);
    private JScrollPane spPlan = new JScrollPane(tblPlan);

    private ShopPanel pnlShop = new ShopPanel(false);

    private ButtonEditPanel bep = new ButtonEditPanel(this);

    private boolean isSetData;

    public PlanDialog(MainFrame mf)
    {
        super(mf, DefCO.MSG_PlAN);
        planEditDialog = new PlanEditDialog(mf);
        jbInit();
    }

    public void jbInit()
    {
        super.jbInit();

        JPanel pnlMain = getPanel();
        pnlMain.setLayout(new GridBagLayout());

        JPanel pnlTop = new JPanel(new GridBagLayout());
        JPanel pnlTop1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel pnlTop2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlTop.add(pnlTop1, new GridBagConstraints(0, 0, 1, 1,
                1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
                DefCO.INSETS0, 0, 0));
        pnlTop.add(pnlTop2, new GridBagConstraints(0, 1, 1, 1,
                1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
                DefCO.INSETS0, 0, 0));
        pnlTop1.add(lbPeriod);
        pnlTop1.add(cbPeriod);
        pnlTop1.add(cbDayNum);
        pnlTop1.add(lbMetro);
        pnlTop1.add(cbMetro);
        pnlTop1.add(btnSearch);
        pnlTop2.add(lbPriority);
        pnlTop2.add(cbPriority);
        pnlTop2.add(lbActive);
        pnlTop2.add(cbActive);
        pnlTop2.add(lbTerritClient);
        pnlTop2.add(cbTerritClient);


        cbMetro.setPreferredSize(new Dimension(200, 23));

        pnlMain.add(pnlTop, new GridBagConstraints(0, 0, 1, 1,
                1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
                DefCO.INSETS, 0, 0));
        pnlMain.add(spPlan, new GridBagConstraints(0, 1, 1, 1,
                1.0, 0.7, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(DefCO.INS, DefCO.INS, 0, DefCO.INS), 0, 0));
        pnlMain.add(bep, new GridBagConstraints(0, 2, 1, 1,
                0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                DefCO.INSETS0, 0, 0));
        pnlMain.add(pnlShop, new GridBagConstraints(0, 3, 1, 1,
                1.0, 0.3, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(DefCO.INS, DefCO.INS, 0, DefCO.INS), 0, 0));

        cbPeriod.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                cbPeriodPressed();
            }
        });

        btnSearch.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                btnSearchPressed();
            }
        });

        btnSearch.setIcon(DefCO.loadIcon(DefCO.PNG_EXEC));

        tblPlan.getSelectionModel().addListSelectionListener(new ListSelectionListener()
        {
            public void valueChanged(ListSelectionEvent e)
            {
                ZpManValue mv = (ZpManValue) tblPlan.getSelObj();
                Long shopId = mv == null ? null : mv.getShopId();
                getShops(shopId);
            }
        });

        EmailTableCellRenderer dtcr = new EmailTableCellRenderer();
        tblPlan.addMouseListener(dtcr);
        tblPlan.addMouseMotionListener(dtcr);

        DefCO.setMaxWindowsSize(this);
        setLocationRelativeTo(getOwner());
    }

    private void cbPeriodPressed()
    {
        Date period = DefCO.getCbPeriod(cbPeriod);
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(period.getTime());
        int dayCnt = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        LinkedHashMap res = new LinkedHashMap();
        for (int i = 1; i <= dayCnt; i++)
        {
            String dateNum = String.valueOf(i);
            res.put(dateNum, dateNum);
        }
        DefComboBox.cbAddItem(cbDayNum, res, true, true);
    }

    private void btnSearchPressed()
    {
        getPlan(null);
    }

    public void setData(int periodIndex)
    {
        isSetData = true;
        DefCO.setPeriodData(cbPeriod, periodIndex);
        DefComboBox.cbAddItem(cbMetro, DicManager.getMetroMap(), true, true);
        List<String> aList = new ArrayList<String>();
        for (int i = 0; i < PRIORITY_LIST.length; i++)
            aList.add(PRIORITY_LIST[i]);
        DefComboBox.cbAddItem(cbPriority, aList);
        DefComboBox.cbAddItem(cbActive, aList);
        DefComboBox.cbAddItem(cbTerritClient, DicManager.getClientMap(), true, true);
        isSetData = false;
    }

    public void btnInsert_actionPerformed(ActionEvent e)
    {
        ZpManValue mv = new ZpManValue();
        Date period = DefCO.getCbPeriod(cbPeriod);
        //mv.setSd(period);
        mv.setValueType(ZpManValue.VALUE_TYPE13);
        mv.setActive(ZpMan.TWO);
        planEditDialog.showPlan(mv);
        if (planEditDialog.modalResult)
            getPlan(null);
    }

    public void btnUpdate_actionPerformed(ActionEvent e)
    {
        if (tblPlan.isSelected())
        {
            ZpManValue mv = (ZpManValue) tblPlan.getSelObj();
            planEditDialog.showPlan(mv);
            if (planEditDialog.modalResult)
                getPlan(mv);
        } else DefCO.showSelected(this);
    }

    public void btnDelete_actionPerformed(ActionEvent e)
    {
        if (tblPlan.isSelected())
        {
            try
            {
                ZpManValue mv = (ZpManValue) tblPlan.getSelObj();
                if (DefCO.isDel())
                {
                    Connect.getZpRemote().removeEntity(mv);
                    getPlan(null);
                }
            } catch (Exception ex)
            {
                DefCO.showError(this, ex);
            }
        } else DefCO.showSelected(this);
    }

    public void getPlan(ZpManValue mvSelected)
    {
        if (!isSetData)
            try
            {
                Date period = DefCO.getCbPeriod(cbPeriod);

                int cbDateNumIndex = cbDayNum.getSelectedIndex();
                Long dayNum = ZpMan.ALL;
                if (cbDateNumIndex > 0)
                    dayNum = new Long(cbDayNum.getSelectedItem());

                Long metroId = cbMetro.getSelectedId();

                int cbPriorityIndex = cbPriority.getSelectedIndex();
                Long priority = ZpMan.ALL;
                if (cbPriorityIndex == 1) priority = ZpMan.ONE;
                else if (cbPriorityIndex == 2) priority = ZpMan.ZERO;

                int cbActiveIndex = cbActive.getSelectedIndex();
                Long active = ZpMan.ALL;
                if (cbActiveIndex == 1) active = ZpMan.ONE;
                else if (cbActiveIndex == 2) active = ZpMan.TWO;

                Long territClientId = cbTerritClient.getSelectedId();

                List<ZpManValue> mvList = Connect.getZpRemote().getPlan(period, dayNum,
                        metroId, priority, active, territClientId);
                ((TmPlan) tblPlan.getModel()).setData(mvList);
                int row = -1;
                if (mvSelected != null)
                    for (ZpManValue mv : mvList)
                    {
                        row++;
                        if (mvSelected.getId().equals(mv.getId()))
                        {
                            tblPlan.getSelectionModel().setSelectionInterval(row, row);
                            break;
                        }
                    }

            } catch (Exception ex)
            {
                DefCO.showError(this, ex);
            }
    }

    public void getShops(Long shopId)
    {
        if (shopId == null)
            pnlShop.showShop(new ZpShop());
        else
            pnlShop.showShop(DicManager.getShopMap().get(shopId));
    }

}
