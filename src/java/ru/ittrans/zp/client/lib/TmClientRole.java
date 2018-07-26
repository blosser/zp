package ru.ittrans.zp.client.lib;

import ru.ittrans.zp.client.Connect;
import ru.ittrans.zp.client.DicManager;
import ru.ittrans.zp.client.def.DefTableModel;
import ru.ittrans.zp.io.ZpClientRole;
import ru.ittrans.zp.io.ZpRole;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 27.01.12
 * Time: 9:57
 * To change this template use File | Settings | File Templates.
 */
public class TmClientRole extends DefTableModel<ZpClientRole>
{
    public String[] getColumnNames()
    {
        return ClientEditDialog.MES_CLIENT_ROLE;
    }

    public Object getValueAt(int rowIndex, int columnIndex)
    {
        ZpClientRole mr = getObj(rowIndex);

        switch (columnIndex)
        {
            case 0:
                boolean isBold = mr.getClientId() != null;
                return (isBold ? "<html><b>" : "") +
                        DicManager.getRoleMap().get(mr.getRoleId()) +
                        (isBold ? "</b></html>" : "");
            case 1:
                return mr.getClientId() != null;
            default:
                return "";
        }
    }

    public Class getColumnClass(int columnIndex)
    {
        if (columnIndex == 0) return ZpRole.class;
        if (columnIndex == 1) return Boolean.class;
        return String.class;
    }

    public boolean isCellEditable(int rowIndex, int columnIndex)
    {
        return Boolean.class.equals(getColumnClass(columnIndex));
    }

    public void setValueAt(Object aValue, int rowIndex, int columnIndex)
    {
        if (aValue instanceof Boolean)
        {
            boolean val = (Boolean) aValue;
            ZpClientRole mr = getObj(rowIndex);
            if (val)
            {
                mr.setClientId(Connect.getClient().getId());
            } else
            {
                mr.setClientId(null);
            }
            fireTableRowsUpdated(rowIndex, rowIndex);
        }
    }
}