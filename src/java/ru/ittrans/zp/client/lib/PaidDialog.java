package ru.ittrans.zp.client.lib;

import ru.ittrans.zp.client.Connect;
import ru.ittrans.zp.client.MainFrame;
import ru.ittrans.zp.client.def.*;
import ru.ittrans.zp.io.ZpPaid;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 10.05.11
 * Time: 17:12
 * To change this template use File | Settings | File Templates.
 */
public class PaidDialog extends DefDialog
        implements ButtonEditInterface
{
    private PaidEditDialog paidEditDialog = null;

    private JLabel lbPeriod = new JLabel("Период");
    private DefComboBox<String> cbPeriod = new DefComboBox<String>();

    private DefTable tblPaid = new DefTable(new TmPaid(), false);

    private JScrollPane spPaid = new JScrollPane(tblPaid);

    private ButtonEditPanel bep = new ButtonEditPanel(this);

    public PaidDialog(MainFrame mf)
    {
        super(mf, PaidEditDialog.COL_NAMES_PAID[0]);
        paidEditDialog = new PaidEditDialog(mf);
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
        pnlMain.add(spPaid, new GridBagConstraints(0, 1, 2, 1,
                1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(DefCO.INS, DefCO.INS, 0, DefCO.INS), 0, 0));
        pnlMain.add(bep, new GridBagConstraints(0, 2, 2, 1,
                0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                DefCO.INSETS0, 0, 0));

        cbPeriod.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                getPaids();
            }
        });

        setSize(500, 300);
        setLocationRelativeTo(getOwner());
    }

    public void btnInsert_actionPerformed(ActionEvent e)
    {
        ZpPaid paid = new ZpPaid();
        Date period = DefCO.getCbPeriod(cbPeriod);
        paid.setPeriod(period);
        paidEditDialog.showPaid(paid);
        if (paidEditDialog.modalResult)
            getPaids();
    }

    public void btnUpdate_actionPerformed(ActionEvent e)
    {
        if (tblPaid.isSelected())
        {
            ZpPaid paid = (ZpPaid) tblPaid.getSelObj();
            paidEditDialog.showPaid(paid);
            if (paidEditDialog.modalResult)
                getPaids();
        } else DefCO.showSelected(this);
    }

    public void btnDelete_actionPerformed(ActionEvent e)
    {
        if (tblPaid.isSelected())
        {
            try
            {
                ZpPaid paid = (ZpPaid) tblPaid.getSelObj();
                if (DefCO.isDel())
                {
                    Connect.getZpRemote().removeEntity(paid);
                    getPaids();
                }
            } catch (Exception ex)
            {
                DefCO.showError(this, ex);
            }
        } else DefCO.showSelected(this);
    }

    public void getPaids()
    {
        try
        {
            Date period = DefCO.getCbPeriod(cbPeriod);
            List<ZpPaid> mList = Connect.getZpRemote().getPaid(period);
            ((TmPaid) tblPaid.getModel()).setData(mList);
        } catch (Exception ex)
        {
            DefCO.showError(this, ex);
        }
    }

    public void setData(int periodIndex)
    {
        DefCO.setPeriodData(cbPeriod, periodIndex);
    }
}
