package ru.ittrans.zp.client.lib;

import ru.ittrans.zp.client.DicManager;
import ru.ittrans.zp.client.def.DefTableModel;
import ru.ittrans.zp.io.ZpMan;
import ru.ittrans.zp.io.ZpShop;

import java.sql.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 28.04.11
 * Time: 18:28
 * To change this template use File | Settings | File Templates.
 */
public class TmMan extends DefTableModel<ZpMan>
{
    private boolean roleNotFioOnly;

    public TmMan(boolean roleNotFioOnly)
    {
        super();
        this.roleNotFioOnly = roleNotFioOnly;
    }

    public int[] getColumntWidth()
    {
        return new int[]{5, 80, 200, 80, 80, 80, 80, 80, 80, 80, 80,
                80, 80, 80, 80, 80, 80, 80, 80, 80,
                80, 80, 80, 80};
    }

    public String[] getColumnNames()
    {
        if (getList() == null || getList().size() == 0) return null;
        if (!roleNotFioOnly)
            return new String[]{ManEditDialog.COL_NAMES_MAN[0],
                    ManEditDialog.COL_NAMES_MAN[1], ManEditDialog.COL_NAMES_MAN[2]};
        return ManEditDialog.COL_NAMES_MAN;
    }

    public void setData(List<ZpMan> mList)
    {
        super.setData(mList);
        fireTableStructureChanged();
    }

    private static final String SPLIT = "-";

    public Object getValueAt(int rowIndex, int columnIndex)
    {
        ZpMan m = getObj(rowIndex);

        switch (columnIndex)
        {
            case 0:
                return ZpMan.ONE.equals(m.getSecondJob()) ? "C" : m.getStatus();
            case 1:
                return DicManager.getShopMap().get(m.getShopId());
            case 2:
                return m;
            case 3:
                if (m.getPhone() != null && m.getPhone().length() == 11)
                    return m.getPhone().substring(0, 1) + SPLIT +
                            m.getPhone().substring(1, 4) + SPLIT +
                            m.getPhone().substring(4, 7) + SPLIT +
                            m.getPhone().substring(7, 9) + SPLIT +
                            m.getPhone().substring(9, 11);
                else
                    return m.getPhone();
            case 4:
                return m.getBirthday();
            case 5:
                return m.getTabNumber();
            case 6:
                if (m.getPassportNumber() != null && m.getPassportNumber().length() == 10)
                    return m.getPassportNumber().substring(0, 4) + SPLIT +
                            m.getPassportNumber().substring(4, 10);
                else
                    return m.getPassportNumber();
            case 7:
                return m.getPassportAddress();
            case 8:
                return m.getRegAddress();
            case 9:
                return m.getPassportWhere();
            case 10:
                return m.getInn();
            case 11:
                if (m.getPensionNumber() != null && m.getPensionNumber().length() == 11)
                    return m.getPensionNumber().substring(0, 3) + SPLIT +
                            m.getPensionNumber().substring(3, 6) + SPLIT +
                            m.getPensionNumber().substring(6, 9) + SPLIT +
                            m.getPensionNumber().substring(9, 11);
                else
                    return m.getPensionNumber();
            case 12:
                return ZpMan.ONE.equals(m.getActive());
            case 13:
                return ZpMan.ONE.equals(m.getBlackList());
            case 14:
                return m.getManScore();
            case 15:
                return m.getComment();
            case 16:
                return ZpMan.ONE.equals(m.getCard());
            case 17:
                return ZpMan.ONE.equals(m.getpZdm());
            case 18:
                return ZpMan.ONE.equals(m.getpTvp());
            case 19:
                return ZpMan.ONE.equals(m.getpSk());
            case 20:
                return ZpMan.ONE.equals(m.getpSb());
            case 21:
                return m.getpSbDate();
            case 22:
                return ZpMan.ONE.equals(m.getpDog());
            case 23:
                return m.getpDogDate();
            case 24:
                return ZpMan.ONE.equals(m.getSpecMobile());
            case 25:
                return ZpMan.ONE.equals(m.getMobile());
            case 26:
                return ZpMan.ONE.equals(m.getZlo());
            case 27:
                return m.getId();
            case 28:
                return m.getDogovor();
            default:
                return "";
        }
    }

    public Class getColumnClass(int columnIndex)
    {
        if (columnIndex == 0) return ManDogovor.class;
        if (columnIndex == 2) return ZpMan.class;
        if (columnIndex == 4 || columnIndex == 21 ||
                columnIndex == 23) return Date.class;
        if (columnIndex == 12 || columnIndex == 13 ||
                columnIndex == 16 || columnIndex == 17 ||
                columnIndex == 18 || columnIndex == 19 ||
                columnIndex == 20 || columnIndex == 22 ||
                columnIndex == 24 || columnIndex == 25 ||
                columnIndex == 26)
            return Boolean.class;
        if (columnIndex == 1) return ZpShop.class;
        return String.class;
    }
}