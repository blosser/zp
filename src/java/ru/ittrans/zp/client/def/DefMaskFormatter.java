package ru.ittrans.zp.client.def;

import javax.swing.text.MaskFormatter;
import java.text.ParseException;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 16.01.12
 * Time: 16:09
 * To change this template use File | Settings | File Templates.
 */
public class DefMaskFormatter extends MaskFormatter
{
    public DefMaskFormatter(String mask) throws ParseException
    {
        super(mask);
        setPlaceholderCharacter('_');
    }

//    public String valueToString(Object iv) throws ParseException
//    {
//        if ((iv == null) || (((Integer) iv).intValue() == -1))
//        {
//            return "";
//        } else
//        {
//            return super.valueToString(iv);
//        }
//    }

    public Object stringToValue(String text) throws ParseException
    {
        if (text == null ||
                "".equals(text) ||
                "".equals(text.replaceAll("-", "").replaceAll("_", "")))
            return null;

        return super.stringToValue(text);
    }

}
