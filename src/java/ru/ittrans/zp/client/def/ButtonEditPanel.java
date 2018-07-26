package ru.ittrans.zp.client.def;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 14.10.2010
 * Time: 12:09:38
 * To change this template use File | Settings | File Templates.
 */
public class ButtonEditPanel extends JPanel implements ActionListener
{
    private DefButton btnAdd = new DefButton(DefCO.MSG_ADD);
    private DefButton btnUpdate = new DefButton(DefCO.MSG_UPD);
    private DefButton btnDelete = new DefButton(DefCO.MSG_DEL);

    private ButtonEditInterface bei;

    public ButtonEditPanel(ButtonEditInterface bei)
    {
        this(bei, true, true, true);
    }

    public ButtonEditPanel(ButtonEditInterface bei,
                           boolean bAdd,
                           boolean bUpdate,
                           boolean bDelete)
    {
        super();
        this.bei = bei;
        jbInit(bAdd, bUpdate, bDelete);
    }

    private void jbInit(boolean bAdd, boolean bUpdate, boolean bDelete)
    {
        setLayout(new FlowLayout(FlowLayout.LEFT));

        btnAdd.setIcon(DefCO.loadIcon(DefCO.PNG_BADD));
        btnUpdate.setIcon(DefCO.loadIcon(DefCO.PNG_BUPDATE));
        btnDelete.setIcon(DefCO.loadIcon(DefCO.PNG_BDELETE));

        if (bAdd)
            add(btnAdd);
        if (bUpdate)
            add(btnUpdate);
        if (bDelete)
            add(btnDelete);

        btnAdd.addActionListener(this);
        btnUpdate.addActionListener(this);
        btnDelete.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e)
    {
        Object o = e.getSource();
        if (btnAdd.equals(o))
        {
            bei.btnInsert_actionPerformed(e);
        } else if (btnUpdate.equals(o))
        {
            bei.btnUpdate_actionPerformed(e);
        } else if (btnDelete.equals(o))
        {
            bei.btnDelete_actionPerformed(e);
        }
    }

    public void setMyEnabled(boolean isEditable)
    {
        btnAdd.setEnabled(isEditable);
        btnUpdate.setEnabled(isEditable);
        btnDelete.setEnabled(isEditable);
    }

    public void addButton(JComponent btn, int index)
    {
        if (index == -1)
            add(btn);
        else
            add(btn, index);
    }

    public void addButton(JComponent btn)
    {
        add(btn, -1);
    }
}
