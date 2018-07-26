package ru.ittrans.zp.client.def;

import com.standbysoft.component.date.swing.JDatePicker;

import java.awt.*;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 13.10.2010
 * Time: 16:41:21
 * To change this template use File | Settings | File Templates.
 */
public class DefDatePicker extends JDatePicker
{
    public static Dimension DP_SIZE = new Dimension(144, 25);

    public DefDatePicker()
    {
        super();
        setEditable(true);
    }

    public void setDate(Date d)
    {
        getDateModel().setDate(d);
        commitEdit();
        setMaximumSize(DP_SIZE);
        setMinimumSize(DP_SIZE);
        setPreferredSize(DP_SIZE);
    }

    public Date getDate()
    {
        if (getDateModel().getDate() == null) return null;
        Date d = new Date(getDateModel().getDate().getTime());
        return d;
    }

    public Timestamp getTimestamp()
    {
        if (getDateModel().getDate() == null) return null;
        return new Timestamp(getDateModel().getDate().getTime());
    }

    public void setTimestamp(Timestamp time)
    {
        if (time == null)
            setDate(null);
        else
            setDate(new Date(time.getTime()));
    }
}