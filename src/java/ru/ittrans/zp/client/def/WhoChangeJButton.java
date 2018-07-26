package ru.ittrans.zp.client.def;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 13.12.11
 * Time: 14:39
 * To change this template use File | Settings | File Templates.
 */
public class WhoChangeJButton extends DefButton
{
    public WhoChangeJButton()
    {
        super("");
        setIcon(DefCO.loadIcon(DefCO.PNG_SESSION));
        setToolTipText("Последний изменивший данные");
    }
}
