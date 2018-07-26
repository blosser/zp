package ru.ittrans.zp.client.report;

import ru.ittrans.zp.client.def.DefTableModel;
import ru.ittrans.zp.client.def.DefTimestamp;
import ru.ittrans.zp.io.ZpClient;
import ru.ittrans.zp.io.ZpSession;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 20.12.10
 * Time: 13:46
 * To change this template use File | Settings | File Templates.
 */
public class TmSession extends DefTableModel<ZpSession>
{
    public String[] getColumnNames()
    {
        return SessionDialog.MES_SESSION;
    }

    public Object getValueAt(int rowIndex, int columnIndex)
    {
        ZpSession s = getObj(rowIndex);

        switch (columnIndex)
        {
            case 0:
                return s.getClient();
            case 1:
                return new DefTimestamp(s.getSd().getTime());
            case 2:
                return new DefTimestamp(s.getFd().getTime());
            case 3:
                return s.getStatusId();
            case 4:
                return s.getCompIp();
            case 5:
                return s.getCompName();
            case 6:
                return s.getCompUser();
            case 7:
                return s.getCompJava();
            default:
                return "";
        }
    }

    public Class getColumnClass(int columnIndex)
    {
        if (columnIndex == 0) return ZpClient.class;
        if (columnIndex == 1 || columnIndex == 2) return DefTimestamp.class;
        return String.class;
    }
}
