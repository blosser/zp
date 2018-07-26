package ru.ittrans.zp.client.report;

import ru.ittrans.zp.client.Connect;
import ru.ittrans.zp.client.def.*;
import ru.ittrans.zp.io.ZpSession;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 20.12.10
 * Time: 13:29
 * To change this template use File | Settings | File Templates.
 */
public class SessionDialog extends DefDialog
{
    public static final String[] MES_SESSION =
            {"Логин", "С", "По", "Статус", "IP", "Имя машины", "Имя пользователя", "Java"};

    private JPanel pnlNorth = new JPanel();

    private JLabel lbSd = new JLabel("Дата с");
    private DefDatePicker ddpSd = new DefDatePicker();
    private JLabel lbFd = new JLabel("По");
    private DefDatePicker ddpFd = new DefDatePicker();
    private DefButton btnDownload = new DefButton("Загрузить");
    private DefButton btnKill = new DefButton("KILL");

    private DefTable tblSession = new DefTable(new TmSession(), false);
    private JScrollPane spSession = new JScrollPane(tblSession);

    public SessionDialog(JFrame f)
    {
        super(f, DefCO.MSG_SESSIONS);
        jbInit();
    }

    protected void jbInit()
    {
        super.jbInit();

        JPanel pnlMain = getPanel();
        pnlMain.setLayout(new GridBagLayout());

        pnlNorth.setLayout(new FlowLayout(FlowLayout.LEFT));
        pnlNorth.add(lbSd);
        pnlNorth.add(ddpSd);
        pnlNorth.add(lbFd);
        pnlNorth.add(ddpFd);
        pnlNorth.add(btnDownload);
        pnlNorth.add(btnKill);

        pnlMain.add(pnlNorth, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, DefCO.INS, DefCO.INS, DefCO.INS), 0, 0));
        pnlMain.add(spSession, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, DefCO.INS, DefCO.INS, DefCO.INS), 0, 0));

        Color borderColor = UIManager.getColor("Tree.selectionBackground");
        MatteBorder mbHoriz = BorderFactory.createMatteBorder(2, 2, 2, 2, borderColor);
        pnlNorth.setBorder(BorderFactory.createTitledBorder(mbHoriz, "Параметры"));

        DefCO.setMaxWindowsSize(this);
        setLocationRelativeTo(getOwner());

        btnDownload.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                btnDownloadPressed(e);
            }
        });

        btnKill.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                btnKillPressed(e);
            }
        });
    }

    public void btnDownloadPressed(ActionEvent e)
    {
        try
        {
            List<ZpSession> sList =
                    Connect.getZpRemote().getSessions(
                            ddpSd.getTimestamp(),
                            ddpFd.getTimestamp());
            ((TmSession) tblSession.getModel()).setData(sList);
        } catch (Exception ex)
        {
            DefCO.showError(this, ex);
        }
    }

    public void btnKillPressed(ActionEvent e)
    {
        try
        {
            ZpSession session = (ZpSession) tblSession.getSelObj();
            if (session == null)
                DefCO.showSelected(this);
            else
            {
                if ((ZpSession.ST_ACTIVE.equals(session.getStatusId())))
                {
                    session.setStatusId(ZpSession.ST_BLOCK);
                    Connect.getZpRemote().mergeEntity(session);
                    btnDownload.doClick();
                } else
                    DefCO.showInf(this, "Выберите активную сессию");
            }
        } catch (Exception ex)
        {
            DefCO.showError(this, ex);
        }
    }

    public void setObject()
    {
        super.setObject(null);
        btnDownload.doClick();
    }
}
