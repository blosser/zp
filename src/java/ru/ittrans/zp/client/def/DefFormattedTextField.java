package ru.ittrans.zp.client.def;

import javax.swing.*;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 16.01.12
 * Time: 17:37
 * To change this template use File | Settings | File Templates.
 */
public class DefFormattedTextField extends JFormattedTextField
{
    public DefFormattedTextField(DefMaskFormatter abstractFormatter)
    {
        super(abstractFormatter);
    }

    public String getMyText()
    {
        return //getText() == null ? "" :
                super.getText().replaceAll("-", "").replaceAll("_", "");
    }

    public void setMyText(String str)
    {
        setText(str);
    }

    public void setMyEditable(boolean editable)
    {
        setEditable(editable);
    }
}
