package ru.ittrans.zp.client.def;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 14.10.2010
 * Time: 13:33:14
 * To change this template use File | Settings | File Templates.
 */
public abstract class DefTableModel<K> extends AbstractTableModel implements DefColumnNamesInterface
{
    private List<K> list = null;

    public K getObj(int row)
    {
        return list == null || row < 0 || row >= list.size() ? null : list.get(row);
    }

    public List<K> getList()
    {
        return list;
    }

    public int getColumnCount()
    {
        return getColumnNames() == null ? 0 : getColumnNames().length;
    }

    public int getRowCount()
    {
        return list == null ? 0 : list.size();
    }

    public void setData(List<K> list)
    {
        this.list = list;
        this.fireTableDataChanged();
    }

    public String getColumnName(int columnIndex)
    {
        String[] columnNames = getColumnNames();
        return columnIndex < 0 || columnNames.length <= columnIndex ? "" : columnNames[columnIndex];
    }

    public Object getValueAt(int rowIndex, int columnIndex)
    {
        K k = getObj(rowIndex);
        return k == null ? "" : k.toString();
    }

    public boolean addObj(K k)
    {
        if (list == null)
        {
            list = new ArrayList<K>();
        }
        boolean res = list.add(k);
        if (res)
        {
            this.fireTableDataChanged();
        }
        return res;
    }

    public K setObj(int index, K k)
    {
        K res = null;
        if (list != null && index != -1)
        {
            res = list.set(index, k);
            fireTableRowsUpdated(index, index);
        }
        return res;
    }

    public boolean removeObj(K k)
    {
        if (list != null)
        {
            boolean res = list.remove(k);
            if (res)
            {
                this.fireTableDataChanged();
            }
            return res;
        }
        return false;
    }

    public int[] getColumntWidth()
    {
        return null;
    }
}