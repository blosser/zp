package ru.ittrans.zp.client.def;

import javax.swing.*;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 09.11.2010
 * Time: 12:27:07
 * To change this template use File | Settings | File Templates.
 */
public class DefTextArea extends JTextArea
{
    private Color cEditable =
            (Color) UIManager.get("TextField.background");
    private Color cNotEditable =
            (Color) UIManager.get("ComboBox.disabledBackground");

    public DefTextArea()
    {
        super();
        setLineWrap(true);
    }

    public void setText(String t)
    {
        super.setText(t);
        setCaretPosition(0);
    }

    public void setMyEditable(boolean editable)
    {
        setEditable(editable);
        setBackground(editable ? cEditable : cNotEditable);
    }
}
