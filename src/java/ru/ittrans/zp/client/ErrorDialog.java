package ru.ittrans.zp.client;

import ru.ittrans.zp.client.def.DefButton;
import ru.ittrans.zp.client.def.DefCO;
import ru.ittrans.zp.client.def.DefDialog;
import ru.ittrans.zp.client.def.DefTextArea;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 14.10.2010
 * Time: 17:28:46
 * To change this template use File | Settings | File Templates.
 */
public class ErrorDialog extends DefDialog<String>
{
    private static final String MES_ERROR = "Ошибка";
    private DefTextArea taMsg = new DefTextArea();
    private JScrollPane spMsg = new JScrollPane(taMsg);
    private DefButton btnDetail = new DefButton("Подробно");
    private DefButton btnClose = new DefButton("Закрыть");
    private DefTextArea taTxt = new DefTextArea();
    private JScrollPane spTxt = new JScrollPane(taTxt);

    public ErrorDialog(JFrame f)
    {
        super(f, MES_ERROR);
        jbInit();
    }

    public ErrorDialog(DefDialog f)
    {
        super(f, MES_ERROR);
        jbInit();
    }

    protected void jbInit()
    {
        super.jbInit();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel pnlMain = getPanel();
        pnlMain.setLayout(new GridBagLayout());

        pnlMain.add(spMsg, new GridBagConstraints(0, 0, 1, 2, 1.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));
        pnlMain.add(btnClose, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));
        pnlMain.add(btnDetail, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));

        pnlMain.add(spTxt, new GridBagConstraints(0, 2, 2, 1, 1.0, 1.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, DefCO.INSETS, 0, 0));

        btnClose.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                dispose();
            }
        });

        btnDetail.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                btnDetailPressed();
            }
        });

        btnDetailPressed();

        taMsg.setMyEditable(false);
        taTxt.setMyEditable(false);

        setLocationRelativeTo(getOwner());
    }

    private void btnDetailPressed()
    {
        spTxt.setVisible(!spTxt.isVisible());
        setSize(600, spTxt.isVisible() ? 600 : 100);
    }

    public void setObject(String mes, String txt)
    {
        taMsg.setText(mes);
        taTxt.setText(txt);
        setVisible(true);
    }
}
