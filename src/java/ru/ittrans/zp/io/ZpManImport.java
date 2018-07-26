package ru.ittrans.zp.io;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 05.05.11
 * Time: 13:28
 * To change this template use File | Settings | File Templates.
 */
public class ZpManImport implements Serializable
{
    public static String SEARCH_TYPE_A = "À";
    public static String SEARCH_TYPE_P = "Ð";
    public static String SEARCH_TYPE_F = "Ô";

    private Long shopId;
    private ZpMan man;
    private ZpMan dbMan;
    private String searchType;

    public Long getShopId()
    {
        return shopId;
    }

    public void setShopId(Long shopId)
    {
        this.shopId = shopId;
    }

    public ZpMan getMan()
    {
        return man;
    }

    public void setMan(ZpMan man)
    {
        this.man = man;
    }

    public ZpMan getDbMan()
    {
        return dbMan;
    }

    public void setDbMan(ZpMan dbMan)
    {
        this.dbMan = dbMan;
    }

    public String getSearchType()
    {
        return searchType;
    }

    public void setSearchType(String searchType)
    {
        this.searchType = searchType;
    }
}
