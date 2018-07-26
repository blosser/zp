package ru.ittrans.zp.io;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by IntelliJ IDEA.
 * User: blohin
 * Date: 13.10.2010
 * Time: 13:13:49
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name = "ZP_ATTR_VALUE")
public class ZpAttrValue extends ZpAbstract
{
    private Long id;
    private Long attrId;
    private Date sd;
    private Date fd;
    private Long value;

    public ZpAttrValue()
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

    @Column(name = "ATTR_ID")
    public Long getAttrId()
    {
        return attrId;
    }

    public void setAttrId(Long attrId)
    {
        this.attrId = attrId;
    }

    public Date getSd()
    {
        return sd;
    }

    public void setSd(Date sd)
    {
        this.sd = sd;
    }

    public Date getFd()
    {
        return fd;
    }

    public void setFd(Date fd)
    {
        this.fd = fd;
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
