package ru.ittrans.zp.client.lib;

import ru.ittrans.zp.client.DicManager;
import ru.ittrans.zp.client.def.DefEmail;
import ru.ittrans.zp.client.def.DefTableModel;
import ru.ittrans.zp.io.ZpMan;
import ru.ittrans.zp.io.ZpManValue;
import ru.ittrans.zp.io.ZpMetro;
import ru.ittrans.zp.io.ZpShop;

import java.sql.Date;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 11.07.12
 * Time: 13:00
 * To change this template use File | Settings | File Templates.
 */
public class TmPlan extends DefTableModel<ZpManValue>
{
    public String[] getColumnNames()
    {
        return PlanEditDialog.COL_NAMES_SHOP;
    }

    public Object getValueAt(int rowIndex, int columnIndex)
    {
        ZpManValue mv = getObj(rowIndex);
        ZpShop shop = DicManager.getShopMap().get(mv.getShopId());
        ZpMetro metro = DicManager.getMetroMap().get(shop.getMetroId());

        switch (columnIndex)
        {
            case 0:
                return mv.getMan();
            case 1:
                return mv.getSd();
            case 2:
                return shop;
            case 3:
                return metro;
            case 4:
                //return shop.getEmail() == null ? null : new DefEmail(shop.getEmail());
                return shop.getComment();
            case 5:
                return ZpMan.ONE.equals(mv.getActive());
            default:
                return "";
        }
    }

    public Class getColumnClass(int columnIndex)
    {
        if (columnIndex == 0) return ZpMan.class;
        if (columnIndex == 1) return Date.class;
        if (columnIndex == 2) return ZpShop.class;
        if (columnIndex == 3) return ZpMetro.class;
        //if (columnIndex == 4) return DefEmail.class;
        if (columnIndex == 5) return Boolean.class;

        return String.class;
    }
}
