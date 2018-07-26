package ru.ittrans.zp.client.lib;

import ru.ittrans.zp.client.def.DefTableModel;
import ru.ittrans.zp.io.ZpPaid;

import java.sql.Date;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 10.05.11
 * Time: 17:14
 * To change this template use File | Settings | File Templates.
 */
public class TmPaid extends DefTableModel<ZpPaid>
{
    public String[] getColumnNames()
    {
        return PaidEditDialog.COL_NAMES_PAID;
    }

    public Object getValueAt(int rowIndex, int columnIndex)
    {
        ZpPaid p = getObj(rowIndex);

        switch (columnIndex)
        {
            case 0:
                return p.getName();
            case 1:
                return p.getD();
            default:
                return "";
        }
    }

    public Class getColumnClass(int columnIndex)
    {
        if (columnIndex == 1) return Date.class;
        return String.class;
    }
}

