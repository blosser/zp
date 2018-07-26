package ru.ittrans.zp.client.report;

import ru.ittrans.zp.client.def.DefTableModel;
import ru.ittrans.zp.io.ZpMan;
import ru.ittrans.zp.io.ZpManValue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 01.08.11
 * Time: 11:04
 * To change this template use File | Settings | File Templates.
 */
public class TmVt11 extends DefTableModel<List>
{
    private List<String> colNames = new ArrayList<String>();

    public String[] getColumnNames()
    {
        String[] res = new String[colNames.size()];
        for (int i = 0; i < colNames.size(); i++)
            res[i] = colNames.get(i);
        return res;
    }

    public Object getValueAt(int rowIndex, int columnIndex)
    {
        List dList = getObj(rowIndex);
        Object obj = dList.get(columnIndex);
        if (obj == null) return "";
        if (obj instanceof ZpMan)
            return ((ZpMan) obj).getName1();
        if (obj instanceof ZpManValue)
            return ((ZpManValue) obj).getValue() == null ? null : ZpMan.ONE;
        return null;
    }

    public Class getColumnClass(int columnIndex)
    {
        if (columnIndex > 0) return Long.class;
        return String.class;
    }

    public void setData(List resList)
    {
        colNames.clear();
        if (resList != null && resList.size() > 0)
        {
            List dList = (List) resList.get(0);
            colNames.add("‘»Œ");
            for (int i = 1; i < dList.size(); i++)
                colNames.add(String.valueOf(i));
        }
        super.setData(resList);
        fireTableStructureChanged();
    }
}
