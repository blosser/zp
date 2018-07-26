package ru.ittrans.zp.client.lib;

import ru.ittrans.zp.client.DicManager;
import ru.ittrans.zp.client.def.DefEmail;
import ru.ittrans.zp.client.def.DefTableModel;
import ru.ittrans.zp.io.*;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 10.05.11
 * Time: 17:49
 * To change this template use File | Settings | File Templates.
 */
public class TmShop extends DefTableModel<ZpShop>
{
    public String[] getColumnNames()
    {
        return ShopPanel.COL_NANES_SHOP;
    }

    public Object getValueAt(int rowIndex, int columnIndex)
    {
        ZpShop p = getObj(rowIndex);

        switch (columnIndex)
        {
            case 0:
                return DicManager.getParentShopMap().get(p.getParentId());
            case 1:
                return p.getCode();
            case 2:
                return p.getAddress();
            case 3:
                return p.getHowToGet();
            case 4:
                return DicManager.getMetroMap().get(p.getMetroId());
            case 5:
                return p.getPhone();
            case 6:
                return p.getPhone2();
            case 7:
                return p.getComment();
            case 8:
                return DicManager.getClientMap().get(p.getTerritClientId());
            case 9:
                return DicManager.getClientMap().get(p.getTabClientId());
            case 10:
                return p.getEmail() == null ? null : new DefEmail(p.getEmail());
            case 11:
                return DicManager.getRegionMap().get(p.getRegionId());
            case 12:
                return p.getDirector();
            case 13:
                return DicManager.getDeliverMap().get(p.getDeliver());
            case 14:
                return ZpMan.ONE.equals(p.getPriority());
            default:
                return "";
        }
    }

    public Class getColumnClass(int columnIndex)
    {
        if (columnIndex == 0) return ZpShop.class;
        if (columnIndex == 4) return ZpMetro.class;
        if (columnIndex == 8 || columnIndex == 9) return ZpClient.class;
        if (columnIndex == 10) return DefEmail.class;
        if (columnIndex == 11) return ZpRegion.class;
        if (columnIndex == 14) return Boolean.class;

        return String.class;
    }
}
