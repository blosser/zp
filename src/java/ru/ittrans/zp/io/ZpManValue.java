package ru.ittrans.zp.io;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 13.10.2010
 * Time: 13:13:35
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name = "ZP_MAN_VALUE")
public class ZpManValue extends ZpAbstract
{
    // данные
    public static Long VALUE_TYPE1 = 1l;
    // штраф
    public static Long VALUE_TYPE2 = 2l;
    // дорога
    public static Long VALUE_TYPE3 = 3l;
    // коррекция
    public static Long VALUE_TYPE4 = 4l;
    // к выплате
    public static Long VALUE_TYPE5 = 5l;
    // примечание
    public static Long VALUE_TYPE6 = 6l;
    // ведомость
    public static Long VALUE_TYPE7 = 7l;
    // смена
    public static Long VALUE_TYPE8 = 8l;
    // кол-во
    public static Long VALUE_TYPE9 = 9l;
    // выплачено
    public static Long VALUE_TYPE10 = 10l;
    // счёт
    //public static Long VALUE_TYPE11 = 11l;
    // прошл. мес. выплачено
    public static Long VALUE_TYPE12 = 12l;
    // план
    public static Long VALUE_TYPE13 = 13l;

    private Long id;
    private Long shopId;
    private Long manId;
    private Date sd;
    private Long valueType;
    private Long value;
    private Long batchId;
    private Long active;
    private Long sessionId;
    private String shopCode;
    private String batchName;
    private String comment;
    private ZpMan man;

    public ZpManValue()
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

    @Column(name = "MAN_ID")
    public Long getManId()
    {
        return manId;
    }

    public void setManId(Long manId)
    {
        this.manId = manId;
    }

    public Date getSd()
    {
        return sd;
    }

    public void setSd(Date sd)
    {
        this.sd = sd;
    }

    @Column(name = "VALUE_TYPE")
    public Long getValueType()
    {
        return valueType;
    }

    public void setValueType(Long valueType)
    {
        this.valueType = valueType;
    }

    public Long getValue()
    {
        return value;
    }

    public void setValue(Long value)
    {
        this.value = value;
    }

    @Column(name = "BATCH_ID")
    public Long getBatchId()
    {
        return batchId;
    }

    public void setBatchId(Long batchId)
    {
        this.batchId = batchId;
    }

    public Long getActive()
    {
        return active;
    }

    public void setActive(Long active)
    {
        this.active = active;
    }

    @Column(name = "SESSION_ID")
    public Long getSessionId()
    {
        return sessionId;
    }

    public void setSessionId(Long sessionId)
    {
        this.sessionId = sessionId;
    }

    //Column(name = "SHOP_CODE")
    @Transient
    public String getShopCode()
    {
        return shopCode;
    }

    public void setShopCode(String shopCode)
    {
        this.shopCode = shopCode;
    }

    //Column(name = "BATCH_NAME")
    @Transient
    public String getBatchName()
    {
        return batchName;
    }

    public void setBatchName(String batchName)
    {
        this.batchName = batchName;
    }

    @Transient
    public String getComment()
    {
        return comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }

    @Transient
    public String getName()
    {
        return null;
    }

    @Transient
    public ZpMan getMan()
    {
        return man;
    }

    public void setMan(ZpMan man)
    {
        this.man = man;
    }

    @Override
    public String toString()
    {
        return getValue() == null ? null : getValue().toString();
    }
}
