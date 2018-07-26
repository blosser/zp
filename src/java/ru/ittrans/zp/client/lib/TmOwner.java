package ru.ittrans.zp.client.lib;

import ru.ittrans.zp.client.def.DefTableModel;
import ru.ittrans.zp.io.ZpOwner;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 17.06.11
 * Time: 12:25
 * To change this template use File | Settings | File Templates.
 */
public class TmOwner extends DefTableModel<ZpOwner>
{
    public String[] getColumnNames()
    {
        return OwnerEditDialog.COL_NAMES_OWNER;
    }

    public Object getValueAt(int rowIndex, int columnIndex)
    {
        ZpOwner p = getObj(rowIndex);

        switch (columnIndex)
        {
            case 0:
                return p.getName();
            default:
                return "";
        }
    }
}
