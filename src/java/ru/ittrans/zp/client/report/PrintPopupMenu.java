package ru.ittrans.zp.client.report;

import ru.ittrans.zp.io.ZpMan;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 24.01.12
 * Time: 12:40
 * To change this template use File | Settings | File Templates.
 */
public class PrintPopupMenu extends JPopupMenu
{
    private ZpMan man;

    private static final String[] PRINT_DOC =
            new String[]{"Все", "Партнер", "ТД Легион", "ТД Партнер",
                    "Энергия", "Легион",
                    "Штраф", "Отказ"};

    public PrintPopupMenu()
    {
        super();
        for (int i = 0; i < PRINT_DOC.length; i++)
        {
            JMenuItem miView = new JMenuItem(PRINT_DOC[i]);
            //miView.setIcon(DefCO.loadIcon(DefCO.PNG_ALL_PURCHASE));
            final int reportType = i - 1;
            miView.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent actionEvent)
                {

                    ManPrint.printMan(reportType, man);
                }
            });
            add(miView);
            if (i == 0) addSeparator();
        }
    }

    public void setMan(ZpMan man)
    {
        this.man = man;
    }
}
