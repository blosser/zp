package ru.ittrans.zp.client.lib;

import ru.ittrans.zp.client.DicManager;
import ru.ittrans.zp.client.def.DefTableModel;
import ru.ittrans.zp.io.ZpClient;
import ru.ittrans.zp.io.ZpMan;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 16.06.11
 * Time: 18:29
 * To change this template use File | Settings | File Templates.
 */
public class TmClient extends DefTableModel<ZpClient>
{
    public String[] getColumnNames()
    {
        return ClientEditDialog.COL_NAMES_CLIENT;
    }

    public Object getValueAt(int rowIndex, int columnIndex)
    {
        ZpClient m = getObj(rowIndex);

        switch (columnIndex)
        {
            case 0:
                return m.getName();
            case 1:
                return m.getLogin();
            case 2:
                return m.getPassword();
            case 3:
                return ZpMan.ONE.equals(m.getAdmin());
            case 4:
                return DicManager.getParentShopMap().get(m.getShopId());
            default:
                return "";
        }
    }

    public Class getColumnClass(int columnIndex)
    {
        if (columnIndex == 3) return Boolean.class;
        return String.class;
    }
}
