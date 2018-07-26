package ru.ittrans.zp.client.def;

import ru.ittrans.zp.client.CO;
import ru.ittrans.zp.io.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 13.10.2010
 * Time: 16:43:22
 * To change this template use File | Settings | File Templates.
 */
public class DefComboBox<K> extends JComboBox
{
    private static void cbAddItem(final DefComboBox cb,
                                  final Object[] list,
                                  final Long id,
                                  boolean isAddAll,
                                  boolean isAddName)
    {
        cb.setModel(new DefaultComboBoxModel(list));
        if (isAddAll)
        {
            if (list != null && list.length > 0)
            {
                Object allObj = null;
                if (list[0] instanceof ZpShop)
                {
                    ZpShop obj = new ZpShop();
                    obj.setId(isAddName ? ZpMan.ALL : null);
                    obj.setCode(isAddName ? DefCO.MSG_ALL : "");

                    allObj = obj;
                } else if (list[0] instanceof ZpPaid)
                {
                    ZpPaid obj = new ZpPaid();
                    obj.setId(isAddName ? ZpMan.ALL : null);
                    obj.setName(isAddName ? DefCO.MSG_ALL : "");

                    allObj = obj;
                } else if (list[0] instanceof ZpBatch)
                {
                    ZpBatch obj = new ZpBatch();
                    obj.setId(isAddName ? ZpMan.ALL : null);
                    obj.setName(DefCO.MSG_ALL);

                    allObj = obj;
                } else if (list[0] instanceof ZpClient)
                {
                    ZpClient obj = new ZpClient();
                    obj.setId(isAddName ? ZpMan.ALL : null);
                    obj.setName(isAddName ? DefCO.MSG_ALL : "");

                    allObj = obj;
                } else if (list[0] instanceof ZpRegion)
                {
                    ZpRegion obj = new ZpRegion();
                    obj.setId(isAddName ? ZpMan.ALL : null);
                    obj.setName(isAddName ? DefCO.MSG_ALL : "");

                    allObj = obj;
                } else if (list[0] instanceof ZpMetro)
                {
                    ZpMetro obj = new ZpMetro();
                    obj.setId(isAddName ? ZpMan.ALL : null);
                    obj.setName(isAddName ? DefCO.MSG_ALL : "");

                    allObj = obj;
                } else if (list[0] instanceof String)
                {
                    String obj = DefCO.MSG_ALL;

                    allObj = obj;
                }
                if (allObj != null)
                    cb.insertItemAt(allObj, 0);
            } else
            {
                ZpAll zpAll = new ZpAll();
                cb.insertItemAt(zpAll, 0);
            }
        }

        if (!cbSetSelectedItem(cb, id))
            if (cb.getItemCount() > 0)
                cb.setSelectedIndex(0);
    }

    public static void cbAddItem(final DefComboBox cb,
                                 final List list,
                                 final Long id)
    {
        cbAddItem(cb, list.toArray(), id, false, true);
    }

    public static void cbAddItem(final DefComboBox cb,
                                 final List list,
                                 final Long id,
                                 boolean isAddAll)
    {
        cbAddItem(cb, list, id, isAddAll, true);
    }

    public static void cbAddItem(final DefComboBox cb,
                                 final List list,
                                 final Long id,
                                 boolean isAddAll,
                                 boolean isAddName)
    {
        cbAddItem(cb, list.toArray(), id, isAddAll, isAddName);
    }

    public static void cbAddItem(final DefComboBox cb,
                                 final List list)
    {
        cbAddItem(cb, list, false);
    }

    public static void cbAddItem(final DefComboBox cb,
                                 final List list,
                                 boolean isAddAll)
    {
        cbAddItem(cb, list, null, isAddAll);
    }

    public static void cbAddItem(final DefComboBox cb,
                                 final LinkedHashMap map,
                                 boolean isAddAll)
    {
        cbAddItem(cb, map, isAddAll, true);
    }

    public static void cbAddItem(final DefComboBox cb,
                                 final LinkedHashMap map,
                                 boolean isAddAll,
                                 boolean isAddName)
    {
        cbAddItem(cb, map.values().toArray(), null, isAddAll, isAddName);
    }

    public static void cbRemoveItem(final DefComboBox cb)
    {
        cb.setModel(new DefaultComboBoxModel());
    }

    public static boolean cbSetSelectedItem(final DefComboBox cb,
                                            final Long id)
    {
        for (int i = 0; i < cb.getItemCount(); i++)
        {
            Object o = cb.getItemAt(i);
            if (o instanceof ZpAbstract)
                if (CO.myEquals(((ZpAbstract) o).getId(), id))
                {
                    cb.setSelectedItem(o);
                    return true;
                }
        }
        return false;
    }

    public K getSelectedItem()
    {
        return (K) super.getSelectedItem();
    }

    public Long getSelectedId()
    {
        Object obj = getSelectedItem();
        if (obj != null && obj instanceof ZpAbstract)
            return ((ZpAbstract) obj).getId();

        return null;
    }

    public static void cbAddItem(final DefComboBox cb,
                                 final String[] strList)
    {
        List list = new ArrayList();
        for (String str : strList)
            list.add(str);

        cbAddItem(cb, list);
    }

    public void setMyEditable(boolean editable)
    {
        setEnabled(editable);
    }
}

class ZpAll extends ZpAbstract
{

    public Long getId()
    {
        return ZpMan.ALL;
    }

    public String getName()
    {
        return DefCO.MSG_ALL;
    }
}