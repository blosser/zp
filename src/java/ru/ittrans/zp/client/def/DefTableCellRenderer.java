package ru.ittrans.zp.client.def;

import ru.ittrans.zp.client.man.TmManValue;
import ru.ittrans.zp.io.*;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 08.11.2010
 * Time: 17:40:51
 * To change this template use File | Settings | File Templates.
 */
public class DefTableCellRenderer extends DefaultTableCellRenderer
{
    private Color selColor = null;

    public DefTableCellRenderer()
    {
        super();
        selColor = UIManager.getColor("Table.selectionBackground");
    }

    public Component getTableCellRendererComponent(JTable table,
                                                   Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column)
    {
        JLabel l = (JLabel) super.getTableCellRendererComponent(table,
                value, isSelected, hasFocus, row, column);
        if (value instanceof ZpShop)
        {
            l.setHorizontalAlignment(SwingConstants.CENTER);
            if (table.getModel() instanceof TmManValue)
            {
                TmManValue tvModel = (TmManValue) table.getModel();
                if (tvModel.isShopNameVisible())
                    l.setText(value.toString());
                else
                    l.setText("X");
            } else
            {
                l.setText(value.toString());
            }
        } else if (value instanceof ZpManValue)
        {
            l.setHorizontalAlignment(SwingConstants.RIGHT);
            Long val = ((ZpManValue) value).getValue();
            l.setText(DefCO.longToStr(val));
        } else if (value instanceof ZpManPaid)
        {
            l.setHorizontalAlignment(SwingConstants.LEFT);
            l.setText(((ZpManPaid) value).getComment());
        } else if (value instanceof ZpPaid)
        {
            l.setHorizontalAlignment(SwingConstants.CENTER);
            l.setText(value.toString());
        } else if (value instanceof ZpMan)
        {
            l.setHorizontalAlignment(SwingConstants.LEFT);
            l.setText(value.toString());
        } else if (value instanceof String)
        {
            l.setHorizontalAlignment(SwingConstants.LEFT);
            l.setText(value.toString());
        } else if (value instanceof Date)
        {
            l.setHorizontalAlignment(SwingConstants.CENTER);
            l.setText(DefCO.dateToStr((Date) value));
        } else if (value instanceof DefTimestamp)
        {
            l.setText(DefCO.dateTimeSecToStr((Timestamp) value));
            l.setHorizontalAlignment(SwingConstants.CENTER);
        } else if (value instanceof Timestamp)
        {
            l.setText(DefCO.dateTimeToStr((Timestamp) value));
            l.setHorizontalAlignment(SwingConstants.CENTER);
        }

        if (isSelected && hasFocus)
        {
            l.setBorder(BorderFactory.createLineBorder(Color.black));
        }

        boolean isCellEditable = table.isCellEditable(row, column);
        if (isCellEditable)
        {
            l.setBackground(DefTable.GREEN_COLOR);
        } else
        {
            l.setBackground(Color.white);
        }

        if (isSelected && !isCellEditable)
        {
//            if (table.getModel() instanceof TmManValue)
//            {
//                if (((TmManValue) table.getModel()).getRowEditable() == -1)
//                    l.setBackground(selColor);
//            } else
            l.setBackground(selColor);
        }

        return l;
    }
}
