package ru.ittrans.zp.client.def;

import javax.swing.*;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 13.10.2010
 * Time: 16:30:32
 * To change this template use File | Settings | File Templates.
 */
public class DefButton extends JButton
{
    public DefButton(String text)
    {
        super(text);
        setMultiClickThreshhold(1000);
        setToolTipText(text);
    }

    public void setMyEditable(boolean editable)
    {
        setEnabled(editable);
    }
}
