package ru.ittrans.zp.client.lib;

import ru.ittrans.zp.client.DicManager;
import ru.ittrans.zp.client.def.DefTableModel;
import ru.ittrans.zp.io.ZpShop;
import ru.ittrans.zp.io.ZpShopShift;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 15.12.11
 * Time: 14:10
 * To change this template use File | Settings | File Templates.
 */
public class TmShopShift extends DefTableModel<ZpShopShift>
{
    public String[] getColumnNames()
    {
        return ShopShiftEditDialog.COL_NAMES_SHOP_SHIFT;
    }

    public Object getValueAt(int rowIndex, int columnIndex)
    {
        ZpShopShift shopShift = getObj(rowIndex);

        switch (columnIndex)
        {
            case 0:
                return DicManager.getShopMap().get(shopShift.getShopId());
            case 1:
                return shopShift.getValue();
            default:
                return "";
        }
    }

    public Class getColumnClass(int columnIndex)
    {
        if (columnIndex == 0) return ZpShop.class;
        if (columnIndex == 1) return Long.class;
        return String.class;
    }
}


