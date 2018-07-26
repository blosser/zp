package ru.ittrans.zp.client.def;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 24.01.12
 * Time: 10:34
 * To change this template use File | Settings | File Templates.
 */
public class ManPrintJButton extends DefButton
{
    public ManPrintJButton()
    {
        super("");
        setIcon(DefCO.loadIcon(DefCO.PNG_EXEC));
        setToolTipText("Печать документов");
    }
}
