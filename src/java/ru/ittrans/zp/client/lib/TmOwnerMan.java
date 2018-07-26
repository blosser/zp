package ru.ittrans.zp.client.lib;

import ru.ittrans.zp.client.DicManager;
import ru.ittrans.zp.client.def.DefTableModel;
import ru.ittrans.zp.io.ZpOwnerMan;

import java.sql.Date;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 17.06.11
 * Time: 13:45
 * To change this template use File | Settings | File Templates.
 */
public class TmOwnerMan extends DefTableModel<ZpOwnerMan>
{

    public String[] getColumnNames()
    {
        return OwnerManEditDialog.COL_NAMES_OWNER_MAN;
    }

    public Object getValueAt(int rowIndex, int columnIndex)
    {
        ZpOwnerMan m = getObj(rowIndex);

        switch (columnIndex)
        {
            case 0:
                return m.getName();
            case 1:
                return DicManager.getOwnerMap().get(m.getOwnerId());
            case 2:
                return m.getSd();
            case 3:
                return m.getFd();
            default:
                return "";
        }
    }

    public Class getColumnClass(int columnIndex)
    {
        if (columnIndex == 2 || columnIndex == 3) return Date.class;
        return String.class;
    }
}