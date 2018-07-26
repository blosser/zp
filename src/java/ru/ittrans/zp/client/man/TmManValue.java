package ru.ittrans.zp.client.man;

import ru.ittrans.zp.client.CO;
import ru.ittrans.zp.client.Connect;
import ru.ittrans.zp.client.DicManager;
import ru.ittrans.zp.client.def.DefCO;
import ru.ittrans.zp.client.def.DefTableModel;
import ru.ittrans.zp.io.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 28.04.11
 * Time: 19:41
 * To change this template use File | Settings | File Templates.
 */
public class TmManValue extends DefTableModel<ZpMan>
{
    private int rowEditable = -1;
    private boolean isShopNameVisible = true;

    private int w = 50;

    public int[] getColumntWidth()
    {
        return new int[]{w * 2, w + 30, w + 20, w + 20, w, w, w, w,
                w, w, w, w, w, w, w, w, w, w,
                w, w, w, w, w, w, w, w, w, w,
                w, w, w, w, w, w, w, w, w, w, w,
                w, w, w, w, w, w, w, w, w, w,
                w, w, w, w, w, w, w, w, w, w,
                w, w, w, w, w, w, w, w, w, w, w};
    }

    public void setW(int w)
    {
        this.w = w;
    }

    private List<String> colNames = new ArrayList<String>();

    public String[] getColumnNames()
    {
        String[] res = new String[colNames.size()];
        for (int i = 0; i < colNames.size(); i++)
            res[i] = colNames.get(i);
        return res;
    }

    public ZpManValue getManValue(int rowIndex, int columnIndex)
    {
        ZpMan man = getObj(rowIndex);
        if (man == null) return null;
        List<ZpManValue> mvList = man.getMvList();
        if (columnIndex >= mvList.size()) return null;
        ZpManValue mv = mvList.get(columnIndex);
        return mv;
    }

    public Object getValueAt(int rowIndex, int columnIndex)
    {
        ZpMan man = getObj(rowIndex);
        List<ZpManValue> mvList = man.getMvList();
        if (columnIndex >= mvList.size()) return null;
        ZpManValue mv = getManValue(rowIndex, columnIndex);
        if (mv == null) return null;
        if (ZpManValue.VALUE_TYPE1.equals(mv.getValueType()) ||
                ZpManValue.VALUE_TYPE13.equals(mv.getValueType()))
        {
            Long shopId = mv.getShopId();
            return shopId == null ? null : DicManager.getShopMap().get(mv.getShopId());
        } else if (ZpManValue.VALUE_TYPE2.equals(mv.getValueType()) ||
                ZpManValue.VALUE_TYPE3.equals(mv.getValueType()) ||
                ZpManValue.VALUE_TYPE4.equals(mv.getValueType()) ||
                ZpManValue.VALUE_TYPE10.equals(mv.getValueType()) ||
                //ZpManValue.VALUE_TYPE11.equals(mv.getValueType()) ||
                ZpManValue.VALUE_TYPE12.equals(mv.getValueType()))
        {
            return mv;
        } else if (ZpManValue.VALUE_TYPE5.equals(mv.getValueType()))
        {
            Long vt2 = ZpMan.ZERO;
            Long vt3 = ZpMan.ZERO;
            Long vt4 = ZpMan.ZERO;
            for (ZpManValue mvc : mvList)
                if (ZpManValue.VALUE_TYPE2.equals(mvc.getValueType()) &&
                        ZpMan.ONE.equals(mvc.getActive()) &&
                        mvc.getValue() != null)
                {
                    vt2 = mvc.getValue();
                } else if (ZpManValue.VALUE_TYPE3.equals(mvc.getValueType()) &&
                        ZpMan.ONE.equals(mvc.getActive()) &&
                        mvc.getValue() != null)
                {
                    vt3 = mvc.getValue();
                } else if (ZpManValue.VALUE_TYPE4.equals(mvc.getValueType()) &&
                        ZpMan.ONE.equals(mvc.getActive()) &&
                        mvc.getValue() != null)
                {
                    vt4 = mvc.getValue();
                }
//к выплате = выплачено
//                else if (ZpManValue.VALUE_TYPE10.equals(mvc.getValueType()))
//                {
//                    if (mvc.getValue() != null && !ZpMan.ZERO.equals(mvc.getValue()))
//                    {
//                        mv.setValue(mvc.getValue());
//                        return mv;
//                    }
//                }
            int vt9 = getDayCnt(mvList);
            int shopCnt = getUniqueShopCnt(mvList);
            Long dayCost = getDayCost(man, vt9, shopCnt);
            Long value = new Long(-vt2.longValue() +
                    vt3.longValue() +
                    vt4.longValue() +
                    dayCost.longValue() * vt9);
            mv.setValue(value);
            return mv;
        } else if (ZpManValue.VALUE_TYPE6.equals(mv.getValueType()))
        {
            ZpManPaid mp = man.getManPaid();
            return mp;
        } else if (ZpManValue.VALUE_TYPE7.equals(mv.getValueType()))
        {
            ZpManPaid mp = man.getManPaid();
            return mp == null ? null : DicManager.getPaidMap().get(mp.getPaidId());
        } else if (ZpManValue.VALUE_TYPE8.equals(mv.getValueType()))
        {
            int vt9 = getDayCnt(mvList);
            int shopCnt = getUniqueShopCnt(mvList);
            Long dayCost = getDayCost(man, vt9, shopCnt);
            mv.setValue(dayCost);
            return mv;
        } else if (ZpManValue.VALUE_TYPE9.equals(mv.getValueType()))
        {
            int cnt = getDayCnt(mvList);
            mv.setValue(cnt > 0 ? new Long(cnt) : null);
            return mv;
        } else return null;
    }

    private int getDayCnt(List<ZpManValue> mvList)
    {
        int cnt = 0;
        for (ZpManValue mvc : mvList)
            if (mvc.getShopId() != null &&
                    ZpManValue.VALUE_TYPE1.equals(mvc.getValueType()) &&
                    ZpMan.ONE.equals(mvc.getActive()))
            {
                cnt++;
            }
        return cnt;
    }

    private int getUniqueShopCnt(List<ZpManValue> mvList)
    {
        List<Long> shopList = new ArrayList<Long>();
        for (ZpManValue mvc : mvList)
            if (mvc.getShopId() != null &&
                    ZpManValue.VALUE_TYPE1.equals(mvc.getValueType()) &&
                    ZpMan.ONE.equals(mvc.getActive()))
            {
                if (!shopList.contains(mvc.getShopId()))
                    shopList.add(mvc.getShopId());
            }
        return shopList.size();
    }

    public static Date D_01_02_2012 = DefCO.strToDate("01.02.2012");
    public static Date D_01_06_2012 = DefCO.strToDate("01.06.2012");
    public static Date D_01_10_2012 = DefCO.strToDate("01.10.2012");
    public static Date D_01_10_2013 = DefCO.strToDate("01.10.2013");

    private Long getDayCost(ZpMan man, int vt9, int shopCnt)
    {
        List<ZpManValue> mvList = man.getMvList();
        ZpManValue mv = mvList.get(0);
        if (ZpMan.ONE.equals(man.getZlo()))
        {
            ZpAttrValue av = DicManager.getAttrValueMap().get(ZpAttr.ATTR_ZLO);
            return av.getValue();
        }
        if (ZpMan.ONE.equals(man.getSecondJob()))
        {
            ZpAttrValue av = DicManager.getAttrValueMap().get(ZpAttr.ATTR_SECOND_JOB);
            if (D_01_06_2012.compareTo(mv.getSd()) <= 0)
            {
                long smenaSumm = 0;
                if (vt9 == 0) return smenaSumm;
                for (ZpManValue mvc : mvList)
                    if (mvc.getShopId() != null &&
                            ZpManValue.VALUE_TYPE1.equals(mvc.getValueType()) &&
                            ZpMan.ONE.equals(mvc.getActive()))
                    {
                        ZpShop shop = DicManager.getShopMap().get(mvc.getShopId());
                        if (ZpRegion.REGION_MOSCOW.equals(shop.getRegionId()))
                        {
                            ZpAttrValue avMoscow = DicManager.getAttrValueMap().get(ZpAttr.ATTR_SECOND_JOB_MOSCOW);
                            smenaSumm += avMoscow.getValue();//1200l;
                        } else
                        {
                            smenaSumm += av.getValue();//1050l;
                        }
                    }
                return smenaSumm / vt9;

            } else if (D_01_02_2012.compareTo(mv.getSd()) <= 0)
            {
                if (vt9 <= 5) return 1100l;
                if (vt9 <= 10) return 1150l;
                if (vt9 <= 14) return 1200l;
                if (vt9 >= 15) return 1250l;
            }
            return av.getValue();
        } else
        {
            if (D_01_10_2013.compareTo(mv.getSd()) <= 0)
            {
                if (ZpMan.ONE.equals(man.getSpecMobile()))
                        return 1550l;
                else
                if (ZpMan.ONE.equals(man.getMobile()))
                {
                        if (vt9 <= 15) return 1350l;
                        else return 1450l;
                } else
                {
                    if (vt9 <= 20) return 1250l;
                    else return 1350l;
                }
            } else
            if (D_01_10_2012.compareTo(mv.getSd()) <= 0)
            {
                if (shopCnt >= 4)
                {
                    if (ZpMan.ONE.equals(man.getSpecMobile()))
                        return 1550l;
                    else
                    {
                        if (vt9 <= 20) return 1300l;
                        else return 1350l;
                    }
                } else
                {
                    if (vt9 <= 20) return 1250l;
                    else return 1350l;
                }
            } else
            {
                if (DicManager.getAttrValueMap().containsKey(ZpAttr.ATTR_SMENA_25) &&
                        vt9 >= 25)
                {
                    ZpAttrValue av = DicManager.getAttrValueMap().get(ZpAttr.ATTR_SMENA_25);
                    return av.getValue();
                } else if (DicManager.getAttrValueMap().containsKey(ZpAttr.ATTR_SMENA_MOBILE) &&
                        shopCnt >= 4)
                {
                    ZpAttrValue av = DicManager.getAttrValueMap().get(ZpAttr.ATTR_SMENA_MOBILE);
                    return av.getValue();
                } else if (DicManager.getAttrValueMap().containsKey(ZpAttr.ATTR_SMENA_20) &&
                        vt9 >= 20)
                {
                    ZpAttrValue av = DicManager.getAttrValueMap().get(ZpAttr.ATTR_SMENA_20);
                    return av.getValue();
                } else if (DicManager.getAttrValueMap().containsKey(ZpAttr.ATTR_SMENA_15) &&
                        vt9 >= 15)
                {
                    ZpAttrValue av = DicManager.getAttrValueMap().get(ZpAttr.ATTR_SMENA_15);
                    return av.getValue();
                } else if (DicManager.getAttrValueMap().containsKey(ZpAttr.ATTR_SMENA))
                {
                    ZpAttrValue av = DicManager.getAttrValueMap().get(ZpAttr.ATTR_SMENA);
                    return av.getValue();
                } else
                    return null;
            }
        }
    }

    public void setData(List<ZpMan> mList)
    {
        colNames.clear();
        if (mList != null && mList.size() > 0)
        {
            List<ZpManValue> mvList = mList.get(0).getMvList();

            for (ZpManValue mv : mvList)
            {
                if (ZpManValue.VALUE_TYPE1.equals(mv.getValueType()) ||
                        ZpManValue.VALUE_TYPE13.equals(mv.getValueType()))
                {
                    Date sd = mv.getSd();
                    Calendar c = Calendar.getInstance();
                    c.setTime(sd);
                    int d = c.get(Calendar.DAY_OF_MONTH);
                    String prefix = ZpManValue.VALUE_TYPE13.equals(mv.getValueType()) ? "ПЛ. " : "";
                    colNames.add(prefix + String.valueOf(d));
                } else if (ZpManValue.VALUE_TYPE2.equals(mv.getValueType()))
                    colNames.add("штраф");
                else if (ZpManValue.VALUE_TYPE3.equals(mv.getValueType()))
                {
                    Date sd = mv.getSd();
                    Calendar c = Calendar.getInstance();
                    c.setTime(sd);
                    int yyyy = c.get(Calendar.YEAR);
                    int mm = c.get(Calendar.MONTH);
                    // 01.11.2011
                    if (yyyy > 2011 || (mm > 9 && yyyy == 2011))
                        colNames.add("доплата");
                    else
                        colNames.add("дорога");
                } else if (ZpManValue.VALUE_TYPE4.equals(mv.getValueType()))
                    colNames.add("корр.");
                else if (ZpManValue.VALUE_TYPE5.equals(mv.getValueType()))
                    colNames.add("к выплате");
                else if (ZpManValue.VALUE_TYPE6.equals(mv.getValueType()))
                    colNames.add("примечание");
                else if (ZpManValue.VALUE_TYPE7.equals(mv.getValueType()))
                    colNames.add("ведомость");
                else if (ZpManValue.VALUE_TYPE8.equals(mv.getValueType()))
                    colNames.add("смена");
                else if (ZpManValue.VALUE_TYPE9.equals(mv.getValueType()))
                    colNames.add("кол-во");
                else if (ZpManValue.VALUE_TYPE10.equals(mv.getValueType()))
                    colNames.add("выплачено");
                    //else if (ZpManValue.VALUE_TYPE11.equals(mv.getValueType()))
                    //    colNames.add("счёт");
                else if (ZpManValue.VALUE_TYPE12.equals(mv.getValueType()))
                    colNames.add("прошл. мес.");
            }
        }
        super.setData(mList);
        fireTableStructureChanged();
    }

    public Class getColumnClass(int columnIndex)
    {
        if (getList() != null && getList().size() > 0)
        {
            List<ZpManValue> mvList = getList().get(0).getMvList();

            if (columnIndex >= mvList.size()) return String.class;
            ZpManValue mv = mvList.get(columnIndex);
            if (ZpManValue.VALUE_TYPE1.equals(mv.getValueType()) ||
                    ZpManValue.VALUE_TYPE13.equals(mv.getValueType()))
                return ZpShop.class;
            if (ZpManValue.VALUE_TYPE2.equals(mv.getValueType()) ||
                    ZpManValue.VALUE_TYPE3.equals(mv.getValueType()) ||
                    ZpManValue.VALUE_TYPE4.equals(mv.getValueType()) ||
                    ZpManValue.VALUE_TYPE5.equals(mv.getValueType()) ||
                    ZpManValue.VALUE_TYPE8.equals(mv.getValueType()) ||
                    ZpManValue.VALUE_TYPE9.equals(mv.getValueType()) ||
                    ZpManValue.VALUE_TYPE10.equals(mv.getValueType()) ||
                    //ZpManValue.VALUE_TYPE11.equals(mv.getValueType()) ||
                    ZpManValue.VALUE_TYPE12.equals(mv.getValueType()))
                return ZpManValue.class;
            if (ZpManValue.VALUE_TYPE6.equals(mv.getValueType()))
                return ZpManPaid.class;
            if (ZpManValue.VALUE_TYPE7.equals(mv.getValueType()))
                return ZpPaid.class;
        }
        return String.class;
    }

//    public boolean isCellBlock(int row, int column)
//    {
//        ZpManValue mv = getManValue(row, column);
//        return !ZpMan.ONE.equals(mv.getActive());
//    }

    public boolean isCellEditable(int row, int column)
    {
        boolean res = rowEditable != -1 && rowEditable == row;
        if (res)
        {
            if (Connect.isRole(ZpRole.ROLE_CHANGE_SMEN))
            {
                Class c = getColumnClass(column);
                res = (ZpManPaid.class.equals(c) ||
                        ZpPaid.class.equals(c));
            } else res = false;

            if (!res)
            {
                List<ZpManValue> mvList = getObj(row).getMvList();
                ZpManValue mv = mvList.get(column);
                Long vt = mv.getValueType();

                if (!Connect.isRole(ZpRole.ROLE_CHANGE_SMEN))
                {
                    if (Connect.isRole(ZpRole.ROLE_CHANGE_PLAN))
                        return ZpManValue.VALUE_TYPE13.equals(vt);
                    else return false;
                }

                res = (ZpManValue.VALUE_TYPE1.equals(vt) ||
                        ZpManValue.VALUE_TYPE2.equals(vt) ||
                        ZpManValue.VALUE_TYPE3.equals(vt) ||
                        ZpManValue.VALUE_TYPE4.equals(vt) ||
                        ZpManValue.VALUE_TYPE10.equals(vt) ||
                        ZpManValue.VALUE_TYPE13.equals(vt));
            }
        }
        return res;
    }

    public void setValueAt(Object aValue, int rowIndex, int columnIndex)
    {
        Class c = getColumnClass(columnIndex);
        if (ZpManPaid.class.equals(c))
        {
            ZpManPaid mp = (ZpManPaid) getValueAt(rowIndex, columnIndex);
            if (aValue == null)
            {
                mp.setComment(null);
            } else if (!aValue.toString().equals(mp.getComment()))
            {
                mp.setComment(aValue.toString());
            }
        } else if (ZpPaid.class.equals(c))
        {
            ZpPaid p = (ZpPaid) aValue;
            ZpMan man = getObj(rowIndex);
            ZpManPaid mp = man.getManPaid();
            mp.setPaidId(p == null ? null : p.getId());
        } else if (ZpShop.class.equals(c))
        {
            ZpShop shop = (ZpShop) aValue;
            List<ZpManValue> mvList = getObj(rowIndex).getMvList();
            ZpManValue mv = mvList.get(columnIndex);
            if (!ZpMan.ONE.equals(mv.getActive()) ||
                    !CO.myEquals(mv.getShopId(), shop == null ? null : shop.getId()))
            {
                mv.setShopId(shop == null ? null : shop.getId());
                mv.setValue(ZpMan.ONE);
                mv.setActive(ZpMan.ONE);
                mv.setSessionId(Connect.getSessionId());
            }
        } else if (ZpManValue.class.equals(c))
        {
            Long value = null;
            if (aValue != null &&
                    aValue instanceof String &&
                    aValue.toString().trim().length() > 0)
                value = new Long(aValue.toString());

            List<ZpManValue> mvList = getObj(rowIndex).getMvList();
            ZpManValue mv = mvList.get(columnIndex);
            mv.setValue(value);
            mv.setActive(ZpMan.ONE);
            mv.setSessionId(Connect.getSessionId());
        }
        fireTableRowsUpdated(rowIndex, rowIndex);
        fireTableRowsUpdated(rowIndex, rowIndex);
    }

    public void setRowEditable(int rowEditable)
    {
        this.rowEditable = rowEditable;
    }

    public boolean isShopNameVisible()
    {
        return isShopNameVisible;
    }

    public void setShopNameVisible(boolean shopNameVisible)
    {
        isShopNameVisible = shopNameVisible;
    }
}
