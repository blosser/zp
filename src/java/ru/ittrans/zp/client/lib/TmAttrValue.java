package ru.ittrans.zp.client.lib;

import ru.ittrans.zp.client.DicManager;
import ru.ittrans.zp.client.def.DefTableModel;
import ru.ittrans.zp.io.ZpAttr;
import ru.ittrans.zp.io.ZpAttrValue;

import java.sql.Date;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 10.05.11
 * Time: 18:52
 * To change this template use File | Settings | File Templates.
 */
public class TmAttrValue extends DefTableModel<ZpAttrValue>
{
    public String[] getColumnNames()
    {
        return AttrValueEditDialog.COL_NAMES_ATTR_VALIE;
    }

    public Object getValueAt(int rowIndex, int columnIndex)
    {
        ZpAttrValue av = getObj(rowIndex);

        switch (columnIndex)
        {
            case 0:
                return DicManager.getAttrMap().get(av.getAttrId());
            case 1:
                return av.getSd();
            case 2:
                return av.getFd();
            case 3:
                return av.getValue();
            default:
                return "";
        }
    }

    public Class getColumnClass(int columnIndex)
    {
        if (columnIndex == 0) return ZpAttr.class;
        if (columnIndex == 1 || columnIndex == 2) return Date.class;
        if (columnIndex == 3) return Long.class;
        return String.class;
    }
}


