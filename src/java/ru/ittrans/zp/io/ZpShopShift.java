package ru.ittrans.zp.io;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 15.12.11
 * Time: 12:09
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "ZP_SHOP_SHIFT")
public class ZpShopShift extends ZpAbstract
{
    private Long id;
    private Long shopId;
    private Date period;
    private Long value;

    public ZpShopShift()
    {
    }

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    @Column(name = "SHOP_ID")
    public Long getShopId()
    {
        return shopId;
    }

    public void setShopId(Long shopId)
    {
        this.shopId = shopId;
    }

    public Date getPeriod()
    {
        return period;
    }

    public void setPeriod(Date period)
    {
        this.period = period;
    }

    public Long getValue()
    {
        return value;
    }

    public void setValue(Long value)
    {
        this.value = value;
    }

    @Transient
    public String getName()
    {
        return null;
    }
}
