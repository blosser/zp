package ru.ittrans.zp.client.def;

import javax.swing.*;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 13.10.2010
 * Time: 16:42:30
 * To change this template use File | Settings | File Templates.
 */
public class DefTextField extends JTextField
{
    public void setMyEditable(boolean editable)
    {
        setEditable(editable);
    }

    public void setText(String t)
    {
        super.setText(t);
        setCaretPosition(0);
    }
}