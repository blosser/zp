package ru.ittrans.zp.client.def;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 04.05.11
 * Time: 18:16
 * To change this template use File | Settings | File Templates.
 */
public class DefTableKeyAdapter extends KeyAdapter
{
    public void keyPressed(KeyEvent e)
    {
        DefTable tbl = null;
        Object o = e.getSource();
        boolean toggle = true;
        if (o instanceof DefTable)
        {
            tbl = (DefTable) e.getSource();
        } else if (o instanceof JTextField)
        {
            Container c = ((JTextField) e.getSource()).getParent();
            if (c instanceof DefTable)
            {
                tbl = (DefTable) c;
                toggle = false;
            }
        }

        if (tbl != null && e.getKeyCode() == KeyEvent.VK_ENTER)
        {
            Point nextCell = tbl.getNextCell();
            if (nextCell != null)
            {
                if (toggle)
                    e.consume();
                tbl.changeSelection(nextCell.x, nextCell.y, false, false);
            }
        }
    }
}
