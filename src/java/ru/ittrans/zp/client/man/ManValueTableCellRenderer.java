package ru.ittrans.zp.client.man;

import ru.ittrans.zp.client.Connect;
import ru.ittrans.zp.client.def.DefTableCellRenderer;
import ru.ittrans.zp.io.ZpMan;
import ru.ittrans.zp.io.ZpManValue;
import ru.ittrans.zp.io.ZpShop;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 17.06.11
 * Time: 17:59
 * To change this template use File | Settings | File Templates.
 */
public class ManValueTableCellRenderer extends DefTableCellRenderer
{
    public Component getTableCellRendererComponent(JTable table,
                                                   Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column)
    {
        JLabel l = (JLabel) super.getTableCellRendererComponent(table,
                value, isSelected, hasFocus, row, column);

        if (ZpShop.class.equals(table.getColumnClass(column)))
        {
            ZpMan man = ((TmManValue) table.getModel()).getObj(row);
            List<ZpManValue> mvList = man.getMvList();
            ZpManValue mv = mvList.get(column);
            if (ZpMan.TWO.equals(mv.getActive()))
            {
                l.setBackground(Color.yellow);
            } else if (!ZpMan.ONE.equals(mv.getActive()))
            {
                if (Connect.isEvild())
                {
                    l.setBackground(Color.gray);
                } else
                {
                    if (mv.getShopId() != null)
                        setText("");
                }
            }
        }

        return l;
    }
}
