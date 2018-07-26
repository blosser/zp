package ru.ittrans.zp.client.def;

import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 22.10.2010
 * Time: 10:57:22
 * To change this template use File | Settings | File Templates.
 */
public class DefButtonDot extends DefButton
{
    public DefButtonDot()
    {
        super(null);
        Dimension d = new Dimension(DefCO.BTN_SMALL_SIZE, DefCO.BTN_SMALL_SIZE);
        DefCO.setComponentSize(this, d);
        setIcon(DefCO.loadIcon(DefCO.GIF_DOT3_16));
    }
}
