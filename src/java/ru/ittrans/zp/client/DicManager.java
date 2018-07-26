package ru.ittrans.zp.client;

import ru.ittrans.zp.client.def.DefCO;
import ru.ittrans.zp.client.lib.ShopPanel;
import ru.ittrans.zp.io.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 18.10.2010
 * Time: 18:27:10
 * To change this template use File | Settings | File Templates.
 */
public class DicManager
{
    private static LinkedHashMap<Long, ZpShop> shopMap =
            new LinkedHashMap<Long, ZpShop>();
    private static LinkedHashMap<Long, ZpShop> parentShopMap =
            new LinkedHashMap<Long, ZpShop>();
    private static LinkedHashMap<Long, ZpPaid> paidMap =
            new LinkedHashMap<Long, ZpPaid>();
    private static LinkedHashMap<Long, ZpAttr> attrMap =
            new LinkedHashMap<Long, ZpAttr>();
    private static LinkedHashMap<Long, ZpAttrValue> attrValueMap =
            new LinkedHashMap<Long, ZpAttrValue>();
    private static LinkedHashMap<Long, ZpBatch> batchMap =
            new LinkedHashMap<Long, ZpBatch>();
    private static LinkedHashMap<Long, ZpOwner> ownerMap =
            new LinkedHashMap<Long, ZpOwner>();
    private static LinkedHashMap<Long, ZpRole> roleMap =
            new LinkedHashMap<Long, ZpRole>();
    private static LinkedHashMap<Long, ZpClient> clientMap =
            new LinkedHashMap<Long, ZpClient>();
    private static LinkedHashMap<Long, ZpRegion> regionMap =
            new LinkedHashMap<Long, ZpRegion>();
    private static LinkedHashMap<Long, ZpShop> deliverMap =
            new LinkedHashMap<Long, ZpShop>();
    private static LinkedHashMap<Long, ZpMetro> metroMap =
            new LinkedHashMap<Long, ZpMetro>();

    public static void preparePeriod(Date period)
    {
        try
        {
            getShopMap(true);

            paidMap.clear();
            List<ZpPaid> pList = Connect.getZpRemote().getPaid(period);
            for (ZpPaid p : pList)
                paidMap.put(p.getId(), p);

            attrMap.clear();
            List<ZpAttr> aList = Connect.getZpRemote().getAttr();
            for (ZpAttr a : aList)
                attrMap.put(a.getId(), a);

            attrValueMap.clear();
            List<ZpAttrValue> avList = Connect.getZpRemote().getAttrValue(period);
            for (ZpAttrValue av : avList)
                attrValueMap.put(av.getAttrId(), av);

            batchMap.clear();
            List<ZpBatch> bList = Connect.getZpRemote().getBatch(period);
            for (ZpBatch b : bList)
                batchMap.put(b.getId(), b);

            ownerMap.clear();
            List<ZpOwner> oList = Connect.getZpRemote().getOwner();
            for (ZpOwner o : oList)
                ownerMap.put(o.getId(), o);

            regionMap.clear();
            List<ZpRegion> rList = Connect.getZpRemote().getRegion();
            for (ZpRegion r : rList)
                regionMap.put(r.getId(), r);
        } catch (Exception ex)
        {
            DefCO.debugException(ex);
        }
    }

    //    private static LinkedHashMap<Long, ZpStatus> sessionMap =
//            new LinkedHashMap<Long, ZpStatus>();
//    private static LinkedHashMap<Long, ZpStatus> quoteMap =
//            new LinkedHashMap<Long, ZpStatus>();
//    private static LinkedHashMap<Long, ZpStatus> purchaseMap =
//            new LinkedHashMap<Long, ZpStatus>();
//    private static LinkedHashMap<Long, ZpStatus> manMap =
//            new LinkedHashMap<Long, ZpStatus>();
//    private static LinkedHashMap<Long, ZpStatus> supplierMap =
//            new LinkedHashMap<Long, ZpStatus>();
//    private static LinkedHashMap<Long, ZpStatus> customerMap =
//            new LinkedHashMap<Long, ZpStatus>();
//
//    private static LinkedHashMap<Long, ZpAttr> roleMap =
//            new LinkedHashMap<Long, ZpAttr>();
//
    public static void initStatusDic()
    {
//        try
//        {
//            HashMap<String, List<ZpStatus>> hmStatus =
//                    Connect.getTpRemote().getAllStatus();
//            HashMap<Long, ZpStatus> hm = null;
//            for (String classname : hmStatus.keySet())
//            {
//                List<ZpStatus> tpList = hmStatus.get(classname);
//                if (ZpSession.NAME.equals(classname))
//                    hm = sessionMap;
//                else if (ZpQuote.NAME.equals(classname))
//                    hm = quoteMap;
//                else if (ZpPurchase.NAME.equals(classname))
//                    hm = purchaseMap;
//                else if (ZpMan.NAME.equals(classname))
//                    hm = manMap;
//                else if (ZpOwner.NAME.equals(classname))
//                    hm = supplierMap;
//                else if (ZpCustomer.NAME.equals(classname))
//                    hm = customerMap;
//
//                for (ZpStatus status : tpList)
//                    hm.put(status.getId(), status);
//
//            }
//
//            for (ZpShop shop : getShop())
//            {
//                shopMap.put(shop.getId(), shop);
//            }
//
//            List<ZpAttr> rList = Connect.getTpRemote().getRole();
//            for (ZpAttr r : rList)
//                roleMap.put(r.getId(), r);
//        }
//        catch (Exception ex)
//        {
//            DefCO.debugException(ex);
//        }
        try
        {
            List<ZpRole> rList = Connect.getZpRemote().getRole();
            roleMap.clear();
            for (ZpRole r : rList)
                roleMap.put(r.getId(), r);

            List<ZpClient> cList = Connect.getZpRemote().getClient();
            clientMap.clear();
            for (ZpClient c : cList)
                clientMap.put(c.getId(), c);

            List<ZpMetro> mList = Connect.getZpRemote().getMetro();
            metroMap.clear();
            for (ZpMetro c : mList)
                metroMap.put(c.getId(), c);
        } catch (Exception ex)
        {
            DefCO.debugException(ex);
        }
    }

    //
//    public static ZpStatus getSessionStatus(Long statusId)
//    {
//        return sessionMap.get(statusId);
//    }
//
//    public static ZpStatus getQuoteStatus(Long statusId)
//    {
//        return quoteMap.get(statusId);
//    }
//
//    public static ZpStatus getPurchaseStatus(Long statusId)
//    {
//        return purchaseMap.get(statusId);
//    }
//
//    public static ZpStatus getManStatus(Long statusId)
//    {
//        return manMap.get(statusId);
//    }
//
//    public static LinkedHashMap<Long, ZpStatus> getSessionMap()
//    {
//        return sessionMap;
//    }
//
//    public static LinkedHashMap<Long, ZpStatus> getQuoteMap()
//    {
//        return quoteMap;
//    }
//
//    public static LinkedHashMap<Long, ZpStatus> getPurchaseMap()
//    {
//        return purchaseMap;
//    }
//
//    public static LinkedHashMap<Long, ZpStatus> getManMap()
//    {
//        return manMap;
//    }
//
//    public static LinkedHashMap<Long, ZpStatus> getSupplierMap()
//    {
//        return supplierMap;
//    }
//
//    public static LinkedHashMap<Long, ZpStatus> getCustomerMap()
//    {
//        return customerMap;
//    }
//

    public static LinkedHashMap<Long, ZpShop> getShopMap()
    {
        Long parentShopId = Connect.getParentShopId();
        if (parentShopId != null)
        {
            LinkedHashMap<Long, ZpShop> res = new LinkedHashMap<Long, ZpShop>();
            for (ZpShop shop : shopMap.values())
                if (parentShopId.equals(shop.getParentId()))
                    res.put(shop.getId(), shop);
            return res;
        }
        return shopMap;
    }

//    public static LinkedHashMap<Long, ZpShop> getShopPremissionMap()
//    {
//        Long parentShopId = Connect.getParentShopId();
//        if (parentShopId != null)
//        {
//            LinkedHashMap<Long, ZpShop> res = new LinkedHashMap<Long, ZpShop>();
//            for (ZpShop shop : shopMap.values())
//                if (parentShopId.equals(shop.getParentId()))
//                    res.put(shop.getId(), shop);
//            return res;
//        }
//        return shopMap;
//    }

    public static LinkedHashMap<Long, ZpShop> getParentShopMap()
    {
        return parentShopMap;
    }

    public static LinkedHashMap<Long, ZpShop> getShopMap(boolean isDownLoad) throws Exception
    {
        if (isDownLoad)
        {
            shopMap.clear();
            parentShopMap.clear();
            List<ZpShop> sList = Connect.getZpRemote().getShop();
            for (ZpShop s : sList)
            {
                if (s.getParentId() == null)
                    parentShopMap.put(s.getId(), s);
                else
                    shopMap.put(s.getId(), s);
            }
        }
        return shopMap;
    }

    public static LinkedHashMap<Long, ZpPaid> getPaidMap()
    {
        return paidMap;
    }

    public static LinkedHashMap<Long, ZpPaid> getPaidDMap()
    {
        LinkedHashMap<Long, ZpPaid> res =
                new LinkedHashMap<Long, ZpPaid>();
        List<Date> dList = new ArrayList<Date>();
        for (ZpPaid p : getPaidMap().values())
            if (p.getD() != null && !dList.contains(p.getD()))
            {
                dList.add(p.getD());

                ZpPaid pD = new ZpPaid();
                pD.setId(p.getId());
                pD.setName(DefCO.dateToStr(p.getD()));
                pD.setD(p.getD());

                res.put(pD.getId(), pD);
            }

        return res;
    }

    public static LinkedHashMap<Long, ZpAttr> getAttrMap()
    {
        return attrMap;
    }

    public static LinkedHashMap<Long, ZpAttrValue> getAttrValueMap()
    {
        return attrValueMap;
    }

    public static LinkedHashMap<Long, ZpBatch> getBatchMap()
    {
        return batchMap;
    }

    public static LinkedHashMap<Long, ZpOwner> getOwnerMap()
    {
        return ownerMap;
    }

    public static Long isShopCodeExist(String code)
    {
        if (code == null) return null;
        for (Long shopId : getShopMap().keySet())
        {
            ZpShop shop = getShopMap().get(shopId);
            if (shop.getCode() == null) continue;
            if (code.equals(shop.getCode())) return shop.getId();
        }
        return null;
    }

    public static LinkedHashMap<Long, ZpRole> getRoleMap()
    {
        return roleMap;
    }

    public static LinkedHashMap<Long, ZpClient> getClientMap()
    {
        return clientMap;
    }

    public static LinkedHashMap<Long, ZpRegion> getRegionMap()
    {
        return regionMap;
    }

    public static LinkedHashMap<Long, ZpShop> getDeliverMap()
    {
        if (deliverMap.isEmpty())
        {
            ZpShop zp0 = new ZpShop();
            zp0.setId(ZpMan.ZERO);
            zp0.setCode(ShopPanel.MES_DELIVER[0]);

            ZpShop zp1 = new ZpShop();
            zp1.setId(ZpMan.ONE);
            zp1.setCode(ShopPanel.MES_DELIVER[1]);

            deliverMap.put(ZpMan.ZERO, zp0);
            deliverMap.put(ZpMan.ONE, zp1);
        }

        return deliverMap;
    }

    public static LinkedHashMap<Long, ZpMetro> getMetroMap()
    {
        return metroMap;
    }
}
