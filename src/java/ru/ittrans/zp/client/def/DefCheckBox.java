package ru.ittrans.zp.client.def;

import ru.ittrans.zp.io.ZpMan;

import javax.swing.*;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 27.12.10
 * Time: 11:05
 * To change this template use File | Settings | File Templates.
 */
public class DefCheckBox extends JCheckBox
{
    public DefCheckBox(String s, boolean b)
    {
        super(s, b);
    }

    public Long getValue()
    {
        return isSelected() ? ZpMan.ONE : null;
    }

    public void setValue(Long value)
    {
        setSelected(ZpMan.ONE.equals(value));
    }

    public void setMyEditable(boolean editable)
    {
        setEnabled(editable);
    }
}
