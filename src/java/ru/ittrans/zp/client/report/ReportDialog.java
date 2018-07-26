package ru.ittrans.zp.client.report;

import ru.ittrans.zp.client.Connect;
import ru.ittrans.zp.client.MainFrame;
import ru.ittrans.zp.client.def.DefCO;
import ru.ittrans.zp.client.def.DefDialog;
import ru.ittrans.zp.client.def.DefTable;
import ru.ittrans.zp.client.lib.ManEditDialog;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.util.List;


/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 31.07.11
 * Time: 21:21
 * To change this template use File | Settings | File Templates.
 */
public class ReportDialog extends DefDialog
{
    private DefTable tblReport = new DefTable(null, false);

    private JScrollPane spReport = new JScrollPane(tblReport);

    public ReportDialog(MainFrame mf)
    {
        super(mf, DefCO.MSG_REPORTS);
        jbInit();
    }

    public void jbInit()
    {
        super.jbInit();

        JPanel pnlMain = getPanel();
        pnlMain.setLayout(new GridBagLayout());

        pnlMain.add(spReport, new GridBagConstraints(0, 0, 1, 1,
                1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                DefCO.INSETS, 0, 0));

        DefCO.setMaxWindowsSize(this);
        setLocationRelativeTo(getOwner());
    }

    public void setData(Date period, String reportName, String manScore)
    {
        try
        {
            setTitle(reportName);
            if (ManEditDialog.COL_NAMES_MAN[0].equals(reportName))
            {
                List<List> bList = Connect.getZpRemote().getReportSecondJob(period);
                tblReport.setModel(new TmReport(TmReport.COLUMN_SECOND_JOB));
                ((TmReport) tblReport.getModel()).setData(bList);
            } else if (DefCO.MSG_MAN_SCORE.equals(reportName))
            {
                List<List> bList = Connect.getZpRemote().getReportVt11(period, manScore);
                tblReport.setModel(new TmVt11());
                ((TmVt11) tblReport.getModel()).setData(bList);
            } else if (DefCO.MSG_SMENA.equals(reportName))
            {
                List<List> bList = Connect.getZpRemote().getReportSmena(period);
                tblReport.setModel(new TmReport(TmReport.COLUMN_SMENA));
                ((TmReport) tblReport.getModel()).setData(bList);
            } else if (DefCO.MSG_SMENA_PAY.equals(reportName))
            {
                List<List> bList = Connect.getZpRemote().getReportSmenaPay(period);
                tblReport.setModel(new TmReport(TmReport.COLUMN_SMENA_PAY));
                ((TmReport) tblReport.getModel()).setData(bList);
            } else if (DefCO.MSG_PLAN_CNT.equals(reportName))
            {
                List<List> bList = Connect.getZpRemote().getReportPlanCnt(period);
                tblReport.setModel(new TmReport(TmReport.COLUMN_PLAN_CNT));
                ((TmReport) tblReport.getModel()).setData(bList);
            }
        } catch (Exception ex)
        {
            DefCO.showError(this, ex);
        }
    }
}


