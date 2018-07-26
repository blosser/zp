package ru.ittrans.zp.client.def;

import javax.swing.*;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 24.11.2010
 * Time: 19:05:16
 * To change this template use File | Settings | File Templates.
 */
public class DefComboBoxRenderer extends JLabel
        implements ListCellRenderer
{

    public DefComboBoxRenderer()
    {
        super();
        setOpaque(true);
    }

    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)
    {
        if (isSelected)
        {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else
        {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }

//        if (value instanceof ZpStatus)
//        {
//            ZpStatus status = (ZpStatus) value;
//            setIcon(DefCO.getIcon(status));
//            setText(status.toString());
//        } else
//        {
//            setIcon(null);
//        }

        return this;
    }
}
