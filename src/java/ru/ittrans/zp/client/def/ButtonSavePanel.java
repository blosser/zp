package ru.ittrans.zp.client.def;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 13.10.2010
 * Time: 16:46:12
 * To change this template use File | Settings | File Templates.
 */
public class ButtonSavePanel extends JPanel implements ActionListener
{
    public DefButton btnSave = new DefButton("Сохранить");
    public DefButton btnCancel = new DefButton("Отмена");

    private ButtonSaveInterface bsi;
    private BSP_TYPE bsp;

    public static enum BSP_TYPE
    {
        BSP_SAVE, BSP_ENTER, BSP_EXEC, BSP_CLOSE, BSP_SELECT
    }

    public ButtonSavePanel(ButtonSaveInterface bsi)
    {
        this(bsi, BSP_TYPE.BSP_SAVE);
    }

    public ButtonSavePanel(ButtonSaveInterface bsi, BSP_TYPE bsp)
    {
        super();
        this.bsi = bsi;
        this.bsp = bsp;
        jbInit();
    }

    private void jbInit()
    {
        String btnSaveText = "";
        if (bsp == BSP_TYPE.BSP_SAVE) btnSaveText = DefCO.MSG_SAVE;
        else if (bsp == BSP_TYPE.BSP_ENTER) btnSaveText = "Войти";
        else if (bsp == BSP_TYPE.BSP_CLOSE) btnSaveText = "Закрыть";
        else if (bsp == BSP_TYPE.BSP_EXEC) btnSaveText = DefCO.MSG_EXEC;
        else if (bsp == BSP_TYPE.BSP_SELECT) btnSaveText = "Выбрать";

        btnSave.setText(btnSaveText);
        btnSave.setToolTipText(btnSaveText);

        setLayout(new FlowLayout(FlowLayout.RIGHT));

        btnSave.setIcon(DefCO.loadIcon(DefCO.PNG_ACTIVE));
        btnCancel.setIcon(DefCO.loadIcon(DefCO.PNG_CANCEL));

        add(btnSave);
        if (bsp != BSP_TYPE.BSP_CLOSE)
            add(btnCancel);

        btnSave.addActionListener(this);
        btnCancel.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e)
    {
        Object o = e.getSource();
        if (btnSave.equals(o))
        {
            bsi.btnSave_actionPerformed(e);
        } else if (btnCancel.equals(o))
        {
            bsi.btnCancel_actionPerformed(e);
        }
    }

    public void setDefaultButton()
    {
        getRootPane().setDefaultButton(btnSave);
    }

    public void setMyEnabled(boolean isEditable)
    {
        btnSave.setEnabled(isEditable);
        btnCancel.setEnabled(isEditable);
    }
}
