package ru.ittrans.zp.client.lib;

import ru.ittrans.zp.client.def.DefTableCellRenderer;
import ru.ittrans.zp.io.ZpMan;

import javax.swing.*;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 17.06.11
 * Time: 16:46
 * To change this template use File | Settings | File Templates.
 */
public class ManTableCellRenderer extends DefTableCellRenderer
{
    public Component getTableCellRendererComponent(JTable table,
                                                   Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column)
    {
        JLabel l = (JLabel) super.getTableCellRendererComponent(table,
                value, isSelected, hasFocus, row, column);

        if (ManDogovor.class.equals(table.getColumnClass(column)))
        {
            ZpMan man = ((TmMan) table.getModel()).getObj(row);
            if (ZpMan.DOGOVOR_ACTIVE.equals(man.getDogovor()))
                l.setBackground(Color.green);
            else if (ZpMan.DOGOVOR_END.equals(man.getDogovor()))
                l.setBackground(Color.red);
            else if (ZpMan.DOGOVOR_END_SOON.equals(man.getDogovor()))
                l.setBackground(Color.yellow);
            else l.setBackground(Color.white);
        }

        return l;
    }
}
