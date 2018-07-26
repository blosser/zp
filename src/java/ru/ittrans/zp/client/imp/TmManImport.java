package ru.ittrans.zp.client.imp;

import ru.ittrans.zp.client.DicManager;
import ru.ittrans.zp.client.def.DefTableModel;
import ru.ittrans.zp.io.ZpMan;
import ru.ittrans.zp.io.ZpManImport;
import ru.ittrans.zp.io.ZpShop;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 05.05.11
 * Time: 13:25
 * To change this template use File | Settings | File Templates.
 */
public class TmManImport extends DefTableModel<ZpManImport>
{
    public static String[] MAN_IMPORT_COL =
            new String[]{"Магазин", "ФИО", "ФИО в базе", "Соответствие"};

    public String[] getColumnNames()
    {
        return MAN_IMPORT_COL;
    }

    public Object getValueAt(int rowIndex, int columnIndex)
    {
        ZpManImport mi = getObj(rowIndex);

        switch (columnIndex)
        {
            case 0:
                return DicManager.getShopMap().get(mi.getShopId());
            case 1:
                return mi.getMan();
            case 2:
                return mi.getDbMan();
            case 3:
                return mi.getSearchType();
            default:
                return "";
        }
    }

    public Class getColumnClass(int columnIndex)
    {
        if (columnIndex == 0) return ZpShop.class;
        if (columnIndex == 1) return ZpMan.class;
        if (columnIndex == 2) return ZpMan.class;
        return String.class;
    }
}
