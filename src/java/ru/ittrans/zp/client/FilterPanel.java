package ru.ittrans.zp.client;

import ru.ittrans.zp.client.def.*;
import ru.ittrans.zp.client.lib.*;
import ru.ittrans.zp.io.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 29.04.11
 * Time: 11:35
 * To change this template use File | Settings | File Templates.
 */
public class FilterPanel extends JPanel
{
    public static int start_year = 2010;
    public static int start_month = 12;

    private JLabel lbPeriod = new JLabel("Период");
    private DefComboBox<String> cbPeriod = new DefComboBox<String>();

    private JLabel lbShop = new JLabel(DefCO.MSG_SHOP);
    private DefComboBox<ZpShop> cbShop = new DefComboBox<ZpShop>();

    private JLabel lbPaid = new JLabel(PaidEditDialog.COL_NAMES_PAID[0]);
    private DefComboBox<ZpPaid> cbPaid = new DefComboBox<ZpPaid>();
    private JLabel lbPaidD = new JLabel("за");
    private DefComboBox<ZpPaid> cbPaidD = new DefComboBox<ZpPaid>();

    private JLabel lbManScore = new JLabel(DefCO.MSG_MAN_SCORE);
    private DefTextField edtManScore = new DefTextField();

    private DefCheckBox cbxShowHide = new DefCheckBox("Уд.", false);

    private JLabel lbBatch = new JLabel(BatchDialog.COL_NAMES_BATCH[0]);
    private DefComboBox<ZpBatch> cbBatch = new DefComboBox<ZpBatch>();

    private JLabel lbCard = new JLabel(ManEditDialog.COL_NAMES_MAN[16]);
    private DefComboBox<String> cbCard = new DefComboBox<String>();

    private JLabel lbFio = new JLabel(ManEditDialog.COL_NAMES_MAN[2]);
    private DefTextField edtFio = new DefTextField();

    public DefButton btnSearch = new DefButton("Поиск");

    private JLabel lbTerritClient = new JLabel(ShopPanel.COL_NANES_SHOP[8]);
    private DefComboBox<ZpClient> cbTerritClient = new DefComboBox<ZpClient>();

    private JLabel lbTabClient = new JLabel(ShopPanel.COL_NANES_SHOP[9]);
    private DefComboBox<ZpClient> cbTabClient = new DefComboBox<ZpClient>();

    private JLabel lbPlanFact = new JLabel("План/Факт");
    private DefComboBox<ZpClient> cbPlanFact = new DefComboBox<ZpClient>();

    public FilterPanel(ActionListener al)
    {
        super(new GridBagLayout());
        btnSearch.addActionListener(al);
        setData();
        jbInit();
    }

    private void setData()
    {
        int index = 0;
        Timestamp sd = Connect.getClient().getSession().getSd();
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(sd.getTime());
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;

        String str = (month < 10 ? "0" : "") + month + "." + year;

        DefCO.setPeriodData(cbPeriod, -1);

        cbPeriod.setSelectedItem(str);
        if (cbPeriod.getSelectedIndex() > 0)
            cbPeriod.setSelectedIndex(cbPeriod.getSelectedIndex() - 1);

        List<String> aList = new ArrayList<String>();
        for (int i = 0; i < ManDialog.SECOND_JOB_LIST.length; i++)
            aList.add(ManDialog.SECOND_JOB_LIST[i]);
        DefComboBox.cbAddItem(cbCard, aList);

        cbPeriodPressed();
    }

    public Date getPeriod()
    {
        Date period = DefCO.getCbPeriod(cbPeriod);
        return period;
    }

    public void cbPeriodPressed()
    {
        Date period = getPeriod();
        DicManager.preparePeriod(period);
        DefComboBox.cbAddItem(cbShop, DicManager.getShopMap(), true);
        DefComboBox.cbAddItem(cbPaid, DicManager.getPaidMap(), true);
        DefComboBox.cbAddItem(cbPaidD, DicManager.getPaidDMap(), true);
        DefComboBox.cbAddItem(cbBatch, DicManager.getBatchMap(), true);
        DefComboBox.cbAddItem(cbTerritClient, DicManager.getClientMap(), true, true);
        DefComboBox.cbAddItem(cbTabClient, DicManager.getClientMap(), true, true);

        ZpShop plan = new ZpShop();
        plan.setId(ZpMan.ZERO);
        plan.setCode("План");
        ZpShop fact = new ZpShop();
        fact.setId(ZpMan.ONE);
        fact.setCode("Факт");
        List<ZpShop> pList = new ArrayList<ZpShop>();
        pList.add(plan);
        pList.add(fact);
        DefComboBox.cbAddItem(cbPlanFact, pList, true);
        cbPlanFact.setSelectedItem(fact);
    }

    private void jbInit()
    {
        cbPeriod.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                cbPeriodPressed();
            }
        });

        JPanel pnlTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel pnlTop2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel pnlBottom = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel pnlBottom2 = new JPanel(new FlowLayout(FlowLayout.LEFT));

        add(pnlTop, new GridBagConstraints(0, 0, 1, 1,
                1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                DefCO.INSETS0, 0, 0));
        add(pnlTop2, new GridBagConstraints(0, 1, 1, 1,
                1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                DefCO.INSETS0, 0, 0));
        add(pnlBottom2, new GridBagConstraints(0, 2, 1, 1,
                1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                DefCO.INSETS0, 0, 0));
        add(pnlBottom, new GridBagConstraints(0, 3, 1, 1,
                1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                DefCO.INSETS0, 0, 0));

        pnlTop.add(lbPeriod);
        pnlTop.add(cbPeriod);
        pnlTop.add(lbShop);
        pnlTop.add(cbShop);
        pnlTop2.add(lbPaid);
        pnlTop2.add(cbPaid);
        pnlTop2.add(lbPaidD);
        pnlTop2.add(cbPaidD);
        pnlTop2.add(lbManScore);
        pnlTop2.add(edtManScore);
        if (Connect.isEvild())
        {
            pnlTop2.add(cbxShowHide);
        }
        pnlTop2.add(lbPlanFact);
        pnlTop2.add(cbPlanFact);

        pnlBottom.add(lbBatch);
        pnlBottom.add(cbBatch);
        pnlBottom.add(lbCard);
        pnlBottom.add(cbCard);
        pnlBottom.add(lbFio);
        pnlBottom.add(edtFio);
        edtFio.setPreferredSize(new Dimension(200, 20));
        edtManScore.setPreferredSize(new Dimension(50, 20));
        //edtFio.setText("ака");
        pnlBottom.add(btnSearch);

        pnlBottom2.add(lbTerritClient);
        pnlBottom2.add(cbTerritClient);
        pnlBottom2.add(lbTabClient);
        pnlBottom2.add(cbTabClient);

        btnSearch.setIcon(DefCO.loadIcon(DefCO.PNG_EXEC));
    }

    public String getFio()
    {
        return edtFio.getText();
    }

    public int getPeriodIndex()
    {
        return cbPeriod.getSelectedIndex();
    }

    public Long getShopId()
    {
        return cbShop.getSelectedId();
    }

    public Long getPaidId()
    {
        return cbPaid.getSelectedId();
    }

    public Long getPaidD()
    {
        return cbPaidD.getSelectedId();
    }

    public Long getBatchId()
    {
        return cbBatch.getSelectedId();
    }

    public Long getShowHide()
    {
        Long showHide = cbxShowHide.isSelected() ? ZpMan.ONE : ZpMan.ZERO;
        return showHide;
    }

    public String getManScore()
    {
        String manScore = edtManScore.getText();
        return manScore;
    }

    public Long getCard()
    {
        Long card = ZpMan.ALL;
        if (cbCard.getSelectedIndex() == 1) card = ZpMan.ONE;
        else if (cbCard.getSelectedIndex() == 2) card = ZpMan.ZERO;
        return card;
    }

    public Long getTerritClientId()
    {
        return cbTerritClient.getSelectedId();
    }

    public Long getTabClientId()
    {
        return cbTabClient.getSelectedId();
    }

    public Long getPlantFactId()
    {
        return cbPlanFact.getSelectedId();
    }

    public void setFilterEnabled(boolean isEditable)
    {
        cbPeriod.setEnabled(isEditable);
        cbShop.setEnabled(isEditable);
        cbPaid.setEnabled(isEditable);
        cbPaidD.setEnabled(isEditable);
        cbBatch.setEnabled(isEditable);
        edtManScore.setEnabled(isEditable);
        cbxShowHide.setEnabled(isEditable);
        edtFio.setEnabled(isEditable);
        cbCard.setEnabled(isEditable);
        cbTerritClient.setEnabled(isEditable);
        cbTabClient.setEnabled(isEditable);
        cbPlanFact.setEnabled(isEditable);
        btnSearch.setEnabled(isEditable);
    }
}
