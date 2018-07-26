package ru.ittrans.zp.client.lib;

import ru.ittrans.zp.client.Connect;
import ru.ittrans.zp.client.MainFrame;
import ru.ittrans.zp.client.def.*;
import ru.ittrans.zp.io.ZpBatch;

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
 * Time: 18:21
 * To change this template use File | Settings | File Templates.
 */
public class BatchDialog extends DefDialog
        implements ButtonEditInterface
{
    public static String[] COL_NAMES_BATCH =
            new String[]{DefCO.MSG_BATCH};

    private JLabel lbPeriod = new JLabel("Период");
    private DefComboBox<String> cbPeriod = new DefComboBox<String>();

    private DefTable tblBatch = new DefTable(new TmBatch(), false);

    private JScrollPane spBatch = new JScrollPane(tblBatch);

    private ButtonEditPanel bep = new ButtonEditPanel(this, false, false, true);
    private DefButton btnDelEmpty = new DefButton("Удалить пустые");

    public BatchDialog(MainFrame mf)
    {
        super(mf, COL_NAMES_BATCH[0]);
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
        pnlMain.add(spBatch, new GridBagConstraints(0, 1, 2, 1,
                1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(DefCO.INS, DefCO.INS, 0, DefCO.INS), 0, 0));
        pnlMain.add(bep, new GridBagConstraints(0, 2, 2, 1,
                0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                DefCO.INSETS0, 0, 0));

        if (false)
            bep.addButton(btnDelEmpty);

        cbPeriod.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                getBatchs();
            }
        });

        btnDelEmpty.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                removeEmptyBatch();
            }
        });

        setSize(500, 300);
        setLocationRelativeTo(getOwner());
    }

    public void btnInsert_actionPerformed(ActionEvent e)
    {
    }

    public void btnUpdate_actionPerformed(ActionEvent e)
    {
    }

    public void btnDelete_actionPerformed(ActionEvent e)
    {
        if (tblBatch.isSelected())
        {
            try
            {
                ZpBatch batch = (ZpBatch) tblBatch.getSelObj();
                if (DefCO.isDel())
                {
                    Connect.getZpRemote().removeBatch(batch);
                    getBatchs();
                }
            } catch (Exception ex)
            {
                DefCO.showError(this, ex);
            }
        } else DefCO.showSelected(this);
    }

    public void getBatchs()
    {
        try
        {
            Date period = DefCO.getCbPeriod(cbPeriod);
            List<ZpBatch> bList = Connect.getZpRemote().getBatch(period);
            ((TmBatch) tblBatch.getModel()).setData(bList);
        } catch (Exception ex)
        {
            DefCO.showError(this, ex);
        }
    }

    public void setData(int periodIndex)
    {
        DefCO.setPeriodData(cbPeriod, periodIndex);
    }

    public void removeEmptyBatch()
    {
        try
        {
            Date period = DefCO.getCbPeriod(cbPeriod);
            Connect.getZpRemote().removeEmptyBatch(period);
            getBatchs();
        } catch (Exception ex)
        {
            DefCO.showError(this, ex);
        }
    }
}

