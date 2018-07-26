package ru.ittrans.zp.client.lib;

import ru.ittrans.zp.client.def.DefTableModel;
import ru.ittrans.zp.io.ZpBatch;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 10.05.11
 * Time: 18:16
 * To change this template use File | Settings | File Templates.
 */
public class TmBatch extends DefTableModel<ZpBatch>
{
    public String[] getColumnNames()
    {
        return BatchDialog.COL_NAMES_BATCH;
    }

    public Object getValueAt(int rowIndex, int columnIndex)
    {
        ZpBatch b = getObj(rowIndex);

        switch (columnIndex)
        {
            case 0:
                return b.getName();
            default:
                return "";
        }
    }
}