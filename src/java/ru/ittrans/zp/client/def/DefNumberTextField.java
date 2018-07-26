package ru.ittrans.zp.client.def;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 13.10.2010
 * Time: 18:50:52
 * To change this template use File | Settings | File Templates.
 */
public class DefNumberTextField extends DefTextField
{
    private int limit;

    public DefNumberTextField()
    {
        this(-1);
    }

    public DefNumberTextField(final int limit)
    {
        super();
        this.limit = limit;
        //setDocument(new JTextFieldLimit(a));
    }

    public int getLimit()
    {
        return limit;
    }

    public void setValue(Long val)
    {
        setText(DefCO.longToStr(val));
    }

    public Long getValue()
    {
        return DefCO.strToLong(getText());
    }
}

class JTextFieldLimit extends PlainDocument
{
    private int limit;

    public JTextFieldLimit(int limit)
    {
        super();
        this.limit = limit;
    }

    public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException
    {
        if (str == null)
            return;

        for (int i = 0; i < str.length(); i++)
            if (!Character.isDigit(str.charAt(i))) return;

        if ((getLength() + str.length()) <= limit)
        {
            super.insertString(offset, str, attr);
        }
    }
}